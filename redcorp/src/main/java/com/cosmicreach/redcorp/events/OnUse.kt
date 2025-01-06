package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.Grinder
import com.cosmicreach.redcorp.menus.Teleport
import com.cosmicreach.redcorp.utils.TeleportActions
import com.cosmicreach.redcorp.utils.Utils
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.window.Window

class OnUse (
    private val event : PlayerInteractEvent,
    private val grinderPlayers: HashMap<Player, VirtualInventory>,
    private val teleportActions: TeleportActions
) {

    fun run() {
        if (event.item !== null) {
            p = event.player
            i = event.item!!
            id = Utils().getID(i)

            when (id) {
                2 -> gavel()
                423, 432, 441 -> useDrugs()
                400 -> grinder()
                401, 403 -> setData()
                2886 -> debug()
                3 -> playerTeleport()
                5 -> deathTeleport()
                6 -> homeTeleport()
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

    private fun useDrugs() {
        if (event.hand == EquipmentSlot.HAND) {
            when (id) {
                //Spliff
                423 -> {
                    p.world.playSound(p.location, Sound.ENTITY_BREEZE_INHALE, 0.2f, 1.0f)
                    p.world.playSound(p.location, Sound.ENTITY_BLAZE_BURN, 0.2f, 1.0f)
                    p.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, 1200, 1, true, false, false))
                    p.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, 1200, 2, true, false, false))
                }
                //Coke
                432 -> {
                    p.world.playSound(p.location, Sound.ENTITY_SNIFFER_SNIFFING, 0.75f, 1.0f)
                    p.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 1200, 2, true, false, false))
                }
                //Opium
                441 -> {
                    p.world.playSound(p.location, Sound.ENTITY_SNIFFER_SNIFFING, 0.75f, 1.0f)
                    p.addPotionEffect(PotionEffect(PotionEffectType.UNLUCK, 6000, 2, true, false, false))
                }
            }

            p.inventory.itemInMainHand.amount -= 1
        }
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
        if (event.clickedBlock == null) {
            p.world.playSound(p.location, Sound.ENTITY_STRIDER_EAT, 0.75f, 1.0f)
            p.addPotionEffect(PotionEffect(PotionEffectType.DARKNESS, 1200, 2, true, false, false))
            p.inventory.itemInMainHand.amount -= 1
        } else if (event.clickedBlock!!.type == Material.MYCELIUM && event.blockFace == BlockFace.UP) {
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
    }
}