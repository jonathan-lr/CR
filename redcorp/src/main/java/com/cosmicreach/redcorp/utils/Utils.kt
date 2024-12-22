package com.cosmicreach.redcorp.utils

import com.cosmicreach.redcorp.RedCorp
import de.tr7zw.nbtapi.NBT
import org.bukkit.Bukkit
import org.bukkit.entity.Player
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

    fun serializeMagicUnlocked(magicUnlocked: HashMap<Player, Boolean>): String {
        return magicUnlocked.entries.joinToString(";") {
            "${it.key.playerListName}=${it.value}"
        }
    }

    fun deserializeMagicUnlocked(serializedData: String) {
        val magicUnlocked = RedCorp.getPlugin().getMagicUnlocked()
        val entries = serializedData.split(";")

        for (entry in entries) {
            if (entry.isNotBlank()) {
                val parts = entry.split("=")
                if (parts.size == 2) {
                    val playerName = parts[0]
                    val unlocked = parts[1].toBoolean()
                    val player = Bukkit.getPlayer(playerName)
                    if (player != null) {
                        magicUnlocked[player] = unlocked
                    }
                }
            }
        }

        return
    }
}