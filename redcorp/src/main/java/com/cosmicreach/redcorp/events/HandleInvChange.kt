package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.utils.DrugTest
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.entity.Player

class HandleInvChange(private val p: Player) {
    fun run() {
        // Check if the player has drugs in their inventory
        if (DrugTest().doTest(p)) {
            // Set the player's scoreboard value
            Utils().setScore(p, "has_drugs", 1)
        } else {
            // If no green dye, reset the scoreboard value
            Utils().setScore(p, "has_drugs", 0)
        }
    }
}