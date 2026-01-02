package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.items.GrindItem
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory

class Grinder() {
    private val grinderPlayers = RedCorp.getPlugin().grinderPlayers

    fun makeGUI(player: Player): Gui {
        var inv: VirtualInventory

        if (grinderPlayers.containsKey(player)) {
            inv = grinderPlayers[player]!!
        } else {
            grinderPlayers[player] = VirtualInventory(1)
            inv = grinderPlayers[player]!!
        }

        val gui = Gui.normal()
            .setStructure(
                ". . . . . . . . .",
                ". . . . x . . . .",
                ". . . . z . . . .",)
            .addIngredient('x', inv)
            .addIngredient('z', GrindItem(inv))
            .build()

        setupHandlers(inv)

        return gui
    }

    private fun setupHandlers(inv: VirtualInventory) {
        inv.setPreUpdateHandler { event ->
            val slot = event.slot

            // VirtualInventory has size 1, but keep it safe anyway
            if (slot != 0) return@setPreUpdateHandler

            val stack = event.newItem ?: return@setPreUpdateHandler

            // Force the item amount to 1 (prevents stacking in the grinder input)
            if (stack.amount > 1) {
                stack.amount = 1
                event.newItem = stack
            }
        }
    }

}