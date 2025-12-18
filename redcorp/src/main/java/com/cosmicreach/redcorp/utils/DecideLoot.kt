package com.cosmicreach.redcorp.utils

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.*
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.Lidded
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.lang.Integer.parseInt
import java.util.ArrayList
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class DecideLoot() {
    private var types = arrayOf("§7Air", "§cFire", "§9Water", "§2Earth")
    private var typesConf = arrayOf("air", "fire", "water", "earth")
    private var items = arrayOf("Pick", "Shovel", "Hoe", "Axe", "Sword", "Helmet", "Chestplate", "Leggings", "Boots", "Wings")

    data class Point3D(val x: Double, val y: Double, val z: Double)
    private val startPoint = Point3D(49.5, 82.0, -9.5)
    private val endPoint = Point3D(49.5, 75.0, -6.5)
    private val peakPoint = Point3D(49.5, 78.0, -8.5)

    private val numPoints = 32
    private val dust = Particle.DustOptions(Color.fromRGB(255, 0, 255), 1.5F)

    private fun interpolateOutwardInwardCurve3D(
        start: Point3D,
        end: Point3D,
        peak: Point3D,
        numPoints: Int
    ): List<Point3D> {
        val steps = numPoints.coerceAtLeast(2)
        val out = ArrayList<Point3D>(steps)

        for (i in 0 until steps) {
            val t = i.toDouble() / (steps - 1).toDouble()
            val u = 1.0 - t

            // Quadratic Bézier: B(t) = u^2*P0 + 2*u*t*P1 + t^2*P2
            val x = (u * u * start.x) + (2.0 * u * t * peak.x) + (t * t * end.x)
            val y = (u * u * start.y) + (2.0 * u * t * peak.y) + (t * t * end.y)
            val z = (u * u * start.z) + (2.0 * u * t * peak.z) + (t * t * end.z)

            out.add(Point3D(x, y, z))
        }
        return out
    }


    fun openChest(p: Player, vault: Int, drop: Int) {
        val block = Location(p.world, endPoint.x, endPoint.y, endPoint.z).block
        val interpolatedPoints = interpolateOutwardInwardCurve3D(startPoint, endPoint, peakPoint, numPoints)

        p.world.strikeLightningEffect(Location(p.world, startPoint.x, startPoint.y, startPoint.z))

        var count = 0
        object : BukkitRunnable() {
            override fun run() {
                val point = interpolatedPoints[count]
                p.world.spawnParticle(Particle.DUST, Location(p.world,point.x, point.y, point.z ), 10, dust)
                if (count == 24) {
                    setChestLid(block, true)
                    spiral(p, vault, drop)
                    cancel()
                }
                count += 1
            }
        }.runTaskTimer(RedCorp.getPlugin(), 10L, 1L)
    }

    fun spiral(p: Player, vault: Int, drop: Int) {
        var radius = 2.0
        var height = endPoint.y
        var count = 0.0
        var count2 = 0
        val numPoints = 32
        val block = Location(p.world, endPoint.x, endPoint.y, endPoint.z).block
        object : BukkitRunnable() {
            override fun run() {

                val theta = 2 * PI * count / numPoints
                val theta2 = 2 * PI * (count+16.0) / numPoints
                val x = endPoint.x + radius * cos(theta)
                val y = endPoint.z + radius * sin(theta)
                val x2 = endPoint.x + radius * cos(theta2)
                val y2 = endPoint.z + radius * sin(theta2)

                p.world.spawnParticle(Particle.DUST, Location(p.world,x,height,y), 1, dust)
                p.world.spawnParticle(Particle.DUST, Location(p.world,x2,height,y2), 1, dust)

                if (radius <= 0.0) {
                    radius = 2.0
                    height = endPoint.y
                }
                if (count >= 32.0) {
                    p.world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                    Bukkit.broadcastMessage("§cCR §8|§r ${5-count2}...")
                    count = 0.0
                    count2 += 1
                    if (count2 >= 5) {
                        setChestLid(block, false)

                        giveLoot(p, vault, drop)
                        cancel()
                    }
                }
                height += 0.1
                radius -= 0.1
                count += 1.0
            }
        }.runTaskTimer(RedCorp.getPlugin(), 5L, 1L)
    }

    fun giveLoot(p: Player, vault: Int, drop: Int) {
        val w = p.world
        Bukkit.broadcastMessage("§cCR §8|§r ${p.displayName} §rgot a §6${types[vault]} ${items[drop]}")
        w.spawnParticle(Particle.TOTEM_OF_UNDYING, p.location, 100)
        object : BukkitRunnable() {
            override fun run() {
                w.spawnParticle(Particle.TOTEM_OF_UNDYING, p.location, 100)
            }
        }.runTaskLater(RedCorp.getPlugin(), 5L)
        object : BukkitRunnable() {
            override fun run() {
                w.spawnParticle(Particle.TOTEM_OF_UNDYING, p.location, 100)
            }
        }.runTaskLater(RedCorp.getPlugin(), 10L)
        w.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BIT, 0.75f, 1.0f)
        when (drop) {
            0 -> {
                when(vault) {
                    0 -> p.inventory.addItem(SkyItems().skyPick())
                    1 -> p.inventory.addItem(FireItems().firePick())
                    2 -> p.inventory.addItem(WaterItems().waterPick())
                    3 -> p.inventory.addItem(EarthItems().earthPick())
                }
            }
            1 -> {
                when(vault) {
                    0 -> p.inventory.addItem(SkyItems().skyShovel())
                    1 -> p.inventory.addItem(FireItems().fireShovel())
                    2 -> p.inventory.addItem(WaterItems().waterShovel())
                    3 -> p.inventory.addItem(EarthItems().earthShovel())
                }
            }
            2 -> {
                when(vault) {
                    0 -> p.inventory.addItem(SkyItems().skyHoe())
                    1 -> p.inventory.addItem(FireItems().fireHoe())
                    2 -> p.inventory.addItem(WaterItems().waterHoe())
                    3 -> p.inventory.addItem(EarthItems().earthHoe())
                }
            }
            3 -> {
                when(vault) {
                    0 -> p.inventory.addItem(SkyItems().skyAxe())
                    1 -> p.inventory.addItem(FireItems().fireAxe())
                    2 -> p.inventory.addItem(WaterItems().waterAxe())
                    3 -> p.inventory.addItem(EarthItems().earthAxe())
                }
            }
            4 -> {
                when(vault) {
                    0 -> p.inventory.addItem(SkyItems().skySword())
                    1 -> p.inventory.addItem(FireItems().fireSword())
                    2 -> p.inventory.addItem(WaterItems().waterSword())
                    3 -> p.inventory.addItem(EarthItems().earthSword())
                }
            }
            5 -> {
                when(vault) {
                    0 -> p.inventory.addItem(SkyItems().skyHelmet())
                    1 -> p.inventory.addItem(FireItems().fireHelmet())
                    2 -> p.inventory.addItem(WaterItems().waterHelmet())
                    3 -> p.inventory.addItem(EarthItems().earthHelmet())
                }
            }
            6 -> {
                when(vault) {
                    0 -> p.inventory.addItem(SkyItems().skyChestplate())
                    1 -> p.inventory.addItem(FireItems().fireChestplate())
                    2 -> p.inventory.addItem(WaterItems().waterChestplate())
                    3 -> p.inventory.addItem(EarthItems().earthChestplate())
                }
            }
            7 -> {
                when(vault) {
                    0 -> p.inventory.addItem(SkyItems().skyLeggings())
                    1 -> p.inventory.addItem(FireItems().fireLeggings())
                    2 -> p.inventory.addItem(WaterItems().waterLeggings())
                    3 -> p.inventory.addItem(EarthItems().earthLeggings())
                }
            }
            8 -> {
                when(vault) {
                    0 -> p.inventory.addItem(SkyItems().skyBoots())
                    1 -> p.inventory.addItem(FireItems().fireBoots())
                    2 -> p.inventory.addItem(WaterItems().waterBoots())
                    3 -> p.inventory.addItem(EarthItems().earthBoots())
                }
            }
            9 -> {
                when(vault) {
                    0 -> p.inventory.addItem(SkyItems().skyWings())
                    1 -> p.inventory.addItem(FireItems().fireWings())
                    2 -> p.inventory.addItem(WaterItems().waterWings())
                    3 -> p.inventory.addItem(EarthItems().earthWings())
                }
            }
        }
    }

    fun decideLoot(player: Player, item: ItemStack): Boolean {
        var vault = 0
        val world = player.world

        when (Utils().getID(item)) {
            52 -> vault = 0
            53 -> vault = 1
            54 -> vault = 2
            55 -> vault = 3
        }

        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §fis opening the ${types[vault]} §fVault")
        world.playSound(player.location, Sound.ENTITY_WITHER_SPAWN, 0.75f, 1.0f)

        object : BukkitRunnable() {
            override fun run() {
                when (ThreadLocalRandom.current().nextInt(1, 10)) {
                    1 -> {
                        world.playSound(player.location, Sound.ENTITY_VILLAGER_DEATH, 0.75f, 1.0f)
                        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §fused a ${types[vault]} Key §rand got nothing :)")
                    }
                    2 -> {
                        world.playSound(player.location, Sound.ENTITY_VILLAGER_HURT, 0.75f, 1.0f)
                        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §fused a ${types[vault]} Key §rand got 1 §6Diamond")
                        val reward = ItemStack(Material.DIAMOND, 1)
                        player.inventory.addItem(reward)
                    }
                    3 -> {
                        world.playSound(player.location, Sound.ENTITY_VILLAGER_TRADE, 0.75f, 1.0f)
                        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §fused a ${types[vault]} Key §rand got 3 §6Diamonds")
                        val reward = ItemStack(Material.DIAMOND, 3)
                        player.inventory.addItem(reward)
                    }
                    4 -> {
                        world.playSound(player.location, Sound.ENTITY_VILLAGER_AMBIENT, 0.75f, 1.0f)
                        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §fused a ${types[vault]} Key §rand got 5 §6Diamonds")
                        val reward = ItemStack(Material.DIAMOND, 5)
                        player.inventory.addItem(reward)
                    }
                    5 -> {
                        world.playSound(player.location, Sound.ENTITY_VILLAGER_AMBIENT, 0.75f, 1.0f)
                        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §fused a ${types[vault]} Key §rand got 1 §6Diamond Block")
                        val reward = ItemStack(Material.DIAMOND_BLOCK, 1)
                        player.inventory.addItem(reward)
                    }
                    6 -> {
                        world.playSound(player.location, Sound.ENTITY_VILLAGER_AMBIENT, 0.75f, 1.0f)
                        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §fused a ${types[vault]} Key §rand got 1 §3Teleport Scrolls")
                        val reward = CustomItems().Scroll(1, "teleport")
                        player.inventory.addItem(reward)
                    }
                    7 -> {
                        world.playSound(player.location, Sound.ENTITY_VILLAGER_AMBIENT, 0.75f, 1.0f)
                        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §fused a ${types[vault]} Key §rand got 3 §3Teleport Scrolls")
                        val reward = CustomItems().Scroll(3, "teleport")
                        player.inventory.addItem(reward)
                    }
                    8 -> {
                        world.playSound(player.location, Sound.ENTITY_VILLAGER_AMBIENT, 0.75f, 1.0f)
                        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §fused a ${types[vault]} Key §rand got 1 §3Teleport Anchor")
                        val reward = CustomItems().TeleportAnchor(1)
                        player.inventory.addItem(reward)
                    }
                    9 -> runRelic(player, vault)
                }
            }
        }.runTaskLater(RedCorp.getPlugin(), 10L)

        return false
    }

    private fun setChestLid(block: Block, open: Boolean) {
        val state = block.state
        if (state is Lidded) {
            if (open) state.open() else state.close()
        }
    }


    private fun runRelic(sender: CommandSender, vault: Int) {
        if (sender !is Player) {return}
        val world = sender.world
        world.playSound(sender.location, Sound.ENTITY_VILLAGER_YES, 0.75f, 1.0f)
        Bukkit.broadcastMessage("§cCR §8|§r ${sender.displayName} §fused a ${types[vault]} Key §rand got a relic, lets see what it is...")

        val drop = ThreadLocalRandom.current().nextInt(0, 10)

        var configItem = parseInt(RedCorp.getPlugin().config.getString("configuration.${typesConf[vault]}.${items[drop].lowercase()}"))

        if (configItem == 0) {
            object : BukkitRunnable() {
                override fun run() {
                    world.playSound(sender.location, Sound.ENTITY_VILLAGER_DEATH, 0.75f, 1.0f)
                    Bukkit.broadcastMessage("§cCR §8|§r ${sender.displayName} §rgot a §6${types[vault]} ${items[drop]} §rbut there were none left :(")
                }
            }.runTaskLater(RedCorp.getPlugin(), 20L)
            return
        }

        object : BukkitRunnable() {
            override fun run() {
                openChest(sender, vault, drop)
            }
        }.runTaskLater(RedCorp.getPlugin(), 20L)

        configItem -= 1
        RedCorp.getPlugin().config.set("configuration.${typesConf[vault]}.${items[drop].lowercase()}", configItem)
        RedCorp.getPlugin().saveConfig()
        return
    }
}