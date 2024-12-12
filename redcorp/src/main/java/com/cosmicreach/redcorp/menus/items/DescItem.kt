package com.cosmicreach.redcorp.menus.items

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.ItemWrapper
import xyz.xenondevs.invui.item.impl.AbstractItem

class DescItem(
    private var descItem: ItemStack,
    private var descTitle: String = "text",
    private var descLore: String = "text"
) : AbstractItem() {
    override fun getItemProvider(): ItemProvider {
        val displayItem = descItem.clone()
        val meta = displayItem.itemMeta as ItemMeta
        meta.setDisplayName(descTitle)
        meta.lore = mutableListOf(descLore)
        displayItem.setItemMeta(meta)
        return ItemWrapper(displayItem)
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        //
    }
}