package com.cosmicreach.redcorp.db

import java.sql.Connection
import java.util.*

class Progress(private var connection: Connection) {
    fun getPlayer(playerUUID: UUID, type: Int): Boolean? {
        val sql = "SELECT completed FROM progress WHERE user = ? AND type = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.setInt(2, type)
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    resultSet.getBoolean("completed")
                } else {
                    null
                }
            }
        }
    }

    fun getStage(playerUUID: UUID, type: Int): Int? {
        val sql = "SELECT stage FROM progress WHERE user = ? AND type = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.setInt(2, type)
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    resultSet.getInt("stage")
                } else {
                    null
                }
            }
        }
    }


    fun getConfirm(playerUUID: UUID, type: Int): Boolean? {
        val sql = "SELECT confirm FROM progress WHERE user = ? AND type = ?"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.setInt(2, type)
            statement.executeQuery().use { resultSet ->
                return if (resultSet.next()) {
                    resultSet.getBoolean("confirm")
                } else {
                    false
                }
            }
        }
    }

    fun addPlayer(playerUUID: UUID, type: Int) {
        val sql = "INSERT INTO progress (user, type) VALUES (?, ?)"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.setInt(2, type)
            statement.executeUpdate()
        }
    }

    fun updateComplete(playerUUID: UUID, type: Int, completed: Boolean): Boolean {
        val sql = "UPDATE progress SET completed = ? WHERE user = ? AND type = ?"
        return try {
            connection.prepareStatement(sql).use { statement ->
                statement.setBoolean(1, completed)
                statement.setString(2, playerUUID.toString())
                statement.setInt(3, type)
                val rowsAffected = statement.executeUpdate()
                rowsAffected > 0
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    fun updateStage(playerUUID: UUID, type: Int, stage: Int): Boolean {
        val sql = "UPDATE progress SET stage = ? WHERE user = ? AND type = ?"
        return try {
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1, stage)
                statement.setString(2, playerUUID.toString())
                statement.setInt(3, type)
                val rowsAffected = statement.executeUpdate()
                rowsAffected > 0
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    fun updateConfirm(playerUUID: UUID, type: Int, confirm: Boolean): Boolean {
        val sql = "UPDATE progress SET confirm = ? WHERE user = ? AND type = ?"
        return try {
            connection.prepareStatement(sql).use { statement ->
                statement.setBoolean(1, confirm)
                statement.setString(2, playerUUID.toString())
                statement.setInt(3, type)
                val rowsAffected = statement.executeUpdate()
                rowsAffected > 0
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }
}