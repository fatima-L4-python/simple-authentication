package com.example.basicauthentication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.basicauthentication.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var textViewLoginLink: TextView
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREF_NAME = "UserPrefs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        // If user is already registered, go to login
        if (isUserRegistered()) {
            redirectToLogin()
        }

        // Initialize UI elements
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        textViewLoginLink = findViewById(R.id.textViewLoginLink)

        // Set click listener for Sign Up button
        buttonSignUp.setOnClickListener {
            registerUser()
        }

        // Set click listener for Login link
        textViewLoginLink.setOnClickListener {
            redirectToLogin()
        }
    }

    private fun isUserRegistered(): Boolean {
        // Check if username exists in SharedPreferences
        return sharedPreferences.contains("username")
    }

    private fun registerUser() {
        // Get values from EditTexts
        val firstName = editTextFirstName.text.toString().trim()
        val lastName = editTextLastName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        // Validate input fields
        when {
            TextUtils.isEmpty(firstName) -> {
                editTextFirstName.error = "First name is required"
                return
            }
            TextUtils.isEmpty(lastName) -> {
                editTextLastName.error = "Last name is required"
                return
            }
            TextUtils.isEmpty(email) -> {
                editTextEmail.error = "Email is required"
                return
            }
            TextUtils.isEmpty(username) -> {
                editTextUsername.error = "Username is required"
                return
            }
            TextUtils.isEmpty(password) -> {
                editTextPassword.error = "Password is required"
                return
            }
        }

        // Save user data to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("firstName", firstName)
        editor.putString("lastName", lastName)
        editor.putString("email", email)
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()

        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
        redirectToLogin()
    }

    private fun redirectToLogin() {
        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}