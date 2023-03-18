package br.blnt.carrent.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import br.blnt.carrent.R
import br.blnt.carrent.persistance.DB
import br.blnt.carrent.setup

/**
 * Loading Screen.
 * First screen of the app. Setup db and execute setup service.
 */
class LoadingScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)


        val animationFade = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val loginIntent = Intent(this@LoadingScreen, LoginScreen::class.java)
        val topTextView: TextView = findViewById(R.id.loadingTop)
        val bottomTextView: TextView = findViewById(R.id.loadingBottom)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val helper = DB(applicationContext)
        val db = helper.readableDatabase

        topTextView.startAnimation(animationFade)
        bottomTextView.startAnimation(animationFade)

        setup(db)

        var i = progressBar.progress
        while (i < 100) {
            i += 1
            Handler().postDelayed({
                progressBar.progress = i
            }, 1000)

        }

        Handler().postDelayed({
            startActivity(loginIntent)
            finish()
        }, 1000)

    }
}