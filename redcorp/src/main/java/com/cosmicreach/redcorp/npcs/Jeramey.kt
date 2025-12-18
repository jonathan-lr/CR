package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.utils.DecideLoot
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.entity.Player
import java.util.concurrent.ThreadLocalRandom

class Jeramey (private val player: Player) {
    fun run () {
        val item = player.inventory.itemInMainHand.clone()
        if (!Utils().checkID(item, arrayOf(52, 53, 54, 55))) {
            player.sendMessage(jerameyResponse())
            return
        }

        player.sendMessage("§cJeramey §8|§r Alright lets crack the vault open")
        player.inventory.itemInMainHand.amount -= 1
        DecideLoot().decideLoot(player, item)
        return
    }

    private fun jerameyResponse(): String {
        when (ThreadLocalRandom.current().nextInt(0, 3)) {
            0 -> return "§cJeramey §8|§r I don't think you have the right thing sweetheart"
            1 -> return "§cJeramey §8|§r That Item isn't even close love"
            2 -> return "§cJeramey §8|§r Go on, show me what im looking for"
        }
        return ""
    }
}