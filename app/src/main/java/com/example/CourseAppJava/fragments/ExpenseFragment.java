package com.example.CourseAppJava.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.CourseAppJava.Adapters.ExpenseAdapter;
import com.example.CourseAppJava.R;
import com.example.CourseAppJava.ViewModel.ExpenseViewModel;
import com.example.CourseAppJava.models.User.Transactions.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseFragment extends Fragment {
    private ListView listView;

    private List<Expense> expenses = new ArrayList<>();
    private ExpenseAdapter expenseAdapter;
    private ExpenseViewModel expenseViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        expenseViewModel = new ViewModelProvider(requireActivity()).get(ExpenseViewModel.class);
        List<Expense> expenses = expenseViewModel.getExpenses();


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