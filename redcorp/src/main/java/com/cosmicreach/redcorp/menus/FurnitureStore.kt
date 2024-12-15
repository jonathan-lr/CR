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

class FurnitureStore (private var econ: Economy) {
    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
            .setStructure(
                "# . . . z . . . #",
                "# . u . x . y . #",
                "# . . . . . . . #")
            .addIngredient('#', border)
            .addIngredient('u', ShopItem(econ, balItem, DrugItems().AgingBarrel(1), 5000.0, 0.0, "§2Oakley"))
            .addIngredient('x', ShopItem(econ, balItem, DrugItems().CoffieMachine(1), 10000.0, 0.0, "§2Oakley"))
            .addIngredient('y', ShopItem(econ, balItem, DrugItems().Grinder(1), 2500.0, 0.0, "§2Oakley"))
            .addIngredient('z', balItem)
            .build()
        return gui
    }
}