package com.cosmicreach.redcorp.items

import de.tr7zw.nbtapi.NBT
import org.bukkit.Bukkit
import org.bukkit.JukeboxSong
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.inventory.meta.components.EquippableComponent
import org.bukkit.inventory.meta.components.JukeboxPlayableComponent
import org.bukkit.profile.PlayerProfile
import java.net.URL
import java.util.UUID

class CustomItems {
    fun MRE (a : Int): ItemStack {
        val item = ItemStack(Material.COOKED_PORKCHOP, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("mre")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§4§lʀᴇᴅ§8ᴄᴏʀᴘ §f§lᴍʀᴇ")

        val lore = listOf("§4§lRed§8Corp§f's sinister sustenance: A pork-packed MRE,", "§fA vile delicacy born of darkness,", "§fSating their hunger for power, one bite at a time.")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Arlbaro (a : Int): ItemStack {
        val item = ItemStack(Material.COOKIE, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("arlbaro")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§c§lᴀʀʟʙᴀʀᴏ ʀᴇᴅ")

        val lore = listOf("§fFumar §4acorta la vida")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Hammer (a : Int): ItemStack {
        val item = ItemStack(Material.WOODEN_AXE, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("hammer")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§6§lʏᴏʀɪᴄᴋ's ʜᴀᴍᴍᴇʀ")

        val lore = listOf("§fWhen found, return to Yorick")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun IvansBeats (a : Int): ItemStack {
        val item = ItemStack(Material.BEETROOT, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 1)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("beets")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§oɪᴠᴀɴ's ᴘʀᴇᴍɪᴜᴍ ʙᴇᴇᴛs")
        item.setItemMeta(meta)

        return item
    }

    fun Gavel (a : Int): ItemStack {
        val item = ItemStack(Material.WOODEN_AXE, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 2)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("gavel")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§6§lᴊᴜᴅɢᴇs ʜᴀᴍᴍᴇʀ")

        val lore = listOf("§fWhen found, return to Judge")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun TeleportScroll (a : Int): ItemStack {
        val item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 3)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("scroll-teleport")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lᴛᴇʟᴇᴘᴏʀᴛ sᴄʀᴏʟʟ")

        val lore = listOf("§fAllows teleport to player")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun DeathScroll (a : Int): ItemStack {
        val item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 5)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("scroll-death")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lᴅᴇᴀᴛʜ sᴄʀᴏʟʟ")

        val lore = listOf("§fAllows teleport to death location")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun HomeScroll (a : Int): ItemStack {
        val item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 6)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("scroll-home")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lʜᴏᴍᴇ sᴄʀᴏʟʟ")

        val lore = listOf("§fAllows teleport to their bed")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun TeleportAnchor (a : Int): ItemStack {
        val item = ItemStack(Material.RECOVERY_COMPASS, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 4)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("anchor")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lᴛᴇʟᴇᴘᴏʀᴛ ᴀɴᴄʜᴏʀ")

        val lore = listOf("§fAllows players to teleport to you")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Penis (a : Int): ItemStack {
        val item = ItemStack(Material.ROTTEN_FLESH, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("penis")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§3§lᴘᴇɴᴜs")

        val lore = listOf("§fLol its kinda funny")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Lanyard (a : Int, type: String): ItemStack {
        val item = ItemStack(Material.NAME_TAG, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("${type}-lanyard")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§c§l${type.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }} §f§lʟᴀɴʏᴀʀᴅ")

        val lore = listOf("§fUsed by the CCP for security")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun Unit (a : Int, type: String): ItemStack {
        val item = ItemStack(Material.SUNFLOWER, a)
        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("${type}-unit")

        meta.setCustomModelDataComponent(cm)
        meta.setDisplayName("§c§lᴜɴɪᴛs")

        val lore = listOf("§fCurrency of Insomnis")
        meta.lore = lore
        item.setItemMeta(meta)

        return item
    }

    fun DebugStick (a : Int): ItemStack {
        val item = ItemStack(Material.STICK, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 2886)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("tag")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§c§lᴅᴇʙᴜɢ sᴛɪᴄᴋ")

        item.setItemMeta(meta)
        return item
    }

    fun TestWing (a : Int): ItemStack {
        val item = ItemStack(Material.PAPER, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 666)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent
        val equip = meta.equippable

        cm.strings = mutableListOf("wing")
        equip.slot = EquipmentSlot.CHEST
        meta.isGlider = true

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)
        meta.setEquippable(equip)

        meta.setDisplayName("§c§lTest Wing")

        item.setItemMeta(meta)
        return item
    }

    fun Fairy (a: Int, id: String): ItemStack {
        val item = ItemStack(Material.PLAYER_HEAD, a)
        val profile = Bukkit.createPlayerProfile(UUID.randomUUID())
        val textures = profile.textures
        val url = URL("https://textures.minecraft.net/texture/8241989eac173a87489233b11239e54643609de929189b9e10e598b5cc896021")
        textures.skin = url
        profile.setTextures(textures)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 700+id.toInt())
        }

        val meta = item.itemMeta as SkullMeta
        meta.ownerProfile = profile

        item.setItemMeta(meta)
        return item
    }

    fun Awp (a: Int): ItemStack {
        val item = ItemStack(Material.BOW, a)

        NBT.modify(item) { nbt ->
            nbt.setInteger("item-id", 800)
        }

        val meta = item.itemMeta as ItemMeta
        val cm = meta.customModelDataComponent

        cm.strings = mutableListOf("awp")

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.setCustomModelDataComponent(cm)

        meta.setDisplayName("§9§lᴀᴡᴘ")

        item.setItemMeta(meta)
        return item
    }
}