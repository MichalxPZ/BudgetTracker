package pl.poznan.put.michalxpz.budgettracker.sharedPrefs

import pl.poznan.put.michalxpz.budgettracker.data.ApplicationData

object SharedPrefs {
    val applicationData = ApplicationData(
        isDarkMode = false,
        lastSearch = ArrayList<String>(),
        name = "name",
        surname = "surname",
        targetAmount = 0.0
    )
}