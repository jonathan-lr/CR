package com.cosmicreach.redcorp

import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class ParticleManager(private val plugin: JavaPlugin) {
    private var partEnabled = false
    private var time = 0.0
    private var height = 0.1
    private var height2 = -1.3
    private var up = false

    private var startingAngle = 0.0
    private var startingAngle2 = 0.0

    fun enableParticles() {
        partEnabled = true
    }

    fun disableParticles() {
        partEnabled = false
    }

    fun getParticle(): Boolean {
        return partEnabled
    }

    fun circle1(p: Player) {
        val dust = Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.0F)
        val angleIncrement = 2 * PI / 40
        val startingAngleRadians = Math.toRadians(startingAngle)

        for (i in 0 until 40) {
            val angle = startingAngleRadians + i * angleIncrement
            val x = 2.0 * cos(angle)
            val y = 2.0 * sin(angle)
            val pos = p.location.add(x, 0.0, y)
            val mod = i.mod(10)
            if (i.mod(10) == 0) {
                circle2(p, pos)
            }
            if (mod in 3..7) {
                val world = p.world
                world.spawnParticle(Particle.DUST, pos, 1, dust)
            }
        }

        startingAngle += 1.0
    }

    fun circle2(p: Player, pos: Location) {
        val dust = Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.0F)
        val angleIncrement = 2 * PI / 32

        for (i in 0 until 32) {
            val angle2 = i * angleIncrement
            val x = pos.x + 0.75 * cos(angle2)
            val y = pos.z + 0.75 * sin(angle2)
            val pos2 = Location(p.location.world, x, p.location.y, y)
            val world = p.world
            world.spawnParticle(Particle.DUST, pos2, 1, dust)
        }
    }

    fun circle3(p: Player) {
        val dust = Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.0F)
        val angleIncrement = 2 * PI / 60
        val startingAngleRadians = Math.toRadians(startingAngle2)

        for (i in 0 until 60) {
            val angle = startingAngleRadians + i * angleIncrement
            val x = 4.0 * cos(angle)
            val y = 4.0 * sin(angle)
            val pos = p.location.add(x, 0.0, y)
            val mod = i.mod(12)
            if ( mod == 0) {
                circle4(p, pos)
            }
            if (mod in 3..9) {
                val world = p.world
                world.spawnParticle(Particle.DUST, pos, 1, dust)
            }
        }

        startingAngle2 -= 1.0
    }

    fun circle4(p: Player, pos: Location) {
        val dust = Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.0F)
        val angleIncrement = 2 * PI / 32

        for (i in 0 until 32) {
            val angle2 = i * angleIncrement
            val x = pos.x + 1.0 * cos(angle2)
            val y = pos.z + 1.0 * sin(angle2)
            val pos2 = Location(p.location.world, x, p.location.y, y)
            val world = p.world
            world.spawnParticle(Particle.DUST, pos2, 1, dust)
        }
    }


    fun testTeleport(p: Player) {
        object : BukkitRunnable() {
            override fun run() {
                circle1(p)
                circle3(p)

                if (!partEnabled) {
                    cancel()
                }
            }
        }.runTaskTimer(plugin, 1L, 1L)

    }

    fun runParticles(p: Player) {
        object : BukkitRunnable() {
            override fun run() {
                if (time > 360.0) {
                    time = 0.0
                }
                var location = p.eyeLocation
                var location2 = p.eyeLocation
                val dust = Particle.DustOptions(Color.fromRGB(255, 0, 0), 1.2F)
                val dust2 = Particle.DustOptions(Color.fromRGB(0, 0, 255), 1.2F)
                var x: Double = 1.5 * cos(time)
                var z: Double = 1.5 * sin(time)
                var x2: Double = 1.5 * cos(time+3.5)
                var z2: Double = 1.5 * sin(time+3.5)
                val players = Bukkit.getServer().onlinePlayers
                var pos1 = location.add(x, height, z)
                var pos2 = location2.add(x2, height2, z2)
                players.forEach {
                    it.spawnParticle(Particle.DUST, pos1, 5, dust)
                    it.spawnParticle(Particle.DUST, pos2, 5, dust2)
                }
                time += 0.05
                if (up) {
                    height += 0.01
                    height2 -= 0.01
                    if (height > 0.1) {
                        up = false
                    }
                } else {
                    height -= 0.01
                    height2 += 0.01
                    if (height < -1.3) {
                        up = true
                    }
                }

                if (!partEnabled) {
                    cancel()
                }
            }
        }.runTaskTimer(plugin, 1L, 1L)
    }
}