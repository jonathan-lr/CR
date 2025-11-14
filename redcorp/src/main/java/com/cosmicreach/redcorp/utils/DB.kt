package com.yourplugin

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.logging.Logger

class DatabaseManager(
    private val url: String,
    private val user: String,
    private val password: String,
    private val logger: Logger
) {
    private var connection: Connection? = null

    fun connect() {
        try {
            connection = DriverManager.getConnection(url, user, password)
            logger.info("Successfully connected to the database.")
        } catch (ex: SQLException) {
            ex.printStackTrace()
            logger.info("Failed to connect to the database: ${ex.message}")
        }
    }

    fun disconnect() {
        try {
            connection?.close()
            logger.info("Database connection closed.")
        } catch (ex: SQLException) {
            ex.printStackTrace()
            logger.info("Failed to close the database connection: ${ex.message}")
        }
    }

    fun getConnection(): Connection? {
        if (connection == null || !isConnectionValid()) {
            println("Re-establishing the database connection...")
            connect()
        }
        return connection
    }

    private fun isConnectionValid(): Boolean {
        return try {
            connection?.isValid(2) == true // Timeout of 2 seconds
        } catch (e: SQLException) {
            false
        }
    }
}
