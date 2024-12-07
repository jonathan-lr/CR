package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.items.DungeonItems
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GetToken  : CommandExecutor {
    private var types = arrayOf("normal", "hard", "reward")
    private var elements = arrayOf("air", "fire", "water", "earth")
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) { return false }
        if (sender.hasPermission("redcorp.getToken")) {
            if (args.isEmpty()) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} requires 1 argument")
                return false
            }

            if (types.indexOf(args[0]) == -1) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} invalid item argument")
                return false
            }

            if (types.indexOf(args[0]) == 2) {
                if (elements.indexOf(args[1]) == -1) {
                    sender.sendMessage("§cCR §8| §c${sender.displayName} invalid item argument")
                    return false
                }
            }

            when(args[0]) {
                "normal" -> sender.inventory.addItem(DungeonItems().dungeonToken())
                "hard" -> sender.inventory.addItem(DungeonItems().dungeonTokenHard())
                "reward" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(DungeonItems().airReward())
                        "fire" -> sender.inventory.addItem(DungeonItems().fireReward())
                        "water" -> sender.inventory.addItem(DungeonItems().waterReward())
                        "earth" -> sender.inventory.addItem(DungeonItems().earthReward())
                    }
                }
            }

        } else {
            sender.sendMessage("§cCR §8| §c${sender.displayName} kindly fuck off")
            return false
        }

        return false
    }
}

class GetTokenComplete: TabCompleter {
    private var types = arrayOf("normal", "hard", "reward")
    private var elements = arrayOf("air", "fire", "water", "earth")
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if (args.size == 1) {
            return types.toMutableList()
        }

        if (args.size == 2) {
            if (args[0] == "reward") {
                return elements.toMutableList()
            }
        }

        return null
    }
}