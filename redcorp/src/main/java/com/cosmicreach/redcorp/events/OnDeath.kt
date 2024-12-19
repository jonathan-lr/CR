package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.meta.ItemMeta
import java.lang.Integer.parseInt

class OnDeath (private val event : PlayerDeathEvent) {

    fun run(ids: Array<Int>, types: Array<String>, items: Array<String>) {
        event.drops.forEach {
            if (it.hasItemMeta()) {
                val meta = it.itemMeta as ItemMeta

                it.setItemMeta((meta))

                if(Utils().checkID(it, ids)) {
                    val index = ids.indexOf(Utils().getID(it))
                    Bukkit.broadcastMessage("§cCR §8| ${event.entity.displayName} §rlost a relic §6${meta.displayName}")
                    it.amount = -1
                    var configItem = parseInt(RedCorp.getPlugin().config.getString("configuration.${types[meta.customModelData-1]}.${items[index]}"))
                    configItem += 1
                    RedCorp.getPlugin().config.set("configuration.${types[meta.customModelData-1]}.${items[index]}", configItem)
                    RedCorp.getPlugin().saveConfig()
                    Bukkit.broadcastMessage("§cCR §8|§r 1 ${meta.displayName} §rhas been returned to the vault")
                }
            }
        }
        return
    }
}