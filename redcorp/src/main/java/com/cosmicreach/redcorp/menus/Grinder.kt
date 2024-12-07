package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.ParticleManager
import com.cosmicreach.redcorp.menus.items.GrindItem
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.inventory.VirtualInventory
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class Grinder(private val grinderPlayers: HashMap<Player, VirtualInventory>) {

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        var inv: VirtualInventory

        if (grinderPlayers.containsKey(player)) {
            inv = grinderPlayers[player]!!
        } else {
            grinderPlayers[player] = VirtualInventory(1)
            inv = grinderPlayers[player]!!
        }

        val gui = Gui.normal()
            .setStructure(
                "# # # # x # # # #",
                "# # # # # # # # #",
                "# # # # z # # # #",)
            .addIngredient('#', border)
            .addIngredient('x', inv)
            .addIngredient('z', GrindItem(inv))
            .build()
        return gui
    }
}