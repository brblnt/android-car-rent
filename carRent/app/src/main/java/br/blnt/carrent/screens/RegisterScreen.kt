package br.blnt.carrent.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import br.blnt.carrent.R
import br.blnt.carrent.addUser
import br.blnt.carrent.userExist
import com.google.android.material.textfield.TextInputEditText

class RegisterScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)


        val registerIntent = Intent(this@RegisterScreen, LoginScreen::class.java)
        val loginIntent = Intent(this@RegisterScreen, LoginScreen::class.java)

        val inputUsername: TextInputEditText = findViewById(R.id.usernameInputRegister)
        val inputPassword: TextInputEditText = findViewById(R.id.passwordInputRegister)
        val inputPasswordRe: TextInputEditText = findViewById(R.id.passwordInputRegisterRe)

        val registerButton: Button = findViewById(R.id.registratiton)
        val backButton: Button = findViewById(R.id.backButton)

        backButton.setOnClickListener{
            startActivity(loginIntent)
            finish()
        }

        registerButton.setOnClickListener{
            if (inputPassword.text.toString() == inputPasswordRe.text.toString()) {
                if (!userExist(inputUsername.text.toString())) {
                    addUser(inputUsername.text.toString(), inputPassword.text.toString())
                    Toast.makeText(applicationContext,
                        getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                    startActivity(registerIntent)
                    finish()
                } else {
                    Toast.makeText(applicationContext,
                        getString(R.string.register_user_exist), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext,
                    getString(R.string.register_password_missmatch), Toast.LENGTH_SHORT).show()
            }
        }

    }


}