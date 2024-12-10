package com.cosmicreach.redcorp.utils

import org.bukkit.entity.Player

class DrugTest {
    private var illegalDrugs = mutableListOf(421, 422, 423, 432, 441, 450, 451)

    fun doTest(p: Player): Boolean {
        var hasDrugs = false

        p.inventory.forEach {
            if (it != null) {
                if (illegalDrugs.contains(Utils().getID(it))) {
                    hasDrugs = true
                }
            }
        }

        if (p.inventory.itemInOffHand != null) {
            if (illegalDrugs.contains(Utils().getID(p.inventory.itemInOffHand))) {
                hasDrugs = true
            }
        }

        return hasDrugs
    }
}