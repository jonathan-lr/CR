package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.utils.Utils
import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Greenhouse
import com.cosmicreach.redcorp.utils.ChatUtil
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class OnEat(private val event: PlayerItemConsumeEvent) {

    fun run() {
        val p = event.player
        if (Utils().checkID(event.item, arrayOf(423))) {
            p.world.playSound(p.location, Sound.ENTITY_BLAZE_BURN, 0.2f, 1.0f)
            p.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, 1200, 1, true, false, false))
            p.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, 1200, 2, true, false, false))
        }
        if (Utils().checkID(event.item, arrayOf(435))) {
            p.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 1200, 2, true, false, false))
        }
        if (Utils().checkID(event.item, arrayOf(444))) {
            p.addPotionEffect(PotionEffect(PotionEffectType.UNLUCK, 6000, 2, true, false, false))
        }
        if (Utils().checkID(event.item, arrayOf(450, 451))) {
            p.addPotionEffect(PotionEffect(PotionEffectType.DARKNESS, 1200, 2, true, false, false))
        }
        if (Utils().checkID(event.item, arrayOf(203))) {
            val connection = RedCorp.getPlugin().getConnection()!!
            val greenhouseDb = Greenhouse(connection)

            val greenhouse = greenhouseDb.getGreenhousesForPlayer(p.uniqueId)
            if (greenhouse == null) {
                ChatUtil.send(p, ChatUtil.json { text("You are not in a greenhouse ${p.displayName}") })
                event.isCancelled = true
                return
            }
            if (greenhouse.upgradeS >= 2) {
                ChatUtil.send(p, ChatUtil.json { text("You have max shop upgrades on your greenhouse") })
                event.isCancelled = true
                return
            }

            val creatorUuid = greenhouseDb.getGreenhouseCreatorUUID(p.uniqueId)
            if (creatorUuid == null) {
                ChatUtil.send(p, ChatUtil.json { text("Could not find greenhouse owner message Zach") })
                event.isCancelled = true
                return
            }

            val creatorName = Bukkit.getOfflinePlayer(creatorUuid).name
            if (creatorName.isNullOrBlank()) {
                ChatUtil.send(p, ChatUtil.json { text("Could not resolve creator username message Zach") })
                event.isCancelled = true
                return
            }

            ChatUtil.send(p, ChatUtil.json { text("Added 4 Blocks to your Greenhouse") })
            greenhouseDb.updateUpgradeS(greenhouse.id, greenhouse.upgradeS + 1)
            val cmd = "bsbadmin range add $creatorName 4"
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd)
        }
        return
    }
}