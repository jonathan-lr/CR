package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.items.PressItem
import org.bukkit.block.Block
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory

class PressMachine(private val agingBarrels: HashMap<Block, VirtualInventory>) {
    private val progressInventories = HashMap<VirtualInventory, VirtualInventory>()
    private val guis = RedCorp.getPlugin().getStoredGuis()
    private var pressing = false

    fun getOrCreateGui(block: Block): Gui {
        return guis.getOrPut(block) {
            makeGUI(block)
        }
    }

    fun makeGUI(block: Block): Gui {
        val inv = agingBarrels.getOrPut(block) { VirtualInventory(3) }
        val progressInv = progressInventories.getOrPut(inv) { VirtualInventory(1) }

        val gui = Gui.normal()
            .setStructure(
                ". . . . p . . . .",
                ". . . x x x . . .",
                ". . . . z . . . .",)
            .addIngredient('x', inv)
            .addIngredient('p', progressInv)
            .addIngredient('z', PressItem(inv, progressInv, block, ::setPressing, ::getPressing))
            .build()

        setupHandlers(inv, progressInv)

        return gui
    }

    private fun setPressing(press: Boolean): Boolean {
        pressing = press
        return pressing
    }

    private fun getPressing(): Boolean {
        return pressing
    }

    private fun setupHandlers(inv: VirtualInventory, progressInv: VirtualInventory) {
        inv.setPreUpdateHandler { event ->
            if (pressing) {
                event.isCancelled = true
                return@setPreUpdateHandler
            }
        }

        progressInv.setPreUpdateHandler { event ->
            event.isCancelled = true
            return@setPreUpdateHandler
        }
    }
}