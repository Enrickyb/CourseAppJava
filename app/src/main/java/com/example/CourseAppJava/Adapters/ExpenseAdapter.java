package com.example.CourseAppJava.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.CourseAppJava.R;
import com.example.CourseAppJava.models.User.Transactions.Expense;

import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    private List<Expense> expenses;
    private LayoutInflater inflater;

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
        this.expenses = expenses;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expense_item, parent, false);
        }

        Expense expense = expenses.get(position);

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        TextView valueTextView = convertView.findViewById(R.id.valueTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);


        titleTextView.setText("Title: " + expense.getTitle());
        descriptionTextView.setText("Description: " + expense.getDescription());
        valueTextView.setText("R$: " + expense.getFormatedValue());
        dateTextView.setText("Date: " + expense.getDate());

        return convertView;
    }
}
