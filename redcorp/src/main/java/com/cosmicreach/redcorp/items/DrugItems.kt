package com.cosmicreach.redcorp.items

import com.cosmicreach.redcorp.utils.Utils
import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.components.FoodComponent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
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
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("agingbarrel")
        meta.setCustomModelDataComponent(cm)

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setDisplayName("§2§lᴀɢɪɴɢ ʙᴀʀʀᴇʟ")
        item.setItemMeta(meta)

        return item
    }

    fun DrugStick (a : Int): ItemStack {
        val item = ItemStack(Material.STICK, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 402)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("tag")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§c§lᴅʀᴜɢ ʙᴀᴛᴛᴏɴ")

        item.setItemMeta(meta)
        return item
    }

    fun CoffieMachine (a : Int): ItemStack {
        val item = ItemStack(Material.BREWING_STAND, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 403)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("coffeemachine")
        meta.setCustomModelDataComponent(cm)

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setDisplayName("§2§lᴄᴏғғɪᴇ ᴍᴀᴄʜɪɴᴇ")
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
        var item = ItemStack(Material.BLAZE_ROD, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 423)
        }

        item = Utils().makeEdibleWithComponents(item, canAlwaysEat = true, saturation = 0f, nutrition = 0, consumeTime = 5.0, animation = "bow", sound = "minecraft:entity.breeze.inhale")

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("weed")

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
        var item = ItemStack(Material.SUGAR, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 432)
        }

        item = Utils().makeEdibleWithComponents(item, canAlwaysEat = true, saturation = 0f, nutrition = 0, consumeTime = 5.0, animation = "toot_horn", sound = "minecraft:entity.sniffer.sniffing")

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
        var item = ItemStack(Material.SWEET_BERRIES, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 440)
        }

        item = Utils().makeEdibleWithComponents(item, canAlwaysEat = true, saturation = 0f, nutrition = 0, consumeTime = 5.0, animation = "toot_horn", sound = "minecraft:entity.sniffer.sniffing")

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

    // Shroom Stuff
    fun Shrooms (a : Int): ItemStack {
        var item = ItemStack(Material.RED_MUSHROOM, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 450)
        }

        item = Utils().makeEdibleWithComponents(item, canAlwaysEat = true, saturation = 0f, nutrition = 0, consumeTime = 5.0, sound = "minecraft:entity.sniffer.eat")

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("shroom")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§4§lsʜʀᴏᴏᴍs")
        item.setItemMeta(meta)

        return item
    }

    fun Truffles (a : Int): ItemStack {
        var item = ItemStack(Material.BROWN_MUSHROOM, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 451)
        }

        item = Utils().makeEdibleWithComponents(item, canAlwaysEat = true, saturation = 0f, nutrition = 0, consumeTime = 5.0, sound = "minecraft:entity.goat.screaming.eat")

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("truffles")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§4§lᴛʀᴜғғʟᴇs")
        item.setItemMeta(meta)

        return item
    }

    // Alchol Stuff
    fun Larger (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 460)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("larger")

        meta.setMaxStackSize(16)
        meta.addCustomEffect(PotionEffect(PotionEffectType.NAUSEA, 1200, 0, true, false, false), true)
        meta.addCustomEffect(PotionEffect(PotionEffectType.STRENGTH, 200, 0, true, false, false), true)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§e§lʟᴀʀɢᴇʀ")
        item.setItemMeta(meta)

        return item
    }

    fun Cider (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 461)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("cider")

        meta.setMaxStackSize(16)
        meta.addCustomEffect(PotionEffect(PotionEffectType.NAUSEA, 1200, 0, true, false, false), true)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§6§lᴄɪᴅᴇʀ")
        item.setItemMeta(meta)

        return item
    }

    fun Wine (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 462)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("wine")

        meta.setMaxStackSize(16)
        meta.addCustomEffect(PotionEffect(PotionEffectType.NAUSEA, 2400, 0, true, false, false), true)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§4§lᴡɪɴᴇ")
        item.setItemMeta(meta)

        return item
    }

    fun Vodka (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 463)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("vodka")

        meta.setMaxStackSize(16)
        meta.addCustomEffect(PotionEffect(PotionEffectType.NAUSEA, -1, 0, true, false, false), true)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§f§lᴠᴏᴅᴋᴀ")
        item.setItemMeta(meta)

        return item
    }

    // Coffee Stuff
    fun CoffeeBean (a : Int): ItemStack {
        val item = ItemStack(Material.COCOA_BEANS, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 473) // Ik it should be 470 but I did this later
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("coffee")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§f§lᴄᴏғғᴇᴇ ʙᴇᴀɴs")
        item.setItemMeta(meta)

        return item
    }

    fun weakCoffee (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 470)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("coffee_small")

        meta.setMaxStackSize(16)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        item.setItemMeta(meta)

        return item
    }

    fun mediumCoffee (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 471)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("coffee_medium")

        meta.setMaxStackSize(16)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        item.setItemMeta(meta)

        return item
    }

    fun strongCoffee (a : Int): ItemStack {
        val item = ItemStack(Material.POTION, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 472)
        }

        val meta = item.itemMeta as PotionMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("coffee_large")

        meta.setMaxStackSize(16)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        item.setItemMeta(meta)

        return item
    }
}