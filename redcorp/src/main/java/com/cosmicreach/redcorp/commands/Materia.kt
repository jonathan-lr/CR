package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.items.CustomItems
import com.cosmicreach.redcorp.items.DrugItems
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class Materia : CommandExecutor {
    private var types = arrayOf("mre", "weed", "coke", "poppy", "shroom", "truffle", "grinder", "barrel", "ivan", "arlbaro", "hammer", "gavel", "anchor", "scroll", "lanyard", "unit", "penis", "debug", "drugstick")

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {return false}
        if (sender.hasPermission("redcorp.materia")) {
            if (args.isEmpty()) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} requires 1 argument")
                return false
            }

            if (types.indexOf(args[0]) == -1) {
                sender.sendMessage("§cCR §8| §c${sender.displayName} invalid item argument")
                return false
            }

            val item = sender.inventory.itemInMainHand
            when (args[0]) {
                "weed" -> {
                    if (!sender.hasPermission("redcorp.materia.weed")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = DrugItems().WeedSeed(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "coke" -> {
                    if (!sender.hasPermission("redcorp.materia.coke")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = DrugItems().CocaSeed(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "poppy" -> {
                    if (!sender.hasPermission("redcorp.materia.poppy")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = DrugItems().OpiumFlower(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "shroom" -> {
                    if (!sender.hasPermission("redcorp.materia.shroom")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = DrugItems().Shrooms(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "truffle" -> {
                    if (!sender.hasPermission("redcorp.materia.truffle")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = DrugItems().Truffles(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "grinder" -> {
                    if (!sender.hasPermission("redcorp.materia.grinder")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = DrugItems().Grinder(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "barrel" -> {
                    if (!sender.hasPermission("redcorp.materia.barrel")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = DrugItems().AgingBarrel(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "mre" -> {
                    if (!sender.hasPermission("redcorp.materia.mre")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = CustomItems().MRE(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "arlbaro" -> {
                    if (!sender.hasPermission("redcorp.materia.arlbaro")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = CustomItems().Arlbaro(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "hammer" -> {
                    if (!sender.hasPermission("redcorp.materia.hammer")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = CustomItems().Hammer(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "ivan" -> {
                    if (!sender.hasPermission("redcorp.materia.ivan")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = CustomItems().IvansBeats(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "gavel" -> {
                    if (!sender.hasPermission("redcorp.materia.gavel")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = CustomItems().Gavel(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "scroll" -> {
                    if (sender.hasPermission("redcorp.materia.scroll")) {
                        var transform = CustomItems().TeleportScroll(item.amount)
                        when(args[1]) {
                            "death" -> transform = CustomItems().DeathScroll(item.amount)
                            "home" -> transform = CustomItems().HomeScroll(item.amount)
                        }
                        if (item.type == transform.type) {
                            sender.inventory.setItemInMainHand(transform)
                            sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                        } else {
                            sender.sendMessage("§cCR §8| §rInvalid Option")
                        }
                    }
                }
                "anchor" -> {
                    if (!sender.hasPermission("redcorp.materia.anchor")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = CustomItems().TeleportAnchor(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "lanyard" -> {
                    if (!sender.hasPermission("redcorp.materia.lanyard")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = CustomItems().Lanyard(item.amount, args[1])
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "unit" -> {
                    if (!sender.hasPermission("redcorp.materia.unit")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = CustomItems().Unit(item.amount, args[1])
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "penis" -> {
                    if (!sender.hasPermission("redcorp.materia.penis")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = CustomItems().Penis(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "debug" -> {
                    if (!sender.hasPermission("redcorp.materia.debug")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = CustomItems().DebugStick(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
                    }
                }
                "drugstick" -> {
                    if (!sender.hasPermission("redcorp.materia.drugstick")) {
                        sender.sendMessage("§cCR §8| §rInvalid Permission")
                        return false
                    }
                    val transform = DrugItems().DrugStick(item.amount)
                    if (item.type == transform.type) {
                        sender.inventory.setItemInMainHand(transform)
                        sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
                    } else {
                        sender.sendMessage("§cCR §8| §rInvalid Option")
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

class MateriaComplete : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if (args.size == 1) {
            val returnValue = mutableListOf<String>()
            if (sender.hasPermission("redcorp.materia.mre")) {
                returnValue.add("mre")
            }
            if (sender.hasPermission("redcorp.materia.weed")) {
                returnValue.add("weed")
            }
            if (sender.hasPermission("redcorp.materia.coke")) {
                returnValue.add("coke")
            }
            if (sender.hasPermission("redcorp.materia.poppy")) {
                returnValue.add("poppy")
            }
            if (sender.hasPermission("redcorp.materia.shroom")) {
                returnValue.add("shroom")
            }
            if (sender.hasPermission("redcorp.materia.truffle")) {
                returnValue.add("truffle")
            }
            if (sender.hasPermission("redcorp.materia.grinder")) {
                returnValue.add("grinder")
            }
            if (sender.hasPermission("redcorp.materia.barrel")) {
                returnValue.add("barrel")
            }
            if (sender.hasPermission("redcorp.materia.arlbaro")) {
                returnValue.add("arlbaro")
            }
            if (sender.hasPermission("redcorp.materia.hammer")) {
                returnValue.add("hammer")
            }
            if (sender.hasPermission("redcorp.materia.ivan")) {
                returnValue.add("ivan")
            }
            if (sender.hasPermission("redcorp.materia.gavel")) {
                returnValue.add("gavel")
            }
            if (sender.hasPermission("redcorp.materia.anchor")) {
                returnValue.add("anchor")
            }
            if (sender.hasPermission("redcorp.materia.scroll")) {
                returnValue.add("scroll")
            }
            if (sender.hasPermission("redcorp.materia.lanyard")) {
                returnValue.add("lanyard")
            }
            if (sender.hasPermission("redcorp.materia.unit")) {
                returnValue.add("unit")
            }
            if (sender.hasPermission("redcorp.materia.penis")) {
                returnValue.add("penis")
            }
            if (sender.hasPermission("redcorp.materia.debug")) {
                returnValue.add("debug")
            }
            if (sender.hasPermission("redcorp.materia.drugstick")) {
                returnValue.add("drugstick")
            }
            return returnValue
        }

        if (args.size == 2) {
            if (sender.hasPermission("redcorp.materia.lanyard")) {
                if (args[0] == "lanyard") {
                    return mutableListOf("red", "orange", "pink", "green", "blue")
                }
            }
            if (sender.hasPermission("redcorp.materia.unit")) {
                if (args[0] == "unit") {
                    return mutableListOf("red", "orange", "pink", "green", "blue")
                }
            }
            if (sender.hasPermission("redcorp.materia.scroll")) {
                if (args[0] == "scroll") {
                    return mutableListOf("teleport", "death", "home")
                }
            }
        }

        return null
    }
}