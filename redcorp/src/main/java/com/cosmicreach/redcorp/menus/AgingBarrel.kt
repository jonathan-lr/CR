package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.items.DescItem
import com.cosmicreach.redcorp.menus.items.FermentItem
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class AgingBarrel(private val agingBarrels: HashMap<Block, VirtualInventory>) {
    private val progressInventories = HashMap<VirtualInventory, VirtualInventory>()
    private val guis = RedCorp.getPlugin().getStoredGuis()
    private var fermenting = false

    fun getOrCreateGui(block: Block): Gui {
        return guis.getOrPut(block) {
            makeGUI(block)
        }
    }

    fun makeGUI(block: Block): Gui {
        val inv = agingBarrels.getOrPut(block) { VirtualInventory(1) }
        val progressInv = progressInventories.getOrPut(inv) { VirtualInventory(1) }

        val gui = Gui.normal()
            .setStructure(
                ". . . . p . . . .",
                ". . . . x . . . .",
                ". . . . z . . . .",)
            .addIngredient('x', inv)
            .addIngredient('p', progressInv)
            .addIngredient('z', FermentItem(inv, progressInv, block, ::setFermenting, fermenting))
            .build()

        setupHandlers(inv, progressInv)

        return gui
    }

    private fun setFermenting(ferment: Boolean): Boolean {
        fermenting = ferment
        return fermenting
    }

    private fun setupHandlers(inv: VirtualInventory, progressInv: VirtualInventory) {
        inv.setPreUpdateHandler { event ->
            if (fermenting) {
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