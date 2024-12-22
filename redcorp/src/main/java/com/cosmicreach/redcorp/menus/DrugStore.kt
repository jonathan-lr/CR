package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.menus.items.*
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class DrugStore(private var econ: Economy) {
    private val connection = RedCorp.getPlugin().getConnection()!!

    fun makeGUI(player: Player): Gui {
        val weedS = StockEx(connection).getInfo("weed_s")
        val cocaS = StockEx(connection).getInfo("coca_s")
        val opiumS = StockEx(connection).getInfo("opium_s")
        val shroomS = StockEx(connection).getInfo("shroom_s")
        val truffleS = StockEx(connection).getInfo("truffle_s")

        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
            .setStructure(
                "# . . . z . . . #",
                "# . u . x . y . #",
                "# . . v . w . . #")
            .addIngredient('#', border)
            .addIngredient('u', ShopItem(econ, balItem, DrugItems().WeedSeed(1), weedS.sellPrice, weedS.buyPrice,"§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!"))
            .addIngredient('x', ShopItem(econ, balItem, DrugItems().CocaSeed(1), cocaS.sellPrice, cocaS.buyPrice, "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!"))
            .addIngredient('y', ShopItem(econ, balItem, DrugItems().OpiumFlower(1), opiumS.sellPrice, opiumS.buyPrice, "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!"))
            .addIngredient('v', ShopItem(econ, balItem, DrugItems().Shrooms(1), shroomS.sellPrice, shroomS.buyPrice, "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!"))
            .addIngredient('w', ShopItem(econ, balItem, DrugItems().Truffles(1), truffleS.sellPrice, truffleS.buyPrice, "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!"))
            .addIngredient('z', balItem)
            .build()
        return gui
    }
}