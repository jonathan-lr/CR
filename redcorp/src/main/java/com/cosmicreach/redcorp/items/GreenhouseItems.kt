package com.cosmicreach.redcorp.items

import com.cosmicreach.redcorp.menus.items.DecreaseItemAmount
import com.cosmicreach.redcorp.utils.Utils
import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.UUID

class GreenhouseItems {
    fun GreenhouseEntrance (greenhouseId: Int): ItemStack {
        val item = ItemStack(Material.GREEN_STAINED_GLASS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 201)
            nbt.setInteger("greenhouse-id", greenhouseId)
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

    fun GreenhouseExit (greenhouseId: Int): ItemStack {
        val item = ItemStack(Material.BLACK_STAINED_GLASS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 202)
            nbt.setInteger("greenhouse-id", greenhouseId)
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

    fun GreenhouseUpgrade (a: Int): ItemStack {
        var item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 203)
        }

        item = Utils().makeEdibleWithComponents(item, canAlwaysEat = true, saturation = 0f, nutrition = 0, consumeTime = 2.0, sound = "minecraft:entity.sniffer.eat")

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("greenhouse")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§2§lɢʀᴇᴇɴʜᴏᴜsᴇ ᴜᴘɢʀᴀᴅᴇ")

        val lore = listOf("§9Right click to use", "§9Max 2 per greenhouse")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun GreenhouseUpgrade2 (a: Int): ItemStack {
        var item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 204)
        }

        item = Utils().makeEdibleWithComponents(item, canAlwaysEat = true, saturation = 0f, nutrition = 0, consumeTime = 2.0, sound = "minecraft:entity.sniffer.eat")

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("greenhouse")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§2§lɢʀᴇᴇɴʜᴏᴜsᴇ ғᴜɴᴅɪɴɢ")

        val lore = listOf("§9Right click to use", "§9Max 2 per greenhouse")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }
}