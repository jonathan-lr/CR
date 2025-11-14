package com.cosmicreach.redcorp.db

import java.sql.Connection
import java.util.*

class Delivery(private var connection: Connection) {
    data class DeliveryInfo(val price: Double, val amount: Int, val location: Int)

    fun getDelivery(playerUUID: UUID): DeliveryInfo? {
        val sql = "SELECT price, amount, location FROM deliveries WHERE user = ? AND finished = false"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    DeliveryInfo(
                        price = resultSet.getDouble("price").takeIf { !resultSet.wasNull() } ?: 0.0,
                        amount = resultSet.getInt("amount").takeIf { !resultSet.wasNull() } ?: 1,
                        location = resultSet.getInt("location").takeIf { !resultSet.wasNull() } ?: 1
                    )
                } else {
                    null
                }
            }
        }
    }

    fun addDelivery(playerUUID: UUID) {
        val sql = "INSERT INTO deliveries (user, price, amount, location) VALUES (?, ?, ?, ?)"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.setString(2, (20.0).toString())
            statement.setString(3, (64).toString())
            statement.setString(4, (1).toString())
            statement.executeUpdate()
        }
    }

    fun finishDelivery(playerUUID: UUID): Boolean {
        val sql = "UPDATE magic SET hasMagic = ? WHERE user = ? AND finished = false"
        return try {
            connection.prepareStatement(sql).use { statement ->
                statement.setBoolean(1, true) // Set the new points value
                statement.setString(2, playerUUID.toString()) // Identify the player to update
                val rowsAffected = statement.executeUpdate()
                rowsAffected > 0 // Return true if the update was successful
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            false // Return false if there was an error
        }
    }

}