package com.cosmicreach.redcorp.db

import com.cosmicreach.redcorp.db.Delivery.DeliveryInfo
import java.sql.Connection
import java.sql.Timestamp
import java.time.Instant
import java.util.*

class Greenhouse(private var connection: Connection) {
    data class GreenhouseInfo(
        val id: Int,
        val home: String,
        val visit: String,
        val exitl: String
    )

    data class VisitCheck(
        val canVisit: Boolean,
        val nextAllowedAt: Instant?
    )

    fun createGreenhouse(home: String, visit: String, exitl: String): Int {
        val sql = "INSERT INTO greenhouse (home, visit, exitl) VALUES (?, ?, ?)"
        connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS).use { stmt ->
            stmt.setString(1, home)
            stmt.setString(2, visit)
            stmt.setString(3, exitl)
            stmt.executeUpdate()

            stmt.generatedKeys.use { rs ->
                if (rs.next()) {
                    return rs.getInt(1)
                } else {
                    throw IllegalStateException("Failed to retrieve generated greenhouse ID.")
                }
            }
        }
    }

    fun getGreenhouseById(id: Int): GreenhouseInfo? {
        val sql = "SELECT id, home, visit, exitl FROM greenhouse WHERE id = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, id)
            stmt.executeQuery().use { rs ->
                return if (rs.next()) {
                    GreenhouseInfo(
                        id = rs.getInt("id"),
                        home = rs.getString("home"),
                        visit = rs.getString("visit"),
                        exitl = rs.getString("exitl")
                    )
                } else {
                    null
                }
            }
        }
    }

    fun updateGreenhouse(id: Int, home: String, visit: String, exitl: String): Boolean {
        val sql = "UPDATE greenhouse SET home = ?, visit = ?, exitl = ? WHERE id = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, home)
            stmt.setString(2, visit)
            stmt.setString(3, exitl)
            stmt.setInt(4, id)
            return stmt.executeUpdate() > 0
        }
    }

    fun updateHome(id: Int, home: String): Boolean {
        val sql = "UPDATE greenhouse SET home = ? WHERE id = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, home)
            stmt.setInt(2, id)
            return stmt.executeUpdate() > 0
        }
    }

    fun updateExit(id: Int, exit: String): Boolean {
        val sql = "UPDATE greenhouse SET exitl = ? WHERE id = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, exit)
            stmt.setInt(2, id)
            return stmt.executeUpdate() > 0
        }
    }

    fun updateVisit(id: Int, visit: String): Boolean {
        val sql = "UPDATE greenhouse SET visit = ? WHERE id = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, visit)
            stmt.setInt(2, id)
            return stmt.executeUpdate() > 0
        }
    }

    fun linkPlayerToGreenhouse(greenhouseId: Int, playerUUID: UUID): Boolean {
        val sql = """
            INSERT INTO greenhouse_users (greenhouse_id, user)
            VALUES (?, ?)
        """.trimIndent()
        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, greenhouseId)
            stmt.setString(2, playerUUID.toString())
            return stmt.executeUpdate() > 0
        }
    }

    fun unlinkPlayerFromGreenhouse(greenhouseId: Int, playerUUID: UUID): Boolean {
        val sql = "DELETE FROM greenhouse_users WHERE greenhouse_id = ? AND user = ?"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, greenhouseId)
            stmt.setString(2, playerUUID.toString())
            return stmt.executeUpdate() > 0
        }
    }

    fun isPlayerLinked(greenhouseId: Int, playerUUID: UUID): Boolean {
        val sql = """
        SELECT 1
        FROM greenhouse_users
        WHERE greenhouse_id = ?
          AND user = ?
        LIMIT 1
    """.trimIndent()

        connection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, greenhouseId)
            stmt.setString(2, playerUUID.toString())
            stmt.executeQuery().use { rs ->
                return rs.next() // true if a row exists
            }
        }
    }

    fun getGreenhousesForPlayer(playerUUID: UUID): GreenhouseInfo? {
        val sql = """
            SELECT g.id, g.home, g.visit, g.exitl
            FROM greenhouse g
            JOIN greenhouse_users gu ON gu.greenhouse_id = g.id
            WHERE gu.user = ?
        """.trimIndent()

        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, playerUUID.toString())
            stmt.executeQuery().use { rs ->
                return if (rs.next()) {
                    GreenhouseInfo(
                        id = rs.getInt("id"),
                        home = rs.getString("home"),
                        visit = rs.getString("visit"),
                        exitl = rs.getString("exitl")
                    )
                } else {
                    return null
                }
            }
        }
    }

    fun addVisit(playerUUID: UUID, greenhouseId: Int) {
        val sql = "INSERT INTO greenhouse_log (user, greenhouse_id) VALUES (?, ?)"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.setString(2, (greenhouseId).toString())
            statement.executeUpdate()
        }
    }

    fun checkVisit(playerUUID: UUID, greenhouseId: Int): VisitCheck? {
        val sql = "SELECT entry_time, died FROM greenhouse_log WHERE user = ? AND finished = true AND greenhouse_id = ? ORDER BY entry_time DESC LIMIT 1"
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, playerUUID.toString())
            statement.setInt(2, greenhouseId)
            statement.executeQuery().use { resultSet ->
                if (!resultSet.next()) {
                    return VisitCheck(canVisit = true, nextAllowedAt = null)
                }

                val entryTime: Timestamp? = resultSet.getTimestamp("entry_time")
                val died: Boolean = resultSet.getBoolean("died")
                if (!died) {
                    return VisitCheck(canVisit = true, nextAllowedAt = null)
                }

                val lastDeath = entryTime!!.toInstant()
                val now = Instant.now()
                val twelveHoursMillis = 12L * 60L * 60L * 1000L
                val diff = now.toEpochMilli() - lastDeath.toEpochMilli()

                return if (diff >= twelveHoursMillis) {
                    VisitCheck(canVisit = true, nextAllowedAt = null)
                } else {
                    val nextAllowed = lastDeath.plusSeconds(12L * 60L * 60L)
                    VisitCheck(canVisit = false, nextAllowedAt = nextAllowed)
                }
            }
        }
    }

    fun setFinished(playerUUID: UUID, died: Boolean): Boolean {
        val sql = "UPDATE greenhouse_log SET finished = true, died = ? WHERE user = ? AND finished = false"
        connection.prepareStatement(sql).use { stmt ->
            stmt.setString(2, playerUUID.toString())
            stmt.setBoolean(1, died)
            return stmt.executeUpdate() > 0
        }
    }

}