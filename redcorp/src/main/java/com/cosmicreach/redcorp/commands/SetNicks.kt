package com.cosmicreach.redcorp.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.lang.Integer.parseInt

class SetNicks(private val plugin: JavaPlugin, private val config: FileConfiguration) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {return false}
        if (args.size <= 1) {
            sender.sendMessage("§cCR §8| §c${sender.displayName} invalid arguments 1st 1-3, 2nd Name")
            return false
        }

        if (sender.hasPermission("redcorp.setnicks")) {
            if (sender.playerListName == "Redrobbo") {
                when(parseInt(args[0])) {
                    1 -> {
                        config.set("configuration.red.survival", args[1])
                        plugin.saveConfig()
                        sender.sendMessage("§cCR §8|§r Survival name updated Successfully")
                    }
                    2 -> {
                        config.set("configuration.red.creative", args[1])
                        plugin.saveConfig()
                        sender.sendMessage("§cCR §8|§r Creative name updated Successfully")
                    }
                    3 -> {
                        config.set("configuration.red.adventure", args[1])
                        plugin.saveConfig()
                        sender.sendMessage("§cCR §8|§r Adventure name updated Successfully")
                    }
                }
            }
            if (sender.playerListName == "YorickSK") {
                when(parseInt(args[0])) {
                    1 -> {
                        config.set("configuration.yorick.survival", args[1])
                        plugin.saveConfig()
                        sender.sendMessage("§cCR §8|§r Survival name updated Successfully")
                    }
                    2 -> {
                        config.set("configuration.yorick.creative", args[1])
                        plugin.saveConfig()
                        sender.sendMessage("§cCR §8|§r Creative name updated Successfully")
                    }
                    3 -> {
                        config.set("configuration.yorick.adventure", args[1])
                        plugin.saveConfig()
                        sender.sendMessage("§cCR §8|§r Adventure name updated Successfully")
                    }
                }
            }
            if (sender.playerListName == "Wasbie") {
                when(parseInt(args[0])) {
                    1 -> {
                        config.set("configuration.jay.survival", args[1])
                        plugin.saveConfig()
                        sender.sendMessage("§cCR §8|§r Survival name updated Successfully")
                    }
                    2 -> {
                        config.set("configuration.jay.creative", args[1])
                        plugin.saveConfig()
                        sender.sendMessage("§cCR §8|§r Creative name updated Successfully")
                    }
                    3 -> {
                        config.set("configuration.jay.adventure", args[1])
                        plugin.saveConfig()
                        sender.sendMessage("§cCR §8|§r Adventure name updated Successfully")
                    }
                }
            }
        } else {
            sender.sendMessage("§cCR §8| §4Your power level is to low to do this")
        }
        return false
    }
}