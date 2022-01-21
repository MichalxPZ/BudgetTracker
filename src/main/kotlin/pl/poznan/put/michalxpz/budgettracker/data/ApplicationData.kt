package pl.poznan.put.michalxpz.budgettracker.data

import java.io.Serializable


data class ApplicationData(
    var isDarkMode: Boolean,
    val lastSearch: ArrayList<String>,
    val name: String,
    val surname: String,
    val targetAmount: Double
) : Serializable
