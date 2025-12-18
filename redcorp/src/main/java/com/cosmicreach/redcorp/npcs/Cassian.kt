package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.CoffeeShop
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Cassian(private val player: Player) {
    fun run () {
        player.sendMessage("§aCassian §8|§r What can I get for you Java the hutt?")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§a§lCassian's Barista")
            .setGui(CoffeeShop().makeGUI(player))
            .build()

        window.open()
        return
    }
}