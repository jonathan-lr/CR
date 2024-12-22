package com.cosmicreach.redcorp.db

import java.sql.Connection
import java.util.*

class Magic(private var connection: Connection) {
    fun getPlayer(playerUUID: UUID): Boolean? {
        val sql = "SELECT hasMagic FROM magic WHERE user = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    resultSet.getBoolean("hasMagic")
                } else {
                    null
                }
            }
        }
    }

    fun getPlayerStage(playerUUID: UUID): Int? {
        val sql = "SELECT stage FROM magic WHERE user = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    resultSet.getInt("stage")
                } else {
                    null
                }
            }
        }
    }

    fun addPlayer(playerUUID: UUID) {
        val sql = "INSERT INTO magic (user) VALUES (?)"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.executeUpdate()
        }
    }

    fun updatePlayerMagic(playerUUID: UUID, hasMagic: Boolean): Boolean {
        val sql = "UPDATE magic SET hasMagic = ? WHERE user = ?"
        return try {
            connection.prepareStatement(sql).use { statement ->
                statement.setBoolean(1, hasMagic) // Set the new points value
                statement.setString(2, playerUUID.toString()) // Identify the player to update
                val rowsAffected = statement.executeUpdate()
                rowsAffected > 0 // Return true if the update was successful
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            false // Return false if there was an error
        }
    }

    fun updatePlayerStage(playerUUID: UUID, stage: Int): Boolean {
        val sql = "UPDATE magic SET stage = ? WHERE user = ?"
        return try {
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, stage) // Set the new points value
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