package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.DiamondExchange
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Sterling (private val player: Player) {
    fun run () {
        player.sendMessage("§6Sterling §8|§r Welcome to the Diamond Exchange brother!")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§3§lDiamond Exchange")
            .setGui(DiamondExchange( 2).makeGUI(player))
            .build()

        window.open()
        return
    }
}