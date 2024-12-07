package com.cosmicreach.redcorp.menus.items

import org.bukkit.Material
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.PageItem

class BackItem : PageItem(false) {

    override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
        val builder = ItemBuilder(Material.RED_STAINED_GLASS_PANE)
        builder.setDisplayName("§7Previous page")
                .addLoreLines(
                        if (gui.hasPreviousPage())
                            "§7Go to page §e" + gui.currentPage + "§7/§e" + gui.pageAmount
                        else "§cYou can't go further back"
                )
        return builder
    }

}