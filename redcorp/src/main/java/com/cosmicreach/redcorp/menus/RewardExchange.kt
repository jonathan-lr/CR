package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.DungeonItems
import com.cosmicreach.redcorp.menus.items.*
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class RewardExchange() {
    private val econ = RedCorp.getPlugin().getEcon()

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
                .setStructure(
                        "# . . . z . . . #",
                        "# x . y . v . u #",
                        "# . . . . . . . #")
                .addIngredient('#', border)
                .addIngredient('x', ShopItem(player, econ, balItem, ItemStack(DungeonItems().airReward()),"§aEloise", name = "dungeon_r"))
                .addIngredient('y', ShopItem(player, econ, balItem, ItemStack(DungeonItems().fireReward()),"§aEloise", name = "dungeon_r"))
                .addIngredient('v', ShopItem(player, econ, balItem, ItemStack(DungeonItems().waterReward()),"§aEloise", name = "dungeon_r"))
                .addIngredient('u', ShopItem(player, econ, balItem, ItemStack(DungeonItems().earthReward()),"§aEloise", name = "dungeon_r"))
                .addIngredient('z', balItem)
                .build()
        return gui
    }
}