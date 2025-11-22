package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.menus.Shipment
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem
import xyz.xenondevs.invui.window.Window

class ShipmentOpen(
    private var drugType: Int
) : ControlItem<Gui>() {
    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.BARREL).setDisplayName("§2§lOpen Shipment Menu").setLegacyLore((mutableListOf("§f§lClick to open")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val window = Window.single()
            .setViewer(player)
            .setTitle("§2Shipments")
            .setGui(Shipment(drugType).makeGUI(player))
            .build()

        window.open()
    }
}