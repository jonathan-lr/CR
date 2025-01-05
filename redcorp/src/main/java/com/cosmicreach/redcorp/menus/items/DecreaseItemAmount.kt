package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.RedCorp
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem

class DecreaseItemAmount(private val items: List<ShopItem>) : ControlItem<Gui>() {
    private val amount = RedCorp.getPlugin().getPurchaseAmount()
    val values = listOf(1, 4, 8, 16, 32, 64)

    override fun getItemProvider(gui: Gui): ItemProvider {
        return ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§c§lDecrease Amount").setLegacyLore((mutableListOf("§f§lClick to decrease")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        if (!amount.containsKey(player)) {
            amount[player] = 0
        }

        if ((amount[player]!! - 1) >= 0) {
            amount[player] = amount[player]!! - 1
        }

        val itemAmount = SimpleItem(ItemBuilder(ItemStack(Material.EMERALD, values[amount.getOrDefault(player, 0)])).setDisplayName("§rYou will purchase #${values[amount.getOrDefault(player, 0)]} items"))
        gui.setItem(4, (gui.size/9)-1, itemAmount)

        items.forEach {
            it.notifyWindows()
        }

        notifyWindows()
    }
}