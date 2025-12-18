package com.cosmicreach.redcorp.events

import com.cosmicreach.redcorp.RedCorp
import io.papermc.paper.advancement.AdvancementDisplay
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.player.PlayerAdvancementDoneEvent

class OnAdvance(private val event : PlayerAdvancementDoneEvent) {

    fun run() {
        // Some "advancements" (like recipes) have no display info
        val display: AdvancementDisplay = event.advancement.display ?: return

        val world: World = Bukkit.getWorld("world") ?: return
        val wb = world.worldBorder

        var worldBorder = RedCorp.getPlugin().getWorldBorder()

        val forbidden = listOf(
            "Vanilla Tweaks",
            "Cauldron Concrete",
            "Custom Nether Portals",
            "Dragon Drops",
            "Double Shulker Shells",
            "More Mob Heads",
            "Anti Enderman Grief",
            "Player Head Drops",
            "Silence Mobs",
            "Mini Blocks",
            "Silk Touch Budding Amethyst",
            "Cauldron Mud",
            "Ender Chest Always Drops",
            "Painting Picker",
            "Fast Leaf Decay"
        )

        // Paper’s AdvancementDisplay#title() returns a Component – convert to plain text for matching
        val plainTitle = PlainTextComponentSerializer.plainText().serialize(display.title())

        if (forbidden.any { plainTitle.contains(it, ignoreCase = true) }) return

        // Decide expansion based on frame type (TASK / GOAL / CHALLENGE)
        val (expansion, frameColor) = when (display.frame()) {
            AdvancementDisplay.Frame.CHALLENGE -> 50.0 to NamedTextColor.DARK_PURPLE
            AdvancementDisplay.Frame.GOAL -> 20.0 to NamedTextColor.BLUE
            AdvancementDisplay.Frame.TASK -> 10.0 to NamedTextColor.GREEN
        }

        // Build Adventure Components for broadcast (Paper prefers Components over String)
        val prefix = Component.text("CR ", NamedTextColor.RED)
            .append(Component.text("| ", NamedTextColor.DARK_GRAY))

        val titleWithHover = display.title()
            .color(frameColor)
            .hoverEvent(HoverEvent.showText(display.description()))

        val completedMsg = prefix
            .append(event.player.displayName()) // already a Component in modern Paper
            .append(Component.text(" completed advancement ", NamedTextColor.WHITE))
            .append(titleWithHover)

        val borderMsg = prefix.append(
            Component.text(
                "The World Border has expanded by ${expansion.toInt()} blocks!",
                NamedTextColor.WHITE
            )
        )

        // Broadcast via Adventure
        Bukkit.getServer().broadcast(completedMsg)
        Bukkit.getServer().broadcast(borderMsg)

        // Update your stored border size + actual world border
        worldBorder += expansion
        RedCorp.getPlugin().setWorldBorder(worldBorder)
        wb.setSize(worldBorder, 5L)
    }
}