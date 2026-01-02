package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.ParticleManager
import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.CustomItems
import com.cosmicreach.redcorp.utils.DrugTest
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.flags.StateFlag
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent
import java.lang.Math.toRadians
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

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
                        p.inventory.addItem(CustomItems().Scroll(1, "death"))
                        teleportSolo.remove(p)
                    } else {
                        p.sendMessage("§cCR §8|§r ${p.displayName} §rreturning scroll")
                        p.inventory.addItem(CustomItems().Scroll(1, "home"))
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
                                p.inventory.addItem(CustomItems().Scroll(1, "teleport"))
                                teleportStarter.remove(p)
                            } else {
                                if (teleportStarter.containsKey(t)) {
                                    t.sendMessage("§cCR §8|§r ${t.displayName} §rreturning scroll")
                                    t.inventory.addItem(CustomItems().Scroll(1, "teleport"))
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
                    doCops(p, region)
                }
            }

            //for (regionId in exitedRegions) {
            //    p.sendMessage("You exited region: $regionId")
            //}
        }
        return
    }

    private fun doCops (p: Player, region: ProtectedRegion) {
        if (DrugTest().doTest(p)) {
            when (ThreadLocalRandom.current().nextInt(0, 3)) {
                0 -> {/*Do Nothing*/}
                1,2 -> {
                    spawnBosses(p, region) // Spawn the appropriate number of bosses based on the case
                }
            }
        }
    }

    private fun spawnBosses(p: Player, region: ProtectedRegion) {
        //val bossCount = ThreadLocalRandom.current().nextInt(1, 11) // Random number of bosses (1 to 10)
        //val loc = p.location
        val world = p.world

        //todo: use these points to check the area for grey carpet and if found roll 50/50 on weather to run Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn cop_$i 1 world,${x.toInt()},${y.toInt()},${z.toInt()}")
        val min = region.minimumPoint
        val max = region.maximumPoint

        val minX = min.blockX
        val minY = min.blockY
        val minZ = min.blockZ
        val maxX = max.blockX
        val maxY = max.blockY
        val maxZ = max.blockZ

        // Safety: clamp Y to the actual world height
        val clampedMinY = minY.coerceAtLeast(world.minHeight)
        val clampedMaxY = maxY.coerceAtMost(world.maxHeight - 1)
        if (clampedMinY > clampedMaxY) return

        // Scan region for gray carpet
        for (x in minX..maxX) {
            for (y in clampedMinY..clampedMaxY) {
                for (z in minZ..maxZ) {
                    val block = world.getBlockAt(x, y, z)
                    if (block.type != Material.GRAY_CARPET) continue

                    // Found gray carpet: roll 50/50
                    val spawn = ThreadLocalRandom.current().nextBoolean()
                    if (spawn) continue

                    // Pick a cop variant index (adjust bounds as needed)
                    val i = ThreadLocalRandom.current().nextInt(1, 6) // 1..5

                    val cmd = "mm mobs spawn cop_$i 1 ${world.name},$x,$y,$z"
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd)
                }
            }
        }

        /*
        // Calculate direction towards (0, 0)
        val directionToOrigin = atan2(-loc.z, -loc.x) // Angle in radians pointing toward (0, 0)

        // Spread bosses in a cone toward the (0, 0) coordinate
        for (i in 1..bossCount) {
            val angleOffset = toRadians(ThreadLocalRandom.current().nextDouble(-30.0, 30.0)) // Random offset in a 60° cone
            val distance = ThreadLocalRandom.current().nextDouble(7.0, 12.0) // Random distance from the player

            val spawnX = loc.x + distance * cos(directionToOrigin + angleOffset)
            val spawnZ = loc.z + distance * sin(directionToOrigin + angleOffset)
            val surfaceY = world.getHighestBlockAt(spawnX.toInt(), spawnZ.toInt()).y + 2.0 // Get surface level

            // Dispatch command to spawn the boss
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn cop_$i 1 world,${spawnX.toInt()},${surfaceY.toInt()},${spawnZ.toInt()}")
        }*/
    }
}