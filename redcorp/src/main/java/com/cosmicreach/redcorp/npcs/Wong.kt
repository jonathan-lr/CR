package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.OpiumStore
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Wong (private val player: Player) {
    fun run () {
        player.sendMessage("§fWong §8|§r You like the name? I took it from, uh, my favorite historical character and my second-favorite Szechuan restaurant in Brooklyn. Now tell me what you want, before I gut ya like a pig and feed you to the skin louse!")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§f§lWong's Noodle Business")
            .setGui(OpiumStore().makeGUI(player))
            .build()

        window.open()
        return
    }
}