package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.ParticleManager
import com.cosmicreach.redcorp.items.CustomItems
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent

class OnMove (private val event : PlayerMoveEvent) {

    fun run(teleportingPlayers: HashMap<Player, Int>, particleTeleport: HashMap<Player, ParticleManager>, teleportSolo: HashMap<Player, Int>, teleportStarter: HashMap<Player, Player>) {
        val p: Player = event.player

        if (teleportingPlayers.containsKey(p)) {
            if (event.to!!.x != event.from.x || event.to!!.y != event.from.y || event.to!!.z != event.from.z) {
                if (teleportSolo.containsKey(p)) {
                    val temp = teleportingPlayers[p]
                    teleportingPlayers.remove(p)
                    p.sendMessage("§cCR §8|§r You moved and teleportation was canceled")
                    particleTeleport[p]?.disableParticles()
                    particleTeleport.remove(p)

                    if (temp != null) {
                        Bukkit.getScheduler().cancelTask(temp)
                    }

                    if (teleportSolo[p] == 1) {
                        p.sendMessage("§cCR §8|§r ${p.displayName} §rreturning scroll")
                        p.inventory.addItem(CustomItems().DeathScroll(1))
                        teleportSolo.remove(p)
                    } else {
                        p.sendMessage("§cCR §8|§r ${p.displayName} §rreturning scroll")
                        p.inventory.addItem(CustomItems().HomeScroll(1))
                        teleportSolo.remove(p)
                    }

                } else {
                    val temp = teleportingPlayers[p]
                    teleportingPlayers.remove(p)
                    teleportingPlayers.forEach { (t, u) ->
                        if (u == temp) {
                            t.sendMessage("§cCR §8|§r ${p.displayName} moved and teleportation was canceled")
                            p.sendMessage("§cCR §8|§r You moved and teleportation was canceled")
                            particleTeleport[p]?.disableParticles()
                            particleTeleport.remove(p)
                            particleTeleport[t]?.disableParticles()
                            particleTeleport.remove(t)

                            teleportingPlayers.remove(t, u)
                            Bukkit.getScheduler().cancelTask(u)

                            if (teleportStarter.containsKey(p)) {
                                p.sendMessage("§cCR §8|§r ${p.displayName} §rreturning scroll")
                                p.inventory.addItem(CustomItems().TeleportScroll(1))
                                teleportStarter.remove(p)
                            } else {
                                if (teleportStarter.containsKey(t)) {
                                    t.sendMessage("§cCR §8|§r ${t.displayName} §rreturning scroll")
                                    t.inventory.addItem(CustomItems().TeleportScroll(1))
                                    teleportStarter.remove(t)
                                }
                            }
                            return
                        }
                    }
                }
            }
        }
        return
    }
}