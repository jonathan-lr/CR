package com.cosmicreach.redcorp.utils

import com.cosmicreach.redcorp.RedCorp
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.concurrent.ThreadLocalRandom

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

        if (illegalDrugs.contains(Utils().getID(p.inventory.itemInOffHand))) {
            hasDrugs = true
        }

        return hasDrugs
    }

    fun arrestPlayer(p: Player) {
        val econ = RedCorp.getPlugin().getEcon()
        if (econ != null) {
            econ.withdrawPlayer(p, 2000.0)
            when (ThreadLocalRandom.current().nextInt(0, 3)) {
                0 -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail ${p.playerListName} 1 5m")
                1 -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail ${p.playerListName} 2 5m")
                2 -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail ${p.playerListName} 3 5m")
            }

            p.inventory.forEach {
                if (it != null) {
                    if (illegalDrugs.contains(Utils().getID(it))) {
                        it.amount = 0
                    }
                }
            }
        }
    }
}