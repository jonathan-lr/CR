package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class OnEat (private val event : PlayerItemConsumeEvent) {

    fun run() {
        if (Utils().checkID(event.item, arrayOf(460, 461, 462, 463))) {
            when (Utils().getID(event.item)) {
                460 -> {
                    event.player.addPotionEffect(PotionEffect(PotionEffectType.NAUSEA, 1200, 1, true, false, false))
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
            }
        }
        return
    }
}