package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Progress
import com.cosmicreach.redcorp.menus.Confirm
import com.cosmicreach.redcorp.menus.Scrolls
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Merlin(
    private val player: Player
) {
    private val connection = RedCorp.getPlugin().getConnection()!!
    private val econ = RedCorp.getPlugin().getEcon()

    fun run() {
        val hasMagic = RedCorp.getPlugin().magicUnlocked
        var stage = Progress(connection).getStage(player.uniqueId, 1)

        if (stage == null) {
            Progress(connection).addPlayer(player.uniqueId, 1)
            stage = 0
        }

        if (hasMagic[player] == true) {
            openGui()
        } else {
            when (stage) {
                0 -> stage1()
                1 -> stage2()
                2 -> stage3()
                3 -> stage4()
                4 -> stage5()
                5 -> stage6()
                6 -> stage7()
                7 -> openGui()
                else -> {
                    player.sendMessage("§cServer §8|§r Something went wrong please tell Red!")
                }
            }
        }
    }

    private fun stage1() {
        player.sendMessage("§dMerlin §8|§r Ah I see you are not yet verse with the ways of §dmagic§r!")
        player.sendMessage("§dMerlin §8|§r Would you like to go on a quest to unlock these powers?")
        Progress(connection).updateStage(player.uniqueId, 1, 1)
        val window = Window.single()
            .setViewer(player)
            .setTitle("§6§lGo on a quest for magic?")
            .setGui(Confirm(1).makeGUI())
            .build()
        window.open()
    }

    private fun stage2() {
        val confirm = Progress(connection).getConfirm(player.uniqueId, 1)!!
        if (confirm) {
            Progress(connection).updateStage(player.uniqueId, 1, 2)
            player.sendMessage("§dMerlin §8|§r Okay then some of my fairies have escaped and I need you to collect them!")
            player.sendMessage("§dMerlin §8|§r Come back to me when you have found all 32.")
        } else {
            Progress(connection).updateStage(player.uniqueId, 1, 0)
            player.sendMessage("§dMerlin §8|§r That's a shame come back any time.")
        }
    }

    private fun stage3() {
        val fairiesFound = RedCorp.getPlugin().fairiesFound
        val playerFairies = fairiesFound[player]
        val totalFound = playerFairies?.count { it } ?: 0
        if (totalFound >= 32) {
            player.sendMessage("§dMerlin §8|§r Well done you have found all the fairies, now go get me a turtle egg I need more for my studies.")
            Progress(connection).updateStage(player.uniqueId, 1, 3)
        } else {
            player.sendMessage("§dMerlin §8|§r You haven't quite got them all yet. Come back to me when you have found them!")
        }
    }

    private fun stage4() {
        if(player.inventory.itemInMainHand.type == Material.TURTLE_EGG) {
            player.inventory.itemInMainHand.amount -= 1
            player.sendMessage("§dMerlin §8|§r Thank you ${player.displayName}§r that's just what I needed!")
            player.world.playSound(player.location, Sound.ITEM_HONEY_BOTTLE_DRINK, 0.75f, 1.0f)
            player.world.playSound(player.location, Sound.ENTITY_WANDERING_TRADER_DRINK_MILK, 0.75f, 1.0f)
            Progress(connection).updateStage(player.uniqueId, 1, 4)
        } else {
            player.sendMessage("§dMerlin §8|§r No ${player.displayName}§r that's not what im looking for.")
        }
    }

    private fun stage5() {
        player.sendMessage("§dMerlin §8|§r Oh right sorry ${player.displayName}§r! There are these new infernal copper creatures. Catch me on of those will you!")
        Progress(connection).updateStage(player.uniqueId, 1, 5)
    }

    private fun stage6() {
        val type = player.inventory.itemInMainHand.type
        if(type == Material.COPPER_GOLEM_STATUE || type == Material.EXPOSED_COPPER_GOLEM_STATUE || type == Material.OXIDIZED_COPPER_GOLEM_STATUE  || type == Material.WEATHERED_COPPER_GOLEM_STATUE) {
            player.inventory.itemInMainHand.amount -= 1
            player.sendMessage("§dMerlin §8|§r Thank you ${player.displayName}§r that's just the companion I needed!")
            player.world.playSound(player.location, Sound.ENTITY_COPPER_GOLEM_NO_ITEM_GET, 0.75f, 1.0f)
            Progress(connection).updateStage(player.uniqueId, 1, 6)
            player.sendMessage("§dMerlin §8|§r ${player.displayName}§r! Final thing I need from is some Dragon's Breath, never can quite bring my self to go to the end. Get that and ill make you a §dWizard§r!")
        } else {
            player.sendMessage("§dMerlin §8|§r No ${player.displayName}§r that's not what im looking for.")
        }
    }

    private fun stage7() {
        if(player.inventory.itemInMainHand.type == Material.DRAGON_BREATH) {
            player.inventory.itemInMainHand.amount -= 1
            player.sendMessage("§dMerlin §8|§r Okay here you go §owizz§r magic powers are yours!")
            player.world.playSound(player.location, Sound.ENTITY_WANDERING_TRADER_DRINK_MILK, 0.75f, 1.0f)
            Progress(connection).updateStage(player.uniqueId, 1, 7)
            Progress(connection).updateComplete(player.uniqueId, 1, true)
            player.world.spawnParticle(Particle.TOTEM_OF_UNDYING, player.location, 100)
        } else {
            player.sendMessage("§dMerlin §8|§r No ${player.displayName}§r that's not what im looking for.")
        }
    }

    private fun openGui() {
        player.sendMessage("§dMerlin §8|§r Need some scrolls traveler?")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§6§lScroll Exchange")
            .setGui(Scrolls(econ).makeGUI(player))
            .build()

        window.open()
        return
    }
}