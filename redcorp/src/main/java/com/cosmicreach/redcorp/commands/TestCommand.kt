package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Greenhouse
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.boss.DragonBattle
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Chicken
import org.bukkit.entity.EnderCrystal
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.sqrt

class TestCommand() : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val connection = RedCorp.getPlugin().getConnection()!!
        if (sender !is Player) { return false }
        if (sender.hasPermission("redcorp.dev")) {
            val test = Greenhouse(connection).getGreenhousesForPlayer(sender.uniqueId)

            if (test != null) {
                val parts: List<String>

                sender.sendMessage("Test", sender.world.name)
                if (sender.world.name == "world") {
                    parts = test.home.split(",")
                    sender.sendMessage("Teleported to your Greenhouse")
                } else {
                    parts = test.exitl.split(",")
                    sender.sendMessage("Teleported to Insomnis")
                }

                val world = Bukkit.getWorld(parts[0])
                val x = parts[1].toDouble()
                val y = parts[2].toDouble()
                val z = parts[3].toDouble()
                val yaw = parts[4].toFloat()
                val pitch = parts[5].toFloat()

                val location = Location(world, x, y, z, yaw, pitch)

                sender.teleport(location)
            } else {
                //val loc = sender.location
                //val locString = "${loc.world?.name},${loc.x},${loc.y},${loc.z},${loc.yaw},${loc.pitch}"

                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sudo ${sender.playerListName} island create greenhouse")

                //val greenhouseTracking = RedCorp.getPlugin().getGreenhouseTracker()

                //greenhouseTracking.put(sender, locString)
            }


            /*sender.sendMessage("§cCR §8| §f${sender.displayName} nothing here")
            sender.sendMessage("")
            sender.sendMessage("§cCR §8| §f Item id: ${ Utils().getID(sender.inventory.itemInMainHand)} ")
            sender.sendMessage("")
            sender.sendMessage("§cCR §8| §f Item meta: ${sender.inventory.itemInMainHand.itemMeta as ItemMeta} ")
            sender.sendMessage("")
            sender.sendMessage("§cCR §8| §f Item Data: ${sender.inventory.itemInMainHand.data} ")
            sender.sendMessage("")*/
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