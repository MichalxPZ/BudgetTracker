package pl.poznan.put.michalxpz.budgettracker.utils

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileDataSource<T>(path: String) {
    val filePath = path

    fun saveToFile(dataEntities: ArrayList<T>) {
        var writer : ObjectOutputStream? = null
        try {
            val fileOutputStream = FileOutputStream(filePath)
            writer = ObjectOutputStream(fileOutputStream)
            writer.writeObject(dataEntities)
        } finally {
            writer?.close()
        }
    }

    fun readFromFile(): ArrayList<T>? {
        val data: ArrayList<T>?
        var reader: ObjectInputStream? = null
        try {
            val fileInputStream = FileInputStream(filePath)
            reader = ObjectInputStream(fileInputStream)
            data = reader.readObject() as ArrayList<T>
        } finally {
            reader?.close()
        }
        return data
    }
}