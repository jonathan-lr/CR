package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Progress
import com.cosmicreach.redcorp.menus.Confirm
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window
import java.util.concurrent.ThreadLocalRandom

class Kyle (private val player: Player) {
    private val connection = RedCorp.getPlugin().getConnection()!!

    fun run () {
        val item = player.inventory.itemInMainHand
        if (!Utils().checkID(item, arrayOf(50))) {
            player.sendMessage(kyleResponse())
            return
        }
        var stage = Progress(connection).getStage(player.uniqueId, 3)

        if (stage == null) {
            Progress(connection).addPlayer(player.uniqueId, 3)
            stage = 0
        }

        when (stage) {
            0 -> {
                player.sendMessage("§cKyle §8|§r Ah I see you have a Dungeon Token perhaps you want to give it to me?")
                Progress(connection).updateStage(player.uniqueId, 3, 1)
                val window = Window.single()
                    .setViewer(player)
                    .setTitle("§6§lGive Kyle the Token?")
                    .setGui(Confirm(3).makeGUI())
                    .build()
                window.open()
            }
            1 -> {
                val confirm = Progress(connection).getConfirm(player.uniqueId, 2)!!
                if (confirm) {
                    Progress(connection).updateStage(player.uniqueId, 3, 0)
                    Progress(connection).updateConfirm(player.uniqueId, 3, false)
                    player.sendMessage("§cKyle §8|§r Thank you ${player.displayName}, I can finally go home and see my son Jeremy")
                    player.inventory.itemInMainHand.amount -= 1
                } else {
                    Progress(connection).updateStage(player.uniqueId, 3, 2)
                    player.sendMessage("§cKyle §8|§r Fine, I suppose you want to defeat the dungeon boss your self?")
                    val window = Window.single()
                        .setViewer(player)
                        .setTitle("§6§lFight the boss yourself?")
                        .setGui(Confirm(3).makeGUI())
                        .build()
                    window.open()
                }
            }
            2 -> {
                val confirm = Progress(connection).getConfirm(player.uniqueId, 2)!!
                if (confirm) {
                    Progress(connection).updateStage(player.uniqueId, 3, 0)
                    Progress(connection).updateConfirm(player.uniqueId, 3, false)
                    player.sendMessage("§cKyle §8|§r Okay well off you go then, ill use my leftover magic to send you to the boss lair. If you find my son please let him know I love him.")
                    player.inventory.itemInMainHand.amount -= 1
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"warp WaterBoss ${player.name}")
                } else {
                    Progress(connection).updateStage(player.uniqueId, 3, 0)
                    player.sendMessage("§cKyle §8|§r So you wont give me the token and you wont fight the boss whats wrong with you?")
                }
            }
        }
        return
    }

    private fun kyleResponse(): String {
        when (ThreadLocalRandom.current().nextInt(0, 8)) {
            0 -> return "§cKyle §8|§r Jeramey, my son, if you can hear me, know that your old man is doing everything he can to reunite with you."
            1 -> return "§cKyle §8|§r These dungeon waters are relentless, just like time itself. I must find a way out of here."
            2 -> return "§cKyle §8|§r In this watery abyss, I've heard whispers of a powerful dungeon token that can unlock the boss lair. Perhaps it's our ticket to escape."
            3 -> return "§cKyle §8|§r The waters here are deep, cold, and unforgiving. If only I had that elusive dungeon token to enter the boss lair and find Jeramey."
            4 -> return "§cKyle §8|§r Oh hi traveler. §o*mubles* §rLost in this endless maze... The walls... they close in on me."
            5 -> return "§cKyle §8|§r §o*wispers* §rThe darkness... it's consuming me. I can't find my way out. Please, forgive me..."
            6 -> return "§cKyle §8|§r I've been here for too long, the water, the echoes... it's all I know now. I can't remember anything else."
            7 -> return "§cKyle §8|§r The dungeon token, I need it. If I can just find it, I can escape this torment."
        }
        return ""
    }
}