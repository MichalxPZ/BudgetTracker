package pl.poznan.put.michalxpz.budgettracker.validators

import pl.poznan.put.michalxpz.budgettracker.exceptions.InvalidNameFormatException

class NameFieldValidator {
    fun parseName(name: String): String {
        if (name.isNotEmpty() && name.matches("^[a-zA-Z]*$".toRegex())) {
            return name
        } else throw InvalidNameFormatException()
    }
}