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
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.trim.ArmorTrim
import org.bukkit.inventory.meta.trim.TrimMaterial
import org.bukkit.inventory.meta.trim.TrimPattern

class EarthItems {
    var speedAttribute = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "earth-speed"), -0.1, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlotGroup.HEAD)

    fun earthPick (): ItemStack {
        val item = ItemStack(Material.NETHERITE_PICKAXE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 101)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")
        
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§2§lEarth §f§lPick")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Born from bedrock's core", "This pickaxe channels the earth's endurance", "Shattering blocks with unwavering strength")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun earthShovel() : ItemStack {
        val item = ItemStack(Material.NETHERITE_SHOVEL, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 102)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§2§lEarth §f§lShovel")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Unleash the tremors with each dig", "As this shovel commands the earth's upheaval", "Turning soil to kingdom")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun earthHoe() : ItemStack {
        val item = ItemStack(Material.NETHERITE_HOE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 103)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§2§lEarth §f§lHoe")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Touched by fertile plains", "This hoe calls forth life from the earth", "Turning barren fields into lush bounty")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun earthAxe() : ItemStack {
        val item = ItemStack(Material.NETHERITE_AXE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 104)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.EFFICIENCY, 10, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.setDisplayName("§2§lEarth §f§lAxe")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Infused with the essence of landscapes", "This axe reshapes the world", "Its blade sculpting terrain as nature's hand")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun earthSword() : ItemStack {
        val item = ItemStack(Material.NETHERITE_SWORD, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 105)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.addEnchant(Enchantment.SHARPNESS, 8, true)
        meta.addEnchant(Enchantment.SMITE, 3, true)
        meta.addEnchant(Enchantment.LOOTING, 3, true)
        meta.setDisplayName("§2§lEarth §f§lSword")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("With the weight of mountains", "This hammer crushes and molds", "Reshaping ores and landscapes alike")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun earthHelmet() : ItemStack {
        val item = ItemStack(Material.NETHERITE_HELMET, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 106)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedAttribute)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.EMERALD, TrimPattern.HOST)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
        meta.setDisplayName("§2§lEarth §f§lHelmet")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Forged from stone's embrace", "This armor makes earth your shield", "& mountains your armor")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun earthChestplate() : ItemStack {
        val item = ItemStack(Material.NETHERITE_CHESTPLATE, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 107)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedAttribute)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.EMERALD, TrimPattern.DUNE)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
        meta.setDisplayName("§2§lEarth §f§lChestplate")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Forged from stone's embrace", "This armor makes earth your shield", "& mountains your armor")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun earthLeggings() : ItemStack {
        val item = ItemStack(Material.NETHERITE_LEGGINGS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 108)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedAttribute)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.EMERALD, TrimPattern.COAST)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.SWIFT_SNEAK, 3, true)
        meta.setDisplayName("§2§lEarth §f§lLeggings")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Forged from stone's embrace", "This armor makes earth your shield", "& mountains your armor")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun earthBoots() : ItemStack {
        val item = ItemStack(Material.NETHERITE_BOOTS, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 109)
        }

        val meta = item.itemMeta as ArmorMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedAttribute)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        meta.trim = ArmorTrim(TrimMaterial.EMERALD, TrimPattern.SENTRY)
        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FEATHER_FALLING, 4, true)
        meta.setDisplayName("§2§lEarth §f§lBoots")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Forged from stone's embrace", "This armor makes earth your shield", "& mountains your armor")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }

    fun earthWings() : ItemStack {
        val item = ItemStack(Material.ELYTRA, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 110)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("earth")

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedAttribute)
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)

        val modifier1 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "earth-wings-armor"), 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
        val modifier2 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "earth-wings-armor-toughness"), 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)
        val modifier3 = AttributeModifier(NamespacedKey(RedCorp.getPlugin(), "earth-wings-knockback-resistance"), 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST)

        meta.addAttributeModifier(Attribute.ARMOR, modifier1)
        meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, modifier2)
        meta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE, modifier3)

        meta.addEnchant(Enchantment.PROTECTION, 4, true)
        meta.addEnchant(Enchantment.BLAST_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.FIRE_PROTECTION, 4, true)
        meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true)
        meta.setDisplayName("§2§lEarth §f§lWings")
        meta.isUnbreakable = true
        meta.setCustomModelDataComponent(cm)

        val lore = listOf("Stone-hearted", "Wearer becomes earth's champion", "Unyielding, unbreakable, unconquerable")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }
}