package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.FurnitureStore
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Oakley(private val player: Player) {
    fun run () {
        player.sendMessage("§2Oakley §8|§r Welcome to my goods emporium?")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§2§lOakley's Furniture Store")
            .setGui(FurnitureStore().makeGUI(player))
            .build()

        window.open()
        return
    }
}