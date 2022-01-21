package pl.poznan.put.michalxpz.budgettracker.validators

import pl.poznan.put.michalxpz.budgettracker.exceptions.InvalidSurnameFormatException

class SurnameValidator {
    fun parseSurname(name: String): String {
        if (!name.isEmpty() && name.matches("^[a-zA-Z]*$".toRegex())) {
            return name
        } else throw InvalidSurnameFormatException()
    }
}