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
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem
import kotlin.math.roundToInt

class BrewItem(private val inv : VirtualInventory, private val progressInv: VirtualInventory, private val block: Block, private val setBrewing: (Boolean) -> Boolean, private val brewing: Boolean) : ControlItem<Gui>() {
    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.BREWING_STAND).setDisplayName("§2§lBrew Coffee").setLegacyLore((mutableListOf("§f§lClick to brew")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val beans = inv.getItem(0)
        val water = inv.getItem(1)
        val milk = inv.getItem(2)
        val sugar = inv.getItem(3)

        if (beans!!.type == Material.AIR || brewing) return
        if (water!!.type == Material.AIR ) return

        var hasMilk = false
        val notMilk: Boolean
        if (milk != null) {
            hasMilk = milk.type == Material.MILK_BUCKET
            notMilk = (milk.type == Material.MILK_BUCKET || milk.type == Material.AIR)
        } else {
            notMilk = true
        }
        var hasSugar = false
        val notSugar: Boolean
        if (sugar != null) {
            hasSugar = sugar.type == Material.SUGAR
            notSugar = (sugar.type == Material.SUGAR || sugar.type == Material.AIR)
        } else {
            notSugar = true
        }

        if (beans.type == Material.COCOA_BEANS &&
            beans.amount >= 1 &&
            beans.amount <= 3 &&
            water.type == Material.POTION &&
            Utils().checkID(beans, arrayOf(473)) &&
            notMilk && notSugar
            ) {

            setBrewing(true)
            object : BukkitRunnable() {
                var elapsed = 0L
                var brewTime = 20L*5

                override fun run() {
                    if (elapsed == 0L) {
                        player.world.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 0.75f, 1.0f)
                    }

                    if (elapsed >= brewTime) {
                        player.world.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 0.75f, 1.0f)

                        val coffeeName: String
                        val coffee: ItemStack

                        when (beans.amount) {
                            1 -> {
                                when {
                                    hasMilk && hasSugar -> coffeeName = "Latte"
                                    hasMilk -> coffeeName = "Cafe Au Lait"
                                    hasSugar -> coffeeName = "Sweetened Espresso"
                                    else -> coffeeName = "Espresso"
                                }

                                coffee = DrugItems().weakCoffee(1)
                            }
                            2 -> {
                                when {
                                    hasMilk && hasSugar -> coffeeName = "Cappuccino"
                                    hasMilk -> coffeeName = "Flat White"
                                    hasSugar -> coffeeName = "Sweetened Coffee"
                                    else -> coffeeName = "Americano"
                                }

                                coffee = DrugItems().mediumCoffee(1)
                            }
                            3 -> {
                                when {
                                    hasMilk && hasSugar -> coffeeName = "Breve"
                                    hasMilk -> coffeeName = "Macchiato"
                                    hasSugar -> coffeeName = "Strong Sweet Coffee"
                                    else -> coffeeName = "Ristretto"
                                }

                                coffee = DrugItems().strongCoffee(1)
                            }
                            else -> {
                                coffee = DrugItems().weakCoffee(1)
                                coffeeName = "How have you done this?"
                            }
                        }

                        val meta = coffee.itemMeta as PotionMeta
                        when (beans.amount) {
                            1 -> {
                                val duration = when {
                                    hasSugar && hasMilk -> 12000
                                    hasSugar || hasMilk -> 6000
                                    else -> 3600
                                }
                                meta.addCustomEffect(PotionEffect(PotionEffectType.HASTE, duration, 0, true, false, false), true)
                            }
                            2 -> {
                                val duration = when {
                                    hasSugar && hasMilk -> 12000
                                    hasSugar || hasMilk -> 6000
                                    else -> 3600
                                }
                                meta.addCustomEffect(PotionEffect(PotionEffectType.HASTE, duration, 1, true, false, false), true)
                            }
                            3 -> {
                                val duration = when {
                                    hasSugar && hasMilk -> 12000
                                    hasSugar || hasMilk -> 6000
                                    else -> 3600
                                }
                                meta.addCustomEffect(PotionEffect(PotionEffectType.HASTE, duration, 2, true, false, false), true)
                            }
                        }

                        meta.setDisplayName(coffeeName)
                        coffee.setItemMeta(meta)

                        inv.setItemSilently(0, coffee)
                        inv.setItemSilently(1, ItemStack(Material.AIR))
                        if (hasMilk) {
                            inv.setItemSilently(2, ItemStack(Material.BUCKET))
                        } else {
                            inv.setItemSilently(2, ItemStack(Material.AIR))
                        }
                        inv.setItemSilently(3, ItemStack(Material.AIR))

                        setBrewing(false)
                        cancel()
                    }

                    val progress = elapsed.toDouble() / brewTime.toDouble()
                    updateProgressTool(progressInv, 0, progress)
                    elapsed += 20L
                }
            }.runTaskTimer(RedCorp.getPlugin(), 0L, 20L).taskId
        } else {
            player.world.playSound(player.location, Sound.ENTITY_SHULKER_AMBIENT, 0.75f, 1.0f)
            block.world.dropItemNaturally(block.location, beans)
            block.world.dropItemNaturally(block.location, water)
            if (milk != null) {
                block.world.dropItemNaturally(block.location, milk)
            }
            if (sugar != null) {
                block.world.dropItemNaturally(block.location, sugar)
            }
            inv.setItemSilently(0, ItemStack(Material.AIR))
            inv.setItemSilently(1, ItemStack(Material.AIR))
            inv.setItemSilently(2, ItemStack(Material.AIR))
            inv.setItemSilently(3, ItemStack(Material.AIR))
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