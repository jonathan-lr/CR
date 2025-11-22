package com.cosmicreach.redcorp.items

import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ShipmentItems {
    fun ShipmentBox (a: Int, id: String): ItemStack {
        val item = ItemStack(Material.BARREL, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 210)
            nbt.setInteger("drop-id", id.toInt())
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("shipment")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§2§lsʜɪᴘᴍᴇɴᴛ ʙᴏx")

        val lore = listOf("§9Admin shit")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }
}