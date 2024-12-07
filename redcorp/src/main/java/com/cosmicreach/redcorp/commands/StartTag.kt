package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.TagItems
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class StartTag(private val plugin: JavaPlugin, private val config: FileConfiguration) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {return false}

        if (args.isEmpty()) {
            runTag(sender)
            Bukkit.broadcastMessage("§cCR §8|§r ${sender.displayName} has started a game of Tag!")
            return false
        }

        val target = Bukkit.getPlayer(args[0])
        if (target is Player) {
            runTag(target)
            Bukkit.broadcastMessage("§cCR §8|§r ${sender.displayName} has started a game of Tag with ${target.displayName} starting!")
            return false
        }
        return false
    }

    private fun runTag(player: Player) {
        val taggedPlayer = RedCorp.getPlugin().getTaggedPlayers()
        val lastTagged = RedCorp.getPlugin().getLastTagged()
        val passedTimes = RedCorp.getPlugin().getPassedTimes()

        val gameId = taggedPlayer.size+1

        taggedPlayer[gameId] = player
        lastTagged[gameId] = player
        passedTimes[gameId] = 0

        player.inventory.addItem(TagItems().tagStick(player.displayName, player.displayName, 0, gameId))
    }
}

class CompleteTag : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if (args.size == 1) {
            val players = Bukkit.getServer().onlinePlayers
            val list = mutableListOf<String>()
            players.forEach { p ->
                list.add(p.playerListName)
            }
            return list
        }

        return null
    }
}