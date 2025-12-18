package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Delivery
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.utils.Utils
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem

class ShipmentSubmitItem(
    private val inv: VirtualInventory,
    private var econ: Economy,
) : ControlItem<Gui>() {
    private val connection = RedCorp.getPlugin().getConnection()!!

    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§2§lConfirm Shipment").setLegacyLore((mutableListOf("§f§lClick to confirm")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val shipment = Delivery(connection).getDelivery(player.uniqueId)

        val idsRequired = arrayOf(shipment!!.drugType)

        val totalMatchingAmount = (0..4).sumOf { slot ->
            val item = inv.getItem(slot)

            if (item != null) {
                if (Utils().checkID(item, idsRequired))
                    item.amount
                else
                    0
            } else {
                0
            }
        }

        if (totalMatchingAmount < shipment.amount) {
            player.sendMessage("§cCR §8|§rSorry ${player.displayName} §rthere are not enough items in this shipment")
            return
        }

        var drugName = "weed_g"
        when(shipment.drugType) {
            422 -> {
                drugName = "weed_g"
            }
            435 -> {
                drugName = "coke"
            }
            444 -> {
                drugName = "opium"
            }
            450 -> {
                drugName = "shroom"
            }
            451 -> {
                drugName = "truffle"
            }
        }
        val item = StockEx(connection).getInfo(drugName)
        StockEx(connection).setStock(item.stock+totalMatchingAmount,drugName)

        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §rhas completed a shipment")
        player.sendMessage("§cCR §8|§r Here is your money ${player.displayName} §r,${econ.format(shipment.price*shipment.amount)} in full")
        Delivery(connection).finishDelivery(player.uniqueId)
        for (slot in 0..4) {
            inv.setItemSilently(slot, null)
        }
    }
}