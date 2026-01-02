package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.utils.Utils
import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Greenhouse
import com.cosmicreach.redcorp.utils.ChatUtil
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.World
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

        if (Utils().checkID(event.item, arrayOf(199))) {
            val world: World = Bukkit.getWorld("world") ?: return
            val wb = world.worldBorder

            var worldBorder = RedCorp.getPlugin().getWorldBorder()

            val prefix = Component.text("CR ", NamedTextColor.RED)
                .append(Component.text("| ", NamedTextColor.DARK_GRAY))

            val completedMsg = prefix
                .append(event.player.displayName()) // already a Component in modern Paper
                .append(Component.text(" has expanded the border by ", NamedTextColor.WHITE))
                .append(Component.text("100", NamedTextColor.DARK_PURPLE))
                .append(Component.text(" blocks", NamedTextColor.WHITE))

            // Broadcast via Adventure
            Bukkit.getServer().broadcast(completedMsg)

            worldBorder += 100
            RedCorp.getPlugin().setWorldBorder(worldBorder)
            wb.setSize(worldBorder, 5L)
        }

        val isShopUpgrade = Utils().checkID(event.item, arrayOf(203))
        val isQualityUpgrade = Utils().checkID(event.item, arrayOf(204))

        if (isShopUpgrade || isQualityUpgrade) {
            val connection = RedCorp.getPlugin().getConnection()!!
            val greenhouseDb = Greenhouse(connection)

            val greenhouse = greenhouseDb.getGreenhousesForPlayer(p.uniqueId)
            if (greenhouse == null) {
                ChatUtil.send(p, ChatUtil.json { text("You are not in a greenhouse ${p.displayName}") })
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

            if (isShopUpgrade) {
                if (greenhouse.upgradeS >= 2) {
                    ChatUtil.send(p, ChatUtil.json { text("You have max shop upgrades on your greenhouse") })
                    event.isCancelled = true
                    return
                }

                ChatUtil.send(p, ChatUtil.json { text("Added 4 Blocks to your Greenhouse") })
                greenhouseDb.updateUpgradeS(greenhouse.id, greenhouse.upgradeS + 1)
                val cmd = "bsbadmin range add $creatorName 4"
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd)
            } else {
                if (greenhouse.upgradeQ >= 2) {
                    ChatUtil.send(p, ChatUtil.json { text("You have max quest upgrades on your greenhouse") })
                    event.isCancelled = true
                    return
                }

                ChatUtil.send(p, ChatUtil.json { text("Added 4 Blocks to your Greenhouse") })
                greenhouseDb.updateUpgradeQ(greenhouse.id, greenhouse.upgradeQ + 1)
                val cmd = "bsbadmin range add $creatorName 4"
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd)
            }
        }
        return
    }
}