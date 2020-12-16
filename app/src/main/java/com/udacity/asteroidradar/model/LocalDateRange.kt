package com.udacity.asteroidradar.model

import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

class LocalDateRange private constructor(
    val start: LocalDate,
    val end: LocalDate
) {

    companion object {
        val EMPTY_RANGE = LocalDateRange(LocalDate.now(), LocalDate.now())
        val WEEKLY_RANGE = LocalDateRange(LocalDate.now(), LocalDate.now().plusWeek())

        fun of(start: LocalDate, end: LocalDate): LocalDateRange {
            return if (start.isBefore(end)) LocalDateRange(start, end)
            else LocalDateRange(end, start)
        }
    }

    fun getDaysBetween(): Long = ChronoUnit.DAYS.between(start, end)
}