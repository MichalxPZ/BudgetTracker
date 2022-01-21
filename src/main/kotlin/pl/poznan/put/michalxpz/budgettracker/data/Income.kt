package pl.poznan.put.michalxpz.budgettracker.data

import java.io.Serializable
import java.time.LocalDate

data class Income(
    val amount: Double,
    val category: IncomeCategory,
    val sender: String,
    val date: LocalDate
) : Serializable

enum class IncomeCategory {
    UNDEFINED,
    SALARY,
    RECHARGE
}
