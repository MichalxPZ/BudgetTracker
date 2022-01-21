package pl.poznan.put.michalxpz.budgettracker.data

import java.io.Serializable
import java.util.*

data class Income(
    val amount: Int,
    val category: IncomeCategory,
    val sender: String,
    val date: Date
) : Serializable

enum class IncomeCategory {
    UNDEFINED,
    SALARY,
    RECHARGE
}
