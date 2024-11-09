package com.example.pokego_tools

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class NameGiver : AppCompatActivity() {
    private lateinit var statsLayout: EditText
    private lateinit var ivsLayout: EditText
    private lateinit var shadowLayout: CheckBox
    private lateinit var nameLayout: EditText

    private var shadow: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.name_giver)
//        enableEdgeToEdge()
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        statsLayout = findViewById(R.id.editText_stats)
        ivsLayout = findViewById(R.id.editText_ivs)
        shadowLayout = findViewById(R.id.checkbox_shadow)
        nameLayout = findViewById(R.id.editText_validate)

        listenCheckShadow()
        listenValidation()
        listenClearAll()
        listenClose()
        listenFocuses()
    }

    private fun listenClose() {
        val btnClose = findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            val home = Intent(this, MainMenu::class.java)
            startActivity(home)
        }
    }


    private fun listenCheckShadow() {
        shadowLayout.setOnCheckedChangeListener { _, isChecked ->
            shadow = isChecked
        }
    }


    private fun listenClearAll() {
        val btnClear = findViewById<Button>(R.id.btn_clear_all)
        btnClear.setOnClickListener {
            statsLayout.setText("")
            ivsLayout.setText("")
            nameLayout.setText("")
            shadowLayout.isChecked = false
            shadow = false
        }
    }

    private fun listenFocuses() {
        statsLayout.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { statsLayout.setText("") }

        }
        ivsLayout.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) { ivsLayout.setText("") }

        }
    }

    private fun listenValidation() {
        val buttonValidate = findViewById<Button>(R.id.button_validate)

        buttonValidate.setOnClickListener {
            updateName()
        }
    }


    private fun updateName() {
        val statsStr = statsLayout.text.toString()
        val ivsStr = ivsLayout.text.toString()

        val stats = strListToIntList(statsStr.split(" "))
        val ivs = strListToIntList(ivsStr.split(" "))
        val overall = mergeLists(stats, ivs, shadow)


//        val text = "[${statsStr}] + [${ivsStr}] + [${shadow}]"
        val text = "${overall[0].toInt()}(${ivs[0]})(${ivs[1]})(${ivs[2]})"

//        nameLayout.text = "[${stats}] [${ivs}] [${shadow}]"
        nameLayout.text = Editable.Factory.getInstance().newEditable(text)
    }


    private fun mergeLists (l1: List<Int>, l2: List<Int>, shadow: Boolean) : List<Double> {
        val res: MutableList<Double> = mutableListOf()
        for (i in 0 until 3) {
            res.add((l1[i] + l2[i]).toDouble())
        }

        if (shadow) {
            res[0] = res[0] * 1.2
            res[1] = res[1] * 0.8333333
        }

        return res

    }


    private fun strListToIntList (l: List<String>) : List<Int> {
        val res: MutableList<Int> = mutableListOf()
        val i = l.listIterator()
        i.forEach { v -> res.add(v.toInt())}
        return res
    }
}