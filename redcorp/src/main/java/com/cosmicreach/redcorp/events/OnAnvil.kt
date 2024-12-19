package com.cosmicreach.redcorp.events


import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

class OnAnvil (private val event : InventoryClickEvent) {

    fun run(extendedIds: Array<Int>) {
        if (event.inventory.type == InventoryType.ANVIL || event.inventory.type == InventoryType.GRINDSTONE || event.inventory.type == InventoryType.BREWING || event.inventory.type == InventoryType.FURNACE || event.inventory.type == InventoryType.BLAST_FURNACE || event.inventory.type == InventoryType.SMOKER || event.inventory.type == InventoryType.WORKBENCH || event.inventory.type == InventoryType.CRAFTER) {
            val player = event.whoClicked
            if (event.currentItem != null) {
                val item = event.currentItem as ItemStack
                if (player !is Player) { return }
                if (Utils().checkID(item, extendedIds)) {
                    if ((Utils().checkID(item, arrayOf(423)) && event.slotType == InventoryType.SlotType.RESULT) || (Utils().checkID(item, arrayOf(440, 441)) && event.inventory.type == InventoryType.SMOKER)) {
                        return
                    } else {
                        event.isCancelled = true
                        player.sendMessage("§cCR §8|§r Stop ${player.displayName} §rthats not allowed! Persistence may result in item loss.")
                    }
                }
            }
        }
        return
    }
}