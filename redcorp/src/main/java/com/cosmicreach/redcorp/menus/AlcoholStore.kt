package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.items.DrugItems
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

class AlcoholStore(private var econ: Economy) {
    private val connection = RedCorp.getPlugin().getConnection()!!
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val larger = StockEx(connection).getInfo("larger")
        val cider = StockEx(connection).getInfo("cider")
        val vodka = StockEx(connection).getInfo("vodka")
        val wine = StockEx(connection).getInfo("wine")
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)
        val largerItem = ShopItem(player, econ, balItem, DrugItems().Larger(1), larger.sellPrice, larger.buyPrice, "§6Charlie")
        val ciderItem = ShopItem(player, econ, balItem, DrugItems().Cider(1), cider.sellPrice, cider.buyPrice, "§6Charlie")
        val vodkaItem = ShopItem(player, econ, balItem, DrugItems().Vodka(1), vodka.sellPrice, vodka.buyPrice, "§6Charlie")
        val wineItem = ShopItem(player, econ, balItem, DrugItems().Wine(1), wine.sellPrice, wine.buyPrice, "§6Charlie")
        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . . . #",
                "# x . y . u . v #",
                "# . . < @ > . . #")
            .addIngredient('#', border)
            .addIngredient('x', largerItem)
            .addIngredient('y', ciderItem)
            .addIngredient('u', vodkaItem)
            .addIngredient('v', wineItem)
            .addIngredient('$', balItem)
            .addIngredient('@', amount)
            .addIngredient('>', IncreaseItemAmount(listOf(largerItem, ciderItem, vodkaItem, wineItem)))
            .addIngredient('<', DecreaseItemAmount(listOf(largerItem, ciderItem, vodkaItem, wineItem)))
            .build()
        return gui
    }
}