package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.CustomItems
import com.cosmicreach.redcorp.items.DrugItems
import com.cosmicreach.redcorp.items.GreenhouseItems
import com.cosmicreach.redcorp.items.ServerItems
import com.cosmicreach.redcorp.menus.items.BalanceItem
import com.cosmicreach.redcorp.menus.items.DecreaseItemAmount
import com.cosmicreach.redcorp.menus.items.IncreaseItemAmount
import com.cosmicreach.redcorp.menus.items.ShopItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class FurnitureStore () {
    private val econ = RedCorp.getPlugin().getEcon()
    private val purchaseAmount = RedCorp.getPlugin().getPurchaseAmount()
    private val values = listOf(1, 2)

    fun makeGUI(player: Player): Gui {
        purchaseAmount[player] = 0
        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val amount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[purchaseAmount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[purchaseAmount.getOrDefault(player, 0)]} items"))
        val balItem = BalanceItem(econ, player)

        val agingBarrelItem = ShopItem(player, econ, balItem, DrugItems().AgingBarrel(1),"§2Oakley", name = "aging_barrel", useStock = true)
        val coffeeMachineItem = ShopItem(player, econ, balItem, DrugItems().CoffieMachine(1),"§2Oakley", name = "coffee_machine", useStock = true)
        val grinderItem = ShopItem(player, econ, balItem, DrugItems().Grinder(1),"§2Oakley", name = "grinder", useStock = true)
        val industrialGrinderItem = ShopItem(player, econ, balItem, DrugItems().IdustrialGrinder(1),"§2Oakley", name = "grinder_i", useStock = true)
        val dryingRackItem = ShopItem(player, econ, balItem, DrugItems().DryingRack(1),"§2Oakley", name = "drying_rack", useStock = true)
        val mixerItem = ShopItem(player, econ, balItem, DrugItems().Mixer(1),"§2Oakley", name = "mixer", useStock = true)
        val pressItem = ShopItem(player, econ, balItem, DrugItems().Press(1),"§2Oakley", name = "press", useStock = true)
        val slotItem = ShopItem(player, econ, balItem, ServerItems().SlotMachine(1),"§2Oakley", name = "slot", useStock = true)
        val greenhouse = ShopItem(player, econ, balItem, GreenhouseItems().GreenhouseEntrance(-1),"§2Oakley", name = "greenhouse")
        val greenhouseUpgrade = ShopItem(player, econ, balItem, GreenhouseItems().GreenhouseUpgrade(1),"§2Oakley", name = "greenhouseUpgrade")
        val borderItem = ShopItem(player, econ, balItem, CustomItems().BorderItem(1),"§2Oakley", name = "border_item", useStock = true, stockTrigger = 1.0, stockMultiplier = 1.5)
        val gui = Gui.normal()
            .setStructure(
                "# . . . $ . g . #",
                "# . u c x z y . #",
                "# . a b d e f . #",
                "# . . < @ > . . #")
            .addIngredient('#', border)
            .addIngredient('u', agingBarrelItem)
            .addIngredient('x', coffeeMachineItem)
            .addIngredient('y', grinderItem)
            .addIngredient('z', industrialGrinderItem)
            .addIngredient('a', dryingRackItem)
            .addIngredient('b', mixerItem)
            .addIngredient('c', pressItem)
            .addIngredient('d', slotItem)
            .addIngredient('e', greenhouse)
            .addIngredient('f', greenhouseUpgrade)
            .addIngredient('g', borderItem)
            .addIngredient('$', balItem)
            .addIngredient('@', amount)
            .addIngredient('>', IncreaseItemAmount(listOf(agingBarrelItem, coffeeMachineItem, grinderItem, industrialGrinderItem, dryingRackItem, mixerItem, pressItem, slotItem, greenhouse, greenhouseUpgrade, borderItem)))
            .addIngredient('<', DecreaseItemAmount(listOf(agingBarrelItem, coffeeMachineItem, grinderItem, industrialGrinderItem, dryingRackItem, mixerItem, pressItem, slotItem, greenhouse, greenhouseUpgrade, borderItem)))
            .build()
        return gui
    }
}