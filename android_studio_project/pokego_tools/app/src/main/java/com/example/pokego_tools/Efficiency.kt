package com.example.pokego_tools

import android.util.Log

object Efficiency {

    //works for only 2 types
    fun determineEfficiencies(
        activeTypes: HashMap<String, Boolean>,
        efficiencies: HashMap<String, HashMap<String, List<String>>>
    ) : HashMap<String, List<String>>
    {
        val effective = mutableListOf<String>()
        val notEffective = mutableListOf<String>()
        val ineffective = mutableListOf<String>()

        //get active types
        val types = mutableListOf<String>()
        activeTypes.forEach { (type, active) ->
            if (active) { types.add(type) }
        }

        for (type in types) {
            //effective
            efficiencies[type]?.get("effective")?.forEach() { subType ->
                effective.add(subType)
            }
            //not effective
            efficiencies[type]?.get("notEffective")?.forEach() { subType ->
                notEffective.add(subType)
            }
            //ineffective
            efficiencies[type]?.get("ineffective")?.forEach() { subType ->
                ineffective.add(subType)
            }
        }

        val res = HashMap<String, List<String>>()
        res["effective"] = effective
        res["notEffective"] = notEffective
        res["ineffective"] = ineffective
        return res
    }


    fun showEfficiencies (efficiencies: HashMap<String, HashMap<String, List<String>>>) {
        val types = listOf("acier", "combat", "dragon", "eau", "electrik", "fee", "feu", "glace", "insecte", "normal", "plante", "poison", "psy", "roche", "sol", "spectre", "tenebres", "vol")

        types.forEach{ type ->
            Log.e(type, efficiencies[type].toString())
        }
        Log.e(efficiencies.count().toString(), efficiencies.toString())
    }


    fun initEfficiencies(): HashMap<String, HashMap<String, List<String>>> {
        var effectiveness = listOf<String>()
        var notEffectiveness = listOf<String>()
        var ineffectiveness = listOf<String>()

        val efficiencies: HashMap<String, HashMap<String, List<String>>> = HashMap()

        val types = listOf("acier", "combat", "dragon", "eau", "electrik", "fee", "feu", "glace", "insecte", "normal", "plante", "poison", "psy", "roche", "sol", "spectre", "tenebres", "vol")

        types.forEach { type ->
            when (type) {
                "acier" -> {
                    effectiveness = listOf("fee", "glace", "roche")
                    notEffectiveness = listOf("acier", "eau", "electrik", "feu")
                    ineffectiveness = listOf()
                }
                "combat" -> {
                    effectiveness = listOf("acier", "glace", "normal", "roche", "tenebres")
                    notEffectiveness = listOf("fee", "insecte", "poison", "psy", "vol")
                    ineffectiveness = listOf("spectre")
                }
                "dragon" -> {
                    effectiveness = listOf("dragon")
                    notEffectiveness = listOf("acier")
                    ineffectiveness = listOf("fee")
                }
                "eau" -> {
                    effectiveness = listOf("feu", "roche", "sol")
                    notEffectiveness = listOf("eau", "dragon", "plante")
                    ineffectiveness = listOf()
                }
                "electrik" -> {
                    effectiveness = listOf("eau", "vol")
                    notEffectiveness = listOf("dragon", "electrik", "plante")
                    ineffectiveness = listOf("sol")
                }
                "fee" -> {
                    effectiveness = listOf("combat", "dragon", "tenebres")
                    notEffectiveness = listOf("acier", "feu", "poison")
                    ineffectiveness = listOf()
                }
                "feu" -> {
                    effectiveness = listOf("acier", "glace", "insecte", "plante")
                    notEffectiveness = listOf("eau", "dragon", "feu", "roche")
                    ineffectiveness = listOf()
                }
                "glace" -> {
                    effectiveness = listOf("dragon", "plante", "sol", "vol")
                    notEffectiveness = listOf("acier", "eau", "feu", "glace")
                    ineffectiveness = listOf()
                }
                "insecte" -> {
                    effectiveness = listOf("plante", "psy", "tenebres")
                    notEffectiveness = listOf("acier", "combat", "fee", "feu", "poison", "spectre", "vol")
                    ineffectiveness = listOf()
                }
                "normal" -> {
                    effectiveness = listOf()
                    notEffectiveness = listOf("acier", "roche")
                    ineffectiveness = listOf("spectre")
                }
                "plante" -> {
                    effectiveness = listOf("eau", "roche", "sol")
                    notEffectiveness = listOf("acier", "dragon", "feu", "insecte", "plante", "poison", "vol")
                    ineffectiveness = listOf()
                }
                "poison" -> {
                    effectiveness = listOf("fee", "plante")
                    notEffectiveness = listOf("poison", "roche", "sol", "spectre")
                    ineffectiveness = listOf("acier")
                }
                "psy" -> {
                    effectiveness = listOf()
                    notEffectiveness = listOf()
                    ineffectiveness = listOf()
                }
                "roche" -> {
                    effectiveness = listOf()
                    notEffectiveness = listOf()
                    ineffectiveness = listOf()
                }
                "sol" -> {
                    effectiveness = listOf("acier", "electrik", "feu", "poison", "roche")
                    notEffectiveness = listOf("insecte", "plante")
                    ineffectiveness = listOf("vol")
                }
                "spectre" -> {
                    effectiveness = listOf("psy", "spectre")
                    notEffectiveness = listOf("tenebres")
                    ineffectiveness = listOf("normal")
                }
                "tenebres" -> {
                    effectiveness = listOf("psy", "spectre")
                    notEffectiveness = listOf("combat", "fee", "tenebres")
                    ineffectiveness = listOf()
                }
                "vol" -> {
                    effectiveness = listOf("combat", "insecte", "plante")
                    notEffectiveness = listOf("acier", "electrik", "roche")
                    ineffectiveness = listOf()
                }
            }

            val efficienciesOfAType: HashMap<String, List<String>> = HashMap()
            efficienciesOfAType["effective"] = effectiveness
            efficienciesOfAType["notEffective"] = notEffectiveness
            efficienciesOfAType["ineffective"] = ineffectiveness
            efficiencies[type] = efficienciesOfAType
        }

        return efficiencies
    }

}