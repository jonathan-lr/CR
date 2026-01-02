package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.db.Greenhouse
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.meta.ItemMeta
import java.lang.Integer.parseInt

class OnDeath (private val event : PlayerDeathEvent) {

    fun run(ids: Array<Int>, items: Array<String>) {

        if (event.entity.location.world?.name == "greenhouse") {
            val connection = RedCorp.getPlugin().getConnection()!!
            Greenhouse(connection).setFinished(event.entity.uniqueId, true)
            event.entity.sendMessage("§cCR §8|§r You have died in a raid and are locked out for 12 hours")
        }

        event.drops.forEach {
            if (it.hasItemMeta()) {
                val meta = it.itemMeta as ItemMeta

                if(Utils().checkID(it, ids)) {
                    val index = ids.indexOf(Utils().getID(it))
                    val type = (Utils().getType(it))
                    Bukkit.broadcastMessage("§cCR §8| ${event.entity.displayName} §rlost a relic §6${meta.displayName}")
                    it.amount = 0
                    var configItem = parseInt(RedCorp.getPlugin().config.getString("configuration.${type}.${items[index]}"))
                    configItem += 1
                    RedCorp.getPlugin().config.set("configuration.${type}.${items[index]}", configItem)
                    RedCorp.getPlugin().saveConfig()
                    Bukkit.broadcastMessage("§cCR §8|§r 1 ${meta.displayName} §rhas been returned to the vault")
                }
            }
        }
        return
    }
}