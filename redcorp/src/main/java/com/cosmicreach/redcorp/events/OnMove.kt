package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.ParticleManager
import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.CustomItems
import com.cosmicreach.redcorp.utils.DrugTest
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.flags.StateFlag
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent
import java.util.concurrent.ThreadLocalRandom

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
                        }
                    }
                }
            }
        }

        if (event.from.block == event.to?.block) return
        val world = BukkitAdapter.adapt(p.world)
        val regionManager = WorldGuard.getInstance().platform.regionContainer[world]
        if (regionManager != null) {
            val location = BukkitAdapter.adapt(p.location)
            val applicableRegions: ApplicableRegionSet = regionManager.getApplicableRegions(location.toVector().toBlockPoint())
            val currentRegions = applicableRegions.regions.map { it.id }.toSet()

            val playerRegions = RedCorp.getPlugin().getPlayerRegions()
            val previousRegions = playerRegions[p] ?: emptySet()

            // Detect newly entered regions
            val enteredRegions = currentRegions - previousRegions
            val exitedRegions = previousRegions - currentRegions

            // Update the player's stored regions
            playerRegions[p] = currentRegions

            // Trigger custom logic for entering/exiting regions
            for (regionId in enteredRegions) {
                val region = regionManager.getRegion(regionId) ?: continue
                val flags = RedCorp.getPlugin().getFlags()
                if (region.getFlag(flags.CHECK_DRUGS) == StateFlag.State.ALLOW) {
                    doCops(p)
                }
            }

            for (regionId in exitedRegions) {
                p.sendMessage("You exited region: $regionId")
            }
        }
        return
    }

    private fun doCops (p: Player) {
        if (DrugTest().doTest(p)) {
            when (ThreadLocalRandom.current().nextInt(0, 10)) {
                0 -> p.sendMessage("§cCR §8|§r Option 0 Nothing")
                1 -> p.sendMessage("§cCR §8|§r Option 1 Nothing")
                2 -> p.sendMessage("§cCR §8|§r Option 2 Nothing")
                3 -> p.sendMessage("§cCR §8|§r Option 3 Nothing")
                4 -> p.sendMessage("§cCR §8|§r Option 4 1 COP")
                5 -> p.sendMessage("§cCR §8|§r Option 5 2 COPS")
                6 -> p.sendMessage("§cCR §8|§r Option 6 4 COPS")
                7 -> p.sendMessage("§cCR §8|§r Option 7 6 COPS")
                8 -> p.sendMessage("§cCR §8|§r Option 8 8 COPS")
                9 -> p.sendMessage("§cCR §8|§r Option 9 ALL COPS")
            }
            //val loc = p.location
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "boss spawn world ${(loc.x + 4.0).toInt()} ${loc.y.toInt()} ${loc.z.toInt()} Test")
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "boss spawn world ${(loc.x - 4.0).toInt()} ${loc.y.toInt()} ${loc.z.toInt()} Test")
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "boss spawn world ${loc.x.toInt()} ${loc.y.toInt()} ${(loc.z + 4.0).toInt()} Test")
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "boss spawn world ${loc.x.toInt()} ${loc.y.toInt()} ${(loc.z - 4.0).toInt()} Test")
        }
    }
}