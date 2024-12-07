package com.cosmicreach.redcorp.items

import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class CustomItems {
    fun MRE (a : Int): ItemStack {
        val item = ItemStack(Material.COOKED_PORKCHOP, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("mre")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§4§lRed§8Corp §f§lMRE")

        val lore = listOf("§4§lRed§8Corp§f's sinister sustenance: A pork-packed MRE,", "§fA vile delicacy born of darkness,", "§fSating their hunger for power, one bite at a time.")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Arlbaro (a : Int): ItemStack {
        val item = ItemStack(Material.COOKIE, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("arlbaro")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§c§lArlbaro Red")

        val lore = listOf("§fFumar §4acorta la vida")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Hammer (a : Int): ItemStack {
        val item = ItemStack(Material.WOODEN_AXE, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("hammer")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§6§lYorick's Hammer")

        val lore = listOf("§fWhen found, return to Yorick")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun IvansBeats (a : Int): ItemStack {
        val item = ItemStack(Material.BEETROOT, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 1)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("beets")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§oIvan's Premium beets")
        item.setItemMeta(meta)

        return item
    }

    fun Gavel (a : Int): ItemStack {
        val item = ItemStack(Material.WOODEN_AXE, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 2)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("gavel")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§6§lJudges Hammer")

        val lore = listOf("§fWhen found, return to Judge")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun TeleportScroll (a : Int): ItemStack {
        val item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 3)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("scroll")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lTeleport Scroll")

        val lore = listOf("§fAllows teleport to player")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun DeathScroll (a : Int): ItemStack {
        val item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 5)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("scroll")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lDeath Scroll")

        val lore = listOf("§fAllows teleport to death location")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun HomeScroll (a : Int): ItemStack {
        val item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 6)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("scroll")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lHome Scroll")

        val lore = listOf("§fAllows teleport to their bed")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun TeleportAnchor (a : Int): ItemStack {
        val item = ItemStack(Material.RECOVERY_COMPASS, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 4)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("anchor")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lTeleport Anchor")

        val lore = listOf("§fAllows players to teleport to you")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Penis (a : Int): ItemStack {
        val item = ItemStack(Material.ROTTEN_FLESH, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("penis")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lPenus")

        val lore = listOf("§fLol its kinda funny")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun RedLanyard (a : Int): ItemStack {
        val item = ItemStack(Material.NAME_TAG, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("red-lanyard")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§c§lRed §f§lLanyard")

        val lore = listOf("§fUsed by the CCP for security")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun OrangeLanyard (a : Int): ItemStack {
        val item = ItemStack(Material.NAME_TAG, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("orange-lanyard")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§6§lOrange §f§lLanyard")

        val lore = listOf("§fUsed by the CCP for security")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun PinkLanyard (a : Int): ItemStack {
        val item = ItemStack(Material.NAME_TAG, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("pink-lanyard")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§d§lPink §f§lLanyard")

        val lore = listOf("§fUsed by the CCP for security")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun GreenLanyard (a : Int): ItemStack {
        val item = ItemStack(Material.NAME_TAG, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("green-lanyard")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§a§lGreen §f§lLanyard")

        val lore = listOf("§fUsed by the CCP for security")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun BlueLanyard (a : Int): ItemStack {
        val item = ItemStack(Material.NAME_TAG, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("blue-lanyard")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§9§lBlue §f§lLanyard")

        val lore = listOf("§fUsed by the CCP for security")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun DebugStick (a : Int): ItemStack {
        val item = ItemStack(Material.STICK, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 2886)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("tag")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§c§lDebug Stick")

        item.setItemMeta(meta)
        return item
    }
}