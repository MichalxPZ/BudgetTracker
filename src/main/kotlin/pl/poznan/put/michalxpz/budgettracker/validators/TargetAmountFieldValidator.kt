package pl.poznan.put.michalxpz.budgettracker.validators

import pl.poznan.put.michalxpz.budgettracker.exceptions.InvalidNumberFormatException
import pl.poznan.put.michalxpz.budgettracker.exceptions.NegativeMoneyValueException

class TargetAmountFieldValidator {
    fun parseValue(value: String): Double {
        if (!value.isEmpty() && value.toDoubleOrNull() != null) {
            if (value.toDouble() > 0) {
                return value.toDouble()
            } else {
                throw NegativeMoneyValueException()
            }
        } else {
            throw InvalidNumberFormatException()
        }
    }
}