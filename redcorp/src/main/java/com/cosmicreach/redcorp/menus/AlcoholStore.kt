package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.ShopItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class AlcoholStore(private var econ: Economy) {
    private val connection = RedCorp.getPlugin().getConnection()!!

    fun makeGUI(player: Player): Gui {
        val larger = StockEx(connection).getInfo("larger")
        val cider = StockEx(connection).getInfo("cider")
        val vodka = StockEx(connection).getInfo("vodka")
        val wine = StockEx(connection).getInfo("wine")
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
            .setStructure(
                "# . . . z . . . #",
                "# x . y . u . v #",
                "# . . . . . . . #")
            .addIngredient('#', border)
            .addIngredient('x', ShopItem(econ, balItem, DrugItems().Larger(1), larger.sellPrice, larger.buyPrice, "§6Charlie"))
            .addIngredient('y', ShopItem(econ, balItem, DrugItems().Cider(1), cider.sellPrice, cider.buyPrice, "§6Charlie"))
            .addIngredient('u', ShopItem(econ, balItem, DrugItems().Vodka(1), vodka.sellPrice, vodka.buyPrice, "§6Charlie"))
            .addIngredient('v', ShopItem(econ, balItem, DrugItems().Wine(1), wine.sellPrice, wine.buyPrice, "§6Charlie"))
            .addIngredient('z', balItem)
            .build()
        return gui
    }
}