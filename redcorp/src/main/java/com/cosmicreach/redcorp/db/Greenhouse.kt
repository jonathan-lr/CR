package com.cosmicreach.redcorp.db

import java.sql.Connection
import java.util.*

class Greenhouse(private var connection: Connection) {
    data class GreenhouseInfo(val home: String, val visit: String, val exitl: String)

    fun getGreenhouse(playerUUID: UUID): GreenhouseInfo? {
        val sql = "SELECT home, visit, exitl FROM greenhouse WHERE user = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    GreenhouseInfo(
                        home = resultSet.getString("home").takeIf { !resultSet.wasNull() } ?: "",
                        visit = resultSet.getString("visit").takeIf { !resultSet.wasNull() } ?: "",
                        exitl = resultSet.getString("exitl").takeIf { !resultSet.wasNull() } ?: ""
                    )
                } else {
                    null
                }
            }
        }
    }

    fun addGreenhouse(playerUUID: UUID, home: String, visit: String, exitl: String) {
        val sql = "INSERT INTO greenhouse (user, home, visit, exitl) VALUES (?, ?, ?, ?)"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.setString(2, home)
            statement.setString(3, visit)
            statement.setString(4, exitl)
            statement.executeUpdate()
        }
    }

    fun updateHome(playerUUID: UUID, home: String): Boolean {
        val sql = "UPDATE greenhouse SET home = ? WHERE user = ?"
        return try {
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, home) // Set the new points value
                statement.setString(2, playerUUID.toString()) // Identify the player to update
                val rowsAffected = statement.executeUpdate()
                rowsAffected > 0 // Return true if the update was successful
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            false // Return false if there was an error
        }
    }

    fun updateVisit(playerUUID: UUID, visit: String): Boolean {
        val sql = "UPDATE greenhouse SET visit = ? WHERE user = ?"
        return try {
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, visit) // Set the new points value
                statement.setString(2, playerUUID.toString()) // Identify the player to update
                val rowsAffected = statement.executeUpdate()
                rowsAffected > 0 // Return true if the update was successful
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            false // Return false if there was an error
        }
    }

    fun updateExit(playerUUID: UUID, exitl: String): Boolean {
        val sql = "UPDATE greenhouse SET exitl = ? WHERE user = ?"
        return try {
            connection.prepareStatement(sql).use { statement ->
                statement.setString(1, exitl) // Set the new points value
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