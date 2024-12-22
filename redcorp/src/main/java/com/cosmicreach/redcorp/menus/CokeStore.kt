package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.ShopItem
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class CokeStore(private var econ: Economy) {
    private val connection = RedCorp.getPlugin().getConnection()!!

    fun makeGUI(player: Player): Gui {
        val coke = StockEx(connection).getInfo("coke")
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val balItem = BalanceItem(econ, player)
        val gui = Gui.normal()
            .setStructure(
                "# . . . z . . . #",
                "# . . y . y . . #",
                "# . . . . . . . #")
            .addIngredient('#', border)
            .addIngredient('y', ShopItem(econ, balItem, DrugItems().Coke(1), coke.sellPrice, coke.buyPrice, "§cPatrick Byattyman", "", "%vendor% §8|§r OHHHHH YEAH that hit the spot thanks %player%§r!", "", "%vendor% §8|§r Whattttt %player%§r! You ran out of Bolivian Marching Powder!", "%vendor% §8|§r Sorry %player%§r I don't sell any of my fun stuff."))
            .addIngredient('z', balItem)
            .build()
        return gui
    }
}