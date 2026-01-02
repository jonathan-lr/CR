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

class IndustrialGrindItem(
    private val inv: VirtualInventory,
    private val progressInv: VirtualInventory,
    private val block: Block,
    private val setGrinding: (Boolean) -> Boolean,
    private val getGrinding: () -> Boolean,
) : ControlItem<Gui>() {
    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.GRINDSTONE)
            .setDisplayName("§2§lGrind drugs")
            .setLegacyLore((mutableListOf("§f§lClick to grind")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        if (getGrinding()) return

        val grindables = arrayOf(432, 424)

        val inputSlots = intArrayOf(0, 1, 2)

        val inputs = ArrayList<Pair<Int, ItemStack>>(inputSlots.size)
        for (slot in inputSlots) {
            val stack = inv.getItem(slot) ?: continue
            if (stack.type == Material.AIR) continue
            if (!Utils().checkID(stack, grindables)) continue
            inputs.add(slot to stack)
        }


        if (inputs.isEmpty()) {
            val firstNonEmpty = inputSlots
                .firstOrNull { slot -> (inv.getItem(slot)?.type ?: Material.AIR) != Material.AIR }
                ?: return

            val stack = inv.getItem(firstNonEmpty) ?: return
            if (stack.type == Material.AIR) return

            // Drop non-grindable item(s)
            block.world.dropItemNaturally(block.location, stack)
            inv.setItemSilently(firstNonEmpty, ItemStack(Material.AIR))
            return
        }

        setGrinding(true)

        object : BukkitRunnable() {
            var elapsed = 0L
            var fermentTime = 20L * 60

            override fun run() {
                if (elapsed == 0L) {
                    player.world.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 0.75f, 1.0f)
                }
                if (elapsed >= fermentTime) {
                    player.world.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 0.75f, 1.0f)

                    for ((slot, grindStack) in inputs) {
                        val current = inv.getItem(slot) ?: continue
                        if (current.type == Material.AIR) continue

                        val consumeAmount = grindStack.amount.coerceAtMost(current.amount)

                        val out: ItemStack = when (Utils().getID(current)) {
                            432 -> ItemStack(DrugItems().GroundCokeLeaf(consumeAmount))
                            424 -> ItemStack(DrugItems().GroundWeed(consumeAmount))
                            else -> continue
                        }

                        inv.setItemSilently(slot, out)
                    }

                    setGrinding(false)
                    cancel()
                    return
                }

                val progress = elapsed.toDouble() / fermentTime.toDouble()
                updateProgressTool(progressInv, 0, progress)
                elapsed += 20L
            }
        }.runTaskTimer(RedCorp.getPlugin(), 0L, 20L).taskId
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