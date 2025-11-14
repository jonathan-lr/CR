package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Delivery
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem

class ShipmentItem() : ControlItem<Gui>() {
    private val connection = RedCorp.getPlugin().getConnection()!!

    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.BARREL).setDisplayName("§2§lStart Shipment").setLegacyLore((mutableListOf("§f§lClick to start delivery")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val shipment = Delivery(connection).getDelivery(player.uniqueId)

        Bukkit.broadcastMessage("§cCR §8|§r Debug info ${shipment.toString()}")
    }
}