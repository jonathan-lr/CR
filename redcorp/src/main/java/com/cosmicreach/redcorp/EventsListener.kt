package com.cosmicreach.redcorp

import com.cosmicreach.redcorp.items.CustomItems
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.items.TagItems
import com.cosmicreach.redcorp.menus.AgingBarrel
import com.cosmicreach.redcorp.menus.CoffeeMachine
import com.cosmicreach.redcorp.menus.Grinder
import com.cosmicreach.redcorp.menus.Teleport
import com.cosmicreach.redcorp.utils.DrugTest
import com.cosmicreach.redcorp.utils.TeleportActions
import com.cosmicreach.redcorp.utils.Utils
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Ageable
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.player.*
import org.bukkit.event.world.StructureGrowEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.window.Window
import java.lang.Integer.parseInt
import kotlin.random.Random


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
        event.drops.forEach {
            if (it.hasItemMeta()) {
                val meta = it.itemMeta as ItemMeta

                it.setItemMeta((meta))

                if(Utils().checkID(it, ids)) {
                    val index = ids.indexOf(Utils().getID(it))
                    Bukkit.broadcastMessage("§cCR §8| ${event.entity.displayName} §rlost a relic §6${meta.displayName}")
                    it.amount = -1
                    var configItem = parseInt(RedCorp.getPlugin().config.getString("configuration.${types[meta.customModelData-1]}.${items[index]}"))
                    configItem += 1
                    RedCorp.getPlugin().config.set("configuration.${types[meta.customModelData-1]}.${items[index]}", configItem)
                    RedCorp.getPlugin().saveConfig()
                    Bukkit.broadcastMessage("§cCR §8|§r 1 ${meta.displayName} §rhas been returned to the vault")
                }
            }
        }
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onAnvil(event : InventoryClickEvent) {
        if (event.inventory.type == InventoryType.ANVIL || event.inventory.type == InventoryType.GRINDSTONE || event.inventory.type == InventoryType.BREWING || event.inventory.type == InventoryType.FURNACE || event.inventory.type == InventoryType.BLAST_FURNACE || event.inventory.type == InventoryType.SMOKER || event.inventory.type == InventoryType.WORKBENCH || event.inventory.type == InventoryType.CRAFTER) {
            val player = event.whoClicked
            if (event.currentItem != null) {
                val item = event.currentItem as ItemStack
                if (player !is Player) { return }
                if (Utils().checkID(item, extendedIds)) {
                    if ((Utils().checkID(item, arrayOf(423)) && event.slotType == InventoryType.SlotType.RESULT) || (Utils().checkID(item, arrayOf(440, 441)) && event.inventory.type == InventoryType.SMOKER)) {
                        return
                    } else {
                        event.isCancelled = true
                        player.sendMessage("§cCR §8|§r Stop ${player.displayName} §rthats not allowed! Persistence may result in item loss.")
                    }

                    /*
                    val tempInv = player.inventory.contents
                    player.closeInventory()

                    player.inventory.contents = tempInv*/
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onInventory(event : InventoryOpenEvent) {
        if (event.inventory.type == InventoryType.BARREL) {
            val p = event.player as Player
            val b = event.inventory.location?.block as Block
            val nbt = NBTBlock(b).data
            val temp = nbt.getBoolean("barrel")
            val fermenting = nbt.getBoolean("ferment")
            var viewers = RedCorp.getPlugin().getAgingViewers()
            if (fermenting) {
                event.isCancelled = true
                p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rthat barrel is still fermenting!")
                return
            }
            if (temp) {
                event.isCancelled = true
                val window = Window.single()
                    .setViewer(p)
                    .setTitle("§6§lAging Barrel")
                    .setGui(AgingBarrel(agingBarrels).makeGUI(b))
                    .build()

                window.open()

                if (viewers[b] != null) {
                    viewers[b]?.add(window)
                } else {
                    viewers[b] = mutableListOf(window)
                }
            }
        }

        if (event.inventory.type == InventoryType.BREWING) {
            val p = event.player as Player
            val b = event.inventory.location?.block as Block
            val nbt = NBTBlock(b).data
            val temp = nbt.getBoolean("coffee")
            val fermenting = nbt.getBoolean("ferment")
            var viewers = RedCorp.getPlugin().getAgingViewers()
            if (fermenting) {
                event.isCancelled = true
                p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rthat coffee is still brewing!")
                return
            }
            if (temp) {
                event.isCancelled = true
                val window = Window.single()
                    .setViewer(p)
                    .setTitle("§6§lCoffee Machine")
                    .setGui(CoffeeMachine(agingBarrels).makeGUI(b))
                    .build()

                window.open()

                if (viewers[b] != null) {
                    viewers[b]?.add(window)
                } else {
                    viewers[b] = mutableListOf(window)
                }
            }
        }

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
        event.inventory.matrix.forEach {
            if (it !== null) {
                if (Utils().checkID(it, extendedIds)) {
                    val player = event.view.player as Player
                    event.view.close()
                    player.sendMessage("§cCR §8|§r Stop ${player.displayName} §rthats not allowed! Persistence may result in item loss.")
                }
            }
        }
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onDrop(event : PlayerDropItemEvent) {
        val item = event.itemDrop.itemStack
        if(Utils().checkID(item, arrayOf(69))) {
            event.isCancelled = true
            event.player.sendMessage("§cCR §8|§r Sorry ${event.player.displayName} §rcan't drop this item bud")
        }
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onMove(event: PlayerMoveEvent) {
        val p: Player = event.player

        if (teleportingPlayers.containsKey(p)) {
            if (event.to!!.x != event.from.x || event.to!!.y != event.from.y || event.to!!.z != event.from.z) {
                if (teleportSolo.containsKey(p)) {
                    val temp = teleportingPlayers[p]
                    teleportingPlayers.remove(p)
                    p.sendMessage("§cCR §8|§r You moved and teleportation was canceled")
                    particleTeleport[p]?.disableParticles()
                    particleTeleport.remove(p)

                    if (temp != null) {
                        Bukkit.getScheduler().cancelTask(temp)
                    }

                    if (teleportSolo[p] == 1) {
                        p.sendMessage("§cCR §8|§r ${p.displayName} §rreturning scroll")
                        p.inventory.addItem(CustomItems().DeathScroll(1))
                        teleportSolo.remove(p)
                    } else {
                        p.sendMessage("§cCR §8|§r ${p.displayName} §rreturning scroll")
                        p.inventory.addItem(CustomItems().HomeScroll(1))
                        teleportSolo.remove(p)
                    }

                } else {
                    val temp = teleportingPlayers[p]
                    teleportingPlayers.remove(p)
                    teleportingPlayers.forEach { (t, u) ->
                        if (u == temp) {
                            t.sendMessage("§cCR §8|§r ${p.displayName} moved and teleportation was canceled")
                            p.sendMessage("§cCR §8|§r You moved and teleportation was canceled")
                            particleTeleport[p]?.disableParticles()
                            particleTeleport.remove(p)
                            particleTeleport[t]?.disableParticles()
                            particleTeleport.remove(t)

                            teleportingPlayers.remove(t, u)
                            Bukkit.getScheduler().cancelTask(u)

                            if (teleportStarter.containsKey(p)) {
                                p.sendMessage("§cCR §8|§r ${p.displayName} §rreturning scroll")
                                p.inventory.addItem(CustomItems().TeleportScroll(1))
                                teleportStarter.remove(p)
                            } else {
                                if (teleportStarter.containsKey(t)) {
                                    t.sendMessage("§cCR §8|§r ${t.displayName} §rreturning scroll")
                                    t.inventory.addItem(CustomItems().TeleportScroll(1))
                                    teleportStarter.remove(t)
                                }
                            }
                            return
                        }
                    }
                }
            }
        }
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onHarvest(event : PlayerHarvestBlockEvent) {
        if (event.harvestedBlock.type == Material.SWEET_BERRY_BUSH) {
            val nbt = NBTBlock(event.harvestedBlock).data
            val temp = nbt.getBoolean("poppy")

            if (temp) {
                event.itemsHarvested.clear()

                event.harvestedBlock.world.dropItemNaturally(event.harvestedBlock.location, DrugItems().OpiumFlower(1))
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onUse(event: PlayerInteractEvent) {
        if (event.item !== null) {
            val p: Player = event.player
            val i: ItemStack = event.item!!

            // Gavel
            if(Utils().checkID(i, arrayOf(2))) {
                p.world.playSound(p.location, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 0.75f, 1.0f)
            }

            // Use Drugs
            if(Utils().checkID(i, arrayOf(423, 432, 441, 450, 451))) {
                when (Utils().getID(i)) {
                    //Spliff
                    423 -> {
                        p.world.playSound(p.location, Sound.ENTITY_BREEZE_INHALE, 0.2f, 1.0f)
                        p.world.playSound(p.location, Sound.ENTITY_BLAZE_BURN, 0.2f, 1.0f)
                        p.addPotionEffect(PotionEffect(PotionEffectType.HUNGER, 1200, 1, true, false, false))
                        p.addPotionEffect(PotionEffect(PotionEffectType.SLOWNESS, 1200, 2, true, false, false))
                    }
                    //Coke
                    432 -> {
                        p.world.playSound(p.location, Sound.ENTITY_SNIFFER_SNIFFING, 0.75f, 1.0f)
                        p.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 1200, 2, true, false, false))
                    }
                    //Opium
                    441 -> {
                        p.world.playSound(p.location, Sound.ENTITY_SNIFFER_SNIFFING, 0.75f, 1.0f)
                        p.addPotionEffect(PotionEffect(PotionEffectType.UNLUCK, 6000, 2, true, false, false))
                    }
                    //Shroom
                    450 -> {
                        if (event.clickedBlock!!.type == Material.MYCELIUM) { return }
                        p.world.playSound(p.location, Sound.ENTITY_STRIDER_EAT, 0.75f, 1.0f)
                        p.addPotionEffect(PotionEffect(PotionEffectType.DARKNESS, 1200, 2, true, false, false))
                    }
                    //Truffle
                    451 -> {
                        if (event.clickedBlock != null) {
                            if (event.clickedBlock!!.type == Material.MYCELIUM) { return }
                        }
                        p.world.playSound(p.location, Sound.ENTITY_STRIDER_EAT, 0.75f, 1.0f)
                        p.addPotionEffect(PotionEffect(PotionEffectType.DARKNESS, 1200, 2, true, false, false))
                    }
                }
                p.inventory.itemInMainHand.amount -= 1

            }

            // Grinder
            if(Utils().checkID(i, arrayOf(400))) {
                val window = Window.single()
                    .setViewer(p)
                    .setTitle("§6§lDrug Grinder")
                    .setGui(Grinder(grinderPlayers).makeGUI(p))
                    .build()

                window.open()
            }

            // Debug Stick
            if(Utils().checkID(i, arrayOf(2886))) {
                val nbt = NBTBlock(event.clickedBlock).data

                Bukkit.broadcastMessage("§cCR §8|§r debug weed ${nbt.getBoolean("weed")}")
                Bukkit.broadcastMessage("§cCR §8|§r debug coke ${nbt.getBoolean("coke")}")
                Bukkit.broadcastMessage("§cCR §8|§r debug popy ${nbt.getBoolean("poppy")}")
                Bukkit.broadcastMessage("§cCR §8|§r debug barrel ${nbt.getBoolean("barrel")}")
                Bukkit.broadcastMessage("§cCR §8|§r debug ferment ${nbt.getBoolean("ferment")}")
                Bukkit.broadcastMessage("§cCR §8|§r debug shroom ${nbt.getBoolean("shroom")}")
            }

            // Player Teleport
            if(Utils().checkID(i, arrayOf(3))) {
                val window = Window.single()
                        .setViewer(p)
                        .setTitle("§f§lAvailable Teleports")
                        .setGui(Teleport(teleportActions).makeGUI(p))
                        .build()

                window.open()
            }

            // Death Teleport
            if(Utils().checkID(i, arrayOf(5))) {
                if (p.lastDeathLocation is Location) {
                    teleportActions.runTeleportLocation(p, p.lastDeathLocation!!, 1)
                } else {
                    p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rwe cant find your death location")
                }

            }

            // Home Teleport
            if(Utils().checkID(i, arrayOf(6))) {
                if (p.respawnLocation is Location) {
                    teleportActions.runTeleportLocation(p, p.respawnLocation!!, 2)
                } else {
                    p.sendMessage("§cCR §8|§r Sorry ${p.displayName} §rwe cant find your bed location")
                }

            }

            // Sets Weed Data
            if(Utils().checkID(i, arrayOf(420))) {
                if (event.clickedBlock!!.type == Material.FARMLAND) {
                    val location = event.clickedBlock!!.location
                    location.y += 1

                    val block = location.block
                    val nbt = NBTBlock(block).data

                    nbt.setBoolean("weed", true)
                }
            }

            // Sets Coke Data
            if(Utils().checkID(i, arrayOf(430))) {
                if (event.clickedBlock!!.type == Material.FARMLAND) {
                    val location = event.clickedBlock!!.location
                    location.y += 1

                    val block = location.block
                    val nbt = NBTBlock(block).data

                    nbt.setBoolean("coke", true)
                }
            }

            // Sets Poppy Data
            if(Utils().checkID(i, arrayOf(440))) {
                if (event.clickedBlock!!.type == Material.PODZOL && event.blockFace == BlockFace.UP) {
                    val location = event.clickedBlock!!.location
                    location.y += 1

                    val block = location.block
                    val nbt = NBTBlock(block).data

                    nbt.setBoolean("poppy", true)
                } else {
                    event.isCancelled = true
                    p.sendMessage("§cCR §8|§r This must be placed on podzol :)")
                }
            }

            // Sets Mushroom Data
            if(Utils().checkID(i, arrayOf(450)) || Utils().checkID(i, arrayOf(451))) {
                if (event.clickedBlock!!.type == Material.MYCELIUM && event.blockFace == BlockFace.UP) {
                    val location = event.clickedBlock!!.location
                    location.y += 1

                    val block = location.block
                    val nbt = NBTBlock(block).data

                    if(i.type == Material.RED_MUSHROOM) {
                        nbt.setBoolean("shroom", true)
                    } else {
                        nbt.setBoolean("truffle", true)
                    }
                } else {
                    event.isCancelled = true
                    p.sendMessage("§cCR §8|§r This must be placed on mycelium :)")
                }
            }

            // Sets Barrel Data
            if(Utils().checkID(i, arrayOf(401))) {
                val location = event.clickedBlock!!.location
                location.y += 1

                val block = location.block
                val nbt = NBTBlock(block).data

                nbt.setBoolean("barrel", true)
                nbt.setBoolean("ferment", false)
            }

            // Sets Coffee Data
            if(Utils().checkID(i, arrayOf(403))) {
                val location = event.clickedBlock!!.location
                location.y += 1

                val block = location.block
                val nbt = NBTBlock(block).data

                nbt.setBoolean("coffee", true)
                nbt.setBoolean("ferment", false)
            }
        }
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBreak(event : BlockBreakEvent) {
        // Weed Stuff
        if (event.block.type == Material.WHEAT) {
            val nbt = NBTBlock(event.block).data
            val temp = nbt.getBoolean("weed")
            val blockAge = event.block.blockData as Ageable
            if (blockAge.age == blockAge.maximumAge) {
                if (temp) {
                    event.isDropItems = false
                    event.block.drops.clear()

                    event.block.world.dropItemNaturally(event.block.location, DrugItems().Weed(1))
                    event.block.world.dropItemNaturally(event.block.location, DrugItems().WeedSeed(Random.nextInt(1, 3)))
                }
            }
            nbt.clearNBT()
        }

        // Coke Stuff
        if (event.block.type == Material.BEETROOTS) {
            val nbt = NBTBlock(event.block).data
            val temp = nbt.getBoolean("coke")
            val blockAge = event.block.blockData as Ageable
            if (blockAge.age == blockAge.maximumAge) {
                if (temp) {
                    event.isDropItems = false
                    event.block.drops.clear()

                    event.block.world.dropItemNaturally(event.block.location, DrugItems().CocaLeaf(1))
                    event.block.world.dropItemNaturally(event.block.location, DrugItems().CocaSeed(Random.nextInt(1, 3)))
                }
            }
            nbt.clearNBT()
        }

        // Drug Cleanup Stuff
        if (event.block.type == Material.SWEET_BERRY_BUSH || event.block.type == Material.RED_MUSHROOM || event.block.type == Material.BROWN_MUSHROOM) {
            val nbt = NBTBlock(event.block).data
            if (nbt.getBoolean("poppy") || nbt.getBoolean("shroom") || nbt.getBoolean("truffle")) {
                event.isDropItems = false
                event.block.drops.clear()
            }

            nbt.clearNBT()
        }

        // Aging Barrel
        if (event.block.type == Material.BARREL) {
            val nbt = NBTBlock(event.block).data
            val temp = nbt.getBoolean("barrel")

            if (temp) {
                event.isDropItems = false
                event.block.drops.clear()

                event.block.world.dropItemNaturally(event.block.location, DrugItems().AgingBarrel(1))

                nbt.clearNBT()
            }
        }

        // Coffee Machine
        if (event.block.type == Material.BREWING_STAND) {
            val nbt = NBTBlock(event.block).data
            val temp = nbt.getBoolean("coffee")

            if (temp) {
                event.isDropItems = false
                event.block.drops.clear()

                event.block.world.dropItemNaturally(event.block.location, DrugItems().CoffieMachine(1))

                nbt.clearNBT()
            }
        }

        // Shroom Stuff
        if (event.block.type == Material.RED_MUSHROOM_BLOCK || event.block.type == Material.BROWN_MUSHROOM_BLOCK || event.block.type == Material.MUSHROOM_STEM) {
            val nbt = NBTBlock(event.block).data
            val shroom = nbt.getBoolean("shroom")
            val truffle = nbt.getBoolean("truffle")
            if (shroom) {
                event.isDropItems = false
                event.block.drops.clear()

                event.block.world.dropItemNaturally(event.block.location, DrugItems().Shrooms(1))
            }
            if (truffle) {
                event.isDropItems = false
                event.block.drops.clear()

                event.block.world.dropItemNaturally(event.block.location, DrugItems().Truffles(1))
            }

            nbt.clearNBT()
        }

        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onHit(event : EntityDamageByEntityEvent) {
        if (event.entity is Player) {
            if (event.damager is Player) {
                val tagger = event.damager as Player
                val taggie = event.entity as Player
                val item = tagger.inventory.itemInMainHand

                if (Utils().checkID(item, arrayOf(69))) {
                    val gameId = Utils().getGameID(item)
                    val current = RedCorp.getPlugin().getTaggedPlayers()[gameId]
                    val last = RedCorp.getPlugin().getLastTagged()[gameId]
                    var passed = RedCorp.getPlugin().getPassedTimes()[gameId]

                    if (tagger != current) {
                        tagger.sendMessage("§cCR §8|§r Well well well, what do we have here? ${tagger.displayName} you shouldn't have that.")
                        tagger.inventory.itemInMainHand.amount = -1
                        Bukkit.broadcastMessage("§cCR §8|§r ${tagger.displayName} has ended game $gameId of tag due to malpractice!")
                        return
                    }

                    if (taggie == last) {
                        tagger.sendMessage("§cCR §8|§r How about we give them a break, they were the last tagger.")
                        return
                    } else {
                        Bukkit.broadcastMessage("§cCR §8|§r ${(event.damager as Player).displayName} has tagged ${(event.entity as Player).displayName}!")
                        RedCorp.getPlugin().getTaggedPlayers()[gameId] = taggie
                        RedCorp.getPlugin().getLastTagged()[gameId] = tagger
                        if (passed != null) {
                            RedCorp.getPlugin().getPassedTimes()[gameId] = passed + 1
                        }

                        tagger.inventory.itemInMainHand.amount = -1
                        if (passed != null) {
                            taggie.inventory.addItem(TagItems().tagStick(taggie.playerListName, tagger.playerListName, passed + 1, gameId))
                        }
                    }
                }

                if (Utils().checkID(item, arrayOf(402))) {
                    if (DrugTest().doTest(taggie)) {
                        Bukkit.broadcastMessage("§cCR §8|§r AHHHHHHHH ${taggie.displayName} HAS DRUGS!!!!!")
                    }
                }
            }
        }
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onGrow(event: StructureGrowEvent) {
        if (event.species == TreeType.RED_MUSHROOM) {
            event.blocks.forEach {
                val block = it.block
                val nbt = NBTBlock(block).data

                nbt.setBoolean("shroom", true)
            }
        }
        if (event.species == TreeType.BROWN_MUSHROOM) {
            event.blocks.forEach {
                val block = it.block
                val nbt = NBTBlock(block).data

                nbt.setBoolean("truffle", true)
            }
        }
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onEat(event: PlayerItemConsumeEvent) {
        event.player.sendMessage("§cCR §8|§r Test eat ${event.eventName}")
        event.player.sendMessage("§cCR §8|§r Test eat ${event.hand}")
        event.player.sendMessage("§cCR §8|§r Test eat ${event.item}")
    }
}