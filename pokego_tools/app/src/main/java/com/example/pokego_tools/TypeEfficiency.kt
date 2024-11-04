package com.example.pokego_tools

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity


class TypeEfficiency : AppCompatActivity() {
    private var types: MutableList<Boolean> = mutableListOf(false)
    private lateinit var btnsLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.type_efficiency)

        listenClose()
        listenTypeBtns()
    }

    private fun listenTypeBtns() {
        btnsLayout = findViewById(R.id.btns_layout)
        val imgsBtns = mutableListOf<ImageButton>()

        for (i in 0 until btnsLayout.childCount) {
            val child = btnsLayout.getChildAt(i)
            // Check if the child is a LinearLayout
            if (child is LinearLayout) {
                // Loop through the children of the inner LinearLayout
                for (j in 0 until child.childCount) {
                    val innerChild = child.getChildAt(j)
                    // Check if the inner child is an ImageButton and add to list
                    if (innerChild is ImageButton) {
                        imgsBtns.add(innerChild)
                    }
                }
            }
        }
    }

    private fun listenClose() {
        val btnClose = findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            val home = Intent(this, MainMenu::class.java)
            startActivity(home)
        }
    }

}