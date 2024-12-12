package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.items.CustomItems
import com.cosmicreach.redcorp.menus.items.*
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class Scrolls(private var econ: Economy) {

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        var balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
                .setStructure(
                        "# . . . z . . . #",
                        "# . x . u . y . #",
                        "# . . . . . . . #")
                .addIngredient('#', border)
                .addIngredient('x', ShopItem(econ, balItem, ItemStack(CustomItems().TeleportScroll(1)), 500.0, 0.0, "§dMerlin"))
                .addIngredient('y', ShopItem(econ, balItem, ItemStack(CustomItems().DeathScroll(1)), 1000.0, 0.0, "§dMerlin"))
                .addIngredient('u', ShopItem(econ, balItem, ItemStack(CustomItems().HomeScroll(1)), 500.0, 0.0, "§dMerlin"))
                .addIngredient('z', balItem)
                .build()
        return gui
    }
}