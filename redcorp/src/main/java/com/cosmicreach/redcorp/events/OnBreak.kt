package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.items.GreenhouseItems
import de.tr7zw.nbtapi.NBTBlock
import de.tr7zw.nbtapi.NBTCompound
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.Ageable
import org.bukkit.entity.ItemDisplay
import org.bukkit.event.block.BlockBreakEvent
import kotlin.random.Random

class OnBreak (private val event : BlockBreakEvent) {

    fun run() {
        b = event.block
        nbt = NBTBlock(b).data

        when (b.type) {
            Material.WHEAT -> weedBreak()
            Material.COCOA -> coffeeBreak()
            Material.BEETROOTS -> cokeBreak()
            Material.SWEET_BERRY_BUSH, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM -> drugBreak()
            Material.RED_MUSHROOM_BLOCK, Material.BROWN_MUSHROOM_BLOCK, Material.MUSHROOM_STEM -> breakMush()
            Material.BARREL -> agingBarrel()
            Material.BREWING_STAND -> coffeeMachine()
            Material.PLAYER_HEAD -> fairyBreak()
            Material.GREEN_STAINED_GLASS -> greenhouseBreak(true)
            Material.BLACK_STAINED_GLASS -> greenhouseBreak(false)
            else -> {}
        }

        return
    }

    private fun greenhouseBreak(entrance: Boolean) {
        val isGreenhouse = nbt.getBoolean("greenhouse")
        if (!isGreenhouse) return
        val greenhouseId = nbt.getInteger("greenhouse-id")
        event.isDropItems = false
        b.drops.clear()

        if (entrance) {
            val center = b.location.clone().add(0.5, 0.5, 0.5)
            b.world.dropItem(center, GreenhouseItems().GreenhouseEntrance(greenhouseId!!))

            val nearby = b.world.getNearbyEntities(
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

        } else {
            b.world.dropItemNaturally(b.location, GreenhouseItems().GreenhouseExit(greenhouseId!!))
        }
        nbt.clearNBT()
    }

    private fun weedBreak () {
        val temp = nbt.getBoolean("weed")
        val blockAge = b.blockData as Ageable
        if (blockAge.age == blockAge.maximumAge) {
            if (temp) {
                event.isDropItems = false
                b.drops.clear()

                b.world.dropItemNaturally(b.location, DrugItems().Weed(1))
                b.world.dropItemNaturally(b.location, DrugItems().WeedSeed(Random.nextInt(1, 3)))
            }
        }
        nbt.clearNBT()
    }

    private fun coffeeBreak () {
        val temp = nbt.getBoolean("coffeeBean")
        val blockAge = b.blockData as Ageable
        if (blockAge.age == blockAge.maximumAge) {
            if (temp) {
                event.isDropItems = false
                b.drops.clear()

                b.world.dropItemNaturally(b.location, DrugItems().CoffeeBean(Random.nextInt(1, 4)))
            }
        }
        nbt.clearNBT()
    }

    private fun cokeBreak () {
        val temp = nbt.getBoolean("coke")
        val blockAge = b.blockData as Ageable
        if (blockAge.age == blockAge.maximumAge) {
            if (temp) {
                event.isDropItems = false
                b.drops.clear()

                b.world.dropItemNaturally(b.location, DrugItems().CocaLeaf(1))
                b.world.dropItemNaturally(b.location, DrugItems().CocaSeed(Random.nextInt(1, 3)))
            }
        }
        nbt.clearNBT()
    }

    private fun drugBreak () {
        if (nbt.getBoolean("poppy") || nbt.getBoolean("shroom") || nbt.getBoolean("truffle")) {
            event.isDropItems = false
            b.drops.clear()
        }

        nbt.clearNBT()
    }

    private fun fairyBreak () {
        if (nbt.getBoolean("fairy")) {
            if (event.player.hasPermission("redcorp.break-fairy")) {
                event.isDropItems = false
                b.drops.clear()
                nbt.clearNBT()
            } else {
                event.isCancelled = true
            }
        }
    }

    private fun agingBarrel () {
        val temp = nbt.getBoolean("barrel")

        if (temp) {
            event.isDropItems = false
            b.drops.clear()

            b.world.dropItemNaturally(b.location, DrugItems().AgingBarrel(1))

            nbt.clearNBT()
        }

        val isShipment = nbt.getBoolean("shipment")

        if (isShipment) {
            if (event.player.hasPermission("redcorp.break-shipment")) {
                event.isDropItems = false
                b.drops.clear()
                nbt.clearNBT()
            } else {
                event.isCancelled = true
            }
        }
    }

    private fun coffeeMachine () {
        val temp = nbt.getBoolean("coffee")

        if (temp) {
            event.isDropItems = false
            b.drops.clear()

            b.world.dropItemNaturally(b.location, DrugItems().CoffieMachine(1))

            nbt.clearNBT()
        }
    }

    private fun breakMush () {
        val shroom = nbt.getBoolean("shroom")
        val truffle = nbt.getBoolean("truffle")
        val drop = Random.nextInt(4)
        if (shroom) {
            event.isDropItems = false
            b.drops.clear()

            if (drop == 1) {
                b.world.dropItemNaturally(b.location, DrugItems().Shrooms(1))
            }
        }
        if (truffle) {
            event.isDropItems = false
            b.drops.clear()

            if (drop == 1) {
                b.world.dropItemNaturally(b.location, DrugItems().Truffles(1))
            }
        }

        nbt.clearNBT()
    }

    companion object {
        private lateinit var b: Block
        private lateinit var nbt: NBTCompound
    }
}