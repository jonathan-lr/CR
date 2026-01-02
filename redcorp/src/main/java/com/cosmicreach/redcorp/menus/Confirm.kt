package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.menus.items.*
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class Confirm(private var type: Int) {
    fun makeGUI(): Gui{
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val gui = Gui.normal()
                .setStructure(
                        "# . . . . . . . #",
                        "# . x . . . y . #",
                        "# . . . . . . . #")
                .addIngredient('#', border)
                .addIngredient('x', YesItem(type))
                .addIngredient('y', NoItem(type))
                .build()
        return gui
    }
}