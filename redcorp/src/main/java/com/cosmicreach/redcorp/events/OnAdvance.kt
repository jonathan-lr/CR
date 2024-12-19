package com.cosmicreach.redcorp.events

import org.bukkit.Bukkit
import org.bukkit.advancement.AdvancementDisplayType
import org.bukkit.event.player.PlayerAdvancementDoneEvent

class OnAdvance(private val event : PlayerAdvancementDoneEvent) {

    fun run() {
        if (event.advancement.display != null) {
            var newSize = Bukkit.getWorld("world")!!.worldBorder.size
            val ad = event.advancement.display!!
            if (ad.type == AdvancementDisplayType.TASK) {
                newSize += 2.0
                Bukkit.broadcastMessage("§cCR §8|§r ${event.player.displayName} §rcompleted advancement §a${ad.title}")
                Bukkit.broadcastMessage("§cCR §8|§r The World Border has expanded by 1 block!")
            } else if (ad.type == AdvancementDisplayType.GOAL) {
                newSize += 4.0
                Bukkit.broadcastMessage("§cCR §8|§r ${event.player.displayName} §rcompleted advancement §9${ad.title}")
                Bukkit.broadcastMessage("§cCR §8|§r The World Border has expanded by 2 blocks!")
            } else if (ad.type == AdvancementDisplayType.CHALLENGE) {
                newSize += 8.0
                Bukkit.broadcastMessage("§cCR §8|§r ${event.player.displayName} §rcompleted advancement §5${ad.title}")
                Bukkit.broadcastMessage("§cCR §8|§r The World Border has expanded by 4 blocks!")
            }
            Bukkit.getWorld("world")!!.worldBorder.setSize(newSize, 5L)
        }
        return
    }
}