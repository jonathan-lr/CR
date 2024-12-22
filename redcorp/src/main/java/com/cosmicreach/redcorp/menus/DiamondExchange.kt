package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.ShopItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class DiamondExchange(private var econ: Economy, private var type: Int) {
    private val connection = RedCorp.getPlugin().getConnection()!!

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        if (type == 1) {
            val diamond = StockEx(connection).getInfo("diamond_p")
            val diamondB = StockEx(connection).getInfo("diamond_block_p")
            val gui = Gui.normal()
                .setStructure(
                    "# . . . z . . . #",
                    "# . x . . . y . #",
                    "# . . . . . . . #")
                .addIngredient('#', border)
                .addIngredient('x', ShopItem(econ, balItem, ItemStack(Material.DIAMOND, 1), diamond.sellPrice, diamond.buyPrice, "§cPatrick Byattyman"))
                .addIngredient('y', ShopItem(econ, balItem, ItemStack(Material.DIAMOND_BLOCK, 1), diamondB.sellPrice, diamondB.buyPrice, "§cPatrick Byattyman"))
                .addIngredient('z', balItem)
                .build()
            return gui
        } else {
            val diamond = StockEx(connection).getInfo("diamond_s")
            val diamondB = StockEx(connection).getInfo("diamond_block_s")
            val gui = Gui.normal()
                .setStructure(
                    "# . . . z . . . #",
                    "# . x . . . y . #",
                    "# . . . . . . . #")
                .addIngredient('#', border)
                .addIngredient('x', ShopItem(econ, balItem, ItemStack(Material.DIAMOND, 1), diamond.sellPrice, diamond.buyPrice, "§6Sterling"))
                .addIngredient('y', ShopItem(econ, balItem, ItemStack(Material.DIAMOND_BLOCK, 1), diamondB.sellPrice, diamondB.buyPrice, "§6Sterling"))
                .addIngredient('z', balItem)
                .build()
            return gui
        }
    }
}