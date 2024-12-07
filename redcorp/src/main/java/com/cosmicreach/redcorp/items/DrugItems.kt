package com.cosmicreach.redcorp.items

import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType

class DrugItems {
    // Drug Makers
    fun Grinder (a : Int): ItemStack {
        val item = ItemStack(Material.RECOVERY_COMPASS, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 400)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("grinder")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§2§lɢʀɪɴᴅᴇʀ")
        item.setItemMeta(meta)

        return item
    }

    fun AgingBarrel (a : Int): ItemStack {
        val item = ItemStack(Material.BARREL, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 401)
        }

        val meta = item.itemMeta as ItemMeta

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setDisplayName("§2§lᴀɢɪɴɢ ʙᴀʀʀᴇʟ")
        item.setItemMeta(meta)

        return item
    }

    // WEED STUFF
    fun WeedSeed (a : Int): ItemStack {
        val item = ItemStack(Material.WHEAT_SEEDS, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 420)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("weed")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§2§lᴄᴀɴɴᴀʙɪꜱ ꜱᴇᴇᴅꜱ")
        item.setItemMeta(meta)

        return item
    }

    fun Weed (a : Int): ItemStack {
        val item = ItemStack(Material.WHEAT, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 421)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("weed")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§2§lᴄᴀɴɴᴀʙɪꜱ")
        item.setItemMeta(meta)

        return item
    }

    fun GroundWeed (a : Int): ItemStack {
        val item = ItemStack(Material.GREEN_DYE, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 422)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("weed")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§2§lɢʀᴏᴜɴᴅ ᴄᴀɴɴᴀʙɪs")
        item.setItemMeta(meta)

        return item
    }

    fun Spliff (a : Int): ItemStack {
        val item = ItemStack(Material.BLAZE_ROD, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 423)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent
        val food = meta.food

        food.setCanAlwaysEat(true)
        food.nutrition = 1
        food.saturation = 1.0F

        cm.strings = mutableListOf("weed")

        meta.setFood(food)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§6§lsᴘʟɪғғ")
        item.setItemMeta(meta)

        return item
    }

    // Coca Stuff
    fun CocaSeed (a : Int): ItemStack {
        val item = ItemStack(Material.BEETROOT_SEEDS, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 430)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("coke")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§f§lᴄᴏᴄᴀ ꜱᴇᴇᴅꜱ")
        item.setItemMeta(meta)

        return item
    }

    fun CocaLeaf (a : Int): ItemStack {
        val item = ItemStack(Material.KELP, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 431)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("coke")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§f§lᴄᴏᴄᴀ ʟᴇᴀꜰ")
        item.setItemMeta(meta)

        return item
    }

    fun Coke (a : Int): ItemStack {
        val item = ItemStack(Material.SUGAR, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 432)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("coke")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§f§lᴄᴏᴋᴇ")
        item.setItemMeta(meta)

        return item
    }

    // Opium Stuff
    fun OpiumFlower (a : Int): ItemStack {
        val item = ItemStack(Material.SWEET_BERRIES, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 440)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("opium")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§4§lᴘᴏᴘᴘʏ")
        item.setItemMeta(meta)

        return item
    }

    fun Opium (a : Int): ItemStack {
        val item = ItemStack(Material.GLOWSTONE_DUST, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 441)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("opium")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§4§lᴏᴘɪᴜᴍ")
        item.setItemMeta(meta)

        return item
    }

    // Alchol Stuff
    fun Larger (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 450)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("larger")

        meta.setMaxStackSize(16)
        meta.basePotionType = PotionType.LEAPING
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§e§lʟᴀʀɢᴇʀ")
        item.setItemMeta(meta)

        return item
    }

    fun Cider (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 451)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("cider")

        meta.setMaxStackSize(16)
        meta.basePotionType = PotionType.FIRE_RESISTANCE
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§6§lᴄɪᴅᴇʀ")
        item.setItemMeta(meta)

        return item
    }

    fun Wine (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 452)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("wine")

        meta.setMaxStackSize(16)
        meta.basePotionType = PotionType.HEALING
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§4§lᴡɪɴᴇ")
        item.setItemMeta(meta)

        return item
    }

    fun Vodka (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 453)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("vodka")

        meta.setMaxStackSize(16)
        meta.basePotionType = PotionType.HEALING
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§f§lᴠᴏᴅᴋᴀ")
        item.setItemMeta(meta)

        return item
    }
}