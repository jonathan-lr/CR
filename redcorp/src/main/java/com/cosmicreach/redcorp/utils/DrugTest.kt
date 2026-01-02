package com.cosmicreach.redcorp.utils

import com.cosmicreach.redcorp.RedCorp
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BundleMeta
import xyz.xenondevs.invui.inventory.get
import java.util.concurrent.ThreadLocalRandom

class DrugTest {
    private var illegalDrugs = mutableListOf(421, 422, 423, 424, 431, 432, 433, 434, 435, 441, 442, 443, 444, 450, 451)
    private var illegalDrugsPlus = mutableListOf(420, 421, 422, 423, 424, 430, 431, 432, 433, 434, 435, 440, 441, 442, 443, 444, 450, 451)
    val allBundles = listOf(
        Material.BUNDLE, // Add here any specific colored bundle materials if applicable
        Material.RED_BUNDLE,
        Material.BLUE_BUNDLE,
        Material.GREEN_BUNDLE,
        Material.YELLOW_BUNDLE,
        Material.PURPLE_BUNDLE,
        Material.BLACK_BUNDLE,
        Material.BROWN_BUNDLE,
        Material.CYAN_BUNDLE,
        Material.GRAY_BUNDLE,
        Material.LIGHT_BLUE_BUNDLE,
        Material.LIGHT_GRAY_BUNDLE,
        Material.LIME_BUNDLE,
        Material.MAGENTA_BUNDLE,
        Material.ORANGE_BUNDLE,
        Material.PINK_BUNDLE,
        Material.WHITE_BUNDLE
        // Add other colored bundle types as needed
    )

    fun itemTest(item: ItemStack): Boolean {
        return illegalDrugs.contains(Utils().getID(item))
    }

    fun doTest(p: Player): Boolean {
        var hasDrugs = false
        val grinders = RedCorp.getPlugin().grinderPlayers

        p.inventory.forEach {
            if (it != null) {
                if (illegalDrugs.contains(Utils().getID(it))) {
                    hasDrugs = true
                }
                if(allBundles.contains(it.type)) {
                    val meta = it.itemMeta as BundleMeta
                    meta.items.forEach { bundle ->
                        if (illegalDrugs.contains(Utils().getID(bundle))) {
                            hasDrugs = true
                        }
                    }
                }
            }
        }

        val grinder = grinders[p]
        if(grinder != null) {
            val inv = grinder[0]
            if (inv != null) {
                if (illegalDrugs.contains(Utils().getID(inv))) {
                    hasDrugs = true
                }
            }
        }

        if (illegalDrugs.contains(Utils().getID(p.inventory.itemInOffHand))) {
            hasDrugs = true
        }

        return hasDrugs
    }

    fun doWiderTest(p: Player): Boolean {
        var hasDrugs = false
        val grinders = RedCorp.getPlugin().grinderPlayers

        p.inventory.forEach {
            if (it != null) {
                if (illegalDrugsPlus.contains(Utils().getID(it))) {
                    hasDrugs = true
                }
                if(allBundles.contains(it.type)) {
                    val meta = it.itemMeta as BundleMeta
                    meta.items.forEach { bundle ->
                        if (illegalDrugsPlus.contains(Utils().getID(bundle))) {
                            hasDrugs = true
                        }
                    }
                }
            }
        }

        val grinder = grinders[p]
        if(grinder != null) {
            val inv = grinder[0]
            if (inv != null) {
                if (illegalDrugs.contains(Utils().getID(inv))) {
                    hasDrugs = true
                }
            }
        }

        if (illegalDrugsPlus.contains(Utils().getID(p.inventory.itemInOffHand))) {
            hasDrugs = true
        }

        return hasDrugs
    }

    fun arrestPlayer(p: Player) {
        val econ = RedCorp.getPlugin().getEcon()
        val grinders = RedCorp.getPlugin().grinderPlayers

        if (econ != null) {
            econ.withdrawPlayer(p, 2000.0)
            when (ThreadLocalRandom.current().nextInt(0, 3)) {
                0 -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail ${p.name} 1 2m")
                1 -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail ${p.name} 2 2m")
                2 -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail ${p.name} 3 2m")
            }

            p.inventory.forEach {
                if (it != null) {
                    if (illegalDrugs.contains(Utils().getID(it))) {
                        it.amount = 0
                    }
                    if(allBundles.contains(it.type)) {
                        val meta = it.itemMeta as BundleMeta
                        meta.items.forEach { bundle ->
                            if (illegalDrugs.contains(Utils().getID(bundle))) {
                                it.amount = 0
                            }
                        }
                        it.setItemMeta(meta)
                    }
                }
            }

            val grinder = grinders[p]
            if(grinder != null) {
                val inv = grinder[0]
                if (inv != null) {
                    if (illegalDrugs.contains(Utils().getID(inv))) {
                        grinders.remove(p)
                    }
                }
            }

            if (illegalDrugsPlus.contains(Utils().getID(p.inventory.itemInOffHand))) {
                p.inventory.itemInOffHand.amount = 0
            }

            Utils().setScore(p, "has_drugs", 0)
        }
    }
}