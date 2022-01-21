package pl.poznan.put.michalxpz.budgettracker.data

import java.io.Serializable
import java.time.LocalDate

data class Expense(
    val amount: Double,
    val category: ExpenseCategory,
    val receiver: String,
    val date: LocalDate
) : Serializable

enum class ExpenseCategory {
    UNDEFINED,
    GROCERIES,
    ENTERTAINMENT,
    HOUSE,
    CLOTHES,
    TRANSPORT,
    HEALTH,
    BILLS
}
