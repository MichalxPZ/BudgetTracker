package pl.poznan.put.michalxpz.budgettracker.repository

import pl.poznan.put.michalxpz.budgettracker.data.Expense
import pl.poznan.put.michalxpz.budgettracker.exceptions.CannotSaveToFileException
import pl.poznan.put.michalxpz.budgettracker.utils.FileDataSource
import java.io.IOException

class ExpensesRepository {
    val path = "expenses.txt"

    fun saveToFile(dataEntities: ArrayList<Expense>) {
        try {
            val dataSource = FileDataSource<Expense>(path)
            dataSource.saveToFile(dataEntities)
        } catch (e: IOException) {
            throw CannotSaveToFileException()
        }
    }

    fun readFromFile(): ArrayList<Expense>? {
        var entities: ArrayList<Expense>? = null
        try {
            val dataSource = FileDataSource<Expense>(path)
            entities = dataSource.readFromFile()
        } catch (e: IOException) {
            entities = ArrayList()
        }
        return entities
    }
}