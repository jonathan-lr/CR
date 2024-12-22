package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Magic
import com.cosmicreach.redcorp.menus.Confirm
import com.cosmicreach.redcorp.menus.Scrolls
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Merlin(
    private val player: Player,
    private val econ: Economy,
    private val confirm: HashMap<Player, Boolean>
) {
    private val connection = RedCorp.getPlugin().getConnection()!!

    fun run() {
        val hasMagic = RedCorp.getPlugin().getMagicUnlocked()
        val stage: Int

        if (hasMagic[player] == true) {
            openGui()
        } else {
            stage = Magic(connection).getPlayerStage(player.uniqueId)!!
            when (stage) {
                0 -> stage1()
                1 -> stage2()
                2 -> stage3()
                3 -> stage4()
                4 -> stage5()
                5 -> stage6()
                6 -> openGui()
                else -> {
                    player.sendMessage("§cServer §8|§r Something went wrong please tell Red!")
                }
            }
        }
    }

    private fun stage1() {
        player.sendMessage("§dMerlin §8|§r Ah I see you are not yet verse with the ways of §dmagic§r!")
        player.sendMessage("§dMerlin §8|§r Would you like to go on a quest to unlock these powers?")
        Magic(connection).updatePlayerStage(player.uniqueId, 1)
        val window = Window.single()
            .setViewer(player)
            .setTitle("§6§lGo on a quest for magic?")
            .setGui(Confirm(confirm).makeGUI())
            .build()
        window.open()
    }

    private fun stage2() {
        if (confirm.containsKey(player)) {
            if (confirm[player] == true) {
                confirm.remove(player)
                Magic(connection).updatePlayerStage(player.uniqueId, 2)
                player.sendMessage("§dMerlin §8|§r Okay then some of my fairies have escaped and I need you to collect them!")
                player.sendMessage("§dMerlin §8|§r Come back to me when you have found all 20.")
            } else {
                confirm.remove(player)
                Magic(connection).updatePlayerStage(player.uniqueId, 0)
                player.sendMessage("§dMerlin §8|§r That's a shame come back any time.")
            }
        } else {
            player.sendMessage("§cServer §8|§r Something went wrong try again")
            Magic(connection).updatePlayerStage(player.uniqueId, 0)
        }
    }

    private fun stage3() {
        val fairiesFound = RedCorp.getPlugin().getFairies()
        val playerFairies = fairiesFound[player]
        val totalFound = playerFairies?.count { it } ?: 0
        if (totalFound >= 20) {
            player.sendMessage("§dMerlin §8|§r Well done you have found all the fairies, now go get me Axolotl I need one for my studies.")
            Magic(connection).updatePlayerStage(player.uniqueId, 3)
        } else {
            player.sendMessage("§dMerlin §8|§r You haven't quite got them all yet. Come back to me when you have found them!")
        }
    }

    private fun stage4() {
        if(player.inventory.itemInMainHand.type == Material.AXOLOTL_BUCKET) {
            player.inventory.itemInMainHand.amount -= 1
            player.sendMessage("§dMerlin §8|§r Thank you ${player.displayName}§r that's just what I needed!")
            player.world.playSound(player.location, Sound.ITEM_HONEY_BOTTLE_DRINK, 0.75f, 1.0f)
            player.world.playSound(player.location, Sound.ENTITY_WANDERING_TRADER_DRINK_MILK, 0.75f, 1.0f)
            Magic(connection).updatePlayerStage(player.uniqueId, 4)
        } else {
            player.sendMessage("§dMerlin §8|§r No ${player.displayName}§r that's not what im looking for.")
        }
    }

    private fun stage5() {
        player.sendMessage("§dMerlin §8|§r Oh right sorry ${player.displayName}§r! Final thing I need from is some Dragon's Breath, never can quite bring my self to go to the end. Get that and ill make you a §dWizard§r!")
        Magic(connection).updatePlayerStage(player.uniqueId, 5)
    }

    private fun stage6() {
        if(player.inventory.itemInMainHand.type == Material.DRAGON_BREATH) {
            player.inventory.itemInMainHand.amount -= 1
            player.sendMessage("§dMerlin §8|§r Okay here you go §owizz§r magic powers are yours!")
            player.world.playSound(player.location, Sound.ENTITY_WANDERING_TRADER_DRINK_MILK, 0.75f, 1.0f)
            Magic(connection).updatePlayerStage(player.uniqueId, 6)
            Magic(connection).updatePlayerMagic(player.uniqueId, true)
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