package com.example.pokego_tools

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var statsLayout: EditText
    private lateinit var ivsLayout: EditText
    private lateinit var shadowLayout: CheckBox
    private lateinit var nameLayout: EditText

    private var shadow: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }


    private fun listenCheckShadow() {
        shadowLayout.setOnCheckedChangeListener { _, isChecked ->
            shadow = isChecked
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

        val averageDefHP: Double = (overall[1] + overall[2]) / 2
        val diffDefHP: String = diffFormated(overall[1], overall[2])

//        val text = "[${statsStr}] + [${ivsStr}] + [${shadow}]"
        val text = "${overall[0].toInt()}(${ivs[0]})(${ivs[1]})(${ivs[2]})${averageDefHP.toInt()}${diffDefHP}"

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


    private fun diffFormated(a:Double, b:Double) : String {
        var res = (a - b) / 2
        var symbol = ">"
        if (res < 0) {
            symbol = "<"
            res = res - res - res
        }
        return "${symbol}${res.toInt()}"
    }


    private fun strListToIntList (l: List<String>) : List<Int> {
        val res: MutableList<Int> = mutableListOf()
        val i = l.listIterator()
        i.forEach { v -> res.add(v.toInt())}
        return res
    }
}