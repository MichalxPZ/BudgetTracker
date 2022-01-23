package pl.poznan.put.michalxpz.budgettracker.repository

import pl.poznan.put.michalxpz.budgettracker.data.Income

class IncomesRepository : Repository<Income>() {
    override val path = "incomes.txt"
}