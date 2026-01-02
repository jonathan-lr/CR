package com.cosmicreach.redcorp

import com.cosmicreach.redcorp.db.Greenhouse
import com.cosmicreach.redcorp.db.Progress
import com.cosmicreach.redcorp.events.*
import com.cosmicreach.redcorp.items.GreenhouseItems
import com.cosmicreach.redcorp.utils.DrugTest
import com.cosmicreach.redcorp.utils.TeleportActions
import com.cosmicreach.redcorp.utils.Utils
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.data.Directional
import org.bukkit.entity.EntityCategory
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockDispenseEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.player.*
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.world.StructureGrowEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.invui.inventory.VirtualInventory


class EventsListener(
    private val teleportingPlayers: HashMap<Player, Int>,
    private val particleTeleport: HashMap<Player, ParticleManager>,
    private val teleportActions: TeleportActions,
    private val teleportStarter: HashMap<Player, Player>,
    private val teleportSolo: HashMap<Player, Int>,
    private val agingBarrels: HashMap<Block, VirtualInventory>,
) : Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, RedCorp.getPlugin())
    }

    private var ids = arrayOf(101, 102, 103, 104, 105, 106, 107, 108, 109, 110)
    private var extendedIds = arrayOf(101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 50, 51, 52, 53, 54, 55, 69, 3, 4, 5, 6, 400, 401, 402, 403, 420, 421, 423, 430, 431, 432, 440, 441, 450, 451, 460, 461, 462, 463, 470, 471, 472)
    private var items = arrayOf("pick", "shovel", "hoe", "axe", "sword", "helmet", "chestplate", "leggings", "boots", "wings")

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onDeath(event : PlayerDeathEvent) {
        OnDeath(event).run(ids, items)
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onAnvil(event : InventoryClickEvent) {
        OnAnvil(event).run(extendedIds)
        val p = event.whoClicked
        if (p is Player) {
            HandleInvChange(p).run()
            OnBundle(p, event).run()
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onInventory(event : InventoryOpenEvent) {
        OnInventory(event, agingBarrels).run()
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onShootBow(event : EntityShootBowEvent) {
        if (event.entity is Player) {
            val player = event.entity as Player
            if (event.bow?.let { Utils().getID(it) } == 800) {
                event.projectile.isSilent = true
                player.stopSound(SoundCategory.PLAYERS)
                object : BukkitRunnable() {
                    override fun run() {
                        player.stopSound(SoundCategory.PLAYERS)
                    }
                }.runTaskLater(RedCorp.getPlugin(), 1L)

                object : BukkitRunnable() {
                    override fun run() {
                        player.world.playSound(player.location, "awp.shoot", 1f, 1f)
                        //player.playSound(player.location, "awp.hit", 1f, 1f)
                    }
                }.runTaskLater(RedCorp.getPlugin(), 2L)
            }
        }
    }

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
        HandleInvChange(event.player).run()
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
        OnUse(event, teleportActions).run()
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
    fun onInvClose(event: InventoryCloseEvent) {
        val p = event.player
        if (p is Player) {
            HandleInvChangeBudget(p).run()
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun pickupItem(event: EntityPickupItemEvent) {
        if (event.entity is Player) {
            val p = event.entity as Player
            if (DrugTest().itemTest(event.item.itemStack)) {
                Utils().setScore(p, "has_drugs", 1)
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onEat(event: PlayerItemConsumeEvent) {
        OnEat(event).run()
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onDispense(event: BlockDispenseEvent) {
        if (event.item.type == Material.BONE_MEAL) {
            val block = event.block
            val dispenser = block.blockData as Directional
            val direction = dispenser.facing
            val targetBlock = block.location.add(direction.direction).block
            val nbt = NBTBlock(targetBlock).data
            if (nbt.getBoolean("weed") || nbt.getBoolean("coke") || nbt.getBoolean("poppy") || nbt.getBoolean("coffeeBean")) {
                Bukkit.broadcastMessage("§cCR §8|§r Someone tried to be cheeky and lost their dispenser :)")
                event.isCancelled = true
                block.type = Material.AIR
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onJoin(event: PlayerJoinEvent) {
        val magicUnlocked = RedCorp.getPlugin().magicUnlocked
        val connection = RedCorp.getPlugin().getConnection()!!

        val result = Progress(connection).getPlayer(event.player.uniqueId, 1)

        if (result == null) {
            Progress(connection).addPlayer(event.player.uniqueId, 1)
            magicUnlocked[event.player] = false
        } else {
            magicUnlocked[event.player] = result
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onEntityDmg(e: EntityDamageEvent) {
        if (e.entity.type != EntityType.MANNEQUIN) return
        val key = NamespacedKey(RedCorp.getPlugin(), "npc_name")
        if (!e.entity.persistentDataContainer.has(key, PersistentDataType.STRING)) return
        val p = e.damageSource.causingEntity
        if (p is Player && p.hasPermission("redcorp.killnpc")) {
            return
        }
        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onWorldChange(event: PlayerChangedWorldEvent) {
        val connection = RedCorp.getPlugin().getConnection()!!
        val player = event.player

        val greenhouseTracking = RedCorp.getPlugin().getGreenhouseTracker()

        if (!greenhouseTracking.containsKey(player)) return

        val greenhouseId = greenhouseTracking.get(player)

        val loc = player.location
        val locString = "${loc.world?.name},${loc.x},${loc.y},${loc.z},${loc.yaw},${loc.pitch}"

        val data = Greenhouse(connection).updateHome(greenhouseId!!, locString)

        player.location.world?.dropItem(player.location, GreenhouseItems().GreenhouseExit(greenhouseId))

        greenhouseTracking.remove(player)

        player.sendMessage("Saved greenhouse location: $data")
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onRightClick(e: PlayerInteractAtEntityEvent) {
        OnNpc(e).run()
    }
}