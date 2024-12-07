package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.utils.TeleportActions
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem
import java.util.UUID

class PlayerItem(private val p: Player, private val t: Player, private val teleportActions: TeleportActions) : ControlItem<PagedGui<*>>() {
    override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
        val skull = ItemStack(Material.PLAYER_HEAD, 1)
        val skullM = skull.itemMeta as SkullMeta
        skullM.owningPlayer = Bukkit.getOfflinePlayer(t.uniqueId)
        skullM.setDisplayName("§f§lTeleport to ${t.displayName}")
        skull.setItemMeta(skullM)
        return ItemBuilder(skull)
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        if (clickType.isLeftClick) {
            teleportActions.runTeleport(p, t)
            gui.closeForAllViewers()
        }
    }

}