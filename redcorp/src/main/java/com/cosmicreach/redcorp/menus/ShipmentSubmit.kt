package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.ShipmentSubmitItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem
import kotlin.collections.set

class ShipmentSubmit(private var econ: Economy) {
    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        val shipmentPlayers = RedCorp.getPlugin().getShipmentPlayers()
        val inv: VirtualInventory

        if (shipmentPlayers.containsKey(player)) {
            inv = shipmentPlayers[player]!!
        } else {
            shipmentPlayers[player] = VirtualInventory(5)
            inv = shipmentPlayers[player]!!
        }

        val shipmentItem = ShipmentSubmitItem(inv, econ)

        val gui = Gui.normal()
            .setStructure(
                "# # # # $ # # # #",
                "# # x x x x x # #",
                "# # # # y # # # #")
            .addIngredient('#', border)
            .addIngredient('x', inv)
            .addIngredient('y', shipmentItem)
            .addIngredient('$', balItem)
            .build()
        return gui
    }
}