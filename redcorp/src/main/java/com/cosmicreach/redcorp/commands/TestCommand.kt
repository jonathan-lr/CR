package com.cosmicreach.redcorp.commands

import com.comphenix.protocol.ProtocolManager
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta

class TestCommand(private val protocolManager: ProtocolManager?) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) { return false }
        if (sender.hasPermission("redcorp.dev")) {
            sender.sendMessage("§cCR §8| §f${sender.displayName} nothing here")
            sender.sendMessage("")
            sender.sendMessage("§cCR §8| §f Item id: ${ Utils().getID(sender.inventory.itemInMainHand)} ")
            sender.sendMessage("")
            sender.sendMessage("§cCR §8| §f Item meta: ${sender.inventory.itemInMainHand.itemMeta as ItemMeta} ")
            sender.sendMessage("")
            sender.sendMessage("§cCR §8| §f Item Data: ${sender.inventory.itemInMainHand.data} ")
            //sender.health = 0.0
        } else {
            sender.sendMessage("§cCR §8| §c${sender.displayName} kindly fuck off")
            return false
        }


        return false
    }
}

class CompleteTestCommand : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {

        return null
    }
}