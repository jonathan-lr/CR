package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Greenhouse
import com.cosmicreach.redcorp.items.CustomItems
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.items.GreenhouseItems
import com.cosmicreach.redcorp.items.ServerItems
import com.cosmicreach.redcorp.menus.AgingBarrel
import com.cosmicreach.redcorp.menus.CoffeeMachine
import com.cosmicreach.redcorp.menus.DryingRack
import com.cosmicreach.redcorp.menus.FruitGamble
import com.cosmicreach.redcorp.menus.Grinder
import com.cosmicreach.redcorp.menus.MixingMachine
import com.cosmicreach.redcorp.menus.PressMachine
import com.cosmicreach.redcorp.menus.Teleport
import com.cosmicreach.redcorp.utils.ChatUtil
import com.cosmicreach.redcorp.utils.TeleportActions
import com.cosmicreach.redcorp.utils.Utils
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
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
                401, 403, 404, 405, 406, 407, 399 -> customBlock()
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
                in 700..732 -> customBlock()
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

                    //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sudo ${p.name} island create greenhouse")

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
                            label = "§8[§2§lʏᴇs</bold>§r§8]",
                            command = "/raid confirm",
                            hoverText = "§7Start the raid!"
                        )

                        space()

                        button(
                            label = "§8[§4§lɴᴏ§r§8]",
                            command = "/raid cancel",
                            hoverText = "§7Nah I changed my mind"
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
                val fairy = nbt.getBoolean("fairy")
                if (fairy) {
                    fairy()
                    return
                }
                val barrel = nbt.getBoolean("barrel")
                val drying = nbt.getBoolean("drying")
                val grind = nbt.getBoolean("grind")
                val mixer = nbt.getBoolean("mixer")
                val press = nbt.getBoolean("press")
                val coffee = nbt.getBoolean("coffee")
                val slot = nbt.getBoolean("slot")
                if (event.action == Action.RIGHT_CLICK_BLOCK) {
                    val viewers = RedCorp.getPlugin().getAgingViewers()
                    val agingBarrels = RedCorp.getPlugin().getAgingBarrels()

                    if (barrel) {
                        val window = Window.single()
                            .setViewer(p)
                            .setTitle("§r\uE100\uE002")
                            .setGui(AgingBarrel(agingBarrels).getOrCreateGui(block))
                            .build()

                        window.open()
                    }

                    if (drying) {
                        val window = Window.single()
                            .setViewer(p)
                            .setTitle("§r\uE100\uE003")
                            .setGui(DryingRack(agingBarrels).getOrCreateGui(block))
                            .build()

                        window.open()
                    }

                    if (grind) {
                        val window = Window.single()
                            .setViewer(p)
                            .setTitle("§r\uE100\uE003")
                            .setGui(DryingRack(agingBarrels).getOrCreateGui(block))
                            .build()

                        window.open()
                    }

                    if (mixer) {
                        val window = Window.single()
                            .setViewer(p)
                            .setTitle("§rMixing Machine")
                            .setGui(MixingMachine(agingBarrels).getOrCreateGui(block))
                            .build()

                        window.open()
                    }

                    if (press) {
                        val window = Window.single()
                            .setViewer(p)
                            .setTitle("§rPress Machine")
                            .setGui(PressMachine(agingBarrels).getOrCreateGui(block))
                            .build()

                        window.open()
                    }

                    if (slot) {
                        val window = Window.single()
                            .setViewer(p)
                            .setTitle("§r\uE100\uE004")
                            .setGui(FruitGamble().makeGUI(p))
                            .build()

                        window.open()
                    }

                    if (coffee) {
                        val window = Window.single()
                            .setViewer(p)
                            .setTitle("§r\uE100\uE001")
                            .setGui(CoffeeMachine(agingBarrels).getOrCreateGui(block))
                            .build()

                        window.open()
                    }
                } else {
                    val center = block.location.clone().add(0.5, 0.5, 0.5)
                    if (coffee) {
                        block.type = Material.AIR
                        block.world.dropItem(center, DrugItems().CoffieMachine(1))

                        nbt.clearNBT()

                        Utils().breakFakeBlock(block)
                    }
                    if (barrel) {
                        block.type = Material.AIR
                        block.world.dropItem(center, DrugItems().AgingBarrel(1))

                        nbt.clearNBT()

                        Utils().breakFakeBlock(block)
                    }
                    if (drying) {
                        block.type = Material.AIR
                        block.world.dropItem(center, DrugItems().DryingRack(1))

                        nbt.clearNBT()

                        Utils().breakFakeBlock(block)
                    }
                    if (grind) {
                        block.type = Material.AIR
                        block.world.dropItem(center, DrugItems().IdustrialGrinder(1))

                        nbt.clearNBT()

                        Utils().breakFakeBlock(block)
                    }
                    if (mixer) {
                        block.type = Material.AIR
                        block.world.dropItem(center, DrugItems().Mixer(1))

                        nbt.clearNBT()

                        Utils().breakFakeBlock(block)
                    }
                    if (press) {
                        block.type = Material.AIR
                        block.world.dropItem(center, DrugItems().Press(1))

                        nbt.clearNBT()

                        Utils().breakFakeBlock(block)
                    }
                    if (slot) {
                        if (event.player.hasPermission("redcorp.break-slot")) {
                            block.type = Material.AIR
                            block.world.dropItem(center, ServerItems().SlotMachine(1))

                            nbt.clearNBT()

                            Utils().breakFakeBlock(block)
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
                    fairiesFound[p] = Array(32) { false }
                }
                val playerFairies = fairiesFound[p]
                if (playerFairies!![fairyId]) {
                    p.sendMessage("§cCR §8|§r You already found Fairy #${fairyId+1} silly")
                } else {
                    p.world.playSound(p.location, Sound.ENTITY_ALLAY_DEATH, 0.75f, 1.0f)
                    playerFairies[fairyId] = true
                    fairiesFound[p] = playerFairies
                    p.sendMessage("§cCR §8|§r You found Fairy #${fairyId+1} you have found ${playerFairies.count { it }}/32")
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
        p.sendMessage("§cCR §8|§r debug press ${nbt.getBoolean("press")}")
        p.sendMessage("§cCR §8|§r debug grinder ${nbt.getBoolean("grinder")}")
        p.sendMessage("§cCR §8|§r debug mixer ${nbt.getBoolean("mixer")}")
        p.sendMessage("§cCR §8|§r debug shroom ${nbt.getBoolean("shroom")}")
        p.sendMessage("§cCR §8|§r debug truffle ${nbt.getBoolean("truffle")}")
        p.sendMessage("§cCR §8|§r debug fairy ${nbt.getBoolean("fairy")}")
        p.sendMessage("§cCR §8|§r debug fairyId ${nbt.getBoolean("fairyId")}")
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
            var scale = 0F
            var freeRotaion = false
            if (id == 401) {
                item = DrugItems().AgingBarrel(1)
                scale = 1.2F
            } else if (id == 399) {
                item = ServerItems().SlotMachine(1)
                scale = 1F
            }  else if (id == 403) {
                item = DrugItems().CoffieMachine(1)
                scale = 0.8F
            } else if (id == 404) {
                item = DrugItems().DryingRack(1)
                scale = 0.4F
            } else if (id == 405) {
                item = DrugItems().IdustrialGrinder(1)
                scale = 1.2F
            } else if (id == 406) {
                item = DrugItems().Mixer(1)
                scale = 1.2F
            } else if (id == 407) {
                item = DrugItems().Press(1)
                scale = 1.2F
            } else if (id in 700..732) {
                item = CustomItems().Fairy(1, (id-700).toString())
                scale = 0.6F
                freeRotaion = true
            }

            Utils().placeFakeBlock(item, block, scale, event, freeRotaion)

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
                        p.sendMessage("§cCR §8|§r Creating Greenhouse")
                        val loc = p.location
                        val locString = "${loc.world?.name},${loc.x},${loc.y},${loc.z},${loc.yaw},${loc.pitch}"

                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sudo ${p.name} island create greenhouse")

                        val newGreenhouse = Greenhouse(connection).createGreenhouse("", locString)
                        Greenhouse(connection).linkPlayerToGreenhouse(newGreenhouse, p.uniqueId, true)
                        i = GreenhouseItems().GreenhouseEntrance(newGreenhouse)
                        val greenhouseTracking = RedCorp.getPlugin().getGreenhouseTracker()

                        greenhouseTracking.put(p, newGreenhouse)
                    }

                    greenhouseId = Utils().getGreenhouseId(i)
                    setData()

                    val greenhouse = Greenhouse(connection).getGreenhouseById(greenhouseId!!)
                    val item = GreenhouseItems().GreenhouseEntrance(greenhouseId)

                    Utils().placeFakeBlock(item, block, 0.7F, event)

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
            if (block.location.world?.name != "greenhouse") {
                p.sendMessage("§cCR §8|§r Sorry this can only grow in a greenhouse")
                event.isCancelled = true
                return
            }
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
            if (block.location.world?.name != "greenhouse") {
                p.sendMessage("§cCR §8|§r Sorry this can only grow in a greenhouse")
                event.isCancelled = true
                return
            }
            if (block.type == Material.PODZOL && event.blockFace == BlockFace.UP) {
                setData()
            } else {
                event.isCancelled = true
                p.sendMessage("§cCR §8|§r This must be placed on podzol :)")
            }
        }
    }

    private fun myceliumDrug() {
        val block = event.clickedBlock
        if (block != null) {
            if (block.location.world?.name != "greenhouse") {
                p.sendMessage("§cCR §8|§r Sorry this can only grow in a greenhouse")
                event.isCancelled = true
                return
            }
            if (block.type == Material.MYCELIUM && event.blockFace == BlockFace.UP) {

                setData()
            } else {
                event.isCancelled = true
                p.sendMessage("§cCR §8|§r This must be placed on mycelium :)")
            }
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
            399 -> nbt.setBoolean("slot", true)
            401 -> nbt.setBoolean("barrel", true)
            403 -> nbt.setBoolean("coffee", true)
            404 -> nbt.setBoolean("drying", true)
            405 -> nbt.setBoolean("grind", true)
            406 -> nbt.setBoolean("mixer", true)
            407 -> nbt.setBoolean("press", true)
            420 -> nbt.setBoolean("weed", true)
            430 -> nbt.setBoolean("coke", true)
            440 -> nbt.setBoolean("poppy", true)
            450 -> nbt.setBoolean("shroom", true)
            451 -> nbt.setBoolean("truffle", true)
            473 -> nbt.setBoolean("coffeeBean", true)
            in 700..732 -> {
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