package com.cosmicreach.redcorp.db

import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.sql.Connection

class StockEx(private var connection: Connection) {
    data class ItemInfo(val sellPrice: Double, val buyPrice: Double, val stock: Int)

    fun getInfo(item: String): ItemInfo {
        val sql = "SELECT sellPrice, buyPrice, stock FROM prices WHERE item = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, item)
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    ItemInfo(
                        sellPrice = resultSet.getDouble("sellPrice").takeIf { !resultSet.wasNull() } ?: 0.0,
                        buyPrice = resultSet.getDouble("buyPrice").takeIf { !resultSet.wasNull() } ?: 0.0,
                        stock = resultSet.getInt("stock").takeIf { !resultSet.wasNull() } ?: 0
                    )
                } else {
                    ItemInfo(sellPrice = 0.0, buyPrice = 0.0, stock = 0)
                }
            }
        }
    }

    fun logSale(item: ItemStack, player: Player, price: Double, amount: Int, purchaseType: String) {
        val id = Utils().getID(item)
        val sql = "INSERT INTO purchases (user, username, itemType, itemId, price, amount, purchaseType) VALUES (?, ?, ?, ?, ?, ?, ?)"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, player.uniqueId.toString())
            statement.setString(2, player.playerListName)
            statement.setString(3, item.type.toString())
            statement.setInt(4, id)
            statement.setDouble(5, price)
            statement.setInt(6, amount)
            statement.setString(7, purchaseType)
            statement.executeUpdate()
        }
    }
}