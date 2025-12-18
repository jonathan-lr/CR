package com.cosmicreach.redcorp.events


import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.npcs.Cassian
import com.cosmicreach.redcorp.npcs.Charlie
import com.cosmicreach.redcorp.npcs.Citizen
import com.cosmicreach.redcorp.npcs.Eloise
import com.cosmicreach.redcorp.npcs.Ivan
import com.cosmicreach.redcorp.npcs.Jeramey
import com.cosmicreach.redcorp.npcs.Kyle
import com.cosmicreach.redcorp.npcs.Merlin
import com.cosmicreach.redcorp.npcs.Milton
import com.cosmicreach.redcorp.npcs.Oakley
import com.cosmicreach.redcorp.npcs.Patrick
import com.cosmicreach.redcorp.npcs.ShadeE
import com.cosmicreach.redcorp.npcs.Shaggy
import com.cosmicreach.redcorp.npcs.Sterling
import com.cosmicreach.redcorp.npcs.Toad
import com.cosmicreach.redcorp.npcs.Wong
import com.cosmicreach.redcorp.npcs.Zarek
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.persistence.PersistentDataType

class OnNpc (private val e : PlayerInteractAtEntityEvent) {
    private var kyleStage = HashMap<Player, Int>()
    private val kyleConfirm = HashMap<Player, Boolean>()
    private val merlinConfirm = HashMap<Player, Boolean>()
    private val zarekConfirm = HashMap<Player, Boolean>()
    private var zarekStage = HashMap<Player, Int>()

    fun run() {
        if (e.hand != org.bukkit.inventory.EquipmentSlot.HAND) return
        val entity = e.rightClicked
        if (entity.type != EntityType.MANNEQUIN) return

        val key = NamespacedKey(RedCorp.getPlugin(), "npc_name")
        val pdc = entity.persistentDataContainer

        val id = pdc.get(key, PersistentDataType.STRING)
        val p = e.player

        if (id != null) {
            when (id) {
                "ivan" -> {
                    Ivan(p).run()
                }
                "kyle" -> {
                    Kyle(p, kyleConfirm, kyleStage).run()
                }
                "jeramey" -> {
                    Jeramey(p).run()
                }
                "patrick" -> {
                    Patrick(p).run()
                }
                "sterling" -> {
                    Sterling(p).run()
                }
                "milton" -> {
                    Milton(p).run()
                }
                "eloise" -> {
                    Eloise(p).run()
                }
                "merlin" -> {
                    Merlin(p).run()
                }
                "zarek" -> {
                    Zarek(p, zarekConfirm, zarekStage).run()
                }
                "shadee" -> {
                    ShadeE(p).run()
                }
                "oakley" -> {
                    Oakley(p).run()
                }
                "shaggy" -> {
                    Shaggy(p).run()
                }
                "toad" -> {
                    Toad(p).run()
                }
                "charlie" -> {
                    Charlie(p).run()
                }
                "wong" -> {
                    Wong(p).run()
                }
                "cassian" -> {
                    Cassian(p).run()
                }
                in (1..10).map { "citizen_$it" } -> {
                    val npc = id.removePrefix("citizen_").toInt()
                    Citizen(p, npc).run()
                }
            }
        } else {
            Bukkit.broadcastMessage("§cCR §8|§r This mannequin has no ID stored.")
        }
        return
    }
}