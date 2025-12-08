package com.cosmicreach.redcorp.commands

import com.cosmicreach.redcorp.RedCorp
import io.papermc.paper.datacomponent.item.ResolvableProfile
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.EntityType
import org.bukkit.entity.Mannequin
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import java.net.URL
import java.util.Locale
import java.util.Locale.getDefault
import java.util.UUID

class CreateNPC : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command can only be used by players.")
            return true
        }

        if (args.size < 2) {
            sender.sendMessage("Usage: /$label <name> <textures.minecraft.net URL>")
            return true
        }

        val name = args[0].replace('_', ' ') // allow underscores instead of spaces
        val urlString = args[1]

        val world = sender.world
        val loc = sender.location

        try {
            val entity = world.spawnEntity(loc, EntityType.MANNEQUIN)
            val mannequin = entity as Mannequin

            val pdc = mannequin.persistentDataContainer
            val key = NamespacedKey(RedCorp.getPlugin(), "npc_name")
            pdc.set(key, PersistentDataType.STRING, name.lowercase(getDefault()))

            mannequin.isImmovable = true
            mannequin.isInvulnerable = true
            mannequin.setGravity(false)

            mannequin.customName = name
            mannequin.isCustomNameVisible = true

            val profile = Bukkit.createProfile(UUID.randomUUID())
            val textures = profile.textures
            textures.skin = URL(urlString)
            profile.setTextures(textures)
            mannequin.profile = ResolvableProfile.resolvableProfile(profile)
        } catch (ex: Exception) {
            sender.sendMessage("Failed to spawn mannequin with skin '$urlString': ${ex.message}")
            sender.sendMessage("§cCould not use that URL as a skin. Make sure it's a textures.minecraft.net link.")
        }

        return true
    }
}

class CreateNPCComplete : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return mutableListOf("")
    }
}