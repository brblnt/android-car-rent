package br.blnt.carrent.screens

import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.blnt.carrent.R
import br.blnt.carrent.cars
import br.blnt.carrent.drawRes
import br.blnt.carrent.model.Car


class RentCarsScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_cars_screen)

        val profileIntent = Intent(this@RentCarsScreen, ProfileScreen::class.java)
        val myCarsIntent = Intent(this@RentCarsScreen, UserPageScreen::class.java)
        val rentButton : Button = findViewById(R.id.rentCar)

        var index = 0
        var actCar = cars[index]
        changeCar(actCar, rentButton)

        val navLeft: Button = findViewById(R.id.buttonLeft)
        val navRight: Button = findViewById(R.id.buttonRight)
        val rentCar: Button = findViewById(R.id.rentCar)

        rentCar.setOnClickListener{
            br.blnt.carrent.rentCar(actCar.id)
            val ad = AlertDialog.Builder(this)
            ad.setTitle(getString(R.string.rent))
            ad.setMessage(getString(R.string.rent_success))
            ad.setPositiveButton(getString(R.string.ok), DialogInterface.OnClickListener{
                    dialogInterface, i ->
                finish()
                startActivity(intent)
            })
            ad.show()

        }

        navLeft.setOnClickListener{
            if (index > 0 ) {
                index -= 1
            } else if ( index == 0) {
                index = 0
                val mp = MediaPlayer.create(this, R.raw.drip2).start()
            }
            actCar = cars[index]
            changeCar(actCar, rentButton)


        }
        navRight.setOnClickListener{
            if (index < cars.size-1) {
                index += 1
            } else if ( index == cars.size-1) {
                index = cars.size-1
                val mp = MediaPlayer.create(this, R.raw.drip2).start()
            }
            actCar = cars[index]
            changeCar(actCar, rentButton)
        }


        val myCars: Button = findViewById(R.id.myCars)
        myCars.setOnClickListener{
            startActivity(myCarsIntent)
            finish()
        }

        val back: Button = findViewById(R.id.back)
        back.setOnClickListener{
            startActivity(profileIntent)
            finish()
        }
    }

    private fun changeCar(actCar: Car, rentButton: Button) {
        val carPic : ImageView = findViewById(R.id.carView)
        val name : TextView = findViewById(R.id.textName)
        val price : TextView = findViewById(R.id.textPrice)
        val status : TextView = findViewById(R.id.textStatus)
        carPic.setImageResource(drawRes(actCar))
        val priceDisplay = "" + actCar.basePrice + " Ft + " +actCar.dayPrice + " Ft / nap"
        name.text = actCar.carName
        price.text = priceDisplay
        if (actCar.available == "na") {
            rentButton.isEnabled = true
            status.text = getString(R.string.avaliable)
            status.setTextColor(Color.parseColor("#00FF00"))
        } else {
            rentButton.isEnabled = false
            status.text = getString(R.string.avaliable_non)
            status.setTextColor(Color.parseColor("#FF0000"))
        }
    }



}