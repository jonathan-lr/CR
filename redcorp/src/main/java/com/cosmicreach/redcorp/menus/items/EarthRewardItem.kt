package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.items.DungeonItems
import com.cosmicreach.redcorp.utils.Utils
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.meta.ItemMeta
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class EarthRewardItem(private var econ: Economy, private var balItem: BalanceItem) : AbstractItem() {
    override fun getItemProvider(): ItemProvider {
        val item = DungeonItems().earthReward()
        val meta = item.itemMeta as ItemMeta
        meta.setDisplayName("§2§lEarth §f§lReward§8§l: §c§l${econ.format(300.0)}")
        meta.lore = mutableListOf("§f§lClick to §2§lSELL")
        item.setItemMeta(meta)
        return ItemBuilder(item)
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        var gotItem = false
        player.inventory.forEach { item ->
            if (item !== null) {
                if (Utils().checkID(item, arrayOf(55))) {
                    player.sendMessage("§aEloise §8|§r Thanks ${player.displayName} §rfor the sale.")
                    item.amount -= 1
                    econ.depositPlayer(player, 300.0)
                    balItem.refreshBal()
                    gotItem = true
                    return
                }
            }
        }
        if (!gotItem) {
            player.sendMessage("§aEloise §8|§r Sorry ${player.displayName}§r, you do not have any Earth Rewards to sell.")
        }
        notifyWindows()
    }
}