package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.items.ServerItems
import com.cosmicreach.redcorp.menus.items.FermentItem
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.scheduler.BukkitRunnable
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import kotlin.math.roundToInt

class DryingRack(
    private val agingBarrels: HashMap<Block, VirtualInventory>
) {
    private val progressInventories = HashMap<VirtualInventory, VirtualInventory>()
    private val guis = RedCorp.getPlugin().getStoredGuis()
    private val DRY_TIME_TICKS = 20L * 20
    private val dryingRecipes: Map<Int, ItemStack> = mapOf(
        421 to DrugItems().DriedWeed(1)
    )

    fun getOrCreateGui(block: Block): Gui {
        return guis.getOrPut(block) {
            makeGUI(block)
        }
    }

    fun makeGUI(block: Block): Gui {
        val inv = agingBarrels.getOrPut(block) { VirtualInventory(5) }
        val progressInv = progressInventories.getOrPut(inv) { VirtualInventory(5) }

        val gui = Gui.normal()
            .setStructure(
                ". . p p p p p . .",
                ". . x x x x x . .",
                ". . . . . . . . .",
            )
            .addIngredient('x', inv)
            .addIngredient('p', progressInv)
            .build()

        setupHandlers(inv, progressInv)

        return gui
    }

    private fun setupHandlers(inv: VirtualInventory, progressInv: VirtualInventory) {
        val lockedSlots = mutableSetOf<Int>()

        inv.setPreUpdateHandler { event ->
            val index = event.slot

            if (index in lockedSlots) {
                event.isCancelled = true
                return@setPreUpdateHandler
            }

            val newItem = event.newItem
            if (newItem != null && !isValidInput(newItem)) {
                event.isCancelled = true
            }
        }

        inv.setPostUpdateHandler { event ->
            val index = event.slot
            val newItem = event.newItem ?: return@setPostUpdateHandler

            if (index in lockedSlots) return@setPostUpdateHandler
            if (!isValidInput(newItem)) return@setPostUpdateHandler

            if (newItem.amount < 4) return@setPostUpdateHandler

            lockedSlots += index
            startDrying(inv, index, lockedSlots)
        }

        progressInv.setPreUpdateHandler { event ->
            event.isCancelled = true
            return@setPreUpdateHandler
        }
    }

    private fun startDrying(
        inv: VirtualInventory,
        invIndex: Int,
        lockedSlots: MutableSet<Int>,
    ) {
        val progressInv = progressInventories[inv] ?: return

        object : BukkitRunnable() {
            var elapsed = 0L

            override fun run() {
                val current = inv.getItem(invIndex)

                if (current == null || !isValidInput(current)) {
                    lockedSlots -= invIndex
                    updateProgressTool(progressInv, invIndex, 0.0)
                    cancel()
                    return
                }

                if (elapsed >= DRY_TIME_TICKS) {
                    val result = getDryResult(current)
                    if (result != null) {
                        result.amount = current.amount
                        inv.setItemSilently(invIndex, result)
                    }
                    lockedSlots -= invIndex
                    updateProgressTool(progressInv, invIndex, 1.0)
                    inv.notifyWindows()
                    cancel()
                    return
                }

                val progress = elapsed.toDouble() / DRY_TIME_TICKS.toDouble()
                updateProgressTool(progressInv, invIndex, progress)

                elapsed += 20L
            }
        }.runTaskTimer(RedCorp.getPlugin(), 0L, 20L)
    }

    private fun getDryResult(input: ItemStack): ItemStack? {
        val id = Utils().getID(input) ?: return null
        val base = dryingRecipes[id] ?: return null
        return base.clone()
    }

    private fun isValidInput(stack: ItemStack): Boolean {
        return getDryResult(stack) != null
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
    }
}
