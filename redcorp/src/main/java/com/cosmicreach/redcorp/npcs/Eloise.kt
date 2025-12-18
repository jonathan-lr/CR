package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.RewardExchange
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Eloise (private val player: Player) {
    fun run () {
        player.sendMessage("§aEloise §8|§r I could take those pesky tokens off your hand for a price ;)")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§6§lDungeon Reward Exchange")
            .setGui(RewardExchange().makeGUI(player))
            .build()

        window.open()
        return
    }
}