package pl.poznan.put.michalxpz.budgettracker.exceptions

abstract class BudgetTrackerExceptions : Exception() {}

class CannotSaveToFileException : BudgetTrackerExceptions() {
    override fun toString(): String {
        return "Couldn't save array object to file"
    }
}

class InvalidNameFormatException : BudgetTrackerExceptions() {
    override fun toString(): String {
        return "Invalid format of Name field"
    }
}

class InvalidSurnameFormatException : BudgetTrackerExceptions() {
    override fun toString(): String {
        return "Invalid format of Surname field"
    }
}

class NegativeMoneyValueException : BudgetTrackerExceptions() {
    override fun toString(): String {
        return "Bet negative balance is not your goal :)"
    }
}

class InvalidNumberFormatException : BudgetTrackerExceptions() {
    override fun toString(): String {
        return "Invalid format of money value field"
    }
}



