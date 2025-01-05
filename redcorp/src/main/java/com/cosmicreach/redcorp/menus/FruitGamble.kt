package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.FruitGambleItem
import com.cosmicreach.redcorp.menus.items.GambleItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem
import kotlin.random.Random

class FruitGamble(private var econ: Economy) {
    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
            .setStructure(
                "# . . . z . . . #",
                "# . # . # . # . #",
                "# . # . # . # . #",
                "# . # . # . # . #",
                "# . . . x . . . #")
            .addIngredient('#', border)
            .addIngredient('z', balItem)
            .addIngredient('x', GambleItem(econ, player))
            .build()
        return gui
    }
}