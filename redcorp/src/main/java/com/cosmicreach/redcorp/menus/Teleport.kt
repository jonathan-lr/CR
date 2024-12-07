package com.cosmicreach.redcorp.menus

import com.cosmicreach.redcorp.menus.items.BackItem
import com.cosmicreach.redcorp.menus.items.ForwardItem
import com.cosmicreach.redcorp.menus.items.PlayerItem
import com.cosmicreach.redcorp.utils.TeleportActions
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.gui.structure.Markers
import xyz.xenondevs.invui.item.Item
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem

class Teleport(private val teleportActions: TeleportActions) {
    fun makeGUI(p: Player): Gui {
        val available = teleportActions.available(p)
        val items = mutableListOf<PlayerItem>()
        // an example list of items to display
        available.forEach { list ->
            items.add(PlayerItem(p, list, teleportActions))
        }

        val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))
        val gui = PagedGui.items() // Creates the GuiBuilder for a normal GUI
                .setStructure(
                        "# . . . . . . . #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# < . . . . . > #")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL) // where paged items should be put
                .addIngredient('#', border)
                .addIngredient('<', BackItem())
                .addIngredient('>', ForwardItem())
                .setContent(items as List<Item>)
                .build()
        return gui
    }
}