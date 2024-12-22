package com.cosmicreach.redcorp

import com.cosmicreach.redcorp.db.Magic
import com.cosmicreach.redcorp.events.*
import com.cosmicreach.redcorp.utils.TeleportActions
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.player.*
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.world.StructureGrowEvent
import xyz.xenondevs.invui.inventory.VirtualInventory


class EventsListener(
    private val teleportingPlayers: HashMap<Player, Int>,
    private val particleTeleport: HashMap<Player, ParticleManager>,
    private val teleportActions: TeleportActions,
    private val teleportStarter: HashMap<Player, Player>,
    private val teleportSolo: HashMap<Player, Int>,
    private val grinderPlayers: HashMap<Player, VirtualInventory>,
    private val agingBarrels: HashMap<Block, VirtualInventory>,
) : Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, RedCorp.getPlugin())
    }

    private var ids = arrayOf(101, 102, 103, 104, 105, 106, 107, 108, 109, 110)
    private var extendedIds = arrayOf(101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 50, 51, 52, 53, 54, 55, 69, 3, 4, 5, 6, 400, 401, 402, 403, 420, 421, 423, 430, 431, 432, 440, 441, 450, 451, 460, 461, 462, 463, 470, 471, 472)
    private var types = arrayOf("air", "fire", "water", "earth")
    private var items = arrayOf("pick", "shovel", "hoe", "axe", "sword", "helmet", "chestplate", "leggings", "boots", "wings")

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onDeath(event : PlayerDeathEvent) {
        OnDeath(event).run(ids, types, items)
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onAnvil(event : InventoryClickEvent) {
        OnAnvil(event).run(extendedIds)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onInventory(event : InventoryOpenEvent) {
        OnInventory(event, agingBarrels).run()
        return
    }

    /* :todo implement bow model and id for checking
       :todo implement play hit on target hit at hit source
    @EventHandler(priority = EventPriority.MONITOR)
    fun onShootBow(event : EntityShootBowEvent) {
        if (event.entity is Player) {
            val player = event.entity as Player
            event.projectile.isSilent = true
            player.stopSound(SoundCategory.PLAYERS)
            object : BukkitRunnable() {
                override fun run() {
                    player.stopSound(SoundCategory.PLAYERS)
                }
            }.runTaskLater(plugin, 1L)

            object : BukkitRunnable() {
                override fun run() {
                    player.playSound(player.location, "awp.shoot", 1f, 1f)
                    player.playSound(player.location, "awp.hit", 1f, 1f)
                }
            }.runTaskLater(plugin, 2L)
        }
    }*/

    @EventHandler(priority = EventPriority.MONITOR)
    fun onCraft(event : PrepareItemCraftEvent) {
        OnCraft(event).run(extendedIds)
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onAdvance(event : PlayerAdvancementDoneEvent) {
        OnAdvance(event).run()
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onDrop(event : PlayerDropItemEvent) {
        OnDrop(event).run()
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onMove(event: PlayerMoveEvent) {
        OnMove(event).run(
            teleportingPlayers,
            particleTeleport,
            teleportSolo,
            teleportStarter
        )

        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onHarvest(event : PlayerHarvestBlockEvent) {
        OnHarvest(event).run()
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onUse(event: PlayerInteractEvent) {
        OnUse(event, grinderPlayers, teleportActions).run()
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBreak(event : BlockBreakEvent) {
        OnBreak(event).run()
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onHit(event : EntityDamageByEntityEvent) {
        OnHit(event).run()
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onGrow(event: StructureGrowEvent) {
        OnGrow(event).run()
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onEat(event: PlayerItemConsumeEvent) {
        OnEat(event).run()
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onJoin(event: PlayerJoinEvent) {
        val magicUnlocked = RedCorp.getPlugin().getMagicUnlocked()
        val connection = RedCorp.getPlugin().getConnection()!!

        val result = Magic(connection).getPlayer(event.player.uniqueId)

        if (result == null) {
            Magic(connection).addPlayer(event.player.uniqueId)
            magicUnlocked[event.player] = false
        } else {
            magicUnlocked[event.player] = result
        }
    }
}