package com.cosmicreach.redcorp.items

import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class DungeonItems {
    fun dungeonToken (): ItemStack {
        val item = ItemStack(Material.GOLD_INGOT, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 50)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("dt")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§e§lᴅᴜɴɢᴇᴏɴ ᴛᴏᴋᴇɴ")

        val lore = listOf("A mysterious relic,", "Said to unlock the secrets of hidden labyrinths,", "Coveted by seekers of forgotten treasures.")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun dungeonTokenHard (): ItemStack {
        val item = ItemStack(Material.GOLD_INGOT, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 51)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("dt")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§4§lᴅᴜɴɢᴇᴏɴ ᴛᴏᴋᴇɴ ʜᴀʀᴅ")

        val lore = listOf("A mysterious relic,", "Said to unlock the secrets of hidden labyrinths,", "Coveted by seekers of forgotten treasures.")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun airReward (): ItemStack {
        val item = ItemStack(Material.GOLD_NUGGET, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 52)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§7§lᴀɪʀ §f§lᴅᴜɴɢᴇᴏɴ ʀᴇᴡᴀʀᴅ")

        val lore = listOf("A rare prize,", "Soaring with the winds of adventure,", "Promising newfound heights to those who claim it.")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun fireReward (): ItemStack {
        val item = ItemStack(Material.GOLD_NUGGET, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 53)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§c§lғɪʀᴇ §f§lᴅᴜɴɢᴇᴏɴ ʀᴇᴡᴀʀᴅ")

        val lore = listOf("A blazing treasure,", "Forged in the crucible of challenge,", "Bestowing infernal power to those who dare to conquer the flames.")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun waterReward (): ItemStack {
        val item = ItemStack(Material.GOLD_NUGGET, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 54)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§9§lᴡᴀᴛᴇʀ §f§lᴅᴜɴɢᴇᴏɴ ʀᴇᴡᴀʀᴅ")

        val lore = listOf("A liquid gem,", "Drawn from the depths of mystery,", "Granting the bearer mastery over the ever-shifting tides of destiny.")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun earthReward (): ItemStack {
        val item = ItemStack(Material.GOLD_NUGGET, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 55)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§2§lᴇᴀʀᴛʜ §f§lᴅᴜɴɢᴇᴏɴ ʀᴇᴡᴀʀᴅ")

        val lore = listOf("A rugged relic,", "Hewn from the depths of the earth's secrets,", "Granting resilience and grounding to those who unearth its power.")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }
}