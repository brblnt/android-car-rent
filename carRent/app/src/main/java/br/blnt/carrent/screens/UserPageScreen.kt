package br.blnt.carrent.screens

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import br.blnt.carrent.*
import br.blnt.carrent.model.Car

class UserPageScreen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_page_screen)

        val profileIntent = Intent(this@UserPageScreen, ProfileScreen::class.java)
        val rentCarsIntent = Intent(this@UserPageScreen, RentCarsScreen::class.java)

        val rentCars: Button = findViewById(R.id.carsRent)
        rentCars.setOnClickListener{
            startActivity(rentCarsIntent)
            finish()
        }

        val back: Button = findViewById(R.id.back)
        back.setOnClickListener{
            startActivity(profileIntent)
            finish()
        }

        val carPic : ImageView = findViewById(R.id.imageViewUser)
        val name : TextView = findViewById(R.id.textCarType)
        val price : TextView = findViewById(R.id.textCarPrice)
        val date : TextView = findViewById(R.id.textDate)
        val pay : TextView = findViewById(R.id.textUserPrice)
        val navLeft: Button = findViewById(R.id.buttonUserNavLeft)
        val navRight: Button = findViewById(R.id.buttonUserNavRight)
        val endRent: Button = findViewById(R.id.buttonGiveBack)

        if (userCars.size > 0) {
            var index = 0
            var actCar = userCars[index]
            changeCar(actCar, carPic, date, name, price, pay)


            endRent.setOnClickListener{
                giveBackCar(actCar.id)
                finish()
                startActivity(intent)
            }

            navLeft.setOnClickListener{
                if (index > 0 ) {
                    index -= 1
                } else if ( index == 0) {
                    index = 0
                    val mp = MediaPlayer.create(this, R.raw.drip2).start()
                }
                actCar = userCars[index]
                changeCar(actCar, carPic, date, name, price, pay)


            }
            navRight.setOnClickListener{
                if (index < userCars.size-1) {
                    index += 1
                } else if ( index == userCars.size-1) {
                    index = userCars.size-1
                    val mp = MediaPlayer.create(this, R.raw.drip2).start()
                }
                actCar = userCars[index]
                changeCar(actCar, carPic, date, name, price, pay)
            }
        } else {
            date.text = getString(R.string.na)
            pay.text = getString(R.string.na)
            name.text = getString(R.string.na)
            price.text = getString(R.string.na)
            navLeft.setOnClickListener{
                val mp = MediaPlayer.create(this, R.raw.drip2).start()
            }
            navRight.setOnClickListener{
                val mp = MediaPlayer.create(this, R.raw.drip2).start()
            }
        }


    }

    private fun changeCar(actCar: Car, carPic: ImageView, date: TextView,
                          name : TextView, price: TextView, pay: TextView) {
        date.text = getDateStart(getActualRent(actUserId, actCar.id))
        carPic.setImageResource(drawRes(actCar))
        val priceDisplay = "" + actCar.basePrice + " Ft + " +actCar.dayPrice + " Ft / nap"
        name.text = actCar.carName
        price.text = priceDisplay
        pay.text = getPay(actUserId, actCar.id).toString() + " Ft"
    }
}