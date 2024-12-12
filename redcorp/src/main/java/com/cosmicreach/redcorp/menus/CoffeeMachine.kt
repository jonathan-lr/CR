package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.menus.items.BrewItem
import com.cosmicreach.redcorp.menus.items.DescItem
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class CoffeeMachine(private val agingBarrels: HashMap<Block, VirtualInventory>) {

    fun makeGUI(block: Block): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        var inv: VirtualInventory

        if (agingBarrels.containsKey(block)) {
            inv = agingBarrels[block]!!
        } else {
            agingBarrels[block] = VirtualInventory(4)
            inv = agingBarrels[block]!!
        }

        val gui = Gui.normal()
            .setStructure(
                "# # x x # x x # #",
                "# # y u # v w # #",
                "# # # # z # # # #",)
            .addIngredient('#', border)
            .addIngredient('x', inv)
            .addIngredient('y', DescItem(ItemStack(Material.BROWN_STAINED_GLASS_PANE),"§cCoffee Slot", "§cAdd coffee beans 1-3 above"))
            .addIngredient('u', DescItem(ItemStack(Material.BLUE_STAINED_GLASS_PANE),"§cWater Bottle", "§cAdd water bottle above"))
            .addIngredient('v', DescItem(ItemStack(Material.WHITE_STAINED_GLASS_PANE),"§cMilk Slot", "§cAdd optional milk above"))
            .addIngredient('w', DescItem(ItemStack(Material.WHITE_STAINED_GLASS_PANE),"§cSugar Slot", "§cAdd optional sugar above"))
            .addIngredient('z', BrewItem(inv, block))
            .build()
        return gui
    }
}