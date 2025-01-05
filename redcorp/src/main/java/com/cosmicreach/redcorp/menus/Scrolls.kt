package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.items.CustomItems
import com.cosmicreach.redcorp.menus.items.*
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class Scrolls(private var econ: Economy) {
    private val connection = RedCorp.getPlugin().getConnection()!!
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val scrollT = StockEx(connection).getInfo("scroll_t")
        val scrollD = StockEx(connection).getInfo("scroll_d")
        val scrollH = StockEx(connection).getInfo("scroll_h")
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        var balItem = BalanceItem(econ, player)

        val tScrollItem = ShopItem(player, econ, balItem, ItemStack(CustomItems().TeleportScroll(1)), scrollT.sellPrice, scrollT.buyPrice, "§dMerlin")
        val dScrollItem = ShopItem(player, econ, balItem, ItemStack(CustomItems().DeathScroll(1)), scrollD.sellPrice, scrollD.buyPrice, "§dMerlin")
        val hScrollItem = ShopItem(player, econ, balItem, ItemStack(CustomItems().HomeScroll(1)), scrollH.sellPrice, scrollH.buyPrice, "§dMerlin")

        val gui = Gui.normal()
                .setStructure(
                        "# . . . $ . . . #",
                        "# . x . u . y . #",
                        "# . . < @ > . . #")
                .addIngredient('#', border)
                .addIngredient('x', tScrollItem)
                .addIngredient('y', dScrollItem)
                .addIngredient('u', hScrollItem)
                .addIngredient('$', balItem)
                .addIngredient('@', amount)
                .addIngredient('>', IncreaseItemAmount(listOf(tScrollItem, dScrollItem, hScrollItem)))
                .addIngredient('<', DecreaseItemAmount(listOf(tScrollItem, dScrollItem, hScrollItem)))
                .build()
        return gui
    }
}