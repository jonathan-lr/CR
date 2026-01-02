package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.items.IndustrialGrindItem
import org.bukkit.block.Block
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory

class IndustrialGrinder() {
    private val progressInventories = HashMap<VirtualInventory, VirtualInventory>()
    private val guis = RedCorp.getPlugin().getStoredGuis()
    private var grinding = false
    private val agingBarrel = RedCorp.getPlugin().agingBarrels

    fun getOrCreateGui(block: Block): Gui {
        return guis.getOrPut(block) {
            makeGUI(block)
        }
    }


    fun makeGUI(block: Block): Gui {
        val inv = agingBarrel.getOrPut(block) { VirtualInventory(3) }
        val progressInv = progressInventories.getOrPut(inv) { VirtualInventory(1) }

        val gui = Gui.normal()
            .setStructure(
                ". . . . p . . . .",
                ". . . x x x . . .",
                ". . . . z . . . .",)
            .addIngredient('x', inv)
            .addIngredient('p', progressInv)
            .addIngredient('z', IndustrialGrindItem(inv, progressInv, block, ::setGrinding, ::getGrinding))
            .build()

        setupHandlers(inv, progressInv)
        return gui
    }
    private fun setGrinding(grind: Boolean): Boolean {
        grinding = grind
        return grinding
    }

    private fun getGrinding(): Boolean {
        return grinding
    }

    private fun setupHandlers(inv: VirtualInventory, progressInv: VirtualInventory) {
        inv.setPreUpdateHandler { event ->
            if (grinding) {
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