package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.DecreaseItemAmount
import com.cosmicreach.redcorp.menus.items.IncreaseItemAmount
import com.cosmicreach.redcorp.menus.items.ShipmentOpen
import com.cosmicreach.redcorp.menus.items.ShopItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class WeedStore() {
    private val econ = RedCorp.getPlugin().getEcon()
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)

        val weedItem = ShopItem(player, econ, balItem, DrugItems().GroundWeed(1),"§2Shaggy", name = "weed_g", sellProduct = false, useStock = true)
        val spliffItem = ShopItem(player, econ, balItem, DrugItems().Spliff(1),"§2Shaggy", name = "weed_z", sellProduct = false, useStock = true)
        val openShipment = ShipmentOpen(422, "weed_g")

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