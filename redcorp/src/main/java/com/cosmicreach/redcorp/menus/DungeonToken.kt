package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.DungeonItems
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

class DungeonToken(private var econ: Economy) {
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)
        val dungeonItem = ShopItem(player, econ, balItem, ItemStack(DungeonItems().dungeonToken()), 100.0, 0.0, "§9Milton")
        val dungeonHardItem = ShopItem(player, econ, balItem, ItemStack(DungeonItems().dungeonTokenHard()), 0.0, 0.0, "§9Milton")
        val gui = Gui.normal()
                .setStructure(
                        "# . . . $ . . . #",
                        "# . x . . . y . #",
                        "# . . < @ > . . #")
                .addIngredient('#', border)
                .addIngredient('x', dungeonItem)
                .addIngredient('y', dungeonHardItem)
                .addIngredient('$', balItem)
                .addIngredient('@', amount)
                .addIngredient('>', IncreaseItemAmount(listOf(dungeonItem, dungeonHardItem)))
                .addIngredient('<', DecreaseItemAmount(listOf(dungeonItem, dungeonHardItem)))
                .build()
        return gui
    }
}