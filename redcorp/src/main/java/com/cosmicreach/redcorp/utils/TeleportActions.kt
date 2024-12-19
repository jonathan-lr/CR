package com.cosmicreach.redcorp.utils

import com.cosmicreach.redcorp.ParticleManager
import com.cosmicreach.redcorp.RedCorp
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.flags.StateFlag
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable


class TeleportActions(private val teleportingPlayers: HashMap<Player, Int>, private val particleTeleport: HashMap<Player, ParticleManager>, private val denyTeleportScroll: StateFlag?, private val teleportStarter: HashMap<Player, Player>, private val teleportSolo: HashMap<Player, Int>) {
    fun available(player: Player): MutableList<Player> {
        val players = Bukkit.getServer().onlinePlayers
        val list = mutableListOf<Player>()
        players.forEach { p ->
            if (p.playerListName != player.playerListName) {
                if (!list.contains(p)) {
                    p.inventory.forEach { i ->
                        if (i !== null) {
                            if (Utils().checkID(i, arrayOf(4))) {
                                list.add(p)
                            }
                        }
                    }
                }
            }
        }
        return list
    }

    fun runTeleport(p: Player, t: Player) {
        if (teleportingPlayers.containsKey(p)) {
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §ryou are already teleporting")
            return
        }

        if (teleportingPlayers.containsKey(t)) {
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §ryour target is already teleporting")
            return
        }

        if (!testRegion(t.location)) {
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §ryour target its currently in a restricted area")
            t.sendMessage("§cCR §8|§r ${p.displayName} §rtried to teleport to you but you were in a restricted area")
            return
        }

        if (!testRegion(p.location)) {
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §ryou are in a restricted area")
            return
        }

        if (!Utils().checkID(p.inventory.itemInMainHand, arrayOf(3))) {
            p.sendMessage("§cCR §8|§r ${p.displayName} §rrequires scroll in hand")
            return
        }

        if (!Utils().checkID(t.inventory.itemInOffHand, arrayOf(4))) {
            p.sendMessage("§cCR §8|§r ${t.displayName} §rrequires target to have anchor in offhand")
            t.sendMessage("§cCR §8|§r ${p.displayName} §rtried to teleport to you but your anchor wasn't equipped")
            return
        }

        particleTeleport[p] = ParticleManager(RedCorp.getPlugin())
        particleTeleport[p]?.enableParticles()
        particleTeleport[p]?.testTeleport(p)

        particleTeleport[t] = ParticleManager(RedCorp.getPlugin())
        particleTeleport[t]?.enableParticles()
        particleTeleport[t]?.testTeleport(t)

        teleportStarter[p] = t

        p.inventory.itemInMainHand.amount -= 1
        teleport(p, t)

        return
    }

    fun runTeleportLocation(p: Player, t: Location, type: Int) {
        if (teleportingPlayers.containsKey(p)) {
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §ryou are already teleporting")
            return
        }

        if (!testRegion(t)) {
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §ryour target is a restricted area")
            return
        }

        if (!testRegion(p.location)) {
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §ryou are in a restricted area")
            return
        }

        if (!Utils().checkID(p.inventory.itemInMainHand, arrayOf(5,6))) {
            p.sendMessage("§cCR §8|§r ${p.displayName} §rrequires scroll in hand")
            return
        }

        particleTeleport[p] = ParticleManager(RedCorp.getPlugin())
        particleTeleport[p]?.enableParticles()
        particleTeleport[p]?.testTeleport(p)

        teleportSolo[p] = type

        p.inventory.itemInMainHand.amount -= 1
        teleportLocation(p, t)

        return
    }

    private fun testRegion(location: Location): Boolean {
        val container = WorldGuard.getInstance().platform.regionContainer
        val query = container.createQuery()
        val set = query.getApplicableRegions(BukkitAdapter.adapt(location))
        if (set != null) {
            return set.testState(null, denyTeleportScroll)
        } else {
            return false
        }
    }

    private fun teleport(p: Player, t: Player) {
        p.sendMessage("§cCR §8|§r Teleporting to ${t.displayName}")
        t.sendMessage("§cCR §8|§r ${p.displayName}§r is teleporting to you")
        val world = p.world

        var count = 6
        val teleportId = object : BukkitRunnable() {
            override fun run() {
                when (count) {
                    6 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        world.playSound(t.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        p.sendMessage("§cCR §8|§r 5.....")
                        t.sendMessage("§cCR §8|§r 5.....")
                    }
                    5 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        world.playSound(t.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        p.sendMessage("§cCR §8|§r 4....")
                        t.sendMessage("§cCR §8|§r 4....")
                    }
                    4 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        world.playSound(t.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        p.sendMessage("§cCR §8|§r 3...")
                        t.sendMessage("§cCR §8|§r 3...")
                    }
                    3 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        world.playSound(t.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        p.sendMessage("§cCR §8|§r 2..")
                        t.sendMessage("§cCR §8|§r 2..")
                    }
                    2 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        world.playSound(t.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        p.sendMessage("§cCR §8|§r 1.")
                        t.sendMessage("§cCR §8|§r 1.")
                    }
                    1 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BIT, 0.75f, 1.0f)
                        world.playSound(t.location, Sound.BLOCK_NOTE_BLOCK_BIT, 0.75f, 1.0f)
                        p.teleport(t.location)
                        p.sendMessage("§cCR §8|§r Teleported to ${t.displayName}")
                        t.sendMessage("§cCR §8|§r ${p.displayName} §rTeleported to you")

                        particleTeleport[p]?.disableParticles()
                        particleTeleport.remove(p)
                        particleTeleport[t]?.disableParticles()
                        particleTeleport.remove(t)

                        teleportingPlayers.remove(p)
                        teleportingPlayers.remove(t)
                        teleportStarter.remove(p)
                        cancel()
                    }
                }
                count -= 1
            }
        }.runTaskTimer(RedCorp.getPlugin(), 0L, 20L).taskId

        teleportingPlayers[p] = teleportId
        teleportingPlayers[t] = teleportId
    }

    private fun teleportLocation(p: Player, t: Location) {
        p.sendMessage("§cCR §8|§r Teleporting to ${t.x} / ${t.y} / ${t.z}")
        val world = p.world

        var count = 6
        val teleportId = object : BukkitRunnable() {
            override fun run() {
                when (count) {
                    6 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        p.sendMessage("§cCR §8|§r 5.....")
                    }
                    5 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        p.sendMessage("§cCR §8|§r 4....")
                    }
                    4 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        p.sendMessage("§cCR §8|§r 3...")
                    }
                    3 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        p.sendMessage("§cCR §8|§r 2..")
                    }
                    2 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                        p.sendMessage("§cCR §8|§r 1.")
                    }
                    1 -> {
                        world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BIT, 0.75f, 1.0f)
                        p.teleport(t)
                        p.sendMessage("§cCR §8|§r Teleported to ${t.x} / ${t.y} / ${t.z}")

                        particleTeleport[p]?.disableParticles()
                        particleTeleport.remove(p)
                        teleportSolo.remove(p)

                        teleportingPlayers.remove(p)
                        cancel()
                    }
                }
                count -= 1
            }
        }.runTaskTimer(RedCorp.getPlugin(), 0L, 20L).taskId

        teleportingPlayers[p] = teleportId
    }
}