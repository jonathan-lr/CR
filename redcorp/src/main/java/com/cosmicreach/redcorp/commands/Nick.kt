package com.cosmicreach.redcorp.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class Nick : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {return false}

        if (args.size > 1) {
            sender.sendMessage("§cCR §8| §4No spaces plox${args.joinToString(" ")}")
            return false
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player ${sender.playerListName} customtagname ${args.joinToString(" ")}")
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player ${sender.playerListName} customtabname ${args.joinToString(" ")}")
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nickname ${sender.playerListName} ${args.joinToString(" ")}")
        sender.sendMessage("§cCR §8| §4Setting new nickname ${args.joinToString(" ")}")

        return false
    }
}

class NickComplete : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return mutableListOf("")
    }
}