package com.example.basicauthentication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.basicauthentication.R

class WelcomeActivity : AppCompatActivity() {

    private lateinit var textViewUserInfo: TextView
    private lateinit var buttonLogout: Button
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREF_NAME = "UserPrefs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        // Initialize UI elements
        textViewUserInfo = findViewById(R.id.textViewUserInfo)
        buttonLogout = findViewById(R.id.buttonLogout)

        // Display user information
        displayUserInfo()

        // Set click listener for Logout button
        buttonLogout.setOnClickListener {
            logout()
        }
    }

    private fun displayUserInfo() {
        // Get user information from SharedPreferences
        val firstName = sharedPreferences.getString("firstName", "") ?: ""
        val lastName = sharedPreferences.getString("lastName", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""
        val username = sharedPreferences.getString("username", "") ?: ""

        // Display user information
        val userInfo = """
            Name: $firstName $lastName
            Email: $email
            Username: $username
        """.trimIndent()

        textViewUserInfo.text = userInfo
    }

    private fun logout() {
        // Clear user session (optional: you could also clear SharedPreferences here if you want to erase user data)
        val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}