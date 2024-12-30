package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.utils.DrugTest
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class Cops(
    private val cop: Int,
    private val p: Player,
) {
    private val cops = arrayOf(
        "PC Mic",
        "DS Andy",
        "PC Danny",
        "SGT Angle",
        "PC Man",
        "SGT Margaret",
        "PC Doris",
        "DS Emily",
        "DI Victoria",
        "PC Abigail"
    )

    fun run() {
        if (DrugTest().doTest(p)) {
            p.sendMessage("§9${cops[cop-1]} §8|§r Gotcha ${p.displayName}§r, time for some prison time!")
            DrugTest().arrestPlayer(p)
            for (i in 1..10) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "boss butcher world cop_${i}")
            }
        } else {
            p.sendMessage("§9${cops[cop-1]} §8|§r Sorry ${p.displayName}§r, please continue your day")
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "boss butcher world cop_${cop}")
        }
    }
}