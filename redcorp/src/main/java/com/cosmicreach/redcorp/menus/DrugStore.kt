package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.menus.items.*
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class DrugStore(private var econ: Economy) {
    private val connection = RedCorp.getPlugin().getConnection()!!
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val weedS = StockEx(connection).getInfo("weed_s")
        val cocaS = StockEx(connection).getInfo("coca_s")
        val opiumS = StockEx(connection).getInfo("opium_s")
        val shroomS = StockEx(connection).getInfo("shroom_s")
        val truffleS = StockEx(connection).getInfo("truffle_s")

        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)

        val weedSeedItem = ShopItem(player, econ, balItem, DrugItems().WeedSeed(1), weedS.sellPrice, weedS.buyPrice,"§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!")
        val cocaSeedItem = ShopItem(player, econ, balItem, DrugItems().CocaSeed(1), cocaS.sellPrice, cocaS.buyPrice, "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!")
        val opiumFlowerItem = ShopItem(player, econ, balItem, DrugItems().OpiumFlower(1), opiumS.sellPrice, opiumS.buyPrice, "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!")
        val shroomItem = ShopItem(player, econ, balItem, DrugItems().Shrooms(1), shroomS.sellPrice, shroomS.buyPrice, "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!")
        val truffleItem = ShopItem(player, econ, balItem, DrugItems().Truffles(1), truffleS.sellPrice, truffleS.buyPrice, "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!")
        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . . . #",
                "# . u . x . y . #",
                "# . . v . w . . #",
                "# . . < @ > . . #")
            .addIngredient('#', border)
            .addIngredient('u', weedSeedItem)
            .addIngredient('x', cocaSeedItem)
            .addIngredient('y', opiumFlowerItem)
            .addIngredient('v', shroomItem)
            .addIngredient('w', truffleItem)
            .addIngredient('$', balItem)
            .addIngredient('@', amount)
            .addIngredient('>', IncreaseItemAmount(listOf(weedSeedItem, cocaSeedItem, opiumFlowerItem, shroomItem, truffleItem)))
            .addIngredient('<', DecreaseItemAmount(listOf(weedSeedItem, cocaSeedItem, opiumFlowerItem, shroomItem, truffleItem)))
            .build()
        return gui
    }
}