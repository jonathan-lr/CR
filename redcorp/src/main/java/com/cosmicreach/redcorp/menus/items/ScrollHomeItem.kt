package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.items.CustomItems
import com.cosmicreach.redcorp.items.DungeonItems
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.meta.ItemMeta
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class ScrollHomeItem(private var econ: Economy, private var balItem: BalanceItem) : AbstractItem() {
    override fun getItemProvider(): ItemProvider {
        val item = CustomItems().DeathScroll(1)
        val meta = item.itemMeta as ItemMeta
        meta.setDisplayName("§3§lHome Scroll §8§l: §c§l${econ.format(500.0)}")
        meta.lore = mutableListOf("§f§lClick to §c§lBUY")
        item.setItemMeta(meta)
        return ItemBuilder(item)
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        //player.sendMessage("§dMerlin §8|§r Sorry ${player.displayName}§r, this item is currently out of stock")
        if (econ.getBalance(player) >= 500.0) {
            player.sendMessage("§cReginald §8|§r Thanks ${player.displayName}§r for the purchase.")
            player.inventory.addItem(CustomItems().HomeScroll(1))
            econ.withdrawPlayer(player, 500.0)
            balItem.refreshBal()
        } else {
            player.sendMessage("§cReginald §8|§r Sorry ${player.displayName}§r, you do not have enough Units for that purchase")
        }
        notifyWindows()
    }

}