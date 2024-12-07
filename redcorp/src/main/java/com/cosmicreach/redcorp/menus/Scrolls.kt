package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.menus.items.*
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
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
                .addIngredient('x', ScrollTeleportItem(econ, balItem))
                .addIngredient('y', ScrollHomeItem(econ, balItem))
                .addIngredient('u', ScrollDeathItem(econ, balItem))
                .addIngredient('z', balItem)
                .build()
        return gui
    }
}