package pl.poznan.put.michalxpz.budgettracker.exceptions

abstract class BudgetTrackerExceptions : Exception() {}

class CannotSaveToFileException : BudgetTrackerExceptions() {
    override fun toString(): String {
        return "Couldn't save array object to file"
    }
}

class CannotReadFromFileException : BudgetTrackerExceptions() {
    override fun toString(): String {
        return "Couldn't read array object from file"
    }
}



