package com.cosmicreach.redcorp.items

import com.cosmicreach.redcorp.RedCorp
import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.trim.ArmorTrim
import org.bukkit.inventory.meta.trim.TrimMaterial
import org.bukkit.inventory.meta.trim.TrimPattern

class FireItems {
    fun firePick (): ItemStack {
        val item = ItemStack(Material.NETHERITE_PICKAXE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 101)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§c§lFire §f§lPick")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Anointed in the flames of subterranean furnaces", "With a second line", "This pickaxe devours minerals with the heat of a smoldering volcano")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun fireShovel() : ItemStack {
        val item = ItemStack(Material.NETHERITE_SHOVEL, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 102)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§c§lFire §f§lShovel")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Harnessing the combustion of the earth's core", "This shovel crumbles rocks", "& forges new paths with explosive power")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun fireHoe() : ItemStack {
        val item = ItemStack(Material.NETHERITE_HOE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 103)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§c§lFire §f§lHoe")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Touched by the sun's touch", "This hoe summons fire's passion", "Turning soil to a fertile blaze")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun fireAxe() : ItemStack {
        val item = ItemStack(Material.NETHERITE_AXE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 104)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§c§lFire §f§lAxe")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Forged in magma's crucible", "This axe erupts with every swing", "Shattering obstacles with volcanic force")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun fireSword() : ItemStack {
        val item = ItemStack(Material.NETHERITE_SWORD, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 105)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.SHARPNESS, 5, true)
        meta.addEnchant(Enchantment.BANE_OF_ARTHROPODS, 5, true)
        meta.addEnchant(Enchantment.SMITE, 5, true)
        meta.addEnchant(Enchantment.LOOTING, 3, true)
        meta.addEnchant(Enchantment.FIRE_ASPECT, 3, true)
        meta.setDisplayName("§c§lFire §f§lSword")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Tempered in molten fury", "This sword wields the blaze's might", "Turning enemies to ash with every searing strike")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun fireHelmet() : ItemStack {
        val item = ItemStack(Material.NETHERITE_HELMET, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 106)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.SHAPER)

        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 6, true)
        meta.setDisplayName("§c§lFire §f§lHelmet")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Forged in the heart of roaring flames", "This armor consumes fire's essence", "Wrapping its wearer in the inferno's undying fervor")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun fireChestplate() : ItemStack {
        val item = ItemStack(Material.NETHERITE_CHESTPLATE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 107)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.SENTRY)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 6, true)
        meta.setDisplayName("§c§lFire §f§lChestplate")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Forged in the heart of roaring flames", "This armor consumes fire's essence", "Wrapping its wearer in the inferno's undying fervor")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun fireLeggings() : ItemStack {
        val item = ItemStack(Material.NETHERITE_LEGGINGS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 108)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.DUNE)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 6, true)
        meta.addEnchant(Enchantment.SWIFT_SNEAK, 3, true)
        meta.setDisplayName("§c§lFire §f§lLeggings")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Forged in the heart of roaring flames", "This armor consumes fire's essence", "Wrapping its wearer in the inferno's undying fervor")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun fireBoots() : ItemStack {
        val item = ItemStack(Material.NETHERITE_BOOTS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 109)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.EYE)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 6, true)
        meta.addEnchant(Enchantment.FEATHER_FALLING, 4, true)
        meta.setDisplayName("§c§lFire §f§lBoots")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Forged in the heart of roaring flames", "This armor consumes fire's essence", "Wrapping its wearer in the inferno's undying fervor")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun fireWings() : ItemStack {
        val item = ItemStack(Material.ELYTRA, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 110)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("fire")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        val modifier1 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "fire-wings-armor"), 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
        val modifier2 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "fire-wings-armor-toughness"), 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
        val modifier3 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "fire-wings-knockback-resistance"), 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)

        meta.addAttributeModifier(Attribute.ARMOR, modifier1)
        meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, modifier2)
        meta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE, modifier3)

        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 6, true)
        meta.setDisplayName("§c§lFire §f§lWings")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Born from ember and heat", "These wings grant mastery over flames", "Channeling fire's heat into a warrior's grasp")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }
}