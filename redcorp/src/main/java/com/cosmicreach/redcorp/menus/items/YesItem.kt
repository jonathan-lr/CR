package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Progress
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem

class YesItem(private val type : Int) : ControlItem<Gui>() {
    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§2§lYes").setLegacyLore((mutableListOf("§f§lClick to say yes")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val connection = RedCorp.getPlugin().getConnection()!!
        Progress(connection).updateConfirm(player.uniqueId, type, true)
        gui.closeForAllViewers()
    }
}