package com.cosmicreach.redcorp

import com.cosmicreach.redcorp.commands.TestCommand
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.cosmicreach.redcorp.commands.*
import com.cosmicreach.redcorp.recipes.Drugs
import com.cosmicreach.redcorp.utils.TeleportActions
import com.cosmicreach.redcorp.utils.Utils
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.flags.Flag
import com.sk89q.worldguard.protection.flags.StateFlag
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException
import net.milkbowl.vault.economy.Economy
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.window.Window


class RedCorp : JavaPlugin() {
    private var config = getConfig()
    private var denyTeleportScroll: StateFlag? = null
    private var teleportingPlayers = HashMap<Player, Int>()
    private var teleportStarter = HashMap<Player, Player>()
    private var teleportSolo = HashMap<Player, Int>()
    private var particlePlayers = HashMap<Player, ParticleManager>()
    private var grinderPlayers = HashMap<Player, VirtualInventory>()
    private var agingBarrels = HashMap<Block, VirtualInventory>()
    private val agingViewers = HashMap<Block, MutableList<Window>>()
    private var particleTeleport = HashMap<Player, ParticleManager>()
    private var economy: Economy? = null
    private var protocolManager: ProtocolManager? = null
    private var taggedPlayer = HashMap<Int, Player>()
    private var lastTagged = HashMap<Int, Player>()
    private var passedTimes = HashMap<Int, Int>()
    private var magicUnlocked = HashMap<Player, Boolean>()

    override fun onLoad() {
        logger.info("Registering Flags")
        //Register Flags
        registerFlag()
        logger.info("Finished Registering Flags")
    }

    override fun onEnable() {
        logger.info("Starting RedCrop Plugin")

        instance = this

        // Deserialize Magic
        val magicData = this.config.getString("configuration.magic")
        if (magicData != null) {
            magicUnlocked = Utils().deserializeMagicUnlocked(magicData)
        }

        val rsp = server.servicesManager.getRegistration(Economy::class.java)
        if (rsp != null) {
            economy = rsp.provider
        };

        protocolManager = ProtocolLibrary.getProtocolManager()

        config.options().copyDefaults(true);
        saveConfig()

        val teleportActions = TeleportActions(teleportingPlayers, particleTeleport, denyTeleportScroll, teleportStarter, teleportSolo)

        logger.info("Registering Listeners")
        //Register event listeners
        EventsListener(teleportingPlayers, particleTeleport, teleportActions, teleportStarter, teleportSolo, grinderPlayers, agingBarrels)
        logger.info("Finished Registering Listeners")

        logger.info("Registering Custom Crafting")
        Drugs()
        logger.info("Finished Registering Crafting")

        logger.info("Registering Commands")
        //Register commands
        registerCommands()
        logger.info("Finished Registering Commands")
    }

    override fun onDisable() {
        // Serialize
        val serialized = Utils().serializeMagicUnlocked(magicUnlocked)
        logger.info("Saving Config")
        this.config.set("configuration.magic", serialized)
        this.saveConfig()
    }

    private fun registerCommands() {
        getCommand("testcommand")?.setExecutor(TestCommand(protocolManager))

        getCommand("togglegod")?.setExecutor(GodMode(config))
        getCommand("getrelic")?.setExecutor(GetRelic())
        getCommand("getrelic")?.tabCompleter = GetRelicComplete()
        getCommand("getToken")?.setExecutor(GetToken())
        getCommand("getToken")?.tabCompleter = GetTokenComplete()
        getCommand("NPC")?.setExecutor(NPC(economy, protocolManager))
        getCommand("getReward")?.setExecutor(GetReward())
        getCommand("toggleparticle")?.setExecutor(ToggleParticle(particlePlayers))
        //getCommand("nick")?.setExecutor(Nick())
        //getCommand("nick")?.tabCompleter = NickComplete()
        getCommand("materia")?.setExecutor(Materia())
        getCommand("materia")?.tabCompleter = MateriaComplete()
        getCommand("starttag")?.setExecutor(StartTag(this, config))
        getCommand("starttag")?.tabCompleter = CompleteTag()
        getCommand("addrelic")?.setExecutor(AddRelic(this, config))
        getCommand("addrelic")?.tabCompleter = AddRelicComplete()
        //getCommand("setnicks")?.setExecutor(SetNicks(this, config))
    }

    private fun registerFlag() {
        val registry = WorldGuard.getInstance().flagRegistry
        try {
            // create a flag with the name "my-custom-flag", defaulting to true
            val flag = StateFlag("deny-teleport-scroll", true)
            registry.register(flag)
            denyTeleportScroll = flag // only set our field if there was no error
        } catch (e: FlagConflictException) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            val existing: Flag<*>? = registry["deny-teleport-scroll"]
            if (existing is StateFlag) {
                denyTeleportScroll = existing
            } else {
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }
    }

    fun getTaggedPlayers(): HashMap<Int, Player> {
        return taggedPlayer
    }

    fun getLastTagged(): HashMap<Int, Player> {
        return lastTagged
    }

    fun getPassedTimes(): HashMap<Int, Int> {
        return passedTimes
    }

    fun getMagicUnlocked(): HashMap<Player, Boolean> {
        return magicUnlocked
    }

    fun getAgingViewers(): HashMap<Block, MutableList<Window>> {
        return agingViewers
    }

    companion object {
        private lateinit var instance: RedCorp

        fun getPlugin(): RedCorp {
            return instance
        }
    }
}
