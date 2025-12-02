package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Greenhouse
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.items.GreenhouseItems
import com.cosmicreach.redcorp.menus.AgingBarrel
import com.cosmicreach.redcorp.menus.CoffeeMachine
import com.cosmicreach.redcorp.menus.Grinder
import com.cosmicreach.redcorp.menus.Teleport
import com.cosmicreach.redcorp.utils.ChatUtil
import com.cosmicreach.redcorp.utils.TeleportActions
import com.cosmicreach.redcorp.utils.Utils
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.entity.Display
import org.bukkit.entity.ItemDisplay
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.window.Window
import java.sql.Connection
import kotlin.math.roundToInt

class OnUse (
    private val event : PlayerInteractEvent,
    private val grinderPlayers: HashMap<Player, VirtualInventory>,
    private val teleportActions: TeleportActions
) {

    fun run() {
        connection = RedCorp.getPlugin().getConnection()!!
        if (event.item !== null) {
            p = event.player
            i = event.item!!
            id = Utils().getID(i)

            when (id) {
                2 -> gavel()
                400 -> grinder()
                401, 403 -> customBlock()
                210 -> setData()
                2886 -> debug()
                3 -> playerTeleport()
                5 -> deathTeleport()
                6 -> homeTeleport()
                201, 202 -> greenhouse()
                420, 430 -> farmlandDrug()
                440 -> podzolDrug()
                450, 451 -> myceliumDrug()
                473 -> woodDrug()
                in 700..720 -> setData()
            }

            if(event.item!!.type == Material.BONE_MEAL && event.clickedBlock != null) {
                val b = event.clickedBlock
                val nbt = NBTBlock(b).data

                if (nbt.getBoolean("weed") || nbt.getBoolean("coke") || nbt.getBoolean("poppy") || nbt.getBoolean("coffeeBean")) {
                    event.isCancelled = true
                    p.sendMessage("§cCR §8|§r Sorry these plants don't like bonemeal!")
                }
            }
        } else {
            p = event.player
            val block = event.clickedBlock
            if (block == null) return

            if (event.hand == EquipmentSlot.OFF_HAND) return

            if(block.type == Material.PLAYER_HEAD) {
                fairy()
            }

            if(block.type == Material.GREEN_STAINED_GLASS) {
                if (event.action != Action.RIGHT_CLICK_BLOCK) return
                val nbt = NBTBlock(event.clickedBlock).data
                val isGreenhouse = nbt.getBoolean("greenhouse")
                val greenhouseId = nbt.getInteger("greenhouse-id")

                if (!isGreenhouse || greenhouseId == null) {
                    p.sendMessage("§cCR §8|§r Something has gone wrong call Zach he can fix it")
                    return
                }

                val greenhouse = Greenhouse(connection).getGreenhouseById(greenhouseId)
                if (greenhouse == null) {
                    //val loc = p.location
                    //val locString = "${loc.world?.name},${loc.x},${loc.y},${loc.z},${loc.yaw},${loc.pitch}"

                    //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sudo ${p.playerListName} island create greenhouse")

                    //val greenhouseTracking = RedCorp.getPlugin().getGreenhouseTracker()

                    //greenhouseTracking.put(p, locString)
                    p.sendMessage("§cCR §8|§r Something has gone wrong call Zach he can fix it")
                    return
                }

                val parts = greenhouse.home.split(",")
                val world = Bukkit.getWorld(parts[0])
                val x = parts[1].toDouble()
                val y = parts[2].toDouble()
                val z = parts[3].toDouble()
                val yaw = parts[4].toFloat()
                val pitch = parts[5].toFloat()

                val location = Location(world, x, y, z, yaw, pitch)

                val isOwner = Greenhouse(connection).isPlayerLinked(greenhouseId, p.uniqueId)
                if (isOwner) {
                    p.teleport(location)
                } else {
                    val canRaid = RedCorp.getPlugin().getCanRaid()
                    canRaid.put(p, greenhouseId)
                    val msg = ChatUtil.json {
                        text("Are you sure you want to raid this? ")

                        button(
                            label = "<dark_gray>[<green><bold>ʏᴇs</bold><dark_gray>]",
                            command = "/raid confirm",
                            hoverText = "<gray>Start the raid!</gray>"
                        )

                        space()

                        button(
                            label = "<dark_gray>[<red><bold>ɴᴏ</bold><dark_gray>]",
                            command = "/raid cancel",
                            hoverText = "<gray>Nah I changed my mind</gray>"
                        )
                    }
                    ChatUtil.send(p, msg)

                    object : BukkitRunnable() {
                        override fun run() {
                            if (canRaid.remove(p) != null) {
                                p.sendMessage("§cCR §8|§r Raid request timed out")
                            }
                        }
                    }.runTaskLater(RedCorp.getPlugin(), 1200L)
                }
            }

            if(block.type == Material.BLACK_STAINED_GLASS) {
                if (event.action != Action.RIGHT_CLICK_BLOCK) return
                val nbt = NBTBlock(event.clickedBlock).data
                val isGreenhouse = nbt.getBoolean("greenhouse")
                val greenhouseId = nbt.getInteger("greenhouse-id")

                if (!isGreenhouse || greenhouseId == null) {
                    p.sendMessage("§cCR §8|§r Something has gone wrong call Zach he can fix it")
                    return
                }

                val greenhouse = Greenhouse(connection).getGreenhouseById(greenhouseId)
                if (greenhouse == null) {
                    p.sendMessage("§cCR §8|§r Something has gone wrong call Zach he can fix it")
                    return
                }

                val parts = greenhouse.exitl.split(",")
                val world = Bukkit.getWorld(parts[0])
                val x = parts[1].toDouble()
                val y = parts[2].toDouble()
                val z = parts[3].toDouble()
                val yaw = parts[4].toFloat()
                val pitch = parts[5].toFloat()

                val location = Location(world, x, y, z, yaw, pitch)

                val isOwner = Greenhouse(connection).isPlayerLinked(greenhouseId, p.uniqueId)
                if (isOwner) {
                    p.teleport(location)
                } else {
                    Greenhouse(connection).setFinished(p.uniqueId, false)
                    p.sendMessage("§cCR §8|§r Leaving Raid")
                    p.teleport(location)
                }
            }

            if(block.type == Material.BARRIER) {
                val nbt = NBTBlock(block).data
                val barrel = nbt.getBoolean("barrel")
                val coffee = nbt.getBoolean("coffee")
                val fermenting = nbt.getBoolean("ferment")
                if (event.action == Action.RIGHT_CLICK_BLOCK) {
                    val viewers = RedCorp.getPlugin().getAgingViewers()
                    val agingBarrels = RedCorp.getPlugin().getAgingBarrels()

                    if (barrel) {
                        if (fermenting) {
                            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rthat barrel is still fermenting!")
                            return
                        }

                        val window = Window.single()
                            .setViewer(p)
                            .setTitle("§6§lAging Barrel")
                            .setGui(AgingBarrel(agingBarrels).makeGUI(block))
                            .build()

                        window.open()

                        if (viewers[block] != null) {
                            viewers[block]?.add(window)
                        } else {
                            viewers[block] = mutableListOf(window)
                        }
                    }

                    if (coffee) {
                        if (fermenting) {
                            event.isCancelled = true
                            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rthat coffee is still brewing!")
                            return
                        }

                        val window = Window.single()
                            .setViewer(p)
                            .setTitle("§6§lCoffee Machine")
                            .setGui(CoffeeMachine(agingBarrels).makeGUI(block))
                            .build()

                        window.open()

                        if (viewers[block] != null) {
                            viewers[block]?.add(window)
                        } else {
                            viewers[block] = mutableListOf(window)
                        }
                    }
                } else {
                    val center = block.location.clone().add(0.5, 0.5, 0.5)
                    if (coffee) {
                        block.type = Material.AIR
                        block.world.dropItem(center, DrugItems().CoffieMachine(1))

                        nbt.clearNBT()

                        breakFakeBlock(block)
                    }
                    if (barrel) {
                        block.type = Material.AIR
                        block.world.dropItem(center, DrugItems().AgingBarrel(1))

                        nbt.clearNBT()

                        breakFakeBlock(block)
                    }
                }
            }
        }

        return
    }

    private fun fairy() {
        val nbt = NBTBlock(event.clickedBlock).data
        val fairy = nbt.getBoolean("fairy")
        val fairyId = nbt.getInteger("fairyId") - 700

        if (fairy) {
            val fairiesFound = RedCorp.getPlugin().getFairies()
            val hasMagic = RedCorp.getPlugin().getMagicUnlocked()
            if (hasMagic[p] == true) {
                p.sendMessage("§cCR §8|§r Your power is beyond this creature!")
            } else {
                if (!fairiesFound.containsKey(p)) {
                    fairiesFound[p] = Array(20) { false }
                }
                val playerFairies = fairiesFound[p]
                if (playerFairies!![fairyId]) {
                    p.sendMessage("§cCR §8|§r You already found Fairy #${fairyId+1} silly")
                } else {
                    p.world.playSound(p.location, Sound.ENTITY_ALLAY_DEATH, 0.75f, 1.0f)
                    playerFairies[fairyId] = true
                    fairiesFound[p] = playerFairies
                    p.sendMessage("§cCR §8|§r You found Fairy #${fairyId+1} you have found ${playerFairies.count { it }}/20")
                }
                //p.sendMessage("debug ${playerFairies.joinToString(", ")}")
            }
        }
    }

    private fun gavel() {
        p.world.playSound(p.location, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 0.75f, 1.0f)
    }

    private fun grinder() {
        val window = Window.single()
            .setViewer(p)
            .setTitle("§6§lDrug Grinder")
            .setGui(Grinder(grinderPlayers).makeGUI(p))
            .build()

        window.open()
    }

    private fun debug() {
        val nbt = NBTBlock(event.clickedBlock).data

        p.sendMessage("§cCR §8|§r debug weed ${nbt.getBoolean("weed")}")
        p.sendMessage("§cCR §8|§r debug coke ${nbt.getBoolean("coke")}")
        p.sendMessage("§cCR §8|§r debug popy ${nbt.getBoolean("poppy")}")
        p.sendMessage("§cCR §8|§r debug barrel ${nbt.getBoolean("barrel")}")
        p.sendMessage("§cCR §8|§r debug ferment ${nbt.getBoolean("ferment")}")
        p.sendMessage("§cCR §8|§r debug shroom ${nbt.getBoolean("shroom")}")
        p.sendMessage("§cCR §8|§r debug truffle ${nbt.getBoolean("truffle")}")
        p.sendMessage("§cCR §8|§r debug fairy ${nbt.getBoolean("fairy")}")
        p.sendMessage("§cCR §8|§r debug shipment ${nbt.getBoolean("shipment")}")
        p.sendMessage("§cCR §8|§r debug shipmentId ${nbt.getBoolean("shipmentId")}")
        p.sendMessage("§cCR §8|§r debug greenhouse ${nbt.getBoolean("greenhouse")}")
        p.sendMessage("§cCR §8|§r debug greenhouseId ${nbt.getUUID("greenhouse-id")}")
    }

    private fun playerTeleport() {
        val window = Window.single()
            .setViewer(p)
            .setTitle("§f§lAvailable Teleports")
            .setGui(Teleport(teleportActions).makeGUI(p))
            .build()

        window.open()
    }

    private fun deathTeleport() {
        if (p.lastDeathLocation is Location) {
            teleportActions.runTeleportLocation(p, p.lastDeathLocation!!, 1)
        } else {
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rwe cant find your death location")
        }
    }

    private fun homeTeleport() {
        if (p.respawnLocation is Location) {
            teleportActions.runTeleportLocation(p, p.respawnLocation!!, 2)
        } else {
            p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rwe cant find your bed location")
        }
    }

    private fun customBlock() {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        val block = event.clickedBlock
        if (block != null) {
            setData()

            var item = ItemStack(Material.STICK)
            var sale = 0F
            if (id == 401) {
                item = DrugItems().AgingBarrel(1)
                sale = 1.2F
            } else if (id == 403) {
                item = DrugItems().CoffieMachine(1)
                sale = 0.8F
            }

            placeFakeBlock(item, block, sale)

            event.isCancelled = true

            val location = event.clickedBlock!!.location
            when (event.blockFace) {
                BlockFace.UP -> location.y += 1
                BlockFace.DOWN -> location.y -= 1
                BlockFace.NORTH -> location.z -= 1
                BlockFace.SOUTH -> location.z += 1
                BlockFace.WEST -> location.x -= 1
                BlockFace.EAST -> location.x += 1
                else -> return // Handle cases where blockFace is invalid or unexpected
            }

            location.block.type = Material.BARRIER
            if (event.item != null) {
                event.item!!.amount -= 1
            }
        }
    }

    private fun greenhouse() {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        val block = event.clickedBlock
        if (block != null) {
            if (id == 201) {
                if (block.location.world?.name == "world") {
                    var greenhouseId = Utils().getGreenhouseId(i)
                    if (greenhouseId == -1) {
                        p.sendMessage("Creating Greenhouse")
                        val loc = p.location
                        val locString = "${loc.world?.name},${loc.x},${loc.y},${loc.z},${loc.yaw},${loc.pitch}"

                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sudo ${p.playerListName} island create greenhouse")

                        val newGreenhouse = Greenhouse(connection).createGreenhouse("", "", locString)
                        Greenhouse(connection).linkPlayerToGreenhouse(newGreenhouse, p.uniqueId)
                        i = GreenhouseItems().GreenhouseEntrance(newGreenhouse)
                        val greenhouseTracking = RedCorp.getPlugin().getGreenhouseTracker()

                        greenhouseTracking.put(p, newGreenhouse)
                    }

                    greenhouseId = Utils().getGreenhouseId(i)
                    p.sendMessage("New assigned id = ${greenhouseId}")
                    setData()

                    val greenhouse = Greenhouse(connection).getGreenhouseById(greenhouseId!!)
                    val item = GreenhouseItems().GreenhouseEntrance(greenhouseId)

                    placeFakeBlock(item, block, 0.7F)

                    if (greenhouse != null) {
                        val loc = p.location
                        val locString = "${loc.world?.name},${loc.x},${loc.y},${loc.z},${loc.yaw},${loc.pitch}"
                        Greenhouse(connection).updateExit(greenhouseId, locString)
                        p.sendMessage("§cCR §8|§r Set greenhouse exit location")
                    }
                } else {
                    event.isCancelled = true
                    p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rthis must be placed in the overworld")
                }
            } else {
                if (block.location.world?.name == "greenhouse") {
                    setData()
                    val greenhouseId = Utils().getGreenhouseId(i)
                    val greenhouse = Greenhouse(connection).getGreenhouseById(greenhouseId!!)

                    if (greenhouse != null) {
                        val loc = p.location
                        val locString = "${loc.world?.name},${loc.x},${loc.y},${loc.z},${loc.yaw},${loc.pitch}"
                        Greenhouse(connection).updateHome(greenhouseId, locString)
                        p.sendMessage("§cCR §8|§r Set greenhouse home location")
                    }
                } else {
                    event.isCancelled = true
                    p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rthis must be placed in your greenhouse")
                }
            }
        }
    }

    private fun farmlandDrug() {
        val block = event.clickedBlock
        if (block != null) {
            if (block.type == Material.FARMLAND) {
                setData()
            } else {
                p.sendMessage("§cCR §8|§r This must be placed on farmland :)")
            }
        }
    }

    private fun podzolDrug() {
        val block = event.clickedBlock
        if (block != null) {
            if (block.type == Material.PODZOL && event.blockFace == BlockFace.UP) {
                setData()
            } else {
                event.isCancelled = true
                p.sendMessage("§cCR §8|§r This must be placed on podzol :)")
            }
        }
    }

    private fun myceliumDrug() {
        if (event.clickedBlock!!.type == Material.MYCELIUM && event.blockFace == BlockFace.UP) {
            setData()
        } else {
            event.isCancelled = true
            p.sendMessage("§cCR §8|§r This must be placed on mycelium :)")
        }
    }

    private fun woodDrug() {
        if (event.clickedBlock!!.type == Material.JUNGLE_LOG && (event.blockFace == BlockFace.NORTH || event.blockFace == BlockFace.SOUTH || event.blockFace == BlockFace.EAST || event.blockFace == BlockFace.WEST)) {
            setData()
        } else {
            event.isCancelled = true
            p.sendMessage("§cCR §8|§r This must be placed on jungle log :)")
        }
    }

    private fun snapYawTo90(rawYaw: Float): Float {
        // Normalize to 0–360
        var yaw = rawYaw
        while (yaw < 0f) yaw += 360f
        yaw %= 360f

        // Round to nearest 90
        val snapped = (yaw / 90f).roundToInt() * 90
        return (snapped % 360).toFloat()
    }

    private fun placeFakeBlock(item: ItemStack, block: Block, scale: Float = 0F) {
        val location = block.location

        when (event.blockFace) {
            BlockFace.UP -> location.y += 1
            BlockFace.DOWN -> location.y -= 1
            BlockFace.NORTH -> location.z -= 1
            BlockFace.SOUTH -> location.z += 1
            BlockFace.WEST -> location.x -= 1
            BlockFace.EAST -> location.x += 1
            else -> return // Handle cases where blockFace is invalid or unexpected
        }

        val worldPlace = block.location.world!!
        val displayLocation = Location(
            worldPlace,
            location.x + 0.5,
            location.y + 0.5,
            location.z + 0.5
        )

        val playerYaw = p.location.yaw
        val snappedYaw = snapYawTo90(playerYaw)
        val finalYaw = ((snappedYaw + 180f) % 360f)

        displayLocation.yaw = finalYaw

        worldPlace.spawn(displayLocation, ItemDisplay::class.java) { d: ItemDisplay ->
            d.itemStack = item              // your custom-model-data item
            d.billboard = Display.Billboard.FIXED  // don't face the player
            d.isPersistent = true
            d.isInvulnerable = true
            d.setGravity(false)
            d.scoreboardTags.add("fake_block_display")

            val t = d.transformation
            t.scale.set(scale, scale, scale)
            t.translation.y = (scale -1F) / 2f
            d.transformation = t
        }
    }

    private fun breakFakeBlock(block: Block) {
        val center = block.location.clone().add(0.5, 0.5, 0.5)

        val nearby = block.world.getNearbyEntities(
            center,
            0.5, // x radius
            0.5, // y radius
            0.5  // z radius
        )

        nearby.forEach { entity ->
            if (entity is ItemDisplay && entity.scoreboardTags.contains("fake_block_display")) {
                entity.remove()
            }
        }
    }

    private fun setData() {
        val location = event.clickedBlock!!.location

        when (event.blockFace) {
            BlockFace.UP -> location.y += 1
            BlockFace.DOWN -> location.y -= 1
            BlockFace.NORTH -> location.z -= 1
            BlockFace.SOUTH -> location.z += 1
            BlockFace.WEST -> location.x -= 1
            BlockFace.EAST -> location.x += 1
            else -> return // Handle cases where blockFace is invalid or unexpected
        }

        val block = location.block
        val nbt = NBTBlock(block).data

        when (id) {
            201 -> {
                val greenhouseId = Utils().getGreenhouseId(i)
                nbt.setBoolean("greenhouse", true)
                nbt.setInteger("greenhouse-id", greenhouseId)
            }
            202 -> {
                val greenhouseId = Utils().getGreenhouseId(i)
                nbt.setBoolean("greenhouse", true)
                nbt.setInteger("greenhouse-id", greenhouseId)
            }
            210 -> {
                val id = Utils().getShipmentId(i)
                nbt.setBoolean("shipment", true)
                nbt.setInteger("shipmentId", id)
            }
            401 -> {
                nbt.setBoolean("barrel", true)
                nbt.setBoolean("ferment", false)
            }
            403 -> {
                nbt.setBoolean("coffee", true)
                nbt.setBoolean("ferment", false)
            }
            420 -> nbt.setBoolean("weed", true)
            430 -> nbt.setBoolean("coke", true)
            440 -> nbt.setBoolean("poppy", true)
            450 -> nbt.setBoolean("shroom", true)
            451 -> nbt.setBoolean("truffle", true)
            473 -> nbt.setBoolean("coffeeBean", true)
            in 700..720 -> {
                nbt.setBoolean("fairy", true)
                nbt.setInteger("fairyId", id)
            }
        }
    }

    companion object {
        private lateinit var p: Player
        private lateinit var i: ItemStack
        private var id: Int = 0
        private lateinit var connection: Connection
    }
}