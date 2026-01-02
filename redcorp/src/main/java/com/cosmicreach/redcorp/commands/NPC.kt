package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.npcs.Cassian
import com.cosmicreach.redcorp.npcs.Charlie
import com.cosmicreach.redcorp.npcs.Citizen
import com.cosmicreach.redcorp.npcs.Cops
import com.cosmicreach.redcorp.npcs.Eloise
import com.cosmicreach.redcorp.npcs.Ivan
import com.cosmicreach.redcorp.npcs.Jeramey
import com.cosmicreach.redcorp.npcs.Kyle
import com.cosmicreach.redcorp.npcs.Merlin
import com.cosmicreach.redcorp.npcs.Milton
import com.cosmicreach.redcorp.npcs.Oakley
import com.cosmicreach.redcorp.npcs.Patrick
import com.cosmicreach.redcorp.npcs.ShadeE
import com.cosmicreach.redcorp.npcs.Shaggy
import com.cosmicreach.redcorp.npcs.Sterling
import com.cosmicreach.redcorp.npcs.Sum
import com.cosmicreach.redcorp.npcs.Ting
import com.cosmicreach.redcorp.npcs.Toad
import com.cosmicreach.redcorp.npcs.Wong
import com.cosmicreach.redcorp.npcs.Zarek
import com.cosmicreach.redcorp.utils.Utils
import com.cosmicreach.redcorp.utils.DrugTest
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.concurrent.ThreadLocalRandom

class NPC(): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player && !sender.hasPermission("redcorp.npc")) {
            sender.sendMessage("§cCR §8| §c${sender.displayName} kindly fuck off")
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage("§cCR §8| kindly fuck off")
            return false
        }

        val player = Bukkit.getServer().getPlayerExact(args[0])

        if (player is Player) {
            when (args[1]) {
                "ivan" -> {
                    Ivan(player).run()
                    return false
                }
                "kyle" -> {
                    Kyle(player).run()
                    return false
                }
                "jeramey" -> {
                    Jeramey(player).run()
                    return false
                }
                "patrick" -> {
                    Patrick(player).run()
                    return false
                }
                "sterling" -> {
                    Sterling(player).run()
                    return false
                }
                "milton" -> {
                    Milton(player).run()
                    return false
                }
                "eloise" -> {
                    Eloise(player).run()
                    return false
                }
                "merlin" -> {
                    Merlin(player).run()
                    return false
                }
                "zarek" -> {
                    Zarek(player).run()
                    return false
                }
                "zarek_portal" -> {
                    Zarek(player).openPortal()
                    return false
                }
                in (1..20).map { "cop_$it" } -> {
                    val copNumber = args[1].removePrefix("cop_").toInt()
                    Cops(copNumber, player).run()
                    return false
                }
                in (1..10).map { "citizen_$it" } -> {
                    val npc = args[1].removePrefix("citizen_").toInt()
                    Citizen(player, npc).run()
                    return false
                }
                "shadee" -> {
                    ShadeE(player).run()
                    return false
                }
                "oakley" -> {
                    Oakley(player).run()
                    return false
                }
                "shaggy" -> {
                    Shaggy(player).run()
                    return false
                }
                "toad" -> {
                    Toad(player).run()
                    return false
                }
                "charlie" -> {
                    Charlie(player).run()
                    return false
                }
                "wong" -> {
                    Wong(player).run()
                    return false
                }
                "sum" -> {
                    Sum(player).run()
                    return false
                }
                "ting" -> {
                    Ting(player).run()
                    return false
                }
                "cassian" -> {
                    Cassian(player).run()
                    return false
                }
            }
        }
        return false
    }
}