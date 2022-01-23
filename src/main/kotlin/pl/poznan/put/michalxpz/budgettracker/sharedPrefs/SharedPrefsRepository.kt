package pl.poznan.put.michalxpz.budgettracker.sharedPrefs

import pl.poznan.put.michalxpz.budgettracker.data.ApplicationData
import pl.poznan.put.michalxpz.budgettracker.repository.Repository

class SharedPrefsRepository : Repository<ApplicationData>() {
    override val path = "sharedPrefs.txt"
}