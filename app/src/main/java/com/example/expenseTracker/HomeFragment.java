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
    private String dailyExpenseString;
    private int dailyExpenseInt;
    private String savingsGoalString;
    private int savingsGoalInt;

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

        dailyExpenseString = WelcomeActivity.expenses;
        dailyExpenseInt = Integer.parseInt(dailyExpenseString);
        savingsGoalString = WelcomeActivity.savings;
        savingsGoalInt = Integer.parseInt(savingsGoalString);

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

        // TODO: Format text w/ graph, label axes, etc.
        // CREATING THE GRAPH FOR INCOME BREAKDOWN
        // "->" is a lambda expression, used for defining functions

        // Tracking the income based on max expenses per month for a line graph:
        Point[] points ={
                new Point(0, annualIncomeInt),
                new Point(1, annualIncomeInt - dailyExpenseInt),
                new Point(2, annualIncomeInt - 2 * dailyExpenseInt),
                new Point(3, annualIncomeInt - 3 * dailyExpenseInt),
                new Point(4, annualIncomeInt - 4 * dailyExpenseInt),
                new Point(5, annualIncomeInt - 5 * dailyExpenseInt),
                new Point(6, annualIncomeInt - 6 * dailyExpenseInt),
                new Point(7, annualIncomeInt - 7 * dailyExpenseInt),
                new Point(8, annualIncomeInt - 8 * dailyExpenseInt),
                new Point(9, annualIncomeInt - 9 * dailyExpenseInt),
                new Point(10, annualIncomeInt - 10 * dailyExpenseInt),
                new Point(11, annualIncomeInt - 11 * dailyExpenseInt),
                new Point(12, annualIncomeInt - 12 * dailyExpenseInt)
        };

        Point[] savingsPoints ={
                new Point(0, savingsGoalInt),
                new Point(12, savingsGoalInt)
        };

        Point[] endPoints={
          new Point(0, annualIncomeInt - 12 * dailyExpenseInt) ,
          new Point(12, annualIncomeInt - 12 * dailyExpenseInt)
        };

        Graph lineGraph = new Graph.Builder()
                .addLineGraph(points, Color.RED)
                //.addFunction(x -> annualIncomeInt - x * dailyExpenseInt, Color.RED)
                // World coordinates defines zoom of graph: xMin, xMax, yMin, yMax. Default is 10x10
                .setWorldCoordinates(-2, 14, -(annualIncomeInt / 10) - 1,
                        annualIncomeInt + annualIncomeInt / 10 + 1)
                // Ticks display the ticks on the label within the graph
                // We cannot combine ticks and labels on the same axis
                // x-axis should be months (1-12)
                .setXTicks(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
                // y-axis should be income from max income to 0
                //.setYTicks(new double[]{0, annualIncomeInt*.25, annualIncomeInt*.5, annualIncomeInt*.75, annualIncomeInt})
                // Setting a label for our max annualIncome
                // Here we should draw a line for our target savings after a year, we can draw a horizontal line
//                .addFunction(x -> savingsGoalInt, Color.GREEN)
                .addLineGraph(savingsPoints, Color.GREEN)
                // Drawing a line for the end result
//                .addFunction(x -> annualIncomeInt - 12 * dailyExpenseInt, Color.BLUE)
                .addLineGraph(endPoints, Color.BLUE)
                // Labeling our y-axis points of interest
                .setYLabels(new Label[]{new Label(savingsGoalInt, "Goal"),
                        new Label(annualIncomeInt, "$" + annualIncomeString),
                        new Label(annualIncomeInt / 2, "$" + String.valueOf(annualIncomeInt / 2)),
                        new Label(annualIncomeInt - 12 * dailyExpenseInt, "$ Saved")})
                .build();
        GraphView graphView1 = view.findViewById(R.id.graph_view1);
        graphView1.setGraph(lineGraph);
        //setTitle("Empty Graph");
        TextView graph1Label = view.findViewById(R.id.graph_view_label1);
        graph1Label.setText("Income Graph per Month");


        Graph barGraph = new Graph.Builder()
                .addFunction(x -> x * x, Color.MAGENTA)
                .build();
        GraphView graphView2 = view.findViewById(R.id.graph_view2);
        graphView2.setGraph(barGraph);
        TextView graph2Label = view.findViewById(R.id.graph_view_label2);
        graph2Label.setText("Expenses vs Income (TEST)");

        // End of function
        return view;
    }

}
