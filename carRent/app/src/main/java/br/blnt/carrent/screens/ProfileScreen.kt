package br.blnt.carrent.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import br.blnt.carrent.R
import br.blnt.carrent.exit

class ProfileScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)

        val myCarsIntent = Intent(this@ProfileScreen, UserPageScreen::class.java)
        val rentCarsIntent = Intent(this@ProfileScreen, RentCarsScreen::class.java)
        val statIntent = Intent(this@ProfileScreen, StatisticScreen::class.java)
        val loginIntent = Intent(this@ProfileScreen, LoginScreen::class.java)

        val myCars: Button = findViewById(R.id.myCars)
        myCars.setOnClickListener{
            startActivity(myCarsIntent)
            finish()
        }

        val rentCars: Button = findViewById(R.id.rentCars)
        rentCars.setOnClickListener{
            startActivity(rentCarsIntent)
            finish()
        }

        val economic: Button = findViewById(R.id.stat)
        economic.setOnClickListener{
            startActivity(statIntent)
            finish()
        }

        val exitButton: Button = findViewById(R.id.exit)
        exitButton.setOnClickListener{
            exit()
            startActivity(loginIntent)
            finish()
        }
    }
}