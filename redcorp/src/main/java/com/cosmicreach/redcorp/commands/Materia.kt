package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.items.CustomItems
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.items.PlayerItems
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Materia : CommandExecutor {
    private val itemTransformers: Map<String, (List<String>) -> ItemStack> = mapOf(
        "anchor" to { args: List<String> -> CustomItems().TeleportAnchor(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "arlbaro" to { args: List<String> -> PlayerItems().Arlbaro(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "awp" to { args: List<String> -> PlayerItems().Awp(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "barrel" to { args: List<String> -> DrugItems().AgingBarrel(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "card" to { args: List<String> ->
            val suit = args.getOrElse(1) { "joker" }
            val number = args.getOrElse(2) { "ace" }
            CustomItems().Card(args.getOrNull(0)?.toIntOrNull() ?: 1, suit, number)
        },
        "coffee" to { args: List<String> -> DrugItems().CoffieMachine(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "coke" to { args: List<String> -> DrugItems().CocaSeed(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "debug" to { args: List<String> -> CustomItems().DebugStick(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "drugstick" to { args: List<String> -> DrugItems().DrugStick(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "fairy" to { args: List<String> ->
            val id = args.getOrElse(1) { "0" }
            CustomItems().Fairy(args.getOrNull(0)?.toIntOrNull() ?: 1, id)
        },
        "gavel" to { args: List<String> -> CustomItems().Gavel(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "grinder" to { args: List<String> -> DrugItems().Grinder(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "hammer" to { args: List<String> -> PlayerItems().Hammer(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "ivan" to { args: List<String> -> CustomItems().IvansBeats(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "kip" to { args: List<String> -> PlayerItems().Kip(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "lanyard" to { args: List<String> ->
            val color = args.getOrElse(1) { "red" }
            PlayerItems().Lanyard(args.getOrNull(0)?.toIntOrNull() ?: 1, color)
        },
        "mre" to { args: List<String> -> PlayerItems().MRE((args.getOrNull(0)?.toIntOrNull() ?: 1)) },
        "penis" to { args: List<String> -> CustomItems().Penis((args.getOrNull(0)?.toIntOrNull() ?: 1)) },
        "poppy" to { args: List<String> -> DrugItems().OpiumFlower((args.getOrNull(0)?.toIntOrNull() ?: 1)) },
        "scroll" to { args: List<String> ->
            val type = args.getOrElse(1) { "teleport" }
            CustomItems().Scroll(args.getOrNull(0)?.toIntOrNull() ?: 1, type)
        },
        "shroom" to { args: List<String> -> DrugItems().Shrooms(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "test" to { args: List<String> -> CustomItems().TestWing(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "truffle" to { args: List<String> -> DrugItems().Truffles(args.getOrNull(0)?.toIntOrNull() ?: 1) },
        "unit" to { args: List<String> ->
            val color = args.getOrElse(1) { "red" }
            CustomItems().Unit(args.getOrNull(0)?.toIntOrNull() ?: 1, color)
        },
        "weed" to { args: List<String> -> DrugItems().WeedSeed(args.getOrNull(0)?.toIntOrNull() ?: 1) }
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        if (!sender.hasPermission("redcorp.materia")) {
            sender.sendMessage("§cCR §8| §c${sender.displayName} kindly fuck off")
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage("§cCR §8| §c${sender.displayName} requires 1 argument")
            return false
        }

        val itemType = args[0]
        val transformer = itemTransformers[itemType]

        if (transformer == null || !sender.hasPermission("redcorp.materia.$itemType")) {
            sender.sendMessage("§cCR §8| §c${sender.displayName} invalid or unauthorized item argument")
            return false
        }

        val item = sender.inventory.itemInMainHand

        try {
            val transformedItem = transformer(args.toList())

            if (item.type == transformedItem.type) {
                sender.inventory.setItemInMainHand(transformedItem)
                sender.sendMessage("§cCR §8| §rPerforming Materia on §c${item.type} §rby ${item.amount}")
            } else {
                sender.sendMessage("§cCR §8| §rInvalid Item for Materia")
            }
        } catch (e: Exception) {
            sender.sendMessage("§cCR §8| §rAn error occurred while performing Materia: ${e.message}")
        }

        return true
    }
}

class MateriaComplete : TabCompleter {
    private val itemPermissions = mapOf(
        "anchor" to "redcorp.materia.anchor",
        "arlbaro" to "redcorp.materia.arlbaro",
        "awp" to "redcorp.materia.awp",
        "barrel" to "redcorp.materia.barrel",
        "card" to "redcorp.materia.card",
        "coffee" to "redcorp.materia.coffee",
        "coke" to "redcorp.materia.coke",
        "debug" to "redcorp.materia.debug",
        "drugstick" to "redcorp.materia.drugstick",
        "fairy" to "redcorp.materia.fairy",
        "gavel" to "redcorp.materia.gavel",
        "grinder" to "redcorp.materia.grinder",
        "hammer" to "redcorp.materia.hammer",
        "ivan" to "redcorp.materia.ivan",
        "kip" to "redcorp.materia.kip",
        "lanyard" to "redcorp.materia.lanyard",
        "mre" to "redcorp.materia.mre",
        "penis" to "redcorp.materia.penis",
        "poppy" to "redcorp.materia.poppy",
        "scroll" to "redcorp.materia.scroll",
        "shroom" to "redcorp.materia.shroom",
        "test" to "redcorp.materia.test",
        "truffle" to "redcorp.materia.truffle",
        "unit" to "redcorp.materia.unit",
        "weed" to "redcorp.materia.weed"
    )

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if (args.isEmpty() || args.size == 1) {
            return itemPermissions.keys.filter { sender.hasPermission(itemPermissions[it]!!) }.toMutableList()
        }

        if (args.size == 2) {
            return when (args[0]) {
                "lanyard", "unit" -> mutableListOf("blue", "green", "orange", "pink", "red")
                "scroll" -> mutableListOf("death", "home", "teleport")
                "card" -> mutableListOf("clubs", "diamonds", "hearts", "spades", "joker", "back")
                else -> null
            }
        }

        if (args.size == 3) {
            return when (args[1]) {
                "clubs", "diamonds", "hearts", "spades" -> mutableListOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace")
                "joker" -> mutableListOf("black", "red")
                "back" -> mutableListOf("back")
                else -> null
            }
        }

        return null
    }
}