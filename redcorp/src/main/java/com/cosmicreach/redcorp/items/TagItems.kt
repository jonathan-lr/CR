package com.cosmicreach.redcorp.items

import de.tr7zw.nbtapi.NBT
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.*

class TagItems {
    fun tagStick (a : String, b : String, c : Int, gameId: Int): ItemStack {
        val item = ItemStack(Material.STICK, 1)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 69)
            nbt.setInteger("game-id", gameId)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("tag")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§c§lTag Stick")

        val lore = listOf("§9Current Tagger: $a", "§9Last Tagger: $b", "§9Times Passed: $c")
        meta.lore = lore

        item.setItemMeta(meta)
        return item
    }
}