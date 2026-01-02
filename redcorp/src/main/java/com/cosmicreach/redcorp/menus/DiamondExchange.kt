package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
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

class DiamondExchange(private var type: Int) {
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)
    private val econ = RedCorp.getPlugin().getEcon()

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)
        if (type == 1) {
            val diamondItem = ShopItem(player, econ, balItem, ItemStack(Material.DIAMOND, values[pruchaseAmount.getOrDefault(player, 0)]), "§cPatrick Byattyman", useStock = true, name="diamond_p")
            val gui = Gui.normal()
                .setStructure(
                    "# . . . $ . . . #",
                    "# . . . x . . . #",
                    "# . . < @ > . . #")
                .addIngredient('#', border)
                .addIngredient('x', diamondItem)
                .addIngredient('$', balItem)
                .addIngredient('@', amount)
                .addIngredient('>', IncreaseItemAmount(listOf(diamondItem)))
                .addIngredient('<', DecreaseItemAmount(listOf(diamondItem)))
                .build()
            return gui
        } else {
            val diamondItem = ShopItem(player, econ, balItem, ItemStack(Material.DIAMOND, values[pruchaseAmount.getOrDefault(player, 0)]), "§6Sterling", useStock = true, name="diamond_s")
           val gui = Gui.normal()
                .setStructure(
                    "# . . . $ . . . #",
                    "# . . . x . . . #",
                    "# . . < @ > . . #")
                .addIngredient('#', border)
                .addIngredient('x', diamondItem)
                .addIngredient('$', balItem)
                .addIngredient('@', amount)
                .addIngredient('>', IncreaseItemAmount(listOf(diamondItem)))
                .addIngredient('<', DecreaseItemAmount(listOf(diamondItem)))
                .build()
            return gui
        }
    }
}