package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.DrugItems
import de.tr7zw.nbtapi.NBTBlock
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem

class BrewItem(private val inv : VirtualInventory, private val block: Block) : ControlItem<Gui>() {
    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.BREWING_STAND).setDisplayName("§2§lBrew Coffee").setLegacyLore((mutableListOf("§f§lClick to brew")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val beans = inv.getItem(0)
        val water = inv.getItem(1)
        val milk = inv.getItem(2)
        val sugar = inv.getItem(3)
        val nbt = NBTBlock(block).data
        val fermenting = nbt.getBoolean("ferment")

        if (beans!!.type == Material.AIR || fermenting) return
        if (water!!.type == Material.AIR ) return

        if (beans.type == Material.COCOA_BEANS &&
            beans.amount >= 1 &&
            beans.amount <= 3 &&
            water.type == Material.POTION
            ) {

            nbt.setBoolean("ferment", true)
            val viewers = RedCorp.getPlugin().getAgingViewers()
            if (viewers[block] != null) {
                viewers[block]?.forEach {
                    it.viewer.closeInventory()
                }
                viewers[block]?.clear()
            }

            var count = 5
            object : BukkitRunnable() {
                override fun run() {
                    when (count) {
                        5 -> {
                            player.world.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 0.75f, 1.0f)
                            player.sendMessage("§cCR §8|§r Starting Brewing")
                        }
                        1 -> {
                            player.sendMessage("§cCR §8|§r Finished Brewing")

                            nbt.setBoolean("ferment", false)
                            player.world.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 0.75f, 1.0f)

                            var hasMilk = false
                            if (milk != null) {
                                hasMilk = milk.type == Material.MILK_BUCKET
                            }
                            var hasSugar = false
                            if (sugar != null) {
                                hasSugar = sugar.type == Material.SUGAR
                            }
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

                            val meta = coffee.itemMeta as ItemMeta
                            meta.setDisplayName(coffeeName)
                            coffee.setItemMeta(meta)

                            inv.setItemSilently(0, coffee)
                            inv.setItemSilently(1, ItemStack(Material.AIR))
                            inv.setItemSilently(2, ItemStack(Material.AIR))
                            inv.setItemSilently(3, ItemStack(Material.AIR))


                            cancel()
                        }
                    }
                    count -= 1
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
}