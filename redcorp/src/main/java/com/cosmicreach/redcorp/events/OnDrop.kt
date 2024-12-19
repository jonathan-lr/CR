package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.event.player.PlayerDropItemEvent

class OnDrop (private val event : PlayerDropItemEvent) {

    fun run() {
        val item = event.itemDrop.itemStack
        if(Utils().checkID(item, arrayOf(69))) {
            event.isCancelled = true
            event.player.sendMessage("§cCR §8|§r Sorry ${event.player.displayName} §rcan't drop this item bud")
        }
        return
    }
}