package pl.poznan.put.michalxpz.budgettracker.repository

import pl.poznan.put.michalxpz.budgettracker.data.Expense

class ExpensesRepository : Repository<Expense>() {
    override val path = "expenses.txt"
}