package com.cosmicreach.redcorp.db

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
}