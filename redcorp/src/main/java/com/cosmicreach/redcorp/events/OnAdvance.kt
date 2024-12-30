package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import org.bukkit.Bukkit
import org.bukkit.advancement.AdvancementDisplayType
import org.bukkit.event.player.PlayerAdvancementDoneEvent

class OnAdvance(private val event : PlayerAdvancementDoneEvent) {

    fun run() {
        if (event.advancement.display != null) {
            val world = Bukkit.getWorld("world") ?: return
            val wb = world.worldBorder
            val ad = event.advancement.display!!
            var worldBorder = RedCorp.getPlugin().getWorldBorder()
            val forbidden = mutableListOf("Vanilla Tweaks", "Cauldron Concrete", "Custom Nether Portals", "Dragon Drops", "Double Shulker Shells", "More Mob Heads", "Anti Enderman Grief", "Player Head Drops", "Silence Mobs", "Mini Blocks", "Silk Touch Budding Amethyst")

            if (forbidden.any { ad.title.contains(it, ignoreCase = true) }) { return }

            // Determine border expansion based on advancement type
            val (expansion, typeColor) = when (ad.type) {
                AdvancementDisplayType.TASK -> 10.0 to "§a" // Green
                AdvancementDisplayType.GOAL -> 20.0 to "§9" // Blue
                AdvancementDisplayType.CHALLENGE -> 50.0 to "§5" // Purple
                else -> return // Unsupported advancement type
            }

            // Broadcast messages and update the world border
            Bukkit.broadcastMessage("§cCR §8|§r ${event.player.displayName} §rcompleted advancement $typeColor${ad.title}")
            Bukkit.broadcastMessage("§cCR §8|§r The World Border has expanded by ${expansion.toInt()} blocks!")
            worldBorder += expansion
            RedCorp.getPlugin().setWorldBorder(worldBorder)
            wb.setSize(worldBorder, 5L)
        }
        return
    }
}