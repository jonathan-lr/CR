package com.cosmicreach.redcorp.commands

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import java.lang.Integer.parseInt

class GodMode(private val config: FileConfiguration) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {return false}
        if (sender.hasPermission("redcorp.god")) {
            if (args.isEmpty()) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} invalid arguments, 1st 1-3")
                return false
            }

            var s = config.getString("configuration.red.survival")
            var c = config.getString("configuration.red.creative")
            var a = config.getString("configuration.red.adventure")
            if (sender.playerListName == "YorickSK") {
                s = config.getString("configuration.yorick.survival")
                c = config.getString("configuration.yorick.creative")
                a = config.getString("configuration.yorick.adventure")
            }
            if (sender.playerListName == "Wasbie") {
                s = config.getString("configuration.jay.survival")
                c = config.getString("configuration.jay.creative")
                a = config.getString("configuration.jay.adventure")
            }

            when(parseInt(args[0])) {
                1 -> {
                    sender.sendMessage("§cCR §8| Transitioning to Survival Mode")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player ${sender.playerListName} customtagname $s")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player ${sender.playerListName} customtabname $s")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nickname ${sender.playerListName} $s")
                    if (sender.gameMode == GameMode.CREATIVE) {
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l-§8]§r ${c?.replace("&", "§")}")
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l+§8]§r ${s?.replace("&", "§")}")
                    } else {
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l-§8]§r ${a?.replace("&", "§")}")
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l+§8]§r ${s?.replace("&", "§")}")
                    }
                    sender.gameMode = GameMode.SURVIVAL
                }
                2 -> {
                    sender.sendMessage("§cCR §8| Transitioning to God Mode")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player ${sender.playerListName} customtagname $c")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player ${sender.playerListName} customtabname $c")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nickname ${sender.playerListName} $c")
                    if (sender.gameMode == GameMode.SURVIVAL) {
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l-§8]§r ${s?.replace("&", "§")}")
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l+§8]§r ${c?.replace("&", "§")}")
                    } else {
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l-§8]§r ${a?.replace("&", "§")}")
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l+§8]§r ${c?.replace("&", "§")}")
                    }
                    sender.gameMode = GameMode.CREATIVE
                }
                3 -> {
                    sender.sendMessage("§cCR §8| Transitioning to Adventure Mode")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player ${sender.playerListName} customtagname $a")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player ${sender.playerListName} customtabname $a")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nickname ${sender.playerListName} $a")
                    if (sender.gameMode == GameMode.SURVIVAL) {
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l-§8]§r ${s?.replace("&", "§")}")
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l+§8]§r ${a?.replace("&", "§")}")
                    } else {
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l-§8]§r ${c?.replace("&", "§")}")
                        Bukkit.broadcastMessage("§cCR §8| §8[§4§l+§8]§r ${a?.replace("&", "§")}")
                    }
                    sender.gameMode = GameMode.ADVENTURE
                }
            }
        }
        else {
            sender.sendMessage("§cCR §8| §4Your power level is to low to transition into a god")
        }
        return false
    }

}