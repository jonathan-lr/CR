package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.items.CustomItems
import net.milkbowl.vault.economy.Economy
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.meta.ItemMeta
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class BalanceItem(private val econ: Economy, private val player: Player) : AbstractItem() {
    private var bal: String = econ.format(econ.getBalance(player))
    override fun getItemProvider(): ItemProvider {
        val item = CustomItems().Unit(1, "red")
        val meta = item.itemMeta as ItemMeta
        meta.setDisplayName("§f§lBalance §8§l: §c§l$bal")
        item.setItemMeta(meta)
        return ItemBuilder(item)
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        refreshBal()
        notifyWindows()
    }

    fun refreshBal() {
        bal = econ.format(econ.getBalance(player))
        notifyWindows()
    }
}