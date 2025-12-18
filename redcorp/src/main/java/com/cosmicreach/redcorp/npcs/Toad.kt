package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.MushroomStore
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Toad(private val player: Player) {
    fun run () {
        player.sendMessage("§5Toad §8|§r Waaahhooooo you got some mushrooms for me")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§5§lToad's Psilocybin Emporium")
            .setGui(MushroomStore().makeGUI(player))
            .build()

        window.open()
        return
    }
}