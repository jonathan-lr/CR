package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.utils.Utils
import net.milkbowl.vault.economy.Economy
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.ItemWrapper
import xyz.xenondevs.invui.item.impl.AbstractItem

class ShopItem(
    private var player: Player,
    private var econ: Economy,
    private var balItem: BalanceItem,
    private var shopItem: ItemStack,
    private var sellPrice: Double,
    private var buyPrice: Double,
    private var vendorName: String,
    private var vendorCompleteSell: String = "%vendor% §8|§r Thanks %player%§r for the purchase.",
    private var vendorCompleteBuy: String = "%vendor% §8|§r %player%§r sold an item successfully!",
    private var vendorFailSell: String = "%vendor% §8|§r Sorry %player%§r, you do not have enough Units for that purchase",
    private var vendorFailBuy: String = "%vendor% §8|§r Sorry %player%§r, you do not have any %item%§r to sell.",
    private var vendorStock: String = "%vendor% §8|§r Sorry %player%§r, %item%§r is currently out of stock."
) : AbstractItem() {
    private val values = listOf(1, 4, 8, 16, 32, 64)

    override fun getItemProvider(): ItemProvider {
        val amount = RedCorp.getPlugin().getPurchaseAmount()
        val displayItem = shopItem.clone()
        val meta = displayItem.itemMeta as ItemMeta
        var sell = ""
        var buy = ""
        if (sellPrice > 0.0) {
            sell = "§f§lLeft Click to §c§lBUY §f§l@ §c§l${econ.format(sellPrice*values[amount.getOrDefault(player, 0)])}"
        }
        if (buyPrice > 0.0) {
            buy = "§f§lRight Click to §c§lSELL §f§l@ §c§l${econ.format(buyPrice*values[amount.getOrDefault(player, 0)])}"
        }
        if (sellPrice == 0.0 && buyPrice == 0.0) {
            sell = "§c§lOut of Stock"
        }
        meta.lore = mutableListOf(sell, buy)
        displayItem.setItemMeta(meta)
        displayItem.amount = values[amount.getOrDefault(player, 0)]
        return ItemWrapper(displayItem)
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        when (clickType) {
            ClickType.LEFT -> handleSell(player)
            ClickType.RIGHT -> handleBuy(player)
            else -> return
        }
    }

    private fun handleBuy(player: Player) {
        var gotItem = false
        val amount = RedCorp.getPlugin().getPurchaseAmount()
        if (buyPrice > 0.0) {
            player.inventory.forEach { item ->
                if (item == null) { return@forEach }
                if (item.type !== shopItem.type) { return@forEach }

                if (Utils().getID(shopItem) > 0) {
                    if (Utils().checkID(item, arrayOf(Utils().getID(shopItem)))) {
                        if (item.amount >= values[amount.getOrDefault(player, 0)]) {
                            player.sendMessage(replacePlaceholders(vendorCompleteBuy, player))
                            item.amount -= values[amount.getOrDefault(player, 0)]
                            econ.depositPlayer(player, buyPrice*values[amount.getOrDefault(player, 0)])
                            balItem.refreshBal()
                            gotItem = true
                        } else {
                            player.sendMessage("§cCR §8|§r Not enough items")
                        }
                        return
                    }
                } else {
                    if (item.amount >= values[amount.getOrDefault(player, 0)]) {
                        player.sendMessage(replacePlaceholders(vendorCompleteBuy, player))
                        item.amount -= values[amount.getOrDefault(player, 0)]
                        econ.depositPlayer(player, buyPrice*values[amount.getOrDefault(player, 0)])
                        balItem.refreshBal()
                        gotItem = true
                    } else {
                        player.sendMessage("§cCR §8|§r Not enough items")
                    }
                    return
                }
                return@forEach
            }
            if (!gotItem) {
                player.sendMessage(replacePlaceholders(vendorFailBuy, player))
            }

            notifyWindows()
        }
    }

    private fun handleSell(player: Player) {
        val amount = RedCorp.getPlugin().getPurchaseAmount()
        if (sellPrice > 0.0) {
            if (econ.getBalance(player) >= (sellPrice*values[amount.getOrDefault(player, 0)])) {
                player.sendMessage(replacePlaceholders(vendorCompleteSell, player))
                shopItem.amount = values[amount.getOrDefault(player, 0)]
                player.inventory.addItem(shopItem)
                econ.withdrawPlayer(player, sellPrice*values[amount.getOrDefault(player, 0)])
                balItem.refreshBal()
            } else {
                player.sendMessage(replacePlaceholders(vendorFailSell, player))
            }

            notifyWindows()
        } else {
            player.sendMessage(replacePlaceholders(vendorStock, player))
        }
    }

    private fun replacePlaceholders(message: String, player: Player): String {
        var temp = message.replace("%player%", player.displayName)
        temp = temp.replace("%vendor%", vendorName)
        temp = temp.replace("%item%", shopItem.itemMeta!!.displayName)
        return temp
    }
}