package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.items.DungeonItems
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.ShopItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class DungeonToken(private var econ: Economy) {

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
                .setStructure(
                        "# . . . z . . . #",
                        "# . x . . . y . #",
                        "# . . . . . . . #")
                .addIngredient('#', border)
                .addIngredient('x', ShopItem(econ, balItem, ItemStack(DungeonItems().dungeonToken()), 100.0, 0.0, "§9Milton"))
                .addIngredient('y', ShopItem(econ, balItem, ItemStack(DungeonItems().dungeonTokenHard()), 0.0, 0.0, "§9Milton"))
                .addIngredient('z', balItem)
                .build()
        return gui
    }
}