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

class FurnitureStore (private var econ: Economy) {
    private val connection = RedCorp.getPlugin().getConnection()!!
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val agingBarrel = StockEx(connection).getInfo("aging_barrel")
        val coffeeMachine = StockEx(connection).getInfo("coffee_machine")
        val grinder = StockEx(connection).getInfo("grinder")
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)

        val agingBarrelItem = ShopItem(player, econ, balItem, DrugItems().AgingBarrel(1), agingBarrel.sellPrice, agingBarrel.buyPrice, "§2Oakley")
        val coffeeMachineItem = ShopItem(player, econ, balItem, DrugItems().CoffieMachine(1), coffeeMachine.sellPrice, coffeeMachine.buyPrice, "§2Oakley")
        val grinderItem = ShopItem(player, econ, balItem, DrugItems().Grinder(1), grinder.sellPrice, grinder.buyPrice, "§2Oakley")
        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . . . #",
                "# . u . x . y . #",
                "# . . < @ > . . #")
            .addIngredient('#', border)
            .addIngredient('u', agingBarrelItem)
            .addIngredient('x', coffeeMachineItem)
            .addIngredient('y', grinderItem)
            .addIngredient('$', balItem)
            .addIngredient('@', amount)
            .addIngredient('>', IncreaseItemAmount(listOf(agingBarrelItem, coffeeMachineItem, grinderItem)))
            .addIngredient('<', DecreaseItemAmount(listOf(agingBarrelItem, coffeeMachineItem, grinderItem)))
            .build()
        return gui
    }
}