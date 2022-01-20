package pl.poznan.put.michalxpz.budgettracker.data

import java.util.*

data class Income(
    val amount: Int,
    val category: IncomeCategory,
    val sender: String,
    val date: Date
)

enum class IncomeCategory {
    UNDEFINED,
    SALARY,
    RECHARGE
}
