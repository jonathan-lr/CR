package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.items.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GetReward() : CommandExecutor {
    private var types = arrayOf("§7Air", "§cFire", "§9Water", "§2Earth")
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            sender.sendMessage("§cCR §8| ${sender.displayName} §fkindly fuck off")
            return false
        }
        if (args.isEmpty()) {
            sender.sendMessage("§cCR §8| §fkindly fuck off")
            return false
        }

        val player = Bukkit.getServer().getPlayerExact(args[0])

        if (player is Player) {
            Bukkit.broadcastMessage("§cCR §8| ${player.displayName} §fcompleted the ${types[Integer.parseInt(args[1])]} §fDungeon")
            player.playSound(player.location, Sound.ENTITY_WITCH_CELEBRATE, 1.0f, 1.0f)
            when(args[1]) {
                "0" -> player.inventory.addItem(DungeonItems().airReward())
                "1" -> player.inventory.addItem(DungeonItems().fireReward())
                "2" -> player.inventory.addItem(DungeonItems().waterReward())
                "3" -> player.inventory.addItem(DungeonItems().earthReward())
            }
            Bukkit.getServer().getWorld("world")?.let { player.teleport(Location(it.spawnLocation.world, 200.5, 100.0, 50.5)) }
        } else {
            Bukkit.broadcastMessage("§cCR §8| §fsomething broke")
        }

        return false
    }
}