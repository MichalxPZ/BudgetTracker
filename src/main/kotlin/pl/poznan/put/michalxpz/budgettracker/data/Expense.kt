package pl.poznan.put.michalxpz.budgettracker.data

import java.io.Serializable
import java.util.*

data class Expense(
    val amount: Int,
    val category: ExpenseCategory,
    val receiver: String,
    val date: Date
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
