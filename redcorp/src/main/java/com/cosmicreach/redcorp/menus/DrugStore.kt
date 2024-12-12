package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.menus.items.*
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class DrugStore(private var econ: Economy) {
    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        var balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
            .setStructure(
                "# . . . z . . . #",
                "# . u . x . y . #",
                "# . . v . w . . #")
            .addIngredient('#', border)
            .addIngredient('u', ShopItem(econ, balItem, DrugItems().WeedSeed(1), 1000.0, 0.0, "§8Shade E", "%player%§r get out of here before the cops come!", "", "Fuck off %player%§r, you do not have enough Units this shizz!"))
            .addIngredient('x', ShopItem(econ, balItem, DrugItems().CocaSeed(1), 2000.0, 0.0, "§8Shade E", "%player%§r get out of here before the cops come!", "", "Fuck off %player%§r, you do not have enough Units this shizz!"))
            .addIngredient('y', ShopItem(econ, balItem, DrugItems().OpiumFlower(1), 2500.0, 0.0, "§8Shade E", "%player%§r get out of here before the cops come!", "", "Fuck off %player%§r, you do not have enough Units this shizz!"))
            .addIngredient('v', ShopItem(econ, balItem, DrugItems().Shrooms(1), 5000.0, 0.0, "§8Shade E", "%player%§r get out of here before the cops come!", "", "Fuck off %player%§r, you do not have enough Units this shizz!"))
            .addIngredient('w', ShopItem(econ, balItem, DrugItems().Truffles(1), 5000.0, 0.0, "§8Shade E", "%player%§r get out of here before the cops come!", "", "Fuck off %player%§r, you do not have enough Units this shizz!"))
            .addIngredient('z', balItem)
            .build()
        return gui
    }
}