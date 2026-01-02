package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.utils.DrugTest
import org.bukkit.entity.Player

class Ting (private val p: Player) {
    fun run () {
        if (DrugTest().doTest(p)) {
            p.sendMessage("§rTing §8|§r Hi ${p.displayName}§r, my boss would like to see you upstairs.")
        } else {
            p.sendMessage("§fTing §8|§r You come four noodle?")
        }
        return
    }
}