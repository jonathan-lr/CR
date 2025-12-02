package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.utils.DrugTest
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.entity.ChestBoat
import org.bukkit.entity.ChestedHorse
import org.bukkit.entity.Player
import org.bukkit.entity.minecart.HopperMinecart
import org.bukkit.entity.minecart.StorageMinecart
import org.bukkit.event.inventory.InventoryType

class HandleInvChange(private val p: Player) {
    fun run() {
        val hasDrugs = DrugTest().doTest(p)

        // Set scoreboard value
        Utils().setScore(p, "has_drugs", if (hasDrugs) 1 else 0)

        if (!hasDrugs) return

        // Check what inventory the player currently has open
        val view = p.openInventory
        val inv = view.topInventory
        val type = inv.type
        val holder = inv.holder

        // If they are in any of the blocked inventories, close it
        if (
            type == InventoryType.ENDER_CHEST ||
            type == InventoryType.SHULKER_BOX ||
            holder is ChestedHorse ||
            holder is ChestBoat ||
            holder is StorageMinecart ||
            holder is HopperMinecart
        ) {
            p.closeInventory()
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §ryou cant open this while holding illegal substances!")
        }
    }
}

class HandleInvChangeBudget(private val p: Player) {
    fun run() {
        val hasDrugs = DrugTest().doTest(p)

        // Set scoreboard value
        Utils().setScore(p, "has_drugs", if (hasDrugs) 1 else 0)
    }
}