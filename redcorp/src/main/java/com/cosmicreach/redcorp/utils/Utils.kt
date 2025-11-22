package com.cosmicreach.redcorp.utils

import com.cosmicreach.redcorp.RedCorp
import de.tr7zw.nbtapi.NBT
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

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

    fun getGreenhouseUUID(item: ItemStack): UUID? {
        var id : UUID? = null

        if (!item.hasItemMeta()) { return id }

        NBT.get(item) { nbt ->
            id = nbt.getUUID("player-id")!!
        }

        return id
    }

    fun getShipmentId(item: ItemStack): Int? {
        var id : Int? = null

        if (!item.hasItemMeta()) { return id }

        NBT.get(item) { nbt ->
            id = nbt.getInteger("drop-id")!!
        }

        return id
    }

    fun makeEdibleWithComponents(
        item: ItemStack,
        canAlwaysEat: Boolean? = null,
        nutrition: Int? = null,
        saturation: Float? = null,
        consumeTime: Double? = null,
        animation: String? = null,
        sound: String? = null,
        consumeParticles: Boolean? = null,
    ): ItemStack {
        NBT.modifyComponents(item) { comp ->

            // --- FOOD COMPONENT ---
            val food = comp.getOrCreateCompound("minecraft:food")

            canAlwaysEat?.let { food.setBoolean("can_always_eat", it) }
            nutrition?.let { food.setInteger("nutrition", it) }
            saturation?.let { food.setFloat("saturation", it) }

            // --- CONSUMABLE COMPONENT ---
            val consumable = comp.getOrCreateCompound("minecraft:consumable")

            consumeTime?.let { consumable.setDouble("consume_seconds", it) }
            animation?.let { consumable.setString("animation", it) }
            sound?.let { consumable.setString("sound", it) }
            consumeParticles?.let { consumable.setBoolean("has_consume_particles", it) }
        }

        return item
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

    fun setScore(player: Player, objectiveName: String, value: Int) {
        val scoreboard = player.server.scoreboardManager?.mainScoreboard
        var objective = scoreboard?.getObjective(objectiveName)

        if (objective == null) {
            objective = scoreboard?.registerNewObjective(objectiveName, "dummy", objectiveName)
        }

        objective?.getScore(player.name)?.score = value
    }
}