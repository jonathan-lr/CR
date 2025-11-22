package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.DecreaseItemAmount
import com.cosmicreach.redcorp.menus.items.IncreaseItemAmount
import com.cosmicreach.redcorp.menus.items.ShipmentOpen
import com.cosmicreach.redcorp.menus.items.ShopItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class WeedStore(private var econ: Economy) {
    private val connection = RedCorp.getPlugin().getConnection()!!
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val weed = StockEx(connection).getInfo("weed_g")
        val spliff = StockEx(connection).getInfo("weed_z")
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)

        val weedItem = ShopItem(player, econ, balItem, DrugItems().GroundWeed(1), weed.sellPrice, weed.buyPrice, "§2Shaggy")
        val spliffItem = ShopItem(player, econ, balItem, DrugItems().Spliff(1), spliff.sellPrice, spliff.buyPrice, "§2Shaggy")
        val openShipment = ShipmentOpen(422)

        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . . . #",
                "# . x . y . z . #",
                "# . . < @ > . . #")
            .addIngredient('#', border)
            .addIngredient('x', weedItem)
            .addIngredient('y', spliffItem)
            .addIngredient('z', openShipment)
            .addIngredient('$', balItem)
            .addIngredient('@', amount)
            .addIngredient('>', IncreaseItemAmount(listOf(weedItem, spliffItem)))
            .addIngredient('<', DecreaseItemAmount(listOf(weedItem, spliffItem)))
            .build()
        return gui
    }
}