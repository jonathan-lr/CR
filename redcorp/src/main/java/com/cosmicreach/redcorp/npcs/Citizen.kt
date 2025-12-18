package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.utils.DrugTest
import org.bukkit.entity.Player

class Citizen (private val p: Player, private val npc: Int) {
    private val citizens = arrayOf(
        "§aElowen",
        "§9Tharic",
        "§dMirella",
        "§6Brom",
        "§3Kaelis",
        "§cVashara",
        "§2Rowan",
        "§5Nyxara",
        "§eAldric",
        "§8Fenrik"
    )

    fun run() {
        if (DrugTest().doTest(p)) {
            p.sendMessage("§r${citizens[npc-1]} §8|§r Hi ${p.displayName}§r, you should hide those from the cops")
        } else {
            p.sendMessage("§r${citizens[npc-1]} §8|§r Hi ${p.displayName}§r, what a lovel day in Insomnis")
        }
    }
}