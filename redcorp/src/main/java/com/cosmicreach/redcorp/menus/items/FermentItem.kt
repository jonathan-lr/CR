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
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.inventory.get
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem

class FermentItem(private val inv : VirtualInventory, private val block: Block) : ControlItem<Gui>() {
    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.GRINDSTONE).setDisplayName("§2§lFerment Booze").setLegacyLore((mutableListOf("§f§lClick to ferment", "§f§l4 Produce = 1 Drink")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val item = inv.getItem(0) ?: return
        val nbt = NBTBlock(block).data
        val fermenting = nbt.getBoolean("ferment")

        if (item.type == Material.AIR || fermenting) return

        val fermentables = mutableListOf(Material.WHEAT, Material.APPLE, Material.POTATO, Material.SWEET_BERRIES)

        if (fermentables.contains(item.type) && item.amount >= 4) {
            nbt.setBoolean("ferment", true)
            gui.closeForAllViewers()

            var count = 5
            object : BukkitRunnable() {
                override fun run() {
                    when (count) {
                        5 -> {
                            player.world.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 0.75f, 1.0f)
                            player.sendMessage("§cCR §8|§r Starting Ferment")
                        }
                        1 -> {
                            player.sendMessage("§cCR §8|§r Finished Ferment")

                            nbt.setBoolean("ferment", false)
                            player.world.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 0.75f, 1.0f)

                            if(item.type == Material.WHEAT) {
                                inv.setItemSilently(0, ItemStack(DrugItems().Larger(item.amount/4)))
                            }
                            if(item.type == Material.APPLE) {
                                inv.setItemSilently(0, ItemStack(DrugItems().Cider(item.amount/4)))
                            }
                            if(item.type == Material.POTATO) {
                                inv.setItemSilently(0, ItemStack(DrugItems().Vodka(item.amount/4)))
                            }
                            if(item.type == Material.SWEET_BERRIES) {
                                inv.setItemSilently(0, ItemStack(DrugItems().Wine(item.amount/4)))
                            }

                            cancel()
                        }
                    }
                    count -= 1
                }
            }.runTaskTimer(RedCorp.getPlugin(), 0L, 20L).taskId
        } else {
            player.world.playSound(player.location, Sound.ENTITY_SHULKER_AMBIENT, 0.75f, 1.0f)
            block.world.dropItemNaturally(block.location, item)
            inv.setItemSilently(0, ItemStack(Material.AIR))
        }
    }
}