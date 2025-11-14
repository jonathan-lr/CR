package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.ShipmentItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class Shipment(private var econ: Economy) {
    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        val shipmentItem = ShipmentItem()

        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . . . #",
                "# . x . . . y . #",
                "# . . . . . . . #")
            .addIngredient('#', border)
            .addIngredient('x', shipmentItem)
            .addIngredient('y', shipmentItem)
            .addIngredient('$', balItem)
            .build()
        return gui
    }
}