package com.cosmicreach.redcorp.recipes

import com.cosmicreach.redcorp.RedCorp
import com.cosmicreach.redcorp.items.DrugItems
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.SmokingRecipe
import org.bukkit.inventory.StonecuttingRecipe

class Drugs() {
    init {
        val spliff = spliffRecipe()
        val coke = cokeRecipe()
        val opium = opiumRecipe()

        Bukkit.addRecipe(spliff)
        Bukkit.addRecipe(coke)
        Bukkit.addRecipe(opium)
    }

    private fun spliffRecipe (): ShapedRecipe {
        val spliff = ShapedRecipe(NamespacedKey(RedCorp.getPlugin(), "spliff"), DrugItems().Spliff(1))
        spliff.shape(" X ", " Y ", " X ")
        spliff.setIngredient('X', Material.PAPER)
        spliff.setIngredient('Y', RecipeChoice.ExactChoice(DrugItems().GroundWeed(1)))
        return spliff
    }

    private fun cokeRecipe (): StonecuttingRecipe {
        val coke = StonecuttingRecipe(NamespacedKey(RedCorp.getPlugin(), "coke"), DrugItems().Coke(1), RecipeChoice.ExactChoice(DrugItems().CocaLeaf(1)))
        return coke
    }

    private fun opiumRecipe (): SmokingRecipe {
        val opium = SmokingRecipe(NamespacedKey(RedCorp.getPlugin(), "poppy"), DrugItems().OpiumResin(1),RecipeChoice.ExactChoice(DrugItems().OpiumSap(1)), 1.0F, 500)
        return opium
    }
}