package eu.zimbelstern.tournant.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecipeList(
	val recipes: List<RecipeWithIngredients>
)
