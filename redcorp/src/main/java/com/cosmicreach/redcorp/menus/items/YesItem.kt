package com.cosmicreach.redcorp.menus.items

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem

class YesItem(private val confirm : HashMap<Player, Boolean>) : ControlItem<Gui>() {
    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§2§lYes").setLegacyLore((mutableListOf("§f§lClick to say yes")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        confirm[player] = true
        gui.closeForAllViewers()
    }
}