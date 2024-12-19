package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.entity.Player
import org.bukkit.event.inventory.PrepareItemCraftEvent

class OnCraft (private val event : PrepareItemCraftEvent) {

    fun run(extendedIds: Array<Int>) {
        event.inventory.matrix.forEach {
            if (it !== null) {
                if (Utils().checkID(it, extendedIds)) {
                    val player = event.view.player as Player
                    event.view.close()
                    player.sendMessage("§cCR §8|§r Stop ${player.displayName} §rthats not allowed! Persistence may result in item loss.")
                }
            }
        }
        return
    }
}