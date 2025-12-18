package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.AlcoholStore
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class Charlie (private val player: Player) {
    fun run () {
        player.sendMessage("§6Charlie §8|§r This company is being bled like a stuffed pig Mac, and I got a paper trail to prove it. Check this out, take a look at this.")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§6§lCharlie's Alcohol Intake")
            .setGui(AlcoholStore().makeGUI(player))
            .build()

        window.open()
        return
    }
}