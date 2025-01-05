package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.items.DungeonItems
import com.cosmicreach.redcorp.menus.items.*
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class RewardExchange(private var econ: Economy) {

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        var balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
                .setStructure(
                        "# . . . z . . . #",
                        "# x . y . v . u #",
                        "# . . . . . . . #")
                .addIngredient('#', border)
                .addIngredient('x', ShopItem(player, econ, balItem, ItemStack(DungeonItems().airReward()), 0.0, 300.0, "§aEloise"))
                .addIngredient('y', ShopItem(player, econ, balItem, ItemStack(DungeonItems().fireReward()), 0.0, 300.0, "§aEloise"))
                .addIngredient('v', ShopItem(player, econ, balItem, ItemStack(DungeonItems().waterReward()), 0.0, 300.0, "§aEloise"))
                .addIngredient('u', ShopItem(player, econ, balItem, ItemStack(DungeonItems().earthReward()), 0.0, 300.0, "§aEloise"))
                .addIngredient('z', balItem)
                .build()
        return gui
    }
}