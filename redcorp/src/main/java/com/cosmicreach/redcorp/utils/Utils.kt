package com.cosmicreach.redcorp.utils

import de.tr7zw.nbtapi.NBT
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Display
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import kotlin.math.roundToInt

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

    fun getGreenhouseId(item: ItemStack): Int? {
        var id : Int? = null

        if (!item.hasItemMeta()) { return id }

        NBT.get(item) { nbt ->
            id = nbt.getInteger("greenhouse-id")!!
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

    private fun snapYawTo90(rawYaw: Float): Float {
        // Normalize to 0–360
        var yaw = rawYaw
        while (yaw < 0f) yaw += 360f
        yaw %= 360f

        // Round to nearest 90
        val snapped = (yaw / 90f).roundToInt() * 90
        return (snapped % 360).toFloat()
    }

    fun placeFakeBlock(item: ItemStack, block: Block, scale: Float = 0F, event: PlayerInteractEvent, freeRotation: Boolean = false) {
        val location = block.location

        when (event.blockFace) {
            BlockFace.UP -> location.y += 1
            BlockFace.DOWN -> location.y -= 1
            BlockFace.NORTH -> location.z -= 1
            BlockFace.SOUTH -> location.z += 1
            BlockFace.WEST -> location.x -= 1
            BlockFace.EAST -> location.x += 1
            else -> return // Handle cases where blockFace is invalid or unexpected
        }

        val worldPlace = block.location.world!!
        val displayLocation = Location(
            worldPlace,
            location.x + 0.5,
            location.y + 0.5,
            location.z + 0.5
        )

        val playerYaw = event.player.location.yaw
        var snappedYaw = snapYawTo90(playerYaw)
        if (freeRotation) { snappedYaw = playerYaw }
        val finalYaw = ((snappedYaw + 180f) % 360f)

        displayLocation.yaw = finalYaw

        worldPlace.spawn(displayLocation, ItemDisplay::class.java) { d: ItemDisplay ->
            d.setItemStack(item)              // your custom-model-data item
            d.billboard = Display.Billboard.FIXED  // don't face the player
            d.isPersistent = true
            d.isInvulnerable = true
            d.setGravity(false)
            d.scoreboardTags.add("fake_block_display")

            val t = d.transformation
            t.scale.set(scale, scale, scale)
            t.translation.y = (scale -1F) / 2f
            d.transformation = t
        }
    }

    fun breakFakeBlock(block: Block) {
        val center = block.location.clone().add(0.5, 0.5, 0.5)

        val nearby = block.world.getNearbyEntities(
            center,
            0.5, // x radius
            0.5, // y radius
            0.5  // z radius
        )

        nearby.forEach { entity ->
            if (entity is ItemDisplay && entity.scoreboardTags.contains("fake_block_display")) {
                entity.remove()
            }
        }
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