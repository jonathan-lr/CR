package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.utils.Utils
import de.tr7zw.nbtapi.NBT
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class OnEat (private val event : PlayerItemConsumeEvent) {

    fun run() {
        if (Utils().checkID(event.item, arrayOf(460, 461, 462, 463, 470, 471, 472))) {
            var hasMilk = false
            var hasSugar = false
            NBT.get(event.item) { nbt ->
                hasMilk = nbt.getBoolean("hasMilk")
                hasSugar = nbt.getBoolean("hasSugar")
            }
            when (Utils().getID(event.item)) {
                460 -> {
                    event.player.addPotionEffect(PotionEffect(PotionEffectType.NAUSEA, 1200, 1, true, false, false))
                    event.player.addPotionEffect(PotionEffect(PotionEffectType.STRENGTH, 200, 1, true, false, false))
                }
                461 -> {
                    event.player.addPotionEffect(PotionEffect(PotionEffectType.NAUSEA, 1200, 1, true, false, false))
                }
                462 -> {
                    event.player.addPotionEffect(PotionEffect(PotionEffectType.NAUSEA, 2400, 1, true, false, false))
                }
                463 -> {
                    event.player.addPotionEffect(PotionEffect(PotionEffectType.NAUSEA, -1, 1, true, false, false))
                }
                470 -> {
                    val duration = when {
                        hasSugar && hasMilk -> 3600
                        hasSugar || hasMilk -> 6000
                        else -> 12000
                    }
                    event.player.addPotionEffect(PotionEffect(PotionEffectType.HASTE, duration, 1, true, false, false))
                }
                471 -> {
                    val duration = when {
                        hasSugar && hasMilk -> 3600
                        hasSugar || hasMilk -> 6000
                        else -> 12000
                    }
                    event.player.addPotionEffect(PotionEffect(PotionEffectType.HASTE, duration, 2, true, false, false))
                }
                472 -> {
                    val duration = when {
                        hasSugar && hasMilk -> 3600
                        hasSugar || hasMilk -> 6000
                        else -> 12000
                    }
                    event.player.addPotionEffect(PotionEffect(PotionEffectType.HASTE, duration, 3, true, false, false))
                }
            }
        }
        return
    }
}