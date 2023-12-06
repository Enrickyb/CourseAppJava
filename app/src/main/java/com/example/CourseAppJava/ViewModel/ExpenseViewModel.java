package com.example.CourseAppJava.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.CourseAppJava.models.User.Transactions.Expense;

import java.util.List;

public class ExpenseViewModel extends ViewModel {
    private List<Expense> expenses;

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
