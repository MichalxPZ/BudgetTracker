package pl.poznan.put.michalxpz.budgettracker.sharedPrefs

import pl.poznan.put.michalxpz.budgettracker.data.ApplicationData
import pl.poznan.put.michalxpz.budgettracker.exceptions.CannotSaveToFileException
import pl.poznan.put.michalxpz.budgettracker.utils.FileDataSource
import java.io.IOException

class SharedPrefsRepository {
    val path = "sharedPrefs.txt"

    fun saveToFile(dataEntities: ArrayList<ApplicationData>) {
        try {
            val dataSource = FileDataSource<ApplicationData>(path)
            dataSource.saveToFile(dataEntities)
        } catch (e: IOException) {
            throw CannotSaveToFileException()
        }
    }

    fun readFromFile(): ArrayList<ApplicationData>? {
        var entities: ArrayList<ApplicationData>? = null
        try {
            val dataSource = FileDataSource<ApplicationData>(path)
             entities = dataSource.readFromFile()
        } catch (e: IOException) {
            entities = ArrayList()
        }
        return entities
    }
}