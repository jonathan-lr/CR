package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.items.ServerItems
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem
import kotlin.math.roundToInt

class FermentItem(private val inv : VirtualInventory, private val progressInv: VirtualInventory, private val block: Block, private val setFermenting: (Boolean) -> Boolean, private val getFermenting: () -> Boolean) : ControlItem<Gui>() {
    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.GRINDSTONE).setDisplayName("§2§lFerment Booze").setLegacyLore((mutableListOf("§f§lClick to ferment", "§f4 Produce = 1 Drink")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val item = inv.getItem(0) ?: return

        if (item.type == Material.AIR || getFermenting()) return

        val fermentables = mutableListOf(Material.WHEAT, Material.APPLE, Material.POTATO, Material.SWEET_BERRIES, Material.GLOWSTONE_DUST)

        if (fermentables.contains(item.type) && item.amount >= 4) {
            if (item.type === Material.GLOWSTONE_DUST && !Utils().checkID(item,arrayOf(443))) return
            setFermenting(true)

            object : BukkitRunnable() {
                var elapsed = 0L
                var fermentTime = 20L*60

                override fun run() {
                    if (elapsed == 0L) {
                        player.world.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 0.75f, 1.0f)
                    }
                    if (elapsed >= fermentTime) {
                        player.world.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 0.75f, 1.0f)

                        when (item.type) {
                            Material.WHEAT -> {
                                inv.setItemSilently(0, ItemStack(DrugItems().Larger(item.amount/4)))
                            }
                            Material.APPLE -> {
                                inv.setItemSilently(0, ItemStack(DrugItems().Cider(item.amount/4)))
                            }
                            Material.POTATO -> {
                                inv.setItemSilently(0, ItemStack(DrugItems().Vodka(item.amount/4)))
                            }
                            Material.SWEET_BERRIES -> {
                                inv.setItemSilently(0, ItemStack(DrugItems().Wine(item.amount/4)))
                            }
                            Material.GLOWSTONE_DUST -> {
                                inv.setItemSilently(0, ItemStack(DrugItems().Opium(item.amount)))
                            }
                            else -> {return}
                        }

                        setFermenting(false)
                        cancel()
                    }

                    val progress = elapsed.toDouble() / fermentTime.toDouble()
                    updateProgressTool(progressInv, 0, progress.toDouble())
                    elapsed += 20L
                }
            }.runTaskTimer(RedCorp.getPlugin(), 0L, 20L).taskId
        } else {
            player.world.playSound(player.location, Sound.ENTITY_SHULKER_AMBIENT, 0.75f, 1.0f)
            block.world.dropItemNaturally(block.location, item)
            inv.setItemSilently(0, ItemStack(Material.AIR))
        }
    }

    private fun updateProgressTool(progressInv: VirtualInventory, invIndex: Int, progressAmount: Double) {
        val item = ServerItems().emptyProgressTool()
        val meta = item.itemMeta

        if (meta is Damageable && item.type.maxDurability > 0) {
            val maxDura = item.type.maxDurability.toInt()
            val clamped = progressAmount.coerceIn(0.0, 1.0)

            val damage = ((1.0 - clamped) * maxDura).roundToInt()

            meta.damage = damage
            item.itemMeta = meta
        }

        progressInv.setItemSilently(invIndex, item)
        progressInv.notifyWindows()
    }
}