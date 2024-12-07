package com.cosmicreach.redcorp.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.lang.Integer.parseInt

class AddRelic(private val plugin: JavaPlugin, private val config: FileConfiguration): CommandExecutor {
    private var types = arrayOf("air", "fire", "water", "earth")
    private var items = arrayOf("pick", "shovel", "hoe", "axe", "sword", "helmet", "chestplate", "leggings", "boots", "wings")

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {return false}
        if (sender.hasPermission("redcorp.add-relic")) {
            if (args.size <= 1) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} requires 2 arguments")
                return false
            }

            if (types.indexOf(args[0]) == -1) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} invalid type argument")
                return false
            }

            if (items.indexOf(args[1]) == -1) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} invalid item argument")
                return false
            }

            var configItem = parseInt(config.getString("configuration.${args[0]}.${args[1]}"))
            configItem += 1
            config.set("configuration.${args[0]}.${args[1]}", configItem)
            plugin.saveConfig()
            sender.sendMessage("§cCR §8| §rIncreased §c${args[0]}.${args[1]} §rby 1")
            Bukkit.broadcastMessage("§cCR §8| §rA God added 1 §6${args[0]} ${args[1]} §rto the vault")
        } else {
            sender.sendMessage("§cCR §8| §c${sender.displayName} kindly fuck off")
            return false
        }
        return false
    }
}

class AddRelicComplete: TabCompleter {
    private var types = arrayOf("air", "fire", "water", "earth")
    private var items = arrayOf("pick", "shovel", "hoe", "axe", "sword", "helmet", "chestplate", "leggings", "boots", "wings")

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if (args.size == 1) {
            return types.toMutableList()
        }

        if (args.size == 2) {
            return items.toMutableList()
        }

        return null
    }
}