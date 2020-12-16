package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.model.LocalDateRange
import com.udacity.asteroidradar.model.nextDay
import org.threeten.bp.LocalDate
import javax.inject.Inject

class CacheStrategy @Inject constructor() {

    companion object {
        private const val DAYS_IN_WEEK = 7
    }

    internal suspend fun getDaysToFetch(lastDayFetched: LocalDate): LocalDateRange {
        return IO {
            val dateRange = LocalDateRange.of(LocalDate.now(), lastDayFetched)
            val daysAheadInCache = dateRange.getDaysBetween()
            if (isWeekAheadCacheNeeded(daysAheadInCache)) {
                getOneWeekRange()
            } else {
                getPartialWeekRange(daysAheadInCache, lastDayFetched)
            }
        }
    }

    private fun isWeekAheadCacheNeeded(daysAheadInCache: Long): Boolean = daysAheadInCache <= 0

    private fun getOneWeekRange() = LocalDateRange.WEEKLY_RANGE

    private fun getPartialWeekRange(daysAheadInCache: Long, lastDayFetched: LocalDate): LocalDateRange {
        val amountToFetch = getAmountToFetch(daysAheadInCache)
        return if (isFetchNeeded(amountToFetch)) {
            getNecessaryRange(lastDayFetched, amountToFetch)
        } else {
            LocalDateRange.EMPTY_RANGE
        }
    }

    private fun getAmountToFetch(daysAheadInCache: Long) = DAYS_IN_WEEK - daysAheadInCache

    private fun isFetchNeeded(amountToFetch: Long) = amountToFetch > 0

    private fun getNecessaryRange(lastDayFetched: LocalDate, amountToFetch: Long): LocalDateRange {
        return LocalDateRange.of(lastDayFetched.nextDay(), lastDayFetched.plusDays(amountToFetch))
    }

}