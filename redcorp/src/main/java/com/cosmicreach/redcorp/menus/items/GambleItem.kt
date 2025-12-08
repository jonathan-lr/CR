package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.RedCorp
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem
import kotlin.random.Random

class GambleItem(
    private val econ: Economy,
    private val player: Player
) : ControlItem<Gui>() {
    private var spin = MutableList(3) { Random.nextInt(0, 5) }
    private var spinDisplay = spin
    private var locked = RedCorp.getPlugin().getGambleLock()

    override fun getItemProvider(gui: Gui): ItemProvider {
        val item = ItemStack(Material.NETHER_STAR)
        val meta = item.itemMeta as ItemMeta
        meta.setDisplayName("§f§lGAMBY GAMBLE!!!")
        meta.lore = mutableListOf("§c${econ.format(100.0)}§f per roll")
        item.setItemMeta(meta)
        return ItemBuilder(item)
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        if (!locked.containsKey(player)) {
            locked[player] = false
        }

        if (locked[player] != true) {
            if (econ.getBalance(player) >= 100.0) {
                econ.withdrawPlayer(player, 100.0)
                spin = MutableList(3) { Random.nextInt(0, 5) }
                spinDisplay = spin
                reverse(16, 0)
                reverse(18, 1)
                reverse(20, 2)
                setSlots()
                gamble()
            }
        }
    }

    private fun gamble() {
        locked[player] = true
        object : BukkitRunnable() {
            var ticks = 0
            override fun run() {
                if (ticks >= 20) {
                    cancel()
                    locked[player] = false
                    spinDisplay = spin
                    setSlots()

                    if (spin.distinct().size == 1) { // All numbers are the same
                        when (spin.first()) {
                            0 -> {
                                player.sendMessage("§cCR §8|§r You won §c§l${econ.format(300.0)}")
                                econ.depositPlayer(player, 300.0)
                                player.world.playSound(player.location, Sound.ENTITY_VILLAGER_AMBIENT, 0.75f, 1.0f)
                            }
                            1 -> {
                                player.sendMessage("§cCR §8|§r You won §c§l${econ.format(500.0)}")
                                econ.depositPlayer(player, 500.0)
                                player.world.playSound(player.location, Sound.ENTITY_VILLAGER_TRADE, 0.75f, 1.0f)
                            }
                            2 -> {
                                player.sendMessage("§cCR §8|§r You won §c§l${econ.format(1000.0)}")
                                econ.depositPlayer(player, 1000.0)
                                player.world.playSound(player.location, Sound.ENTITY_VILLAGER_TRADE, 0.75f, 1.0f)
                            }
                            3 -> {
                                player.sendMessage("§cCR §8|§r You won §c§l${econ.format(1500.0)}")
                                econ.depositPlayer(player, 1500.0)
                                player.world.playSound(player.location, Sound.ENTITY_WITHER_SPAWN, 0.75f, 1.0f)
                            }
                            4 -> {
                                player.sendMessage("§cCR §8|§r You won §c§l${econ.format(3000.0)}")
                                Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName}§r won the Jackpot")
                                econ.depositPlayer(player, 3000.0)
                                player.world.playSound(player.location, Sound.ENTITY_WITHER_SPAWN, 0.75f, 1.0f)
                            }
                        }
                    } else if (spin.distinct().size == 2) { // Two numbers are the same
                        player.sendMessage("§cCR §8|§r You won §c§l${econ.format(50.0)}")
                        player.world.playSound(player.location, Sound.ENTITY_VILLAGER_HURT, 0.75f, 1.0f)
                        econ.depositPlayer(player, 50.0)
                    } else { // All numbers are different
                        player.sendMessage("§cCR §8|§r Womp Womp")
                        player.world.playSound(player.location, Sound.ENTITY_VILLAGER_DEATH, 0.75f, 1.0f)
                    }
                } else {
                    if (ticks != 15 && ticks != 17) {
                        player.world.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BIT, 0.75f, 1.0f)
                    } else {
                        player.world.playSound(player.location, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.75f, 2.0f)
                    }
                    // Cycle through numbers 1 to 5 for display
                    for (index in spinDisplay.indices) {
                        if ((index == 0 && ticks >= 15) || (index == 1 && ticks >= 17)) {
                            // Do nothing
                        } else {
                            if (spinDisplay[index] + 1 > 4) {
                                spinDisplay[index] = 0
                            } else {
                                spinDisplay[index] += 1
                            }
                        }
                    }
                    setSlots()
                }
                ticks++
            }
        }.runTaskTimer(RedCorp.getPlugin(), 0, 5)
    }

    private fun setSlots () {
        gui.setItem(2, 1, FruitGambleItem(spinDisplay[0]-1))
        gui.setItem(4, 1, FruitGambleItem(spinDisplay[1]-1))
        gui.setItem(6, 1, FruitGambleItem(spinDisplay[2]-1))
        gui.setItem(2, 2, FruitGambleItem(spinDisplay[0]))
        gui.setItem(4, 2, FruitGambleItem(spinDisplay[1]))
        gui.setItem(6, 2, FruitGambleItem(spinDisplay[2]))
        gui.setItem(2, 3, FruitGambleItem(spinDisplay[0]+1))
        gui.setItem(4, 3, FruitGambleItem(spinDisplay[1]+1))
        gui.setItem(6, 3, FruitGambleItem(spinDisplay[2]+1))
        notifyWindows()
    }

    private fun reverse(reverse: Int, index: Int) {
        for (i in 1..reverse) {
            if (spinDisplay[index] - 1 < 0) {
                spinDisplay[index] = 4
            } else {
                spinDisplay[index] -= 1
            }
        }
    }
}