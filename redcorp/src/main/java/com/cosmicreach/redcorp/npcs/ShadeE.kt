package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.DrugStore
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window

class ShadeE (private val player: Player) {
    fun run () {
        player.sendMessage("§8Shade E §8|§r Looking for a lil something something?")
        val window = Window.single()
            .setViewer(player)
            .setTitle("§8§lShade E's Drug Store")
            .setGui(DrugStore().makeGUI(player))
            .build()

        window.open()
        return
    }
}