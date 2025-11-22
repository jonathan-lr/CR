package com.cosmicreach.redcorp.db

import java.sql.Connection
import java.util.*

class Delivery(private var connection: Connection) {
    data class DeliveryInfo(val price: Double, val amount: Int, val location: Int, val drugType: Int)

    fun getDelivery(playerUUID: UUID): DeliveryInfo? {
        val sql = "SELECT price, amount, location, drugtype FROM deliveries WHERE user = ? AND finished = false"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    DeliveryInfo(
                        price = resultSet.getDouble("price").takeIf { !resultSet.wasNull() } ?: 0.0,
                        amount = resultSet.getInt("amount").takeIf { !resultSet.wasNull() } ?: 1,
                        location = resultSet.getInt("location").takeIf { !resultSet.wasNull() } ?: 1,
                        drugType = resultSet.getInt("drugtype").takeIf { !resultSet.wasNull() } ?: 1
                    )
                } else {
                    null
                }
            }
        }
    }

    fun addDelivery(playerUUID: UUID, price: Double, amount: Int, location: Int, drugType: Int) {
        val sql = "INSERT INTO deliveries (user, price, amount, location, drugtype) VALUES (?, ?, ?, ?, ?)"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.setString(2, (price).toString())
            statement.setString(3, (amount).toString())
            statement.setString(4, (location).toString())
            statement.setString(5, (drugType).toString())
            statement.executeUpdate()
        }
    }

    fun finishDelivery(playerUUID: UUID): Boolean {
        val sql = "UPDATE deliveries SET finished = true WHERE user = ? AND finished = false"
        return try {
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, playerUUID.toString()) // Identify the player to update
                val rowsAffected = statement.executeUpdate()
                rowsAffected > 0 // Return true if the update was successful
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            false // Return false if there was an error
        }
    }

}