package com.cosmicreach.redcorp.items

import com.cosmicreach.redcorp.RedCorp
import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.trim.ArmorTrim
import org.bukkit.inventory.meta.trim.TrimMaterial
import org.bukkit.inventory.meta.trim.TrimPattern
import java.util.*

class WaterItems {
    fun waterPick (): ItemStack {
        val item = ItemStack(Material.NETHERITE_PICKAXE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 101)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§9§lWater §f§lPick")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Infused with the essence of the sea's embrace", "this pickaxe carves through stone with the fluidity of a river", "revealing treasures hidden within")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun waterShovel() : ItemStack {
        val item = ItemStack(Material.NETHERITE_SHOVEL, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 102)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§9§lWater §f§lShovel")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Crafted from the heart of mighty waterfalls", "this shovel shapes the land with the grace of flowing currents", "molding terrain with fluid ease")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun waterHoe() : ItemStack {
        val item = ItemStack(Material.NETHERITE_HOE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 103)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§9§lWater §f§lHoe")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Nurtured by the gentle touch of rain", "this hoe coaxes life from the earth", "turning barren fields into lush oases")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun waterAxe() : ItemStack {
        val item = ItemStack(Material.NETHERITE_AXE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 104)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§9§lWater §f§lAxe")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Born from the purest springs", "this axe's blade cleaves through trees like a deluge", "clearing paths with the force of rushing waters")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun waterSword() : ItemStack {
        val item = ItemStack(Material.NETHERITE_SWORD, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 105)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.SHARPNESS, 5, true)
        meta.addEnchant(Enchantment.SMITE, 5, true)
        meta.addEnchant(Enchantment.LOOTING, 3, true)
        meta.setDisplayName("§9§lWater §f§lSword")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Forged in the depths of the ocean's abyss", "this sword wields the power of crashing waves", "drenching foes in the relentless force of water")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun waterHelmet() : ItemStack {
        val item = ItemStack(Material.NETHERITE_HELMET, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 106)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.LAPIS, TrimPattern.WARD)

        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true)
        meta.addEnchant(Enchantment.RESPIRATION, 5, true)
        meta.setDisplayName("§9§lWater §f§lHelmet")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Crafted from the essence of the ocean's depths", "this armor grants its wearer the resilience of coral reefs", "protecting them in the depths of adversity")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun waterChestplate() : ItemStack {
        val item = ItemStack(Material.NETHERITE_CHESTPLATE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 107)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.LAPIS, TrimPattern.EYE)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true)
        meta.setDisplayName("§9§lWater §f§lChestplate")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Crafted from the essence of the ocean's depths", "this armor grants its wearer the resilience of coral reefs", "protecting them in the depths of adversity")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun waterLeggings() : ItemStack {
        val item = ItemStack(Material.NETHERITE_LEGGINGS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 108)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.LAPIS, TrimPattern.WILD)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.SWIFT_SNEAK, 3, true)
        meta.setDisplayName("§9§lWater §f§lLeggings")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Crafted from the essence of the ocean's depths", "this armor grants its wearer the resilience of coral reefs", "protecting them in the depths of adversity")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun waterBoots() : ItemStack {
        val item = ItemStack(Material.NETHERITE_BOOTS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 109)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.LAPIS, TrimPattern.WARD)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FEATHER_FALLING, 4, true)
        meta.addEnchant(Enchantment.DEPTH_STRIDER, 4, true)
        meta.setDisplayName("§9§lWater §f§lBoots")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Crafted from the essence of the ocean's depths", "this armor grants its wearer the resilience of coral reefs", "protecting them in the depths of adversity")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun waterWings() : ItemStack {
        val item = ItemStack(Material.ELYTRA, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 110)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("water")

        val modifier1 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "water-wings-armor"), 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
        val modifier2 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "water-wings-armor-toughness"), 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
        val modifier3 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "water-wings-knockback-resistance"), 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addAttributeModifier(Attribute.ARMOR, modifier1)
        meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, modifier2)
        meta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE, modifier3)

        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true)
        meta.setDisplayName("§9§lWater §f§lWings")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Crafted from the scales of aquatic beasts", "these wings allow you to glide through water and air alike", "uniting the realms of earth and sea in graceful harmony")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }
}