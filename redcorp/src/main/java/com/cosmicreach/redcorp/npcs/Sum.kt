package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Delivery
import com.cosmicreach.redcorp.db.Progress
import com.cosmicreach.redcorp.items.GreenhouseItems
import org.bukkit.entity.Player

class Sum (private val p: Player) {
    private val connection = RedCorp.getPlugin().getConnection()!!
    fun run () {
        val deliveries = Delivery(connection).getFinishedDeliveryCount(p.uniqueId, 444)
        if (deliveries >= 5) {
            val recived = Progress(connection).getPlayer(p.uniqueId, 4)
            if (recived == true) {
                p.sendMessage("§fSum §8|§r Wong is happy with your progress keep working at it.")
            } else {
                Progress(connection).addPlayer(p.uniqueId, 4)
                Progress(connection).updateComplete(p.uniqueId, 4, true)
                p.sendMessage("§fSum §8|§r Wong is happy with your progress here is an investment from him.")
                p.inventory.addItem(GreenhouseItems().GreenhouseUpgrade2(1))
            }
        } else {
            p.sendMessage("§fSum §8|§r You have not done enough for Master Wong yet. Come back to me when you are a greater man.")
        }
        return
    }
}