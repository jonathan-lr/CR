package com.cosmicreach.redcorp.utils

import de.tr7zw.nbtapi.NBT
import org.bukkit.inventory.ItemStack

class Utils {
    fun checkID(item: ItemStack, ids: Array<Int>): Boolean {
        var id = 0

        if (!item.hasItemMeta()) { return false }

        NBT.get(item) { nbt ->
            id = nbt.getInteger("item-id")
        }

        return ids.contains(id)
    }

    fun getID(item: ItemStack): Int {
        var id = 0

        if (!item.hasItemMeta()) { return id }

        NBT.get(item) { nbt ->
            id = nbt.getInteger("item-id")
        }

        return id
    }

    fun getGameID(item: ItemStack): Int {
        var id = 0

        if (!item.hasItemMeta()) { return id }

        NBT.get(item) { nbt ->
            id = nbt.getInteger("game-id")
        }

        return id
    }
}