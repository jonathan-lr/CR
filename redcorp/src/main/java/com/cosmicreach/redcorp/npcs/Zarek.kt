package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.Confirm
import com.cosmicreach.redcorp.utils.DrugTest
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.entity.EnderCrystal
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.invui.window.Window
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.sqrt

class Zarek(
    private val player: Player,
    private val confirm: HashMap<Player, Boolean>,
    private val stage: HashMap<Player, Int>
) {
    fun run() {
        val item = player.inventory.itemInMainHand
        if (!Utils().checkID(item, arrayOf(50))) {
            player.sendMessage(zarekResponse())
            return
        }

        if (!stage.containsKey(player)) {
            stage[player] = 1
        }

        when (stage[player]) {
            1 -> {
                player.sendMessage("§cZarek §8|§r Do you want me to summon Firebelly for a Dungeon Token?")
                stage[player] = 2
                val window = Window.single()
                    .setViewer(player)
                    .setTitle("§6§lSummon Firebelly?")
                    .setGui(Confirm(confirm).makeGUI())
                    .build()
                window.open()
            }
            2 -> {
                if (confirm.containsKey(player)) {
                    if (confirm[player] == true) {
                        confirm.remove(player)
                        stage[player] = 1
                        player.sendMessage("§cZarek §8|§r Okay the ritual will start in 30 seconds head over to the alter now!")
                        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} has summoned Firebelly")
                        player.inventory.itemInMainHand.amount -= 1
                        object : BukkitRunnable() {
                            override fun run() {
                                summonFireBelly()
                            }
                        }.runTaskLater(RedCorp.getPlugin(), 600L) // 600 ticks = 30 seconds
                    } else {
                        confirm.remove(player)
                        stage[player] = 1
                        player.sendMessage("§cZarek §8|§r Fine by me, come back when you are ready to challenge him!")
                    }
                } else {
                    player.sendMessage("§cServer §8|§r Something went wrong try again")
                    stage[player] = 1
                }
            }
        }
    }

    fun openPortal() {
        val world: World = Bukkit.getWorld("world_the_end") ?: return
        val portalBlocks = mutableListOf<Block>()

        // Locate the portal frame around (0, 61, 0)
        for (x in -4..4) {
            for (z in -4..4) {
                val block = world.getBlockAt(x, 61, z)
                if (block.type == Material.AIR) {
                    portalBlocks.add(block)
                }
            }
        }

        // Add portal
        for (block in portalBlocks) {
            block.type = Material.END_PORTAL
        }
    }

    private fun zarekResponse(): String {
        when (ThreadLocalRandom.current().nextInt(0, 8)) {
            0 -> return "§cZarek §8|§r You don't have the dungeon token? Why are you wasting my time?"
            1 -> return "§cZarek §8|§r I have more important matters to attend to. Come back when you're actually prepared."
            2 -> return "§cZarek §8|§r Without the token, there's no summoning. This is pointless."
            3 -> return "§cZarek §8|§r I don't have time to entertain amateurs. Get the token, then maybe I'll consider helping."
            4 -> return "§cZarek §8|§r This is not a game. If you don't have the token, turn around and leave."
            5 -> return "§cZarek §8|§r Do you think I summon dragons for fun? Get the token or get lost."
            6 -> return "§cZarek §8|§r You're just wasting my time. I don't have time for you to be unprepared."
            7 -> return "§cZarek §8|§r Find the token, and only then will I entertain your request. Until then, leave me be."
        }
        return ""
    }

    private fun summonFireBelly() {
        val world: World = Bukkit.getWorld("world_the_end") ?: return

        val crystalLocations = listOf(
            // Ground positions
            getCenterBlockLocation(world.getBlockAt(0, 62, 3).location),
            getCenterBlockLocation(world.getBlockAt(3, 62, 0).location),
            getCenterBlockLocation(world.getBlockAt(0, 62, -3).location),
            getCenterBlockLocation(world.getBlockAt(-3, 62, 0).location),

            // Pillar positions
            getCenterBlockLocation(world.getBlockAt(12, 86, 39).location),
            getCenterBlockLocation(world.getBlockAt(-13, 77, 39).location),
            getCenterBlockLocation(world.getBlockAt(-34, 104, 24).location),
            getCenterBlockLocation(world.getBlockAt(-42, 101, -1).location),
            getCenterBlockLocation(world.getBlockAt(-34, 89, -25).location),
            getCenterBlockLocation(world.getBlockAt(-13, 98, -40).location),
            getCenterBlockLocation(world.getBlockAt(12, 95, -40).location),
            getCenterBlockLocation(world.getBlockAt(33, 92, -25).location),
            getCenterBlockLocation(world.getBlockAt(42, 83, 0).location),
            getCenterBlockLocation(world.getBlockAt(33, 80, 24).location)
        )

        val beam = world.getBlockAt(0, 120, 0).location
        val song = world.getBlockAt(0, 60, 0).location

        destroyEndPortal(world)

        object : BukkitRunnable() {
            var index = 0
            override fun run() {
                if (index >= crystalLocations.size) {
                    // Final actions
                    destroyGroundCrystals(world, crystalLocations.subList(0, 4))
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn Firebelly 1 world_the_end,0,120,0")
                    val crystals = world.getEntitiesByClass(EnderCrystal::class.java)
                    crystals.forEach { crystal ->
                        if (crystal.beamTarget == beam) {
                            crystal.beamTarget = null
                        }
                    }
                    for (p in world.players) {
                        if (p.location.distance(song) <= 200) {
                            p.playSound(player.location, "minecraft:mwm.millet", SoundCategory.MUSIC, 0.5F, 1.0F)
                        }
                    }
                    cancel()
                    return
                }
                val location = crystalLocations[index]

                // Strike lightning before spawning pillar crystals
                if (index >= 4) {
                    world.strikeLightningEffect(location)
                    encaseInGlass(world, location)
                }

                val crystal = world.spawnEntity(location, EntityType.END_CRYSTAL) as EnderCrystal
                crystal.beamTarget = beam
                crystal.isShowingBottom = false
                index++
            }
        }.runTaskTimer(RedCorp.getPlugin(), 0L, 40L) // 40 ticks = 2 seconds
    }

    private fun getCenterBlockLocation(location: Location): Location {
        return location.clone().add(0.5, 0.0, 0.5)
    }

    private fun encaseInGlass(world: World, center: Location) {
        val glassMaterial = Material.PURPLE_STAINED_GLASS
        val radius = 5

        for (x in -radius..radius) {
            for (y in -radius..radius) {
                for (z in -radius..radius) {
                    val distance = sqrt((x * x + y * y + z * z).toDouble())
                    if (distance <= radius && distance > radius - 1) { // Hollow sphere
                        val block = world.getBlockAt(center.blockX + x, center.blockY + y, center.blockZ + z)
                        if (block.type == Material.AIR) { // Do not replace existing blocks
                            block.type = glassMaterial
                        }
                    }
                }
            }
        }
    }

    private fun destroyGroundCrystals(world: World, groundLocations: List<Location>) {
        groundLocations.forEach { location ->
            val nearbyEntities = world.getNearbyEntities(location, 1.0, 1.0, 1.0)
            nearbyEntities.filterIsInstance<EnderCrystal>().forEach { crystal ->
                world.createExplosion(crystal.location, 4.0F, false, false) // Manual explosion
                crystal.remove()
            }
        }
    }

    private fun destroyEndPortal(world: World) {
        val portalBlocks = mutableListOf<Block>()

        // Locate the portal frame around (0, 61, 0)
        for (x in -4..4) {
            for (z in -4..4) {
                val block = world.getBlockAt(x, 61, z)
                if (block.type == Material.END_PORTAL) {
                    portalBlocks.add(block)
                }
            }
        }

        // Remove all portal blocks
        for (block in portalBlocks) {
            block.type = Material.AIR
        }
    }
}
