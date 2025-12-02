package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Greenhouse
import com.cosmicreach.redcorp.utils.ChatUtil
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.time.Instant
import java.time.Duration

class Raid : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {return false}
        if (args.isEmpty()) { return false }

        val canRaid = RedCorp.getPlugin().getCanRaid()

        when (args[0].lowercase()) {
            "confirm" -> {
                val pCanRaid = canRaid[sender]
                if (pCanRaid == null) {
                    ChatUtil.send(sender, ChatUtil.json { text("You have no raid to confirm or it has expired") })
                    return true
                }
                val connection = RedCorp.getPlugin().getConnection()!!
                val hasTimeout = Greenhouse(connection).checkVisit(sender.uniqueId, pCanRaid)

                if (!hasTimeout!!.canVisit) {
                    val duration = Duration.between(Instant.now(), hasTimeout.nextAllowedAt)

                    val hours = duration.toHours()
                    val minutes = duration.toMinutesPart()

                    val formatted = when {
                        hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
                        hours > 0 -> "${hours}h"
                        minutes > 0 -> "${minutes}m"
                        else -> "less than a minute"
                    }

                    ChatUtil.send(sender, ChatUtil.json { text("You currently have a death timeout for ${formatted}") })
                    canRaid.remove(sender)
                    return true
                }

                val greenhouse = Greenhouse(connection).getGreenhouseById(pCanRaid)

                if (greenhouse == null) {
                    sender.sendMessage("§cCR §8|§r Something has gone wrong call Zach he can fix it")
                    canRaid.remove(sender)
                    return true
                }

                val parts = greenhouse.home.split(",")
                val world = Bukkit.getWorld(parts[0])
                val x = parts[1].toDouble()
                val y = parts[2].toDouble()
                val z = parts[3].toDouble()
                val yaw = parts[4].toFloat()
                val pitch = parts[5].toFloat()

                val location = Location(world, x, y, z, yaw, pitch)

                Greenhouse(connection).addVisit(sender.uniqueId, pCanRaid)

                sender.teleport(location)

                ChatUtil.send(sender, ChatUtil.json { text("Starting Raid") })
                canRaid.remove(sender)
                return true
            }

            "cancel" -> {
                if (canRaid[sender] == null) {
                    ChatUtil.send(sender, ChatUtil.json { text("You have no raid to cancel or it has expired") })
                    return true
                }
                ChatUtil.send(sender, ChatUtil.json { text("Raid cancelled") })
                canRaid.remove(sender)
                return true
            }

            else -> {
                ChatUtil.send(sender, ChatUtil.json { text("Invalid usage") })
            }
        }

        return false
    }
}

class RaidComplete : TabCompleter {
    private var types = arrayOf("confirm", "cancel")
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return types.toMutableList()
    }
}