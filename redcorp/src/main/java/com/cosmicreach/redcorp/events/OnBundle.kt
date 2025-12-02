package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.utils.DrugTest
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BundleMeta

class OnBundle(private val p: Player, private val event: InventoryClickEvent) {
    private fun isDrug(item: ItemStack?): Boolean {
        if (item == null || item.type.isAir) return false
        // Use your own logic here
        return DrugTest().itemTest(item) // or whatever you already use per-item
    }

    fun run() {
        val current = event.currentItem   // slot being clicked
        val cursor  = event.cursor        // item on mouse

        // --- 1) Direct bundle <-> drug interactions ---

        // Cursor is drug, clicking on a bundle
        if (isDrug(cursor) && current?.type == Material.BUNDLE) {
            event.isCancelled = true
            p.updateInventory()
            p.sendMessage("§cYou cannot store drugs in bundles.")
            return
        }

        // Cursor is bundle, clicking on a drug stack
        if (cursor?.type == Material.BUNDLE && isDrug(current)) {
            event.isCancelled = true
            p.updateInventory()
            p.sendMessage("§cYou cannot store drugs in bundles.")
            return
        }

        // --- 2) Shift-clicking a drug (could auto-merge into a bundle) ---

        if (event.click.isShiftClick && isDrug(current)) {
            // If the player has any bundle in their own inventory, block the shift-click
            val bottomInv = event.view.bottomInventory
            if (bottomInv.contents.any { it?.type == Material.BUNDLE }) {
                event.isCancelled = true
                p.updateInventory()
                p.sendMessage("§cYou cannot store drugs in bundles.")
                return
            }
        }

        // --- 3) Optional: clean bundles that already contain drugs ---

        // If the player clicks a bundle, strip any drug items out of it
        if (current?.type == Material.BUNDLE && current.itemMeta is BundleMeta) {
            val meta = current.itemMeta as BundleMeta
            val before = meta.items.size
            val cleaned = meta.items.filterNot { isDrug(it) }

            if (cleaned.size != before) {
                meta.items.clear()
                cleaned.forEach { meta.addItem(it) }

                current.itemMeta = meta
                p.updateInventory()
                p.sendMessage("§cDrugs were removed from that bundle.")
            }
        }


        // Same if the cursor is a bundle
        if (cursor?.type == Material.BUNDLE && cursor.itemMeta is BundleMeta) {
            val meta = cursor.itemMeta as BundleMeta
            val before = meta.items.size
            val cleaned = meta.items.filterNot { isDrug(it) }

            if (cleaned.size != before) {
                // Must clear and re-add, because meta.items is read-only
                meta.items.clear()
                cleaned.forEach { meta.addItem(it) }

                cursor.itemMeta = meta
                p.updateInventory()
                p.sendMessage("§cDrugs were removed from that bundle.")
            }
        }
    }
}