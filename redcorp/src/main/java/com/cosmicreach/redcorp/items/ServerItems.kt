package com.cosmicreach.redcorp.items

import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ServerItems {
    fun emptyProgressTool(): ItemStack {
        val item = ItemStack(Material.NETHERITE_HOE) // or your invisible tool
        val meta = item.itemMeta
        meta.addItemFlags(
            ItemFlag.HIDE_ATTRIBUTES,
            ItemFlag.HIDE_UNBREAKABLE,
            ItemFlag.HIDE_ENCHANTS
        )
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("blank")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§r") // no name
        item.itemMeta = meta
        return item
    }

    fun SlotMachine (a : Int): ItemStack {
        val item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 399)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("slots")
        meta.setCustomModelDataComponent(cm)

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setDisplayName("§2§lSlot machine")
        item.setItemMeta(meta)

        return item
    }
}