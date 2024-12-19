package com.cosmicreach.redcorp.events

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
import org.bukkit.event.player.PlayerInteractEvent
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

            }
        }

        return
    }

    private fun gavel() {
        p.world.playSound(p.location, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 0.75f, 1.0f)
    }

    private fun useDrugs() {
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

        Bukkit.broadcastMessage("§cCR §8|§r debug weed ${nbt.getBoolean("weed")}")
        Bukkit.broadcastMessage("§cCR §8|§r debug coke ${nbt.getBoolean("coke")}")
        Bukkit.broadcastMessage("§cCR §8|§r debug popy ${nbt.getBoolean("poppy")}")
        Bukkit.broadcastMessage("§cCR §8|§r debug barrel ${nbt.getBoolean("barrel")}")
        Bukkit.broadcastMessage("§cCR §8|§r debug ferment ${nbt.getBoolean("ferment")}")
        Bukkit.broadcastMessage("§cCR §8|§r debug shroom ${nbt.getBoolean("shroom")}")
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
        if (event.clickedBlock!!.type == Material.FARMLAND) {
            setData()
        } else {
            p.sendMessage("§cCR §8|§r This must be placed on farmland :)")
        }
    }

    private fun podzolDrug() {
        if (event.clickedBlock!!.type == Material.PODZOL && event.blockFace == BlockFace.UP) {
            setData()
        } else {
            event.isCancelled = true
            p.sendMessage("§cCR §8|§r This must be placed on podzol :)")
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


    private fun setData() {
        val location = event.clickedBlock!!.location
        location.y += 1

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
        }
    }

    companion object {
        private lateinit var p: Player
        private lateinit var i: ItemStack
        private var id: Int = 0
    }
}