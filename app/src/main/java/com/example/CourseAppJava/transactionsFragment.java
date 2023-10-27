package com.example.CourseAppJava;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.CourseAppJava.Adapters.ExpenseAdapter;
import com.example.CourseAppJava.models.User.Transactions.Expense;

import java.util.ArrayList;
import java.util.List;

public class transactionsFragment extends Fragment {
    private ListView listView;
    private Bundle expenseBundle;
    private List<Expense> expenses = new ArrayList<>();
    private ExpenseAdapter expenseAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        expenseBundle = this.getArguments();

        if (expenseBundle != null) {
            expenses = (List<Expense>) expenseBundle.getSerializable("expenses");
        }

        listView = view.findViewById(R.id.listView);

        if (expenses != null) {
            expenseAdapter = new ExpenseAdapter(requireContext(), expenses);
            listView.setAdapter(expenseAdapter);
        } else {
            Log.i("expenses is null", "expenses is null");
        }

        return view;
    }


}