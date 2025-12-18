package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.menus.items.*
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class DrugStore() {
    private val econ = RedCorp.getPlugin().getEcon()
    private val pruchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 4, 8, 16, 32, 64)

    fun makeGUI(player: Player): Gui {
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[pruchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[pruchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)

        val weedSeedItem = ShopItem(player, econ, balItem, DrugItems().WeedSeed(1), "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!", name = "weed_s", useStock = true)
        val cocaSeedItem = ShopItem(player, econ, balItem, DrugItems().CocaSeed(1), "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!", name = "coca_s", useStock = true)
        val opiumFlowerItem = ShopItem(player, econ, balItem, DrugItems().OpiumFlower(1), "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!", name = "opium_s", useStock = true)
        val shroomItem = ShopItem(player, econ, balItem, DrugItems().Shrooms(1), "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!", name = "shroom_s", useStock = true)
        val truffleItem = ShopItem(player, econ, balItem, DrugItems().Truffles(1), "§8Shade E", "%vendor% §8|§r %player%§r get out of here before the cops come!", "", "%vendor% §8|§r Fuck off %player%§r, you do not have enough Units this shizz!", name = "truffle_s", useStock = true)
        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . . . #",
                "# . u . x . y . #",
                "# . . v . w . . #",
                "# . . < @ > . . #")
            .addIngredient('#', border)
            .addIngredient('u', weedSeedItem)
            .addIngredient('x', cocaSeedItem)
            .addIngredient('y', opiumFlowerItem)
            .addIngredient('v', shroomItem)
            .addIngredient('w', truffleItem)
            .addIngredient('$', balItem)
            .addIngredient('@', amount)
            .addIngredient('>', IncreaseItemAmount(listOf(weedSeedItem, cocaSeedItem, opiumFlowerItem, shroomItem, truffleItem)))
            .addIngredient('<', DecreaseItemAmount(listOf(weedSeedItem, cocaSeedItem, opiumFlowerItem, shroomItem, truffleItem)))
            .build()
        return gui
    }
}