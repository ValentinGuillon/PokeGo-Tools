package com.example.pokego_tools

import android.util.Log

object Resistance {

    //works for only 2 types
    fun determineResistances(
        activeTypes: HashMap<String, Boolean>,
        resistances: HashMap<String, HashMap<String, List<String>>>
    ) : HashMap<String, List<String>>
    {
        val doubleWeakness = mutableListOf<String>()
        val weakness = mutableListOf<String>()
        val resistance = mutableListOf<String>()
        val immunity = mutableListOf<String>()
        val doubleImmunity = mutableListOf<String>()
        val tripleImmunity = mutableListOf<String>()

        //get active types
        val types = mutableListOf<String>()
        activeTypes.forEach { (type, active) ->
            if (active) { types.add(type) }
        }

        for (type in types) {
            //weakness
            resistances[type]?.get("weakness")?.forEach() { subType ->
                when (subType) {
                    //is already a weakness => double weakness
                    in weakness -> {
                        weakness.remove(subType)
                        doubleWeakness.add(subType)
                        }
                    //is already a immunity => resistance
                    in immunity -> {
                        immunity.remove(subType)
                        resistance.add(subType)
                    }
                    //is already a resistance => cancel
                    in resistance ->
                        resistance.remove(subType)
                    else -> weakness.add(subType)
                }
            }
            //resistance
            resistances[type]?.get("resistance")?.forEach() { subType ->
                when (subType) {
                    //is already a weakness => cancel
                    in weakness ->
                        weakness.remove(subType)
                    //is already a immunity => double immunity
                    in immunity -> {
                        immunity.remove(subType)
                        doubleImmunity.add(subType)
                    }
                    //is already a resistance => immunity
                    in resistance -> {
                        resistance.remove(subType)
                        immunity.add(subType)
                    }
                    else -> resistance.add(subType)
                }
            }
            //immunity
            resistances[type]?.get("immunity")?.forEach() { subType ->
                when (subType) {
                    //is already a weakness => resistance
                    in weakness -> {
                        weakness.remove(subType)
                        resistance.add(subType)
                    }
                    //is already a immunity => triple immunity
                    in immunity -> {
                        immunity.remove(subType)
                        tripleImmunity.add(subType)
                    }
                    //is already a resistance => double immunity
                    in resistance -> {
                        resistance.remove(subType)
                        doubleImmunity.add(subType)
                    }
                    else -> immunity.add(subType)
                }
            }
        }

        val res = HashMap<String, List<String>>()
        res["doubleWeakness"] = doubleWeakness
        res["weakness"] = weakness
        res["resistance"] = resistance
        res["immunity"] = immunity
        res["doubleImmunity"] = doubleImmunity
        res["tripleImmunity"] = tripleImmunity
        return res
    }


    fun showResistances (resistances: HashMap<String, HashMap<String, List<String>>>) {
        val types = listOf("acier", "combat", "dragon", "eau", "electrik", "fee", "feu", "glace", "insecte", "normal", "plante", "poison", "psy", "roche", "sol", "spectre", "tenebres", "vol")

        types.forEach { type ->
            Log.e(type, resistances[type].toString())
        }
        Log.e(resistances.count().toString(), resistances.toString())
    }


    fun initResistances(): HashMap<String, HashMap<String, List<String>>> {
        var weakness = listOf<String>()
        var resistance = listOf<String>()
        var immunity = listOf<String>()

        val resistances: HashMap<String, HashMap<String, List<String>>> = HashMap()

        val types = listOf("acier", "combat", "dragon", "eau", "electrik", "fee", "feu", "glace", "insecte", "normal", "plante", "poison", "psy", "roche", "sol", "spectre", "tenebres", "vol")

        types.forEach { type ->
            when (type) {
                "acier" -> {
                    weakness = listOf("combat", "feu", "sol")
                    resistance = listOf("acier", "dragon", "fee", "glace", "insecte", "normal", "plante", "psy", "roche", "vol")
                    immunity = listOf("poison")
                }
                "combat" -> {
                    weakness = listOf("fee", "psy", "vol")
                    resistance = listOf("insecte", "roche", "tenebres")
                    immunity = listOf()
                }
                "dragon" -> {
                    weakness = listOf("dragon", "fee", "glace")
                    resistance = listOf("eau", "electrik", "feu", "plante")
                    immunity = listOf()
                }
                "eau" -> {
                    weakness = listOf("electrik", "plante")
                    resistance = listOf("acier", "eau", "feu", "glace")
                    immunity = listOf()
                }
                "electrik" -> {
                    weakness = listOf("sol")
                    resistance = listOf("acier", "electrik", "vol")
                    immunity = listOf()
                }
                "fee" -> {
                    weakness = listOf("acier", "poison")
                    resistance = listOf("combat", "insecte", "tenebres")
                    immunity = listOf("dragon")
                }
                "feu" -> {
                    weakness = listOf("eau", "roche", "sol")
                    resistance = listOf("acier", "fee", "feu", "glace", "insecte", "plante")
                    immunity = listOf()
                }
                "glace" -> {
                    weakness = listOf("acier", "combat", "feu", "roche")
                    resistance = listOf("glace")
                    immunity = listOf()
                }
                "insecte" -> {
                    weakness = listOf("feu", "roche", "vol")
                    resistance = listOf("combat", "plante", "sol")
                    immunity = listOf()
                }
                "normal" -> {
                    weakness = listOf("combat")
                    resistance = listOf()
                    immunity = listOf("spectre")
                }
                "plante" -> {
                    weakness = listOf("feu", "glace", "insecte", "poison", "vol")
                    resistance = listOf("eau", "electrik", "plante", "sol")
                    immunity = listOf()
                }
                "poison" -> {
                    weakness = listOf("psy", "sol")
                    resistance = listOf("combat", "fee", "insecte", "plante", "poison")
                    immunity = listOf()
                }
                "psy" -> {
                    weakness = listOf("insecte", "spectre", "tenebres")
                    resistance = listOf("combat", "psy")
                    immunity = listOf()
                }
                "roche" -> {
                    weakness = listOf("acier", "combat", "eau", "plante", "sol")
                    resistance = listOf("feu", "normal", "poison", "vol")
                    immunity = listOf()
                }
                "sol" -> {
                    weakness = listOf("eau", "glace", "plante")
                    resistance = listOf("poison", "roche")
                    immunity = listOf("electrik")
                }
                "spectre" -> {
                    weakness = listOf("spectre", "tenebres")
                    resistance = listOf("insecte", "poison")
                    immunity = listOf("combat", "normal")
                }
                "tenebres" -> {
                    weakness = listOf("combat", "fee", "insecte")
                    resistance = listOf("spectre", "tenebres")
                    immunity = listOf("psy")
                }
                "vol" -> {
                    weakness = listOf("electrik", "glace", "roche")
                    resistance = listOf("combat", "insecte", "plante")
                    immunity = listOf("sol")
                }
            }

            val resistancesOfAType: HashMap<String, List<String>> = HashMap()
            resistancesOfAType["weakness"] = weakness
            resistancesOfAType["resistance"] = resistance
            resistancesOfAType["immunity"] = immunity
            resistances[type] = resistancesOfAType
        }

        return resistances
    }

}