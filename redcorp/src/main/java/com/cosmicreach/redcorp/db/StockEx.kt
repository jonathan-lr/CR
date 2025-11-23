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

    fun setStock(stock: Int, item: String) {
        val sql = "UPDATE prices SET stock = ? WHERE item = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setInt(1, stock)
            statement.setString(2, item)
            val rowsAffected = statement.executeUpdate()
            rowsAffected > 0 // Return true if the update was successful
        }
    }

    fun decayAllStocksTowardsZero(step: Int): Int {
        // step is how much stock moves towards 0 per hour (from config)
        val sql = """
        UPDATE prices
        SET stock = CASE
            WHEN stock > 0 THEN 
                CASE 
                    WHEN stock - ? > 0 THEN stock - ?
                    ELSE 0
                END
            WHEN stock < 0 THEN 
                CASE
                    WHEN stock + ? < 0 THEN stock + ?
                    ELSE 0
                END
            ELSE stock
        END
    """.trimIndent()

        connection.prepareStatement(sql).use { statement ->
            // positive side
            statement.setInt(1, step)
            statement.setInt(2, step)
            // negative side
            statement.setInt(3, step)
            statement.setInt(4, step)

            val rowsAffected = statement.executeUpdate()
            return rowsAffected
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