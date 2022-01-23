package pl.poznan.put.michalxpz.budgettracker.validators

import pl.poznan.put.michalxpz.budgettracker.exceptions.InvalidDateFormatException

class DateFieldValidator {
    fun parseDate(date: String): String {
        if (date.isNotEmpty() && date.length == 10) {
            val year = date.take(4).toInt()
            val month = date.take(7).takeLast(2).toInt()
            val day = date.takeLast(2).toInt()
            if (year in 1900..2100 && month in 1..12 && day in 1..31) {
                return date
            } else throw InvalidDateFormatException()
        } else throw InvalidDateFormatException()
    }
}