package com.example.pokego_tools

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        listenNameGiver()
    }


    private fun listenNameGiver() {
        val buttonNameGiver = findViewById<Button>(R.id.button_name_giver)

        buttonNameGiver.setOnClickListener {
            switchToNameGiver()
        }
    }

    private fun switchToNameGiver() {
        val switchActivity = Intent(this, NameGiver::class.java);
        startActivity(switchActivity)
    }

}

