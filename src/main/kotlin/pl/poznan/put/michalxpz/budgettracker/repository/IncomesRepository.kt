package pl.poznan.put.michalxpz.budgettracker.repository

import pl.poznan.put.michalxpz.budgettracker.data.Income
import pl.poznan.put.michalxpz.budgettracker.exceptions.CannotSaveToFileException
import pl.poznan.put.michalxpz.budgettracker.utils.FileDataSource
import java.io.IOException

class IncomesRepository {
    val path = "incomes.txt"

    fun saveToFile(dataEntities: ArrayList<Income>) {
        try {
            val dataSource = FileDataSource<Income>(path)
            dataSource.saveToFile(dataEntities)
        } catch (e: IOException) {
            throw CannotSaveToFileException()
        }
    }

    fun readFromFile(): ArrayList<Income>? {
        var entities: ArrayList<Income>? = null
        try {
            val dataSource = FileDataSource<Income>(path)
            entities = dataSource.readFromFile()
        } catch (e: IOException) {
            entities = ArrayList()
        }
        return entities
    }
}