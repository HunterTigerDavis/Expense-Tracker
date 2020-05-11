package com.example.expenseTracker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.softmoore.android.graphlib.Function;
import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;
import com.softmoore.android.graphlib.Label;
import com.softmoore.android.graphlib.Point;

public class HomeFragment extends Fragment {

    private Button button;
    private String annualIncomeString;
    private int annualIncomeInt;

    private TextView annualIncomeText;
    private TextView monthlyIncomeText;
    private TextView weeklyIncomeText;
    private TextView dailyIncomeText;

    public HomeFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        annualIncomeString = WelcomeActivity.income;
        annualIncomeInt = Integer.parseInt(annualIncomeString);

//
//        annualIncomeText = view.findViewById(R.id.annualIncomeDisplay);
//        monthlyIncomeText = view.findViewById(R.id.monthlyIncomeDisplay);
//        weeklyIncomeText = view.findViewById(R.id.weeklyIncomeDisplay);
//        dailyIncomeText = view.findViewById(R.id.dailyIncomeDisplay);
//
//        annualIncomeText.setText("Annual Income: $" + WelcomeActivity.income);
//        monthlyIncomeText.setText("Monthly Income: $" + annualIncomeInt / 12);
//        weeklyIncomeText.setText("Weekly Income: $" + annualIncomeInt / 52);
//        dailyIncomeText.setText("Daily Income: $" + annualIncomeInt / 365);

        // TODO: Format text w/ graph
        // CREATING THE GRAPH FOR INCOME BREAKDOWN
        // "->" is a lambda expression, used for defining functions
        Graph graph = new Graph.Builder()
                .addFunction(x -> 20 - x, Color.RED)
                // World coordinates defines zoom of graph: xMin, xMax, yMin, yMax
                .setWorldCoordinates(-1, 21, -1, 21)
                // Ticks display the ticks within the graph
                .setXTicks(new double[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 20})
                .setYTicks(new double[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 20})
                .build();
        GraphView graphView = view.findViewById(R.id.graph_view);
        graphView.setGraph(graph);
        //setTitle("Empty Graph");
        TextView textView = view.findViewById(R.id.graph_view_label);
        textView.setText("Income Graph");


        // End of function
        return view;
    }

}
