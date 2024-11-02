package com.example.pokego_tools

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class TypeEfficiency : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.type_efficiency)

        listenClose()
    }

    private fun listenClose() {
        val btnClose = findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            val home = Intent(this, MainMenu::class.java)
            startActivity(home)
        }
    }

}