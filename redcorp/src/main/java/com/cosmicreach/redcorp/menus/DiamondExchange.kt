package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.ShopItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class DiamondExchange(private var econ: Economy, private var type: Int) {

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        var balItem = BalanceItem(econ, player)
        if (type == 1) {
            val gui = Gui.normal()
                .setStructure(
                    "# . . . z . . . #",
                    "# . x . . . y . #",
                    "# . . . . . . . #")
                .addIngredient('#', border)
                .addIngredient('x', ShopItem(econ, balItem, ItemStack(Material.DIAMOND, 1), 100.0, 98.0, "§cPatrick Byattyman"))
                .addIngredient('y', ShopItem(econ, balItem, ItemStack(Material.DIAMOND_BLOCK, 1), 900.0, 882.0, "§cPatrick Byattyman"))
                .addIngredient('z', balItem)
                .build()
            return gui
        } else {
            val gui = Gui.normal()
                .setStructure(
                    "# . . . z . . . #",
                    "# . x . . . y . #",
                    "# . . . . . . . #")
                .addIngredient('#', border)
                .addIngredient('x', ShopItem(econ, balItem, ItemStack(Material.DIAMOND, 1), 102.0, 100.0, "§6Sterling"))
                .addIngredient('y', ShopItem(econ, balItem, ItemStack(Material.DIAMOND_BLOCK, 1), 918.0, 900.0, "§6Sterling"))
                .addIngredient('z', balItem)
                .build()
            return gui
        }
    }
}