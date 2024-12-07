package com.cosmicreach.redcorp.menus.items

import org.bukkit.Material

import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.controlitem.PageItem

class ForwardItem : PageItem(true) {

    override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
        val builder = ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
        builder.setDisplayName("§7Next page")
                .addLoreLines(
                        if (gui.hasNextPage())
                            "§7Go to page §e" + (gui.currentPage + 2) + "§7/§e" + gui.pageAmount
                        else "§cThere are no more pages"
                )
        return builder
    }

}