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
import xyz.xenondevs.invui.inventory.get
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem
import kotlin.math.roundToInt

class MixItem(
    private val inv: VirtualInventory,
    private val progressInv: VirtualInventory,
    private val block: Block,
    private val setMixing: (Boolean) -> Boolean,
    private val getMixing: () -> Boolean
) : ControlItem<Gui>() {
    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.GRINDSTONE).setDisplayName("§2§lPress Item")
            .setLegacyLore((mutableListOf("§f§lClick to press", "§f4 Produce = 1 condense")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        if (getMixing()) return

        val inputSlots = listOf(0, 1, 2)

        data class SlotData(val index: Int, val stack: ItemStack)

        val filledSlots = inputSlots.mapNotNull { index ->
            val stack = inv.get(index)
            if (stack != null && stack.type != Material.AIR) SlotData(index, stack) else null
        }
        if (filledSlots.size != inputSlots.size) return

        fun isCustom433(stack: ItemStack): Boolean = Utils().checkID(stack, arrayOf(433))
        fun isAllowedIngredient(stack: ItemStack): Boolean {
            return isCustom433(stack) ||
                    stack.type == Material.SUGAR ||
                    stack.type == Material.REDSTONE
        }

        val customCount = filledSlots.count { (_, stack) -> isCustom433(stack) }
        val sugarCount = filledSlots.count { (_, stack) -> stack.type == Material.SUGAR }
        val redstoneCount = filledSlots.count { (_, stack) -> stack.type == Material.REDSTONE }
        val hasCorrectParts = customCount >= 1 && sugarCount >= 1 && redstoneCount >= 1

        val amounts = filledSlots.map { it.stack.amount }
        val equalAmounts = amounts.distinct().size == 1

        val invalidSlots = filledSlots.filter { (_, stack) ->
            !isAllowedIngredient(stack)
        }

        if (!hasCorrectParts || !equalAmounts || invalidSlots.isNotEmpty()) {
            player.world.playSound(block.location, Sound.ENTITY_SHULKER_AMBIENT, 0.75f, 1.0f)

            invalidSlots.forEach { (index, stack) ->
                block.world.dropItemNaturally(block.location, stack)
                inv.setItemSilently(index, ItemStack(Material.AIR))
            }

            return
        }


        setMixing(true)

        object : BukkitRunnable() {
            var elapsed = 0L
            var fermentTime = 20L * 20

            override fun run() {
                if (elapsed == 0L) {
                    player.world.playSound(block.location, Sound.ENTITY_BREEZE_IDLE_GROUND, 1.2f, 1.2f)
                    player.world.playSound(block.location, Sound.ITEM_BRUSH_BRUSHING_SAND_COMPLETE, 0.75f, 0.7f)
                }
                if (elapsed >= fermentTime) {
                    player.world.playSound(block.location, Sound.BLOCK_NOTE_BLOCK_BIT, 0.75f, 1.4f)

                    val total = filledSlots[0].stack.amount * 2

                    val firstAmt = total.coerceAtMost(64)
                    val secondAmt = (total - firstAmt).coerceAtMost(64)

                    inv.setItemSilently(0, ItemStack(DrugItems().MixedCoke(firstAmt)))
                    inv.setItemSilently(
                        1,
                        if (secondAmt > 0) ItemStack(DrugItems().MixedCoke(secondAmt)) else ItemStack(Material.AIR)
                    )

                    inv.setItemSilently(2, ItemStack(Material.AIR))

                    setMixing(false)
                    cancel()
                }
                if (elapsed % 40L == 0L) {
                    player.world.playSound(block.location, Sound.ITEM_BRUSH_BRUSHING_SAND_COMPLETE, 0.75f, 0.7f)
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