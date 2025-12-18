package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.ShipmentItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class Shipment(private var drugType: Int, private var drugName: String) {
    private val econ = RedCorp.getPlugin().getEcon()
    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ!!, player)
        val shipmentItemSmall = ShipmentItem(econ, 1, drugType, drugName)
        val shipmentItemMedium = ShipmentItem(econ, 2, drugType, drugName)
        val shipmentItemLarge = ShipmentItem(econ, 3, drugType, drugName)

        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . . . #",
                "# . x . y . z . #",
                "# . . . . . . . #")
            .addIngredient('#', border)
            .addIngredient('x', shipmentItemSmall)
            .addIngredient('y', shipmentItemMedium)
            .addIngredient('z', shipmentItemLarge)
            .addIngredient('$', balItem)
            .build()
        return gui
    }
}