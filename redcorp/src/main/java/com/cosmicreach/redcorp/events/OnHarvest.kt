package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.items.DrugItems
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.Material
import org.bukkit.event.player.PlayerHarvestBlockEvent
import kotlin.random.Random

class OnHarvest (private val event : PlayerHarvestBlockEvent) {

    fun run() {
        if (event.harvestedBlock.type == Material.SWEET_BERRY_BUSH) {
            val nbt = NBTBlock(event.harvestedBlock).data
            val temp = nbt.getBoolean("poppy")
            val dropPoppy = Random.nextInt(4)
            val dropSap = Random.nextInt(4)

            if (temp) {
                event.itemsHarvested.clear()

                if (dropPoppy == 1) {
                    event.harvestedBlock.world.dropItemNaturally(event.harvestedBlock.location, DrugItems().OpiumFlower(1))
                }
                if (dropSap == 1) {
                    event.harvestedBlock.world.dropItemNaturally(event.harvestedBlock.location, DrugItems().OpiumSap(1))
                }
            }
        }
        return
    }
}