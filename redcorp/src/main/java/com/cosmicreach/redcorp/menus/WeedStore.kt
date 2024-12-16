package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.ShopItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class WeedStore(private var econ: Economy) {
    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
            .setStructure(
                "# . . . z . . . #",
                "# . . x . y . . #",
                "# . . . . . . . #")
            .addIngredient('#', border)
            .addIngredient('x', ShopItem(econ, balItem, DrugItems().GroundWeed(1), 100.0, 50.0, "§2Shaggy"))
            .addIngredient('y', ShopItem(econ, balItem, DrugItems().Spliff(1), 100.0, 50.0, "§2Shaggy"))
            .addIngredient('z', balItem)
            .build()
        return gui
    }
}