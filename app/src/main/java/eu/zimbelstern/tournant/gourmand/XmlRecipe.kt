package eu.zimbelstern.tournant.gourmand

import android.os.Parcelable
import androidx.core.text.isDigitsOnly
import eu.zimbelstern.tournant.data.Recipe
import eu.zimbelstern.tournant.data.RecipeWithIngredients
import eu.zimbelstern.tournant.toIngredientList
import eu.zimbelstern.tournant.withTimeWordsToMinInt
import kotlinx.parcelize.Parcelize
import kotlin.math.ceil
import kotlin.math.roundToInt

@Parcelize
data class XmlRecipe(
	val id: Int?,
	val title: String?,
	val category: String?,
	val cuisine: String?,
	val source: String?,
	val link: String?,
	val rating: Float?,
	val preptime: String?,
	val cooktime: String?,
	val yields: String?,
	val ingredientList: List<XmlIngredientListElement>?,
	val instructions: String?,
	val modifications: String?,
	val image: ByteArray?
) : Parcelable {

	fun getYieldsValue(): Float? {
		return if (yields?.get(0)?.isDigit() == true) {
			yields.split(" ")[0].toFloat()
		}
		else null
	}

	fun getYieldsUnit(): String? {
		return if (yields?.isDigitsOnly() == true) {
			null
		}
		else if (yields?.get(0)?.isDigit() == true) {
			yields.split(" ").drop(1).joinToString(" ")
		}
		else yields
	}

	fun moreYield(currentYield: Float): Float {
		return if (currentYield >= 1) {
			currentYield.roundToInt() + 1f
		}
		else if (currentYield >= 0.5f) {
			1f
		}
		else if (currentYield >= 0.25f) {
			0.5f
		}
		else {
			currentYield
		}
	}

	fun lessYield(currentYield: Float): Float {
		return if (currentYield > 1) {
			ceil(currentYield) - 1
		}
		else if (currentYield > 0.5f) {
			0.5f
		}
		else if (currentYield > 0.25f){
			0.25f
		}
		else {
			currentYield
		}
	}

	fun toRecipeWithIngredients() = RecipeWithIngredients(
		Recipe(
			id,
			title ?: "",
			null,
			category, cuisine, source, link,
			rating?.times(2)?.toInt(),
			preptime?.withTimeWordsToMinInt(), cooktime?.withTimeWordsToMinInt(),
			getYieldsValue(), getYieldsUnit(),
			instructions, modifications, image
		),
		ingredientList?.toIngredientList() ?: mutableListOf()
	)

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is XmlRecipe) return false

		if (id != other.id) return false
		if (title != other.title) return false
		if (category != other.category) return false
		if (cuisine != other.cuisine) return false
		if (source != other.source) return false
		if (link != other.link) return false
		if (rating != other.rating) return false
		if (preptime != other.preptime) return false
		if (cooktime != other.cooktime) return false
		if (yields != other.yields) return false
		if (ingredientList != other.ingredientList) return false
		if (instructions != other.instructions) return false
		if (modifications != other.modifications) return false
		if (image != null) {
			if (other.image == null) return false
			if (!image.contentEquals(other.image)) return false
		} else if (other.image != null) return false

		return true
	}

	override fun hashCode(): Int {
		var result = id ?: 0
		result = 31 * result + (title?.hashCode() ?: 0)
		result = 31 * result + (category?.hashCode() ?: 0)
		result = 31 * result + (cuisine?.hashCode() ?: 0)
		result = 31 * result + (source?.hashCode() ?: 0)
		result = 31 * result + (link?.hashCode() ?: 0)
		result = 31 * result + (rating?.hashCode() ?: 0)
		result = 31 * result + (preptime?.hashCode() ?: 0)
		result = 31 * result + (cooktime?.hashCode() ?: 0)
		result = 31 * result + (yields?.hashCode() ?: 0)
		result = 31 * result + (ingredientList?.hashCode() ?: 0)
		result = 31 * result + (instructions?.hashCode() ?: 0)
		result = 31 * result + (modifications?.hashCode() ?: 0)
		result = 31 * result + (image?.contentHashCode() ?: 0)
		return result
	}

}