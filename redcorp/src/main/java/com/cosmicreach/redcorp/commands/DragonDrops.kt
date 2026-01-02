package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.items.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class DragonDrops() : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            sender.sendMessage("§cCR §8| ${sender.displayName} §fkindly fuck off")
            return false
        }
        if (args.isEmpty()) {
            sender.sendMessage("§cCR §8| §fkindly fuck off")
            return false
        }

        val player = Bukkit.getServer().getPlayerExact(args[0])

        if (player is Player) {
            player.playSound(player.location, Sound.ENTITY_WITCH_CELEBRATE, 1.0f, 1.0f)
            when(args[1]) {
                "0" -> {
                    player.inventory.addItem(CustomItems().TeleportAnchor(1))
                    player.inventory.addItem(ItemStack(Material.ELYTRA, 1))
                    player.inventory.addItem(ItemStack(Material.DRAGON_EGG, 1))
                }
                "1" -> {
                    player.inventory.addItem(CustomItems().TeleportAnchor(1))
                    player.inventory.addItem(ItemStack(Material.ELYTRA, 1))
                }
                "2" -> player.inventory.addItem(CustomItems().TeleportAnchor(1))
            }
        } else {
            Bukkit.broadcastMessage("§cCR §8| §fsomething broke")
        }

        return false
    }
}