package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.menus.CokeStore
import com.cosmicreach.redcorp.menus.DiamondExchange
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.entity.Player
import xyz.xenondevs.invui.window.Window
import java.util.concurrent.ThreadLocalRandom

class Patrick(private val player: Player) {
    fun run () {
        var hasCoke = false
        player.inventory.forEach {
            if (it != null) {
                if(Utils().checkID(it, arrayOf(435))) {
                    hasCoke = true
                }
            }
        }
        if (hasCoke) {
            player.sendMessage("§cPatrick Byattyman §8|§r Oooohhh is that for me?")
            val window = Window.single()
                .setViewer(player)
                .setTitle("§c§lPatrick's Prison Wallet")
                .setGui(CokeStore().makeGUI(player))
                .build()

            window.open()
            return
        } else {
            when (ThreadLocalRandom.current().nextInt(0, 4)) {
                0 -> player.sendMessage("§cPatrick Byattyman §8|§r Yeah ill trade some Diamonds.")
                1 -> player.sendMessage("§cPatrick Byattyman §8|§r Got anything other than the usual shiny gems?")
                2 -> player.sendMessage("§cPatrick Byattyman §8|§r Man I could do with some Devils Dandruff!")
                3 -> player.sendMessage("§cPatrick Byattyman §8|§r Hey ${player.displayName}, do you wanna build a snowman?")
            }
            val window = Window.single()
                .setViewer(player)
                .setTitle("§3§lDiamond Exchange")
                .setGui(DiamondExchange( 1).makeGUI(player))
                .build()

            window.open()
            return
        }
    }
}