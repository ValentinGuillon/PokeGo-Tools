package com.example.pokego_tools

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import kotlin.math.sqrt


class TypeRelations : AppCompatActivity() {
    private lateinit var resistances: HashMap<String, HashMap<String, List<String>>>
    private lateinit var efficiencies: HashMap<String, HashMap<String, List<String>>>
    private lateinit var activeTypes: HashMap<String, Boolean>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.type_relations)

        // Initialize resistances and efficiencies
        resistances = Resistance.initResistances()
        efficiencies = Efficiency.initEfficiencies()

        // Set listeners for buttons
        listenClose()
        listenTypeButtons()

        // Clear layouts placed as visual representation while coding
        clearResEff()
    }


    // Replace resistances and efficiencies based on active types
    private fun updateOutputs() {
        clearResEff()
        updateResistances()
        updateEfficiencies()
    }


    // Determine resistances, then creates and places layouts
    private fun updateResistances() {
        // Get resistances of active types
        val relativeResistances = Resistance.determineResistances(activeTypes, resistances)
        var grid:GridLayout

        // Place then on the corresponding grid
        // double weakness
        grid = findViewById(R.id.super_weak)
        relativeResistances["doubleWeakness"]?.let { addChildrenToGrid(grid, it) }
        // weakness
        grid = findViewById(R.id.very_weak)
        relativeResistances["weakness"]?.let { addChildrenToGrid(grid, it) }
        // resistant
        grid = findViewById(R.id.very_resistant)
        relativeResistances["resistance"]?.let { addChildrenToGrid(grid, it) }
        // immunity
        grid = findViewById(R.id.super_resistance)
        relativeResistances["immunity"]?.let { addChildrenToGrid(grid, it) }
        // double immunity
        grid = findViewById(R.id.immune)
        relativeResistances["doubleImmunity"]?.let { addChildrenToGrid(grid, it) }

        // implemented, but not used category: tripleImmunity
        //Log.e(relativeResistances.keys.toString(), relativeResistances["tripleImmunity"].toString())
    }


    //Determine efficiencies, then creates and places layouts
    private fun updateEfficiencies() {
        // Execute only if ONE unique type is activated
        if (countActiveButtons() != 1) {
            return
        }

        // Get efficiencies of active types
        val relativeEfficiencies = Efficiency.determineEfficiencies(activeTypes, efficiencies)
        var grid:GridLayout

        // Place then on the corresponding grid
        // effectiveness
        grid = findViewById(R.id.super_effective)
        relativeEfficiencies["effective"]?.let { addChildrenToGrid(grid, it) }

        // notEffectiveness
        grid = findViewById(R.id.not_effective)
        relativeEfficiencies["notEffective"]?.let { addChildrenToGrid(grid, it) }

        // ineffectiveness
        grid = findViewById(R.id.ineffective)
        relativeEfficiencies["ineffective"]?.let { addChildrenToGrid(grid, it) }

    }

    // Add an ImageView in the grid for each given types
    private fun addChildrenToGrid(grid: GridLayout, types: List<String>) {
        types.forEach { subType ->
            // Prepare grid
            val nbChildren = types.count()
            grid.rowCount = sqrt(nbChildren.toDouble()).toInt() + 1

            // Prepare parameters
            val param = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f) ).apply { width = 0; height = 0
            }

            // Create an ImageView
            val view: ImageView = ImageView(this)
            view.setImageResource(getImageId(subType))
            // Add the ImageView to the grid
            grid.addView(view, param)
        }
    }


    // Create listener for each types buttons
    private fun listenTypeButtons() {
        activeTypes = HashMap()
        val buttonsLayout = findViewById<GridLayout>(R.id.buttons_layout)

        buttonsLayout.forEach { child ->
            // Check if the inner child is an ImageButton and add to list
            if (child is ImageButton) {
                imgBtnListener(child)
            }
        }
    }


    private fun imgBtnListener(button:ImageButton) {
        // Get tag
        val type = button.tag.toString()
        // Set default values
        activeTypes[button.tag.toString()] = false
        button.alpha = 0.15F

        // Change value in the hashmap and button's opacity based on button active or not
        button.setOnClickListener {
            val active = activeTypes[type]
            // Switch state
            if (active == true) {
                // Disable
                activeTypes.replace(type, false)
                button.alpha = 0.15F
            } else if (countActiveButtons() < 2) {
                // Enable
                activeTypes.replace(type, true)
                button.alpha = 1.0F
            }

            updateOutputs()
        }
    }


    private fun listenClose() {
        val btnClose = findViewById<Button>(R.id.btn_close)
        btnClose.setOnClickListener {
            val home = Intent(this, MainMenu::class.java)
            startActivity(home)
        }
    }


    // Get the number of active buttons
    private fun countActiveButtons(): Int {
        var count = 0
        activeTypes.forEach { (_, active) ->
            if (active) { count += 1 }
        }
        return count
    }


    // Remove all layouts inside Resistance and Efficiency layouts
    private fun clearResEff() {
        val viewsToRemove = mutableListOf<View>()
        val idsOfLayoutsToEmpty = listOf(
            R.id.super_weak, R.id.very_weak, R.id.very_resistant, R.id.super_resistance, R.id.immune,
            R.id.ineffective, R.id.not_effective, R.id.cancel_effective, R.id.super_effective
        )

        // Get all views to remove
        for (i in 0 until idsOfLayoutsToEmpty.count()) {
            val viewGroup = findViewById<ViewGroup>(idsOfLayoutsToEmpty[i])

            viewGroup.forEach { child ->
                viewsToRemove.add(child)
            }
        }

        // Remove views through their parent
        viewsToRemove.forEach { view ->
            val parent = view.parent as? ViewGroup ?: return
            parent.removeView(view)
        }
    }


    private fun getImageId(imageName: String): Int {
        when (imageName) {
            "acier" -> return R.drawable.pokemon_type_acier
            "combat" -> return R.drawable.pokemon_type_combat
            "dragon" -> return R.drawable.pokemon_type_dragon
            "eau" -> return R.drawable.pokemon_type_eau
            "electrik" -> return R.drawable.pokemon_type_electrik
            "fee" -> return R.drawable.pokemon_type_fee
            "feu" -> return R.drawable.pokemon_type_feu
            "glace" -> return R.drawable.pokemon_type_glace
            "insecte" -> return R.drawable.pokemon_type_insecte
            "normal" -> return R.drawable.pokemon_type_normal
            "plante" -> return R.drawable.pokemon_type_plante
            "poison" -> return R.drawable.pokemon_type_poison
            "psy" -> return R.drawable.pokemon_type_psy
            "roche" -> return R.drawable.pokemon_type_roche
            "sol" -> return R.drawable.pokemon_type_sol
            "spectre" -> return R.drawable.pokemon_type_spectre
            "tenebres" -> return R.drawable.pokemon_type_tenebres
            "vol" -> return R.drawable.pokemon_type_vol
            else ->  {
                Toast.makeText(this, "$imageName image doesn't exists", Toast.LENGTH_SHORT).show()
                return R.drawable.pokemon_type_unknown
            }
        }
    }

}