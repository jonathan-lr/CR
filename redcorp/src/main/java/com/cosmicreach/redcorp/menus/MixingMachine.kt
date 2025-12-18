package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.items.MixItem
import org.bukkit.block.Block
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory

class MixingMachine(private val agingBarrels: HashMap<Block, VirtualInventory>) {
    private val progressInventories = HashMap<VirtualInventory, VirtualInventory>()
    private val guis = RedCorp.getPlugin().getStoredGuis()
    private var mixing = false

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
            .addIngredient('z', MixItem(inv, progressInv, block, ::setMixing, ::getMixing))
            .build()

        setupHandlers(inv, progressInv)

        return gui
    }

    private fun setMixing(mix: Boolean): Boolean {
        mixing = mix
        return mixing
    }

    private fun getMixing(): Boolean {
        return mixing
    }

    private fun setupHandlers(inv: VirtualInventory, progressInv: VirtualInventory) {
        inv.setPreUpdateHandler { event ->
            if (mixing) {
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