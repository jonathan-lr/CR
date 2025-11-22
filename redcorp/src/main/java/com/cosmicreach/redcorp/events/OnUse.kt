package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.commands.Materia
import com.cosmicreach.redcorp.db.Greenhouse
import com.cosmicreach.redcorp.items.GreenhouseItems
import com.cosmicreach.redcorp.menus.Grinder
import com.cosmicreach.redcorp.menus.Teleport
import com.cosmicreach.redcorp.utils.TeleportActions
import com.cosmicreach.redcorp.utils.Utils
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.entity.ArmorStand
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.window.Window
import java.sql.Connection

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
                401, 403, 210 -> setData()
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

            if (event.clickedBlock != null) {
                if(event.clickedBlock!!.type == Material.PLAYER_HEAD) {
                    if (event.hand == EquipmentSlot.HAND) {
                        fairy()
                    }
                }

                if(event.clickedBlock!!.type == Material.GREEN_STAINED_GLASS) {
                    if (event.action != Action.RIGHT_CLICK_BLOCK) return
                    if (event.hand == EquipmentSlot.HAND) {
                        val nbt = NBTBlock(event.clickedBlock).data
                        val isGreenhouse = nbt.getBoolean("greenhouse")
                        val greenhouseOwner = nbt.getUUID("greenhouse-owner")

                        if (isGreenhouse) {
                            if (greenhouseOwner != null) {
                                val greenhouse = Greenhouse(connection).getGreenhouse(greenhouseOwner)

                                if (greenhouse != null) {
                                    val parts: List<String>
                                    if (greenhouseOwner == p.uniqueId) {
                                        parts = greenhouse.home.split(",")
                                    } else {
                                        parts = greenhouse.visit.split(",")
                                    }

                                    val world = Bukkit.getWorld(parts[0])
                                    val x = parts[1].toDouble()
                                    val y = parts[2].toDouble()
                                    val z = parts[3].toDouble()
                                    val yaw = parts[4].toFloat()
                                    val pitch = parts[5].toFloat()

                                    val location = Location(world, x, y, z, yaw, pitch)

                                    p.teleport(location)
                                } else {
                                    val loc = p.location
                                    val locString = "${loc.world?.name},${loc.x},${loc.y},${loc.z},${loc.yaw},${loc.pitch}"

                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sudo ${p.playerListName} island create greenhouse")

                                    val greenhouseTracking = RedCorp.getPlugin().getGreenhouseTracker()

                                    greenhouseTracking.put(p, locString)
                                }
                            }
                        }
                    }
                }

                if(event.clickedBlock!!.type == Material.BLACK_STAINED_GLASS) {
                    if (event.action != Action.RIGHT_CLICK_BLOCK) return
                    if (event.hand == EquipmentSlot.HAND) {
                        val nbt = NBTBlock(event.clickedBlock).data
                        val isGreenhouse = nbt.getBoolean("greenhouse")
                        val greenhouseOwner = nbt.getUUID("greenhouse-owner")

                        if (isGreenhouse) {
                            if (greenhouseOwner != null) {
                                val greenhouse = Greenhouse(connection).getGreenhouse(greenhouseOwner)

                                if (greenhouse != null) {
                                    val parts = greenhouse.exitl.split(",")

                                    val world = Bukkit.getWorld(parts[0])
                                    val x = parts[1].toDouble()
                                    val y = parts[2].toDouble()
                                    val z = parts[3].toDouble()
                                    val yaw = parts[4].toFloat()
                                    val pitch = parts[5].toFloat()

                                    val location = Location(world, x, y, z, yaw, pitch)

                                    p.teleport(location)
                                } else {
                                    p.sendMessage("§cCR §8|§r Something has gone wrong call Zach he can fix it")
                                }
                            }
                        }
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
        p.sendMessage("§cCR §8|§r debug greenhouseOwner ${nbt.getUUID("greenhouse-owner")}")
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


    private fun greenhouse() {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        val block = event.clickedBlock
        if (block != null) {
            if (id == 201) {
                if (block.location.world?.name == "world") {
                    setData()
                    val owner = Utils().getGreenhouseUUID(i)
                    val greenhouse = Greenhouse(connection).getGreenhouse(owner!!)

                    val worldPlace = block.location.world!!

                    val standLocation = Location(
                        worldPlace,
                        block.x + 0.5,
                        block.y - 0.3,
                        block.z + 0.5
                    )

                    val stand = worldPlace.spawn(standLocation, ArmorStand::class.java) { asd: ArmorStand ->
                        asd.isInvisible = true
                        asd.isMarker = true
                        asd.setGravity(false)
                        asd.isInvulnerable = true
                        asd.isCustomNameVisible = false
                        asd.setBasePlate(false)
                        asd.setArms(false)
                        asd.isSmall = false
                    }

                    val item = GreenhouseItems().GreenhouseEntrance(p.uniqueId)
                    stand.equipment?.helmet = item
                    stand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING)

                    if (greenhouse != null) {
                        val loc = p.location
                        val locString = "${loc.world?.name},${loc.x},${loc.y},${loc.z},${loc.yaw},${loc.pitch}"
                        Greenhouse(connection).updateExit(owner, locString)
                        p.sendMessage("§cCR §8|§r Set greenhouse exit location")
                    }
                } else {
                    event.isCancelled = true
                    p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rthis must be placed in the overworld")
                }
            } else {
                if (block.location.world?.name == "greenhouse") {
                    setData()
                    val owner = Utils().getGreenhouseUUID(i)
                    val greenhouse = Greenhouse(connection).getGreenhouse(owner!!)

                    if (greenhouse != null) {
                        val loc = p.location
                        val locString = "${loc.world?.name},${loc.x},${loc.y},${loc.z},${loc.yaw},${loc.pitch}"
                        Greenhouse(connection).updateHome(owner, locString)
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
                val owner = Utils().getGreenhouseUUID(i)
                nbt.setBoolean("greenhouse", true)
                nbt.setUUID("greenhouse-owner", owner)
            }
            202 -> {
                val owner = Utils().getGreenhouseUUID(i)
                nbt.setBoolean("greenhouse", true)
                nbt.setUUID("greenhouse-owner", owner)
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