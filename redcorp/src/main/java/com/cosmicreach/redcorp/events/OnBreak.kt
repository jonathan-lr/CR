package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.items.DrugItems
import de.tr7zw.nbtapi.NBTBlock
import de.tr7zw.nbtapi.NBTCompound
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.Ageable
import org.bukkit.event.block.BlockBreakEvent
import kotlin.random.Random

class OnBreak (private val event : BlockBreakEvent) {

    fun run() {
        b = event.block
        nbt = NBTBlock(b).data

        when (b.type) {
            Material.WHEAT -> weedBreak()
            Material.BEETROOTS -> cokeBreak()
            Material.SWEET_BERRY_BUSH, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM -> drugBreak()
            Material.RED_MUSHROOM_BLOCK, Material.BROWN_MUSHROOM_BLOCK, Material.MUSHROOM_STEM -> breakMush()
            Material.BARREL -> agingBarrel()
            Material.BREWING_STAND -> coffeeMachine()
            else -> {}
        }

        return
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

    private fun agingBarrel () {
        val temp = nbt.getBoolean("barrel")

        if (temp) {
            event.isDropItems = false
            b.drops.clear()

            b.world.dropItemNaturally(b.location, DrugItems().AgingBarrel(1))

            nbt.clearNBT()
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
        if (shroom) {
            event.isDropItems = false
            b.drops.clear()

            b.world.dropItemNaturally(b.location, DrugItems().Shrooms(1))
        }
        if (truffle) {
            event.isDropItems = false
            b.drops.clear()

            b.world.dropItemNaturally(b.location, DrugItems().Truffles(1))
        }

        nbt.clearNBT()
    }

    companion object {
        private lateinit var b: Block
        private lateinit var nbt: NBTCompound
    }
}