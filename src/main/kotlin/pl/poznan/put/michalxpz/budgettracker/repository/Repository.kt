package pl.poznan.put.michalxpz.budgettracker.repository

import pl.poznan.put.michalxpz.budgettracker.exceptions.CannotSaveToFileException
import pl.poznan.put.michalxpz.budgettracker.utils.FileDataSource
import java.io.IOException

abstract class Repository<T> {
    abstract val path: String

    fun saveToFile(dataEntities: ArrayList<T>) {
        try {
            val dataSource = FileDataSource<T>(path)
            dataSource.saveToFile(dataEntities)
        } catch (e: IOException) {
            throw CannotSaveToFileException()
        }
    }

    fun readFromFile(): ArrayList<T>? {
        val entities: ArrayList<T>? = try {
            val dataSource = FileDataSource<T>(path)
            dataSource.readFromFile()
        } catch (e: IOException) {
            ArrayList()
        }
        return entities
    }
}