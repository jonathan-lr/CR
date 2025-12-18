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

class MushroomStore() {
    private val econ = RedCorp.getPlugin().getEcon()
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)

        val shroomItem = ShopItem(player, econ, balItem, DrugItems().Shrooms(1),"§5Toad", name = "shroom", useStock = true)
        val truffleItem = ShopItem(player, econ, balItem, DrugItems().Truffles(1),"§5Toad", name = "truffle", useStock = true)
        val openShipment1 = ShipmentOpen(450, "shroom")
        val openShipment2 = ShipmentOpen(451, "truffle")
        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . . . #",
                "# . . x . y . . #",
                "# . . z . p . . #",
                "# . . < @ > . . #")
            .addIngredient('#', border)
            .addIngredient('x', shroomItem)
            .addIngredient('y', truffleItem)
            .addIngredient('z', openShipment1)
            .addIngredient('p', openShipment2)
            .addIngredient('$', balItem)
            .addIngredient('@', amount)
            .addIngredient('>', IncreaseItemAmount(listOf(shroomItem, truffleItem)))
            .addIngredient('<', DecreaseItemAmount(listOf(shroomItem, truffleItem)))
            .build()
        return gui
    }
}