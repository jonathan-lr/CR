package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.AgingBarrel
import com.cosmicreach.redcorp.menus.CoffeeMachine
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.window.Window

class OnInventory (private val event : InventoryOpenEvent, private val agingBarrels: HashMap<Block, VirtualInventory>) {
    fun run() {
        if (event.inventory.type == InventoryType.BARREL) {
            barrel()
        }

        if (event.inventory.type == InventoryType.BREWING) {
            brewing()
        }
    }

    private fun barrel() {
        val p = event.player as Player
        val b = event.inventory.location?.block as Block
        val nbt = NBTBlock(b).data
        val temp = nbt.getBoolean("barrel")
        val fermenting = nbt.getBoolean("ferment")
        val viewers = RedCorp.getPlugin().getAgingViewers()
        if (fermenting) {
            event.isCancelled = true
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rthat barrel is still fermenting!")
            return
        }
        if (temp) {
            event.isCancelled = true
            val window = Window.single()
                .setViewer(p)
                .setTitle("§6§lAging Barrel")
                .setGui(AgingBarrel(agingBarrels).makeGUI(b))
                .build()

            window.open()

            if (viewers[b] != null) {
                viewers[b]?.add(window)
            } else {
                viewers[b] = mutableListOf(window)
            }
        }
    }

    private fun brewing() {
        val p = event.player as Player
        val b = event.inventory.location?.block as Block
        val nbt = NBTBlock(b).data
        val temp = nbt.getBoolean("coffee")
        val fermenting = nbt.getBoolean("ferment")
        val viewers = RedCorp.getPlugin().getAgingViewers()
        if (fermenting) {
            event.isCancelled = true
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rthat coffee is still brewing!")
            return
        }
        if (temp) {
            event.isCancelled = true
            val window = Window.single()
                .setViewer(p)
                .setTitle("§6§lCoffee Machine")
                .setGui(CoffeeMachine(agingBarrels).makeGUI(b))
                .build()

            window.open()

            if (viewers[b] != null) {
                viewers[b]?.add(window)
            } else {
                viewers[b] = mutableListOf(window)
            }
        }
    }

}