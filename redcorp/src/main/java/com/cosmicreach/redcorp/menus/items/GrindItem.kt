package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.inventory.event.UpdateReason
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem

class GrindItem(private val inv : VirtualInventory) : ControlItem<Gui>() {
    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.GRINDSTONE).setDisplayName("§2§lGrind drugs").setLegacyLore((mutableListOf("§f§lClick to grind")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val item = inv.getItem(0) ?: return
        if (Utils().checkID(item, arrayOf(432))) {
            inv.setItem(UpdateReason.SUPPRESSED, 0, DrugItems().GroundCokeLeaf(item.amount))
        }

        if (Utils().checkID(item, arrayOf(424))) {
            inv.setItem(UpdateReason.SUPPRESSED, 0, DrugItems().GroundWeed(item.amount))
        }
    }
}