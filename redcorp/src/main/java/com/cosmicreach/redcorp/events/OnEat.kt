package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.Sound
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class OnEat (private val event : PlayerItemConsumeEvent) {

    fun run() {
        if (Utils().checkID(event.item, arrayOf(423))) {
            val p = event.player
            p.world.playSound(p.location, Sound.ENTITY_BLAZE_BURN, 0.2f, 1.0f)
            p.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, 1200, 1, true, false, false))
            p.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, 1200, 2, true, false, false))
        }
        if (Utils().checkID(event.item, arrayOf(432))) {
            val p = event.player
            p.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 1200, 2, true, false, false))
        }
        if (Utils().checkID(event.item, arrayOf(441))) {
            val p = event.player
            p.addPotionEffect(PotionEffect(PotionEffectType.UNLUCK, 6000, 2, true, false, false))
        }
        if (Utils().checkID(event.item, arrayOf(450,451))) {
            val p = event.player
            p.addPotionEffect(PotionEffect(PotionEffectType.DARKNESS, 1200, 2, true, false, false))
        }
        return
    }
}