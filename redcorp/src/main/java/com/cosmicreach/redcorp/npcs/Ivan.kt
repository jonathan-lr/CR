package com.cosmicreach.redcorp.npcs

import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.concurrent.ThreadLocalRandom

class Ivan (private val player: Player) {
    fun run () {
        val item = player.inventory.itemInMainHand
        if (!Utils().checkID(item, arrayOf(1))) {
            player.sendMessage(ivanResponse())
            return
        }

        player.sendMessage("§cIvan §8|§r I suppose for this I could lower the bridge")
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "opendoor 10")
        player.inventory.itemInMainHand.amount -= 1
        return
    }

    private fun ivanResponse(): String {
        when (ThreadLocalRandom.current().nextInt(0, 10)) {
            0 -> return "§cIvan §8|§r Welcome, comrade! You seek passage into the sewer? First, let's talk beets. They are the people's choice, after all."
            1 -> return "§cIvan §8|§r Ah, the troll toll? It's simple, just a few beets for the journey. Keeps the sewer strong, just like our unity."
            2 -> return "§cIvan §8|§r In the Soviet tradition, we believe in sharing. You buy my beets, and I let you in without hassle. Fair, da?"
            3 -> return "§cIvan §8|§r Remember, comrade, beets are the lifeblood of our revolution. Buy some, and you support the cause."
            4 -> return "§cIvan §8|§r Entering the sewer is easy, but only for those who appreciate the power of beets. Care to indulge in some beet-utiful trade?"
            5 -> return "§cIvan §8|§r No beets, no passage! That's the way of the sewer, and the way of the revolution."
            6 -> return "§cIvan §8|§r A wise choice, comrade! With my beets in your possession, your journey into the sewer will be smooth as the hammer and sickle."
            7 -> return "§cIvan §8|§r Entering the sewer of friendship and commerce, one beet at a time. Together, we shall prevail!"
            8 -> return "§cIvan §8|§r We may be on different sides of the grate, but we can all appreciate the earthy goodness of beets. Buy some, and let's bridge our differences."
            9 -> return "§cIvan §8|§r Ah, capitalism may crumble, but my beets stand strong. Care to invest in a taste of the future?"
        }
        return ""
    }
}