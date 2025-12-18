package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.Material
import org.bukkit.block.data.Levelled
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.scheduler.BukkitRunnable

class OnDrop (private val event : PlayerDropItemEvent) {

    fun run() {
        val item = event.itemDrop.itemStack
        if(Utils().checkID(item, arrayOf(69))) {
            event.isCancelled = true
            event.player.sendMessage("§cCR §8|§r Sorry ${event.player.displayName} §rcan't drop this item bud")
        }

        if(Utils().checkID(item, arrayOf(434))) {
            object : BukkitRunnable() {
                var ticks = 0
                var eItem = event.itemDrop

                override fun run() {
                    if (!eItem.isValid || eItem.isDead) {
                        cancel(); return
                    }
                    if (ticks++ > 200) {
                        cancel(); return
                    }

                    val block = event.itemDrop.location.block
                    val type = block.type

                    if (type != Material.WATER_CAULDRON) return

                    val newStack = DrugItems().Coke(item.amount)
                    eItem.itemStack = newStack

                    val data = block.blockData
                    if (data is Levelled && data.level > 0) {
                        data.level = (data.level - 1).coerceAtLeast(0)
                        block.blockData = data
                    }
                    cancel()
                }
            }.runTaskTimer(RedCorp.getPlugin(), 1L, 1L) // start next tick, run every tick
        }
        return
    }
}