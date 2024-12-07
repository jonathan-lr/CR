package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.ParticleManager
import com.cosmicreach.redcorp.RedCorp
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.collections.HashMap

class ToggleParticle(private val particlePlayers: HashMap<Player, ParticleManager>) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {return false}
        if (sender.hasPermission("redcorp.particle")) {
            if (particlePlayers.containsKey(sender)) {
                particlePlayers[sender]?.disableParticles()
                particlePlayers.remove(sender)
                sender.sendMessage("${particlePlayers[sender]?.getParticle()}")
                sender.sendMessage("${particlePlayers}")
                sender.sendMessage("§cCR §8|§r Particles off")
            } else {
                particlePlayers[sender] = ParticleManager(RedCorp.getPlugin())
                sender.sendMessage("${particlePlayers[sender]?.getParticle()}")
                particlePlayers[sender]?.enableParticles()
                particlePlayers[sender]?.runParticles(sender)
                sender.sendMessage("§cCR §8|§r Particles on")
            }
        } else {
            sender.sendMessage("§cCR §8| §c${sender.displayName} kindly fuck off")
        }
        return false
    }
}