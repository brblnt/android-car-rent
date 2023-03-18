package br.blnt.carrent.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import br.blnt.carrent.R
import br.blnt.carrent.login
import com.google.android.material.textfield.TextInputEditText

class LoginScreen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)


        val registerIntent = Intent(this@LoginScreen, RegisterScreen::class.java)
        val userIntent = Intent(this@LoginScreen, ProfileScreen::class.java)

        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)
        val inputUsername: TextInputEditText = findViewById(R.id.usernameInput)
        val inputPassword: TextInputEditText = findViewById(R.id.passwordInput)

        loginButton.setOnClickListener{

            if (login(inputUsername.text.toString(), inputPassword.text.toString()) == -1)
                Toast.makeText(applicationContext,
                    getString(R.string.wrong_username_or_password),Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(applicationContext,
                    getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                startActivity(userIntent)
                finish()
            }
        }

        registerButton.setOnClickListener{
            startActivity(registerIntent)
            finish()
        }

    }




}