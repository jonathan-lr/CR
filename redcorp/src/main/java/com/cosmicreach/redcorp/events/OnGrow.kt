package com.cosmicreach.redcorp.events

import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.TreeType
import org.bukkit.event.world.StructureGrowEvent

class OnGrow (private val event : StructureGrowEvent) {

    fun run() {
        if (event.species == TreeType.RED_MUSHROOM) {
            event.blocks.forEach {
                val block = it.block
                val nbt = NBTBlock(block).data

                nbt.setBoolean("shroom", true)
            }
        }
        if (event.species == TreeType.BROWN_MUSHROOM) {
            event.blocks.forEach {
                val block = it.block
                val nbt = NBTBlock(block).data

                nbt.setBoolean("truffle", true)
            }
        }
        return
    }
}