package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.WeedStore
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Shaggy (private val player: Player) {
    fun run () {
        player.sendMessage("§2Shaggy §8|§r Hey yooo you got something to sell me")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§2§lShaggy's Collection")
            .setGui(WeedStore().makeGUI(player))
            .build()

        window.open()
        return
    }
}