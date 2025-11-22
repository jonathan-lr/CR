package com.cosmicreach.redcorp.items

import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.UUID

class GreenhouseItems {
    fun GreenhouseEntrance (playerId: UUID): ItemStack {
        val item = ItemStack(Material.GREEN_STAINED_GLASS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 201)
            nbt.setUUID("player-id", playerId)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("greenhouse")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§2§lɢʀᴇᴇɴʜᴏᴜsᴇ ᴇɴᴛʀᴀɴᴄᴇ")

        val lore = listOf("§9Your own personal drug lab", "§9Place in base and right click to teleport", "§9Can be broken")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun GreenhouseExit (playerId: UUID): ItemStack {
        val item = ItemStack(Material.BLACK_STAINED_GLASS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 202)
            nbt.setUUID("player-id", playerId)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("greenhouse")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§2§lɢʀᴇᴇɴʜᴏᴜsᴇ ᴇxɪᴛ")

        val lore = listOf("§9Your own personal drug lab", "§9Place in greenhouse and right click to teleport", "§9Can be broken")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }
}