package com.cosmicreach.redcorp

import com.cosmicreach.redcorp.commands.TestCommand
import com.cosmicreach.redcorp.commands.*
import com.cosmicreach.redcorp.recipes.Drugs
import com.cosmicreach.redcorp.utils.TeleportActions
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.flags.StateFlag
import com.yourplugin.DatabaseManager
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.window.Window
import java.sql.Connection


class RedCorp : JavaPlugin() {
    private var config = getConfig()
    private var teleportingPlayers = HashMap<Player, Int>()
    private var teleportStarter = HashMap<Player, Player>()
    private var teleportSolo = HashMap<Player, Int>()
    private var particlePlayers = HashMap<Player, ParticleManager>()
    private var grinderPlayers = HashMap<Player, VirtualInventory>()
    private var shipmentPlayers = HashMap<Player, VirtualInventory>()
    private var agingBarrels = HashMap<Block, VirtualInventory>()
    private val agingViewers = HashMap<Block, MutableList<Window>>()
    private var particleTeleport = HashMap<Player, ParticleManager>()
    private var economy: Economy? = null
    private var taggedPlayer = HashMap<Int, Player>()
    private var lastTagged = HashMap<Int, Player>()
    private var passedTimes = HashMap<Int, Int>()
    private var magicUnlocked = HashMap<Player, Boolean>()
    private var greenhouseTracker = HashMap<Player, String>()
    private var gambleLock = HashMap<Player, Boolean>()
    private var purchaseAmount = HashMap<Player, Int>()
    private var fairiesFound = HashMap<Player, Array<Boolean>>()
    private lateinit var databaseManager: DatabaseManager
    private var lastExecutionTime: Long = 0
    private var worldBorder: Double = 0.0
    private val playerRegions = mutableMapOf<Player, Set<String>>()

    override fun onLoad() {
        logger.info("Registering Flags")
        //Register Flags
        RedCorpFlags.registerFlags()
        logger.info("Finished Registering Flags")
    }

    override fun onEnable() {
        logger.info("Starting RedCrop Plugin")

        instance = this

        val url = this.config.getString("configuration.mysql.url")!!
        val user = this.config.getString("configuration.mysql.user")!!
        val pass = this.config.getString("configuration.mysql.pass")!!

        databaseManager = DatabaseManager(url, user, pass, logger)
        databaseManager.connect()

        val rsp = server.servicesManager.getRegistration(Economy::class.java)
        if (rsp != null) {
            economy = rsp.provider
        };

        config.options().copyDefaults(true);
        saveConfig()

        val teleportActions = TeleportActions(teleportingPlayers, particleTeleport, teleportStarter, teleportSolo)

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

        logger.info("Staring World Border Setup")
        val world = Bukkit.getWorld("world")
        if (world != null) {
            worldBorder = world.worldBorder.size
        }
        startHourly()
        logger.info("Finished World Border Setup")
    }

    override fun onDisable() {
        databaseManager.disconnect()
    }

    private fun registerCommands() {
        getCommand("testcommand")?.setExecutor(TestCommand())

        getCommand("togglegod")?.setExecutor(GodMode(config))
        getCommand("getrelic")?.setExecutor(GetRelic())
        getCommand("getrelic")?.tabCompleter = GetRelicComplete()
        getCommand("getToken")?.setExecutor(GetToken())
        getCommand("getToken")?.tabCompleter = GetTokenComplete()
        getCommand("NPC")?.setExecutor(NPC(economy))
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

    private fun startHourly() {
        lastExecutionTime = System.currentTimeMillis()

        object : BukkitRunnable() {
            override fun run() {
                val currentTime = System.currentTimeMillis()

                // Check if one hour (3600000 milliseconds) has passed
                if (currentTime - lastExecutionTime >= 3600000) {
                    lastExecutionTime = currentTime // Reset the last execution time

                    // Run your hourly task
                    logger.info("CR Running Hourly Expansion")
                    performExpanse()
                }
            }
        }.runTaskTimer(this,0L,1200L )
    }

    private fun performExpanse() {
        // Add the logic for your hourly task here
        Bukkit.broadcastMessage("§cCR §8|§r The World Border hourly expansion has happened!")
        val world = Bukkit.getWorld("world") ?: return
        val wb = world.worldBorder
        worldBorder += 10
        wb.setSize(worldBorder, 5L)
    }

    fun getTaggedPlayers(): HashMap<Int, Player> {
        return taggedPlayer
    }

    fun getShipmentPlayers(): HashMap<Player, VirtualInventory> {
        return shipmentPlayers
    }

    fun getGreenhouseTracker(): HashMap<Player, String> {
        return greenhouseTracker
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

    fun getConnection(): Connection? {
        return databaseManager.getConnection()
    }

    fun getFairies(): HashMap<Player, Array<Boolean>> {
        return fairiesFound
    }

    fun getWorldBorder(): Double {
        return worldBorder
    }

    fun setWorldBorder(wb: Double) {
        worldBorder = wb
    }

    fun getPlayerRegions(): MutableMap<Player, Set<String>> {
        return playerRegions
    }

    fun getFlags(): RedCorpFlags {
        return RedCorpFlags
    }

    fun getEcon(): Economy?{
        return economy
    }

    fun getGrinderPlayers(): HashMap<Player, VirtualInventory> {
        return grinderPlayers
    }

    fun getGambleLock(): HashMap<Player, Boolean> {
        return gambleLock
    }

    fun getPurchaseAmount(): HashMap<Player, Int> {
        return purchaseAmount
    }

    companion object {
        private lateinit var instance: RedCorp

        fun getPlugin(): RedCorp {
            return instance
        }
    }
}

object RedCorpFlags {
    var CHECK_DRUGS: StateFlag = StateFlag("check-for-drugs", false)
    var DENY_TP: StateFlag = StateFlag("deny-teleport-scroll", false)

    fun registerFlags() {
        val registry = WorldGuard.getInstance().flagRegistry
        try {
            registry.register(CHECK_DRUGS)
        } catch (e: Exception) {
            val existing = registry["check-for-drugs"]
            if (existing is StateFlag) {
                CHECK_DRUGS = existing
            } else {
                throw IllegalStateException("Unexpected flag type!")
            }
        }
        try {
            registry.register(DENY_TP)
        } catch (e: Exception) {
            val existing = registry["deny-teleport-scroll"]
            if (existing is StateFlag) {
                DENY_TP = existing
            } else {
                throw IllegalStateException("Unexpected flag type!")
            }
        }
    }
}
