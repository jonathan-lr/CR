package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.StockEx
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
import kotlin.math.floor

class ShopItem(
    private var player: Player,
    private var econ: Economy,
    private var balItem: BalanceItem,
    private var shopItem: ItemStack,
    private var vendorName: String,
    private var vendorCompleteSell: String = "%vendor% §8|§r Thanks %player%§r for the purchase.",
    private var vendorCompleteBuy: String = "%vendor% §8|§r %player%§r sold an item successfully!",
    private var vendorFailSell: String = "%vendor% §8|§r Sorry %player%§r, you do not have enough Units for that purchase",
    private var vendorFailBuy: String = "%vendor% §8|§r Sorry %player%§r, you do not have any %item%§r to sell.",
    private var vendorStock: String = "%vendor% §8|§r Sorry %player%§r, %item%§r is currently out of stock.",
    private var useStock: Boolean = false,
    private var name: String = "null",
    private var sellProduct: Boolean = true,
) : AbstractItem() {
    private val values = listOf(1, 4, 8, 16, 32, 64)
    private val connection = RedCorp.getPlugin().getConnection()!!
    private var item = StockEx(connection).getInfo(name)
    private var buyPrice = item.buyPrice
    private var sellPrice = item.sellPrice
    private var stock = item.stock
    private val newBuyPrice: Double
        get() = if (useStock) {
            buyPrice * (1 - 0.01 * floor(stock / 8.0)).coerceAtLeast(0.0)
        } else {
            buyPrice
        }

    private val newSellPrice: Double
        get() = if (useStock) {
            val cappedStock = stock.coerceAtMost(256) // caps stock at 256
            sellPrice * (1 - 0.01 * floor(cappedStock / 8.0))
        } else {
            sellPrice
        }

    override fun getItemProvider(): ItemProvider {
        val amount = RedCorp.getPlugin().getPurchaseAmount()
        val displayItem = shopItem.clone()
        val meta = displayItem.itemMeta as ItemMeta
        var sell = ""
        var buy = ""
        if (sellPrice > 0.0) {
            sell = "§f§lLeft Click to §c§lBUY §f§l@ §c§l${econ.format(newSellPrice*values[amount.getOrDefault(player, 0)])}"
        }
        if (buyPrice > 0.0 && sellProduct) {
            buy = "§f§lRight Click to §c§lSELL §f§l@ §c§l${econ.format(newBuyPrice*values[amount.getOrDefault(player, 0)])}"
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
        val requiredAmount = values[amount.getOrDefault(player, 0)]

        if (buyPrice > 0.0 && sellProduct) {
            var totalAvailable = 0
            val stacksToUpdate = mutableListOf<ItemStack>()

            // First pass: Calculate the total available items and track stacks to update
            player.inventory.forEach { item ->
                if (item == null || item.type != shopItem.type) return@forEach

                if (Utils().getID(shopItem) > 0) {
                    if (Utils().checkID(item, arrayOf(Utils().getID(shopItem)))) {
                        totalAvailable += item.amount
                        stacksToUpdate.add(item)
                    }
                } else {
                    totalAvailable += item.amount
                    stacksToUpdate.add(item)
                }
            }

            // Check if there are enough items
            if (totalAvailable >= requiredAmount) {
                var remainingToDeduct = requiredAmount

                val tempItem = StockEx(connection).getInfo(name)
                if (tempItem.stock != stock) {
                    player.sendMessage("§cCR §8|§r Stock has changed since you opened the UI. Try again")
                    stock = tempItem.stock
                    notifyWindows()
                    return
                }

                // Second pass: Deduct items from stacks
                for (stack in stacksToUpdate) {
                    if (remainingToDeduct <= 0) break

                    if (stack.amount <= remainingToDeduct) {
                        remainingToDeduct -= stack.amount
                        stack.amount = 0 // Remove all items from this stack
                    } else {
                        stack.amount -= remainingToDeduct
                        remainingToDeduct = 0 // All required items deducted
                    }
                }

                // Log the sale and reward the player
                StockEx(connection).logSale(
                    shopItem,
                    player,
                    newBuyPrice * requiredAmount,
                    requiredAmount,
                    "playerSell"
                )
                if (useStock && name != "null") {
                    StockEx(connection).setStock(stock+requiredAmount,name)
                    stock = stock+values[amount.getOrDefault(player, 0)]
                    notifyWindows()
                }
                player.sendMessage(replacePlaceholders(vendorCompleteBuy, player))
                econ.depositPlayer(player, newBuyPrice * requiredAmount)
                balItem.refreshBal()
                gotItem = true
            } else {
                player.sendMessage("§cCR §8|§r Not enough items")
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
            val tempItem = StockEx(connection).getInfo(name)
            if (tempItem.stock != stock) {
                player.sendMessage("§cCR §8|§r Stock has changed since you opened the UI. Try again")
                stock = tempItem.stock
                notifyWindows()
                return
            }

            if (econ.getBalance(player) >= (newSellPrice*values[amount.getOrDefault(player, 0)])) {
                player.sendMessage(replacePlaceholders(vendorCompleteSell, player))
                shopItem.amount = values[amount.getOrDefault(player, 0)]
                player.inventory.addItem(shopItem)
                econ.withdrawPlayer(player, newSellPrice*values[amount.getOrDefault(player, 0)])
                balItem.refreshBal()
                StockEx(connection).logSale(shopItem, player, newSellPrice*values[amount.getOrDefault(player, 0)], values[amount.getOrDefault(player, 0)], "playerBuy")
                if (useStock && name != "null") {
                    StockEx(connection).setStock(stock-values[amount.getOrDefault(player, 0)],name)
                    stock = stock-values[amount.getOrDefault(player, 0)]
                    notifyWindows()
                }
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