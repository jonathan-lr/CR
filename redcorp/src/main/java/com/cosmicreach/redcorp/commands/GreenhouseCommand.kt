package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Greenhouse
import com.cosmicreach.redcorp.utils.ChatUtil
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GreenhouseCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {return false}
        if (args.isEmpty()) { return false }

        val greenhouseInvites = RedCorp.getPlugin().getGreenhouseInvite()
        val connection = RedCorp.getPlugin().getConnection()!!

        when (args[0].lowercase()) {
            "invite" -> {
                val greenhouse = Greenhouse(connection).getGreenhousesForPlayer(sender.uniqueId)

                if (greenhouse == null) {
                    ChatUtil.send(sender, ChatUtil.json { text("You are not in any greenhouses") })
                    return true
                }

                val target = Bukkit.getPlayer(args[1])
                if (target !is Player) {
                    ChatUtil.send(sender, ChatUtil.json { text("Invalid Target") })
                    return true
                }

                greenhouseInvites[sender] = target
                ChatUtil.send(sender, ChatUtil.json {
                    text("Successfully invited ${target.displayName}. Click to ")
                    button(
                        label = "<dark_gray>[<blue><bold>ᴄᴀɴᴄᴇʟ</bold><dark_gray>]",
                        command = "/greenhouse cancel ${target.playerListName}",
                        hoverText = "<gray>Click to cancel invite</gray>"
                    )
                })
                ChatUtil.send(target, ChatUtil.json {
                    text("You have been invited to join ${sender.displayName}'s Greenhouse. Click to ")
                    button(
                        label = "<dark_gray>[<blue><bold>ᴊᴏɪɴ</bold><dark_gray>]",
                        command = "/greenhouse join ${sender.playerListName}",
                        hoverText = "<gray>Click to join</gray>"
                    )
                })
                return true
            }

            "join" -> {
                val target = Bukkit.getPlayer(args[1])
                if (target !is Player) {
                    ChatUtil.send(sender, ChatUtil.json { text("Invalid Target") })
                    return true
                }

                if (greenhouseInvites[target] != sender) {
                    ChatUtil.send(sender, ChatUtil.json { text("You dont have an invite to join ${target.displayName}'s Greenhouse") })
                    return true
                }

                val greenhouse = Greenhouse(connection).getGreenhousesForPlayer(target.uniqueId)
                if (greenhouse == null) {
                    ChatUtil.send(sender, ChatUtil.json { text("${target.displayName} is not in any Greenhouses") })
                    greenhouseInvites.remove(target, sender)
                    return true
                }

                Greenhouse(connection).linkPlayerToGreenhouse(greenhouse.id, sender.uniqueId)
                ChatUtil.send(sender, ChatUtil.json { text("You have joined ${sender.displayName}'s Greenhouse") })
                ChatUtil.send(target, ChatUtil.json { text("${sender.displayName} has joined your Greenhouse") })
                greenhouseInvites.remove(target, sender)

                return true
            }

            "cancel" -> {
                val target = Bukkit.getPlayer(args[1])
                if (target !is Player) {
                    ChatUtil.send(sender, ChatUtil.json { text("Invalid Target") })
                    return true
                }

                if (greenhouseInvites[sender] != target) {
                    ChatUtil.send(sender, ChatUtil.json { text("You haven't sent an invite to ${target.displayName}") })
                    return true
                }
                greenhouseInvites.remove(target, sender)
                ChatUtil.send(sender, ChatUtil.json { text("Canceled invite to ${target.displayName}") })
                ChatUtil.send(target, ChatUtil.json { text("${sender.displayName} canceled their invite sorry :(") })
                return true
            }

            "leave" -> {
                val greenhouse = Greenhouse(connection).getGreenhousesForPlayer(sender.uniqueId)
                if (greenhouse == null) {
                    ChatUtil.send(sender, ChatUtil.json { text("You are not in a greenhouse") })
                    return true
                }

                Greenhouse(connection).unlinkPlayerFromGreenhouse(greenhouse.id, sender.uniqueId)
                ChatUtil.send(sender, ChatUtil.json { text("Successfully unlinked from Greenhouse") })
            }

            else -> {
                ChatUtil.send(sender, ChatUtil.json { text("Invalid usage") })
            }
        }

        return false
    }
}

class GreenhouseComplete : TabCompleter {
    private var types = arrayOf("invite", "join", "cancel", "leave")
    val players = Bukkit.getServer().onlinePlayers
    val list = mutableListOf<String>()

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if (args.size == 1) {
            return types.toMutableList()
        }

        players.forEach { p ->
            list.add(p.playerListName)
        }

        if (args.size == 2) {
            return list
        }

        return null
    }
}