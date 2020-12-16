package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.LocalDateRange
import com.udacity.asteroidradar.model.dateFormatter
import com.udacity.asteroidradar.model.inc
import org.json.JSONObject
import javax.inject.Inject

class AsteroidResultParser @Inject constructor(private val moshi: Moshi) {

    fun parseAsteroidsJsonResult(
        jsonResult: JSONObject,
        range: LocalDateRange
    ): ArrayList<Asteroid> {
        val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")

        val asteroidList = ArrayList<Asteroid>()

        for (formattedDate in getFormattedDays(range)) {
            val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)

            for (i in 0 until dateAsteroidJsonArray.length()) {
                val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
                getParsedAsteroid(asteroidJson, formattedDate)?.let {
                    asteroidList.add(it)
                }
            }
        }
        return asteroidList
    }

    private fun getParsedAsteroid(asteroidJson: JSONObject, formattedDate: String): Asteroid? {
        val id = asteroidJson.getLong("id")
        val codename = asteroidJson.getString("name")
        val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
        val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
            .getJSONObject("kilometers").getDouble("estimated_diameter_max")

        val closeApproachData = asteroidJson
            .getJSONArray("close_approach_data").getJSONObject(0)
        val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
            .getDouble("kilometers_per_second")
        val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
            .getDouble("astronomical")
        val isPotentiallyHazardous = asteroidJson
            .getBoolean("is_potentially_hazardous_asteroid")

        return Asteroid(
            id, codename, formattedDate, absoluteMagnitude,
            estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous
        )
    }

    private fun getFormattedDays(range: LocalDateRange): ArrayList<String> {
        val formattedDateList = ArrayList<String>()

        var currentDate = range.start
        for (i in 0..range.getDaysBetween()) {
            formattedDateList.add(dateFormatter.format(currentDate++))
        }

        return formattedDateList
    }

}