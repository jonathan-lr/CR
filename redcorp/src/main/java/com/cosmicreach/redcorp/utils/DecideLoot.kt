package com.cosmicreach.redcorp.utils

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.BlockPosition
import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.*
import org.bukkit.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.lang.Integer.parseInt
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class DecideLoot(private val protocolManager: ProtocolManager?) {
    private var types = arrayOf("§7Air", "§cFire", "§9Water", "§2Earth")
    private var typesConf = arrayOf("air", "fire", "water", "earth")
    private var items = arrayOf("Pick", "Shovel", "Hoe", "Axe", "Sword", "Helmet", "Chestplate", "Leggings", "Boots", "Wings")

    data class Point(val x: Double, val y: Double)
    private val startPoint = Point(232.5,43.5)
    private val endPoint = Point(221.5,50.5)
    private val peakPoint = Point(230.5,48.5)
    private val numPoints = 32
    private val dust = Particle.DustOptions(Color.fromRGB(255, 0, 255), 1.5F)

    private fun interpolateOutwardInwardCurve(startPoint: Point, endPoint: Point, peakPoint: Point, numPoints: Int): List<Point> {
        val points = mutableListOf<Point>()

        for (t in 0 until numPoints) {
            val tNormalized = t.toDouble() / (numPoints - 1)
            val x = (1 - tNormalized).pow(2) * startPoint.x +
                    2 * (1 - tNormalized) * tNormalized * peakPoint.x +
                    tNormalized.pow(2) * endPoint.x
            val y = (1 - tNormalized).pow(2) * startPoint.y +
                    2 * (1 - tNormalized) * tNormalized * peakPoint.y +
                    tNormalized.pow(2) * endPoint.y
            points.add(Point(x, y))
        }

        return points
    }

    fun openChest(p: Player, vault: Int, drop: Int) {
        val block = Location(p.world, endPoint.x, 91.0, endPoint.y).block
        val testPacket = PacketContainer(PacketType.Play.Server.BLOCK_ACTION)
        testPacket.blockPositionModifier.write(0, BlockPosition(block.x, block.y, block.z));
        testPacket.integers.write(0, 1)
        testPacket.integers.write(0, 1)
        testPacket.blocks.write(0, block.type)
        val loc = block.location
        val interpolatedPoints = interpolateOutwardInwardCurve(startPoint, endPoint, peakPoint, numPoints)

        var count = 0
        object : BukkitRunnable() {
            override fun run() {
                val point = interpolatedPoints[count]
                p.world.spawnParticle(Particle.DUST, Location(p.world,point.x,91.5,point.y ), 10, dust)
                if (count == 24) {
                    try {
                        block.world.players.forEach {
                            if (it.location.distanceSquared(loc) < 4096) {
                                protocolManager?.sendServerPacket(it, testPacket)
                            }
                        }
                    } catch (e: InvocationTargetException) {
                        e.message?.let { Bukkit.broadcastMessage(it) }
                    }
                    spiral(p, vault, drop)
                    cancel()
                }
                count += 1
            }
        }.runTaskTimer(RedCorp.getPlugin(), 10L, 1L)
    }

    fun spiral(p: Player, vault: Int, drop: Int) {
        var radius = 2.0
        var height = 91.0
        var count = 0.0
        var count2 = 0
        val numPoints = 32
        val block = Location(p.world, endPoint.x, 91.0, endPoint.y).block
        val testPacket = PacketContainer(PacketType.Play.Server.BLOCK_ACTION)
        testPacket.blockPositionModifier.write(0, BlockPosition(block.x, block.y, block.z));
        testPacket.integers.write(0, 0)
        testPacket.integers.write(0, 0)
        testPacket.blocks.write(0, block.type)
        val loc = block.location
        object : BukkitRunnable() {
            override fun run() {

                val theta = 2 * PI * count / numPoints
                val theta2 = 2 * PI * (count+16.0) / numPoints
                val x = endPoint.x + radius * cos(theta)
                val y = endPoint.y + radius * sin(theta)
                val x2 = endPoint.x + radius * cos(theta2)
                val y2 = endPoint.y + radius * sin(theta2)

                p.world.spawnParticle(Particle.DUST, Location(p.world,x,height,y), 1, dust)
                p.world.spawnParticle(Particle.DUST, Location(p.world,x2,height,y2), 1, dust)

                if (radius <= 0.0) {
                    radius = 2.0
                    height = 91.0
                }
                if (count >= 32.0) {
                    p.world.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.75f, 1.0f)
                    Bukkit.broadcastMessage("§cCR §8|§r ${5-count2}...")
                    count = 0.0
                    count2 += 1
                    if (count2 >= 5) {
                        try {
                            block.world.players.forEach {
                                if (it.location.distanceSquared(loc) < 4096) {
                                    protocolManager?.sendServerPacket(it, testPacket)
                                }
                            }
                        } catch (e: InvocationTargetException) {
                            e.message?.let { Bukkit.broadcastMessage(it) }
                        }
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
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "closedoor 93")
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

    private fun runRelic(sender: CommandSender, vault: Int) {
        if (sender !is Player) {return}
        val world = sender.world
        world.playSound(sender.location, Sound.ENTITY_VILLAGER_YES, 0.75f, 1.0f)
        Bukkit.broadcastMessage("§cCR §8|§r ${sender.displayName} §fused a ${types[vault]} Key §rand got a relic, lets see what it is...")

        val drop = ThreadLocalRandom.current().nextInt(0, 10)

        var configItem = parseInt(RedCorp.getPlugin().config.getString("configuration.${typesConf[vault]}.${items[drop].lowercase()}"))
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "opendoor 93")

        if (configItem == 0) {
            object : BukkitRunnable() {
                override fun run() {
                    world.playSound(sender.location, Sound.ENTITY_VILLAGER_DEATH, 0.75f, 1.0f)
                    Bukkit.broadcastMessage("§cCR §8|§r ${sender.displayName} §rgot a §6${types[vault]} ${items[drop]} §rbut there were none left :(")
                }
            }.runTaskLater(RedCorp.getPlugin(), 60L)
            return
        }

        object : BukkitRunnable() {
            override fun run() {
                openChest(sender, vault, drop)
            }
        }.runTaskLater(RedCorp.getPlugin(), 80L)

        configItem -= 1
        RedCorp.getPlugin().config.set("configuration.${typesConf[vault]}.${items[drop].lowercase()}", configItem)
        RedCorp.getPlugin().saveConfig()
        return
    }
}