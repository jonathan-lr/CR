package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.menus.items.FermentItem
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class AgingBarrel(private val agingBarrels: HashMap<Block, VirtualInventory>) {

    fun makeGUI(block: Block): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        var inv: VirtualInventory

        if (agingBarrels.containsKey(block)) {
            inv = agingBarrels[block]!!
        } else {
            agingBarrels[block] = VirtualInventory(1)
            inv = agingBarrels[block]!!
        }

        val gui = Gui.normal()
            .setStructure(
                "# # # # x # # # #",
                "# # # # # # # # #",
                "# # # # z # # # #",)
            .addIngredient('#', border)
            .addIngredient('x', inv)
            .addIngredient('z', FermentItem(inv, block))
            .build()
        return gui
    }
}