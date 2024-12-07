package com.cosmicreach.redcorp.menus.items

import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class DiamondItem(private var econ: Economy, private var balItem: BalanceItem) : AbstractItem() {
    override fun getItemProvider(): ItemProvider {
        return ItemBuilder(Material.DIAMOND).setDisplayName("§3§lDiamond §8§l: §c§l${econ.format(100.0)}").setLegacyLore((mutableListOf("§f§lLeft Click to §2§lSELL", "§f§lRight Click to §c§lBUY")))
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        if (clickType.isLeftClick) {
            var gotItem = false
            player.inventory.forEach { item ->
                if (item !== null) {
                    if (item.type == Material.DIAMOND) {
                        player.sendMessage("§cReginald §8|§r Thanks ${player.displayName} §rfor the sale.")
                        item.amount -= 1
                        econ.depositPlayer(player, 100.0)
                        balItem.refreshBal()
                        gotItem = true
                        return
                    }
                }
            }
            if (!gotItem) {
                player.sendMessage("§cReginald §8|§r Sorry ${player.displayName}§r, you do not have any Diamonds to sell.")
            }
        } else {
           if (econ.getBalance(player) >= 100.0) {
               player.sendMessage("§cReginald §8|§r Thanks ${player.displayName} §rfor the purchase.")
               player.inventory.addItem(ItemStack(Material.DIAMOND, 1))
               econ.withdrawPlayer(player, 100.0)
               balItem.refreshBal()
           } else {
               player.sendMessage("§cReginald §8|§r Sorry ${player.displayName}, you do not have enough Units for that purchase")
           }
        }
        notifyWindows()
    }

}