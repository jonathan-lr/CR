package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.AgingBarrel
import com.cosmicreach.redcorp.menus.CoffeeMachine
import com.cosmicreach.redcorp.utils.DrugTest
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.block.Block
import org.bukkit.entity.ChestBoat
import org.bukkit.entity.ChestedHorse
import org.bukkit.entity.Player
import org.bukkit.entity.minecart.HopperMinecart
import org.bukkit.entity.minecart.StorageMinecart
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

        if (event.inventory.type == InventoryType.ENDER_CHEST || event.inventory.type == InventoryType.SHULKER_BOX || event.inventory.holder is ChestedHorse || event.inventory.holder is ChestBoat || event.inventory.holder is StorageMinecart || event.inventory.holder is HopperMinecart) {
            val p = event.player as Player
            if(DrugTest().doWiderTest(p)) {
                event.isCancelled = true
                p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §ryou cant open this while holding illegal substances!")
            }
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