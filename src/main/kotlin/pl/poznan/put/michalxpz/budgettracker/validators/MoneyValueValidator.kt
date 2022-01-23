package pl.poznan.put.michalxpz.budgettracker.validators

import pl.poznan.put.michalxpz.budgettracker.exceptions.InvalidNumberFormatException

class MoneyValueValidator {
    fun parseValue(value: String): Double {
        if (value.isNotEmpty() && value.toDoubleOrNull() != null) {
            if (value.toDouble() > 0) {
                return value.toDouble()
            } else {
                throw InvalidNumberFormatException()
            }
        } else {
            throw InvalidNumberFormatException()
        }
    }
}