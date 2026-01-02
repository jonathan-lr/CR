package com.cosmicreach.redcorp

import com.cosmicreach.redcorp.commands.TestCommand
import com.cosmicreach.redcorp.commands.*
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.recipes.Drugs
import com.cosmicreach.redcorp.utils.TeleportActions
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.flags.StateFlag
import com.yourplugin.DatabaseManager
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.window.Window
import java.sql.Connection
import java.time.Duration
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit


class RedCorp : JavaPlugin() {
    private var config = getConfig()
    private var teleportingPlayers = HashMap<Player, Int>()
    private var teleportStarter = HashMap<Player, Player>()
    private var teleportSolo = HashMap<Player, Int>()
    private var particlePlayers = HashMap<Player, ParticleManager>()
    var grinderPlayers = HashMap<Player, VirtualInventory>()
    private var shipmentPlayers = HashMap<Player, VirtualInventory>()
    var agingBarrels = HashMap<Block, VirtualInventory>()
    private var storedGuis = HashMap<Block, Gui>()
    private val agingViewers = HashMap<Block, MutableList<Window>>()
    private var particleTeleport = HashMap<Player, ParticleManager>()
    private var economy: Economy? = null
    private var taggedPlayer = HashMap<Int, Player>()
    private var lastTagged = HashMap<Int, Player>()
    private var passedTimes = HashMap<Int, Int>()
    var magicUnlocked = HashMap<Player, Boolean>()
    private var canRaid = HashMap<Player, Int>()
    private var greenhouseInvite = HashMap<Player, Player>()
    private var greenhouseTracker = HashMap<Player, Int>()
    private var gambleLock = HashMap<Player, Boolean>()
    private var purchaseAmount = HashMap<Player, Int>()
    var fairiesFound = HashMap<Player, Array<Boolean>>()
    private lateinit var databaseManager: DatabaseManager
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
        EventsListener(teleportingPlayers, particleTeleport, teleportActions, teleportStarter, teleportSolo, agingBarrels)
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
        scheduleNextTopOfHour()
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
        getCommand("NPC")?.setExecutor(NPC())
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
        getCommand("raid")?.setExecutor(Raid())
        getCommand("raid")?.tabCompleter = RaidComplete()
        getCommand("greenhouse")?.setExecutor(GreenhouseCommand())
        getCommand("greenhouse")?.tabCompleter = GreenhouseComplete()
        getCommand("createnpc")?.setExecutor(CreateNPC())
        getCommand("createnpc")?.tabCompleter = CreateNPCComplete()
        getCommand("dragondrops")?.setExecutor(DragonDrops())
        //getCommand("setnicks")?.setExecutor(SetNicks(this, config))
    }

    private fun scheduleNextTopOfHour() {
        val zone = java.time.ZoneId.of("Europe/London")
        val now = ZonedDateTime.now(zone)
        var nextHour = now.truncatedTo(ChronoUnit.HOURS).plusHours(1)

        var delayMillis = Duration.between(now, nextHour).toMillis()

        if (delayMillis in 0 until Duration.ofMinutes(2).toMillis()) {
            nextHour = nextHour.plusHours(1)
            delayMillis = Duration.between(now, nextHour).toMillis()
        }

        val delayTicks = (delayMillis / 50L).coerceAtLeast(1L) // 20 ticks/sec, min 1 tick

        logger.info("Next scheduled run at: " + nextHour)

        Bukkit.getScheduler().runTaskLater(this, Runnable {
            try {
                logger.info("Running Hourly Expansion & Inflation")
                performExpanse()
                val db = getConnection()!!
                StockEx(db).decayAllStocksTowardsZero(8)
            } finally {
                scheduleNextTopOfHour()
            }
        }, delayTicks)
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

    fun getGreenhouseTracker(): HashMap<Player, Int> {
        return greenhouseTracker
    }

    fun getLastTagged(): HashMap<Int, Player> {
        return lastTagged
    }

    fun getPassedTimes(): HashMap<Int, Int> {
        return passedTimes
    }

    fun getCanRaid(): HashMap<Player, Int> {
        return canRaid
    }

    fun getGreenhouseInvite(): HashMap<Player, Player> {
        return greenhouseInvite
    }

    fun getStoredGuis(): HashMap<Block, Gui> {
        return storedGuis
    }

    fun getAgingViewers(): HashMap<Block, MutableList<Window>> {
        return agingViewers
    }

    fun getConnection(): Connection? {
        return databaseManager.getConnection()
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

    fun getEcon(): Economy{
        return economy!!
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
