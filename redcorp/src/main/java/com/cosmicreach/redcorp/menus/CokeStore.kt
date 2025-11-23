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

class CokeStore(private var econ: Economy) {
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)
        val cokeItem = ShopItem(player, econ, balItem, DrugItems().Coke(1), "§cPatrick Byattyman", "", "%vendor% §8|§r OHHHHH YEAH that hit the spot thanks %player%§r!", "", "%vendor% §8|§r Whattttt %player%§r! You ran out of Bolivian Marching Powder!", "%vendor% §8|§r Sorry %player%§r I don't sell any of my fun stuff.", name = "coke")
        val openShipment = ShipmentOpen(432)

        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . . . #",
                "# . . y . x . . #",
                "# . . < @ > . . #")
            .addIngredient('#', border)
            .addIngredient('y', cokeItem)
            .addIngredient('x', openShipment)
            .addIngredient('$', balItem)
            .addIngredient('@', amount)
            .addIngredient('>', IncreaseItemAmount(listOf(cokeItem)))
            .addIngredient('<', DecreaseItemAmount(listOf(cokeItem)))
            .build()
        return gui
    }
}