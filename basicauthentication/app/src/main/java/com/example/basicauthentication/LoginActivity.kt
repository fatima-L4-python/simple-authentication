package com.example.basicauthentication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.basicauthentication.R

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextLoginUsername: EditText
    private lateinit var editTextLoginPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewSignUpLink: TextView
    private lateinit var textViewResult: TextView
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREF_NAME = "UserPrefs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        // Initialize UI elements
        editTextLoginUsername = findViewById(R.id.editTextLoginUsername)
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewSignUpLink = findViewById(R.id.textViewSignUpLink)
        textViewResult = findViewById(R.id.textViewResult)

        // Set click listener for Login button
        buttonLogin.setOnClickListener {
            loginUser()
        }

        // Set click listener for Sign Up link
        textViewSignUpLink.setOnClickListener {
            redirectToSignUp()
        }
    }

    private fun loginUser() {
        // Get values from EditTexts
        val usernameOrEmail = editTextLoginUsername.text.toString().trim()
        val password = editTextLoginPassword.text.toString().trim()

        // Validate input fields
        when {
            TextUtils.isEmpty(usernameOrEmail) -> {
                editTextLoginUsername.error = "Username or Email is required"
                return
            }
            TextUtils.isEmpty(password) -> {
                editTextLoginPassword.error = "Password is required"
                return
            }
        }

        // Get stored values from SharedPreferences
        val storedUsername = sharedPreferences.getString("username", "") ?: ""
        val storedEmail = sharedPreferences.getString("email", "") ?: ""
        val storedPassword = sharedPreferences.getString("password", "") ?: ""

        // Check if login is successful
        val isUsernameOrEmailMatch = usernameOrEmail == storedUsername || usernameOrEmail == storedEmail
        val isPasswordMatch = password == storedPassword

        if (isUsernameOrEmailMatch && isPasswordMatch) {
            // Login successful, redirect to welcome screen
            redirectToWelcome()
        } else {
            // Show error message indicating what mismatches
            if (!isUsernameOrEmailMatch) {
                textViewResult.text = "Username or Email does not match"
                textViewResult.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            } else if (!isPasswordMatch) {
                textViewResult.text = "Password does not match"
                textViewResult.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }
        }
    }

    private fun redirectToSignUp() {
        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun redirectToWelcome() {
        val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}