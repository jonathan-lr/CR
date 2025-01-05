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

class OpiumStore(private var econ: Economy) {
    private val connection = RedCorp.getPlugin().getConnection()!!
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val opium = StockEx(connection).getInfo("opium")

        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)

        val opiumItem = ShopItem(player, econ, balItem, DrugItems().Opium(1), opium.sellPrice, opium.buyPrice, "§fWong", "%vendor% §8|§r The CCP thanks you for your support!", "%vendor% §8|§r The CCP thanks you for your support!", "%vendor% §8|§r The CCP has no place for poor capitalist scum!", "%vendor% §8|§r The CCP has no place for capitalist scum with no product!")
        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . . . #",
                "# . . x . x . . #",
                "# . . < @ > . . #")
            .addIngredient('#', border)
            .addIngredient('x', opiumItem)
            .addIngredient('$', balItem)
            .addIngredient('@', amount)
            .addIngredient('>', IncreaseItemAmount(listOf(opiumItem)))
            .addIngredient('<', DecreaseItemAmount(listOf(opiumItem)))
            .build()
        return gui
    }
}