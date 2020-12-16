package com.udacity.asteroidradar.model

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

const val DATE_FORMAT = "yyyy-MM-dd";
val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

fun LocalDate.asFormattedString(): String = dateFormatter.format(this)

fun String.asLocalDate(): LocalDate = LocalDate.parse(this, dateFormatter)

fun LocalDate.plusWeek(): LocalDate = plusWeeks(1)

fun LocalDate.minusWeek(): LocalDate = minusWeeks(1)

fun LocalDate.nextDay(): LocalDate = plusDays(1)

fun LocalDate.previousDay(): LocalDate = minusDays(1)

operator fun LocalDate.inc(): LocalDate = nextDay()