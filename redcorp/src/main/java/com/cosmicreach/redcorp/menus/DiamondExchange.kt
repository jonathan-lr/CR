package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.DecreaseItemAmount
import com.cosmicreach.redcorp.menus.items.IncreaseItemAmount
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
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)
        if (type == 1) {
            val diamond = StockEx(connection).getInfo("diamond_p")
            val diamondItem = ShopItem(player, econ, balItem, ItemStack(Material.DIAMOND, values[pruchaseAmount.getOrDefault(player, 0)]), diamond.sellPrice, diamond.buyPrice, "§cPatrick Byattyman")
            val diamondBlockItem = ShopItem(player, econ, balItem, ItemStack(Material.DIAMOND_BLOCK, values[pruchaseAmount.getOrDefault(player, 0)]), (diamond.sellPrice*9), (diamond.buyPrice*9), "§cPatrick Byattyman")
            val gui = Gui.normal()
                .setStructure(
                    "# . . . $ . . . #",
                    "# . x . . . y . #",
                    "# . . < @ > . . #")
                .addIngredient('#', border)
                .addIngredient('x', diamondItem)
                .addIngredient('y', diamondBlockItem)
                .addIngredient('$', balItem)
                .addIngredient('@', amount)
                .addIngredient('>', IncreaseItemAmount(listOf(diamondItem, diamondBlockItem)))
                .addIngredient('<', DecreaseItemAmount(listOf(diamondItem, diamondBlockItem)))
                .build()
            return gui
        } else {
            val diamond = StockEx(connection).getInfo("diamond_s")
            val diamondItem = ShopItem(player, econ, balItem, ItemStack(Material.DIAMOND, values[pruchaseAmount.getOrDefault(player, 0)]), diamond.sellPrice, diamond.buyPrice, "§6Sterling")
            val diamondBlockItem = ShopItem(player, econ, balItem, ItemStack(Material.DIAMOND_BLOCK, values[pruchaseAmount.getOrDefault(player, 0)]), (diamond.sellPrice*9), (diamond.buyPrice*9), "§6Sterling")
            val gui = Gui.normal()
                .setStructure(
                    "# . . . $ . . . #",
                    "# . x . . . y . #",
                    "# . . < @ > . . #")
                .addIngredient('#', border)
                .addIngredient('x', diamondItem)
                .addIngredient('y', diamondBlockItem)
                .addIngredient('$', balItem)
                .addIngredient('@', amount)
                .addIngredient('>', IncreaseItemAmount(listOf(diamondItem, diamondBlockItem)))
                .addIngredient('<', DecreaseItemAmount(listOf(diamondItem, diamondBlockItem)))
                .build()
            return gui
        }
    }
}