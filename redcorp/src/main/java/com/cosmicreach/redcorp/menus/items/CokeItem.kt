package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.items.DrugItems
import net.milkbowl.vault.economy.Economy
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.meta.ItemMeta
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class CokeItem(private var econ: Economy, private var balItem: BalanceItem) : AbstractItem() {
    override fun getItemProvider(): ItemProvider {
        val item = DrugItems().CocaSeed(1)
        val meta = item.itemMeta as ItemMeta
        meta.setDisplayName("${meta.displayName}: §c§l${econ.format(1000.0)}")
        meta.lore = mutableListOf("§f§lClick to §c§lBUY")
        item.setItemMeta(meta)
        return ItemBuilder(item)
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        if (econ.getBalance(player) >= 1000.0) {
            player.sendMessage("§8Shade E §8|§r ${player.displayName}§r get out of here before the cops come!")
            player.inventory.addItem(DrugItems().CocaSeed(1))
            econ.withdrawPlayer(player, 1000.0)
            balItem.refreshBal()
        } else {
            player.sendMessage("§8Shade E §8|§r Fuck off ${player.displayName}§r, you do not have enough Units this shit")
        }
        notifyWindows()
    }
}