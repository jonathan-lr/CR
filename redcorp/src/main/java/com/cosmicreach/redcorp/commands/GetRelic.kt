package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.items.EarthItems
import com.cosmicreach.redcorp.items.FireItems
import com.cosmicreach.redcorp.items.SkyItems
import com.cosmicreach.redcorp.items.WaterItems
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GetRelic  : CommandExecutor {
    private var types = arrayOf("air", "fire", "water", "earth")
    private var items = arrayOf("pick", "shovel", "hoe", "axe", "sword", "helmet", "chestplate", "leggings", "boots", "wings")

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {return false}
        if (sender.hasPermission("redcorp.get")) {
            if (args.size <= 1) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} requires 2 arguments")
                return false
            }

            if (items.indexOf(args[0]) == -1) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} invalid type argument")
                return false
            }

            if (types.indexOf(args[1]) == -1) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} invalid item argument")
                return false
            }

            when (args[0]) {
                "pick" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(SkyItems().skyPick())
                        "fire" -> sender.inventory.addItem(FireItems().firePick())
                        "water" -> sender.inventory.addItem(WaterItems().waterPick())
                        "earth" -> sender.inventory.addItem(EarthItems().earthPick())
                    }
                }
                "shovel" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(SkyItems().skyShovel())
                        "fire" -> sender.inventory.addItem(FireItems().fireShovel())
                        "water" -> sender.inventory.addItem(WaterItems().waterShovel())
                        "earth" -> sender.inventory.addItem(EarthItems().earthShovel())
                    }
                }
                "hoe" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(SkyItems().skyHoe())
                        "fire" -> sender.inventory.addItem(FireItems().fireHoe())
                        "water" -> sender.inventory.addItem(WaterItems().waterHoe())
                        "earth" -> sender.inventory.addItem(EarthItems().earthHoe())
                    }
                }
                "axe" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(SkyItems().skyAxe())
                        "fire" -> sender.inventory.addItem(FireItems().fireAxe())
                        "water" -> sender.inventory.addItem(WaterItems().waterAxe())
                        "earth" -> sender.inventory.addItem(EarthItems().earthAxe())
                    }
                }
                "sword" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(SkyItems().skySword())
                        "fire" -> sender.inventory.addItem(FireItems().fireSword())
                        "water" -> sender.inventory.addItem(WaterItems().waterSword())
                        "earth" -> sender.inventory.addItem(EarthItems().earthSword())
                    }
                }
                "helmet" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(SkyItems().skyHelmet())
                        "fire" -> sender.inventory.addItem(FireItems().fireHelmet())
                        "water" -> sender.inventory.addItem(WaterItems().waterHelmet())
                        "earth" -> sender.inventory.addItem(EarthItems().earthHelmet())
                    }
                }
                "chestplate" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(SkyItems().skyChestplate())
                        "fire" -> sender.inventory.addItem(FireItems().fireChestplate())
                        "water" -> sender.inventory.addItem(WaterItems().waterChestplate())
                        "earth" -> sender.inventory.addItem(EarthItems().earthChestplate())
                    }
                }
                "leggings" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(SkyItems().skyLeggings())
                        "fire" -> sender.inventory.addItem(FireItems().fireLeggings())
                        "water" -> sender.inventory.addItem(WaterItems().waterLeggings())
                        "earth" -> sender.inventory.addItem(EarthItems().earthLeggings())
                    }
                }
                "boots" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(SkyItems().skyBoots())
                        "fire" -> sender.inventory.addItem(FireItems().fireBoots())
                        "water" -> sender.inventory.addItem(WaterItems().waterBoots())
                        "earth" -> sender.inventory.addItem(EarthItems().earthBoots())
                    }
                }
                "wings" -> {
                    when(args[1]) {
                        "air" -> sender.inventory.addItem(SkyItems().skyWings())
                        "fire" -> sender.inventory.addItem(FireItems().fireWings())
                        "water" -> sender.inventory.addItem(WaterItems().waterWings())
                        "earth" -> sender.inventory.addItem(EarthItems().earthWings())
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

class GetRelicComplete: TabCompleter {
    private var types = arrayOf("air", "fire", "water", "earth")
    private var items = arrayOf("pick", "shovel", "hoe", "axe", "sword", "helmet", "chestplate", "leggings", "boots", "wings")

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if (args.size == 1) {
            return items.toMutableList()
        }

        if (args.size == 2) {
            return types.toMutableList()
        }

        return null
    }
}