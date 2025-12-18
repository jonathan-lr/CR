package com.cosmicreach.redcorp.menus.items

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Delivery
import com.cosmicreach.redcorp.db.StockEx
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.ItemWrapper
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem
import kotlin.math.floor
import kotlin.random.Random

class ShipmentItem(
    private var econ: Economy,
    private var size: Int,
    private var drugType: Int,
    private val drugName: String
) : ControlItem<Gui>() {
    private val connection = RedCorp.getPlugin().getConnection()!!
    private var item = StockEx(connection).getInfo(drugName)
    private val newBuyPrice = item.buyPrice * (1 - 0.01 * floor(item.stock / 8.0)).coerceAtLeast(0.0)

    override fun getItemProvider(gui: Gui): ItemProvider {
        var amount = 32
        var shipmentText = "sᴍᴀʟʟ"
        var multiplier = 1.00
        if (size == 2) {
            amount = 128
            shipmentText = "ᴍᴇᴅɪᴜᴍ"
            multiplier = 1.01
        }
        if (size == 3) {
            amount = 320
            shipmentText = "ʟᴀʀɢᴇ"
            multiplier = 1.02
        }
        val displayItem = ItemStack(Material.BARREL, amount/8)
        val meta = displayItem.itemMeta as ItemMeta

        meta.setDisplayName("§c§l${shipmentText} sʜɪᴘᴍᴇɴᴛ")

        val lore = listOf("§fClick to start shipment for $amount @ ${econ.format(newBuyPrice*multiplier)} each")
        meta.lore = lore
        displayItem.itemMeta = meta

        return ItemWrapper(displayItem)
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val shipment = Delivery(connection).getDelivery(player.uniqueId)

        if (shipment != null) {
            player.sendMessage("§cCR §8|§rSorry ${player.displayName} §ryou have an active shipment")
            return
        }

        val sellPrice = newBuyPrice
        var amount = 32
        var label = "small"
        var multiplier = 1.00
        if (size == 2) {
            amount = 128
            multiplier = 1.01
            label = "medium"
        }
        if (size == 3) {
            amount = 320
            multiplier = 1.02
            label = "large"
        }
        val randomLocation = Random.nextInt(0, 5)
        Delivery(connection).addDelivery(player.uniqueId, sellPrice*multiplier, amount, randomLocation, drugType)

        player.sendMessage("§cCR §8|§r Your shipment has been created for ${econ.format(sellPrice*amount*multiplier)} at dropbox ${randomLocation}")
        Bukkit.broadcastMessage("§cCR §8|§r ${player.displayName} §rhas started a $label shipment")
    }
}