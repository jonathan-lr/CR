package com.cosmicreach.redcorp.menus.items

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class FruitGambleItem(
    private val index: Int,
) : AbstractItem() {
    private val gambleItems = listOf(
        Material.APPLE,
        Material.SWEET_BERRIES,
        Material.MELON_SLICE,
        Material.GLOW_BERRIES,
        Material.ENCHANTED_GOLDEN_APPLE
    )

    private val gambleItemsText = listOf(
        "§f§lᴀᴘᴘʟᴇ",
        "§c§lʙᴇʀʀɪᴇs",
        "§2§lᴍᴇʟᴏɴ",
        "§5§lɢʟᴏᴡ ʙᴇʀʀɪᴇs",
        "§6§lɢᴏʟᴅᴇɴ ᴀᴘᴘʟᴇ"
    )

    override fun getItemProvider(): ItemProvider {
        val item = ItemStack(gambleItems[wrapValue(index)], 1)
        val meta = item.itemMeta as ItemMeta
        meta.setDisplayName(gambleItemsText[wrapValue(index)])
        item.setItemMeta(meta)
        return ItemBuilder(item)
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        //Do nothing
        notifyWindows()
    }

    private fun wrapValue(value: Int): Int {
        return when {
            value < 0 -> 4
            value > 4 -> 0
            else -> value
        }
    }
}