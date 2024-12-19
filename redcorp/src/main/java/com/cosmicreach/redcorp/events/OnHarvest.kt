package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.items.DrugItems
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.Material
import org.bukkit.event.player.PlayerHarvestBlockEvent

class OnHarvest (private val event : PlayerHarvestBlockEvent) {

    fun run() {
        if (event.harvestedBlock.type == Material.SWEET_BERRY_BUSH) {
            val nbt = NBTBlock(event.harvestedBlock).data
            val temp = nbt.getBoolean("poppy")

            if (temp) {
                event.itemsHarvested.clear()

                event.harvestedBlock.world.dropItemNaturally(event.harvestedBlock.location, DrugItems().OpiumFlower(1))
            }
        }
        return
    }
}