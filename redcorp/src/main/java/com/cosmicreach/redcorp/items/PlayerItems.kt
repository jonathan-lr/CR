package com.cosmicreach.redcorp.items

import com.cosmicreach.redcorp.utils.Utils
import de.tr7zw.nbtapi.NBT
import de.tr7zw.nbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.components.FoodComponent
import org.bukkit.inventory.meta.components.consumable.ConsumableComponent

class PlayerItems {
    fun MRE (a : Int): ItemStack {
        val item = ItemStack(Material.COOKED_PORKCHOP, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("mre")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§4§lʀᴇᴅ§8ᴄᴏʀᴘ §f§lᴍʀᴇ")

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
        meta.setDisplayName("§c§lᴀʀʟʙᴀʀᴏ ʀᴇᴅ")

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
        meta.setDisplayName("§6§lʏᴏʀɪᴄᴋ's ʜᴀᴍᴍᴇʀ")

        val lore = listOf("§fWhen found, return to Yorick")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Hammer2 (a : Int): ItemStack {
        var item = ItemStack(Material.NETHERITE_AXE, a)

        item = Utils().makeEdibleWithComponents(item, canAlwaysEat = true, saturation = 20.0f, nutrition = 20)

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("hammer")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§6§lʏᴏʀɪᴄᴋ's ʜᴀᴍᴍᴇʀ")

        val lore = listOf("§fWhen found, return to Yorick")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Lanyard (a : Int, type: String): ItemStack {
        val item = ItemStack(Material.NAME_TAG, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("${type}-lanyard")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§c§l${type.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }} §f§lʟᴀɴʏᴀʀᴅ")

        val lore = listOf("§fUsed by the CCP for security")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }


    fun Awp (a: Int): ItemStack {
        val item = ItemStack(Material.BOW, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 800)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("awp")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§9§lᴀᴡᴘ")

        item.setItemMeta(meta)
        return item
    }

    fun Kip (a : Int): ItemStack {
        val item = ItemStack(Material.COOKED_CHICKEN, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("kip")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§c§lʏᴇʙᴏ §5ᴋɪᴘʙᴜʀɢᴇʀ")

        val lore = listOf("§fKip burger init")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }
}