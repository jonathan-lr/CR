package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.menus.items.BrewItem
import com.cosmicreach.redcorp.menus.items.DescItem
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory

class CoffeeMachine(private val agingBarrels: HashMap<Block, VirtualInventory>) {
    private val progressInventories = HashMap<VirtualInventory, VirtualInventory>()
    private val guis = RedCorp.getPlugin().getStoredGuis()
    private var brewing = false

    fun getOrCreateGui(block: Block): Gui {
        return guis.getOrPut(block) {
            makeGUI(block)
        }
    }

    fun makeGUI(block: Block): Gui {
        val inv = agingBarrels.getOrPut(block) { VirtualInventory(4) }
        val progressInv = progressInventories.getOrPut(inv) { VirtualInventory(1) }

        val gui = Gui.normal()
            .setStructure(
                ". . . . . . . . .",
                ". . x x p x x . .",
                ". . y u z v w . .",)
            .addIngredient('x', inv)
            .addIngredient('p', progressInv)
            .addIngredient('y', DescItem(ItemStack(Material.BROWN_STAINED_GLASS_PANE),"§cCoffee Slot", "§cAdd coffee beans 1-3 above"))
            .addIngredient('u', DescItem(ItemStack(Material.BLUE_STAINED_GLASS_PANE),"§cWater Bottle", "§cAdd water bottle above"))
            .addIngredient('v', DescItem(ItemStack(Material.WHITE_STAINED_GLASS_PANE),"§cMilk Slot", "§cAdd optional milk above"))
            .addIngredient('w', DescItem(ItemStack(Material.WHITE_STAINED_GLASS_PANE),"§cSugar Slot", "§cAdd optional sugar above"))
            .addIngredient('z', BrewItem(inv, progressInv, block, ::setBrewing, ::setBrewing))
            .build()

        setupHandlers(inv, progressInv)

        return gui
    }

    private fun setBrewing(brew: Boolean): Boolean {
        brewing = brew
        return brewing
    }

    private fun setBrewing(): Boolean {
        return brewing
    }


    private fun setupHandlers(inv: VirtualInventory, progressInv: VirtualInventory) {
        inv.setPreUpdateHandler { event ->
            if (brewing) {
                event.isCancelled = true
                return@setPreUpdateHandler
            }
        }

        progressInv.setPreUpdateHandler { event ->
            event.isCancelled = true
            return@setPreUpdateHandler
        }
    }
}