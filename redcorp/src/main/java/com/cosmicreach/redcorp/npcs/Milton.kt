package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.DungeonToken
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Milton (private val player: Player) {
    fun run () {
        player.sendMessage("§9Milton §8|§r Iv got some dungeon tokens for ya")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§6§lDungeon Token Exchange")
            .setGui(DungeonToken().makeGUI(player))
            .build()

        window.open()
        return
    }
}