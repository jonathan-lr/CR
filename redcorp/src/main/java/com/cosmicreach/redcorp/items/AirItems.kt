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

class AirItems {
    var speedAttribute = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "earth-speed"), 0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.HEAD)

    fun airPick (): ItemStack {
        val item = ItemStack(Material.NETHERITE_PICKAXE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 101)
            nbt.setString("item-type", "air")
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§7§lAir §f§lPick")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Infused with the essence of high-flying winds", "This pickaxe pierces through stone like a hurricane", "Uncovering secrets hidden in the earth")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun airShovel() : ItemStack {
        val item = ItemStack(Material.NETHERITE_SHOVEL, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 102)
            nbt.setString("item-type", "air")
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§7§lAir §f§lShovel")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Crafted from the feathers of mythical birds", "This shovel sculpts the sky", "Shaping the terrain with the grace of a gentle breeze")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun airHoe() : ItemStack {
        val item = ItemStack(Material.NETHERITE_HOE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 103)
            nbt.setString("item-type", "air")
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§7§lAir §f§lHoe")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Channeling the breath of the wind", "This hoe nurtures life", "Transforming barren fields into lush gardens")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun airAxe() : ItemStack {
        val item = ItemStack(Material.NETHERITE_AXE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 104)
            nbt.setString("item-type", "air")
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§7§lAir §f§lAxe")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Born from the breath of storms", "This axe's blade slices through trees like a gust of wind", "Clearing paths with effortless precision")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun airSword() : ItemStack {
        val item = ItemStack(Material.NETHERITE_SWORD, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 105)
            nbt.setString("item-type", "air")
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.SHARPNESS, 7, true)
        meta.addEnchant(Enchantment.LOOTING, 3, true)
        meta.addEnchant(Enchantment.KNOCKBACK, 3, true)
        meta.setDisplayName("§7§lAir §f§lSword")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Tempered in the heart of swirling tornadoes", "This sword cuts through the air with unmatched swiftness", "Leaving foes breathless")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun airHelmet() : ItemStack {
        val item = ItemStack(Material.NETHERITE_HELMET, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 106)
            nbt.setString("item-type", "air")
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedAttribute)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.SILENCE)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.RESPIRATION, 3, true)
        meta.setDisplayName("§7§lAir §f§lHelmet")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Woven from the threads of air itself", "This armor grants its wearer the grace and agility of a breeze", "Protecting them in the whirlwind of battle")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun airChestplate() : ItemStack {
        val item = ItemStack(Material.NETHERITE_CHESTPLATE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 107)
            nbt.setString("item-type", "air")
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedAttribute)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.SILENCE)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
        meta.setDisplayName("§7§lAir §f§lChestplate")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Woven from the threads of air itself", "This armor grants its wearer the grace and agility of a breeze", "Protecting them in the whirlwind of battle")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun airLeggings() : ItemStack {
        val item = ItemStack(Material.NETHERITE_LEGGINGS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 108)
            nbt.setString("item-type", "air")
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedAttribute)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.SILENCE)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.SWIFT_SNEAK, 3, true)
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
        meta.setDisplayName("§7§lAir §f§lLeggings")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Woven from the threads of air itself", "This armor grants its wearer the grace and agility of a breeze", "Protecting them in the whirlwind of battle")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun airBoots() : ItemStack {
        val item = ItemStack(Material.NETHERITE_BOOTS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 109)
            nbt.setString("item-type", "air")
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedAttribute)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.DIAMOND, TrimPattern.SILENCE)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FEATHER_FALLING, 6, true)
        meta.setDisplayName("§7§lAir §f§lBoots")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Woven from the threads of air itself", "This armor grants its wearer the grace and agility of a breeze", "Protecting them in the whirlwind of battle")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun airWings() : ItemStack {
        val item = ItemStack(Material.ELYTRA, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 110)
            nbt.setString("item-type", "air")
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("sky")

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedAttribute)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        val modifier1 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "air-wings-armor"), 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
        val modifier2 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "air-wings-armor-toughness"), 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
        val modifier3 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "air-wings-knockback-resistance"), 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)

        meta.addAttributeModifier(Attribute.ARMOR, modifier1)
        meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, modifier2)
        meta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE, modifier3)

        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
        meta.setDisplayName("§7§lAir §f§lWings")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Crafted from the feathers of the sky's mightiest birds", "These wings allow you to ride the winds", "Uniting earth and air in a majestic flight")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }
}