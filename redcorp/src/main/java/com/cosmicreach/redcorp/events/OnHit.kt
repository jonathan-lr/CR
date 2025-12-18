package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.TagItems
import com.cosmicreach.redcorp.utils.DrugTest
import com.cosmicreach.redcorp.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

class OnHit (private val event : EntityDamageByEntityEvent) {

    fun run() {
        if (event.entity is Player) {
            if (event.damager is Player) {
                val tagger = event.damager as Player
                val taggie = event.entity as Player
                val item = tagger.inventory.itemInMainHand

                if (Utils().checkID(item, arrayOf(69))) {
                    val gameId = Utils().getGameID(item)
                    val current = RedCorp.getPlugin().getTaggedPlayers()[gameId]
                    val last = RedCorp.getPlugin().getLastTagged()[gameId]
                    var passed = RedCorp.getPlugin().getPassedTimes()[gameId]

                    if (tagger != current) {
                        tagger.sendMessage("§cCR §8|§r Well well well, what do we have here? ${tagger.displayName} you shouldn't have that.")
                        tagger.inventory.itemInMainHand.amount = -1
                        Bukkit.broadcastMessage("§cCR §8|§r ${tagger.displayName} has ended game $gameId of tag due to malpractice!")
                        return
                    }

                    if (taggie == last) {
                        tagger.sendMessage("§cCR §8|§r How about we give them a break, they were the last tagger.")
                        return
                    } else {
                        Bukkit.broadcastMessage("§cCR §8|§r ${(event.damager as Player).displayName} has tagged ${(event.entity as Player).displayName}!")
                        RedCorp.getPlugin().getTaggedPlayers()[gameId] = taggie
                        RedCorp.getPlugin().getLastTagged()[gameId] = tagger
                        if (passed != null) {
                            RedCorp.getPlugin().getPassedTimes()[gameId] = passed + 1
                        }

                        tagger.inventory.itemInMainHand.amount = -1
                        if (passed != null) {
                            taggie.inventory.addItem(TagItems().tagStick(taggie.name, tagger.name, passed + 1, gameId))
                        }
                    }
                }

                if (Utils().checkID(item, arrayOf(402))) {
                    val econ = RedCorp.getPlugin().getEcon()
                    if (DrugTest().doTest(taggie)) {
                        DrugTest().arrestPlayer(taggie)
                        taggie.sendMessage("§cCR §8|§r ${tagger.displayName}§r has arrested you!")
                        tagger.sendMessage("§cCR §8|§r You have been awarded ${econ!!.format(100.0)} for a job well done!")
                        econ.depositPlayer(tagger, 100.0)
                    } else {
                        taggie.sendMessage("§cCR §8|§r ${tagger.displayName}§r tried to arrest you but you had nothing illegal on you they will be punished!")
                        tagger.sendMessage("§cCR §8|§r You have been fined ${econ!!.format(25.0)} for pestering a civilian!")
                    }
                }
            }
        }
        return
    }
}