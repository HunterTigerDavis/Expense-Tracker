package com.example.expenseTracker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;
import com.softmoore.android.graphlib.Label;
import com.softmoore.android.graphlib.Point;

/**
 * This class is a fragment which holds the graphs within the WelcomeActivity,
 * they are only generated once numbers are entered and generated, and are refreshed each time
 * the button is hit.
 */
public class HomeFragment extends Fragment {

    private Button button;
    private String annualIncomeString;
    private int annualIncomeInt;
    private String monthlyExpenseString;
    private int monthlyExpenseInt;
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

        monthlyExpenseString = WelcomeActivity.expenses;
        monthlyExpenseInt = Integer.parseInt(monthlyExpenseString);
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

        // Graph 1 will be expenses over time
        // Tracking the income based on max expenses per month for a line graph:
        Point[] g1Points ={
                new Point(0, annualIncomeInt),
                new Point(1, annualIncomeInt - monthlyExpenseInt),
                new Point(2, annualIncomeInt - 2 * monthlyExpenseInt),
                new Point(3, annualIncomeInt - 3 * monthlyExpenseInt),
                new Point(4, annualIncomeInt - 4 * monthlyExpenseInt),
                new Point(5, annualIncomeInt - 5 * monthlyExpenseInt),
                new Point(6, annualIncomeInt - 6 * monthlyExpenseInt),
                new Point(7, annualIncomeInt - 7 * monthlyExpenseInt),
                new Point(8, annualIncomeInt - 8 * monthlyExpenseInt),
                new Point(9, annualIncomeInt - 9 * monthlyExpenseInt),
                new Point(10, annualIncomeInt - 10 * monthlyExpenseInt),
                new Point(11, annualIncomeInt - 11 * monthlyExpenseInt),
                new Point(12, annualIncomeInt - 12 * monthlyExpenseInt)
        };

        Point[] g1SavingsPoints ={
                new Point(0, savingsGoalInt),
                new Point(12, savingsGoalInt)
        };

        Point[] g1EndPoints={
          new Point(0, annualIncomeInt - 12 * monthlyExpenseInt) ,
          new Point(12, annualIncomeInt - 12 * monthlyExpenseInt)
        };

        Graph lineGraph = new Graph.Builder()
                .addLineGraph(g1Points, Color.RED)
                //.addFunction(x -> annualIncomeInt - x * dailyExpenseInt, Color.RED)
                // World coordinates defines zoom of graph: xMin, xMax, yMin, yMax. Default is 10x10
                .setWorldCoordinates(-2, 13, -(annualIncomeInt / 10) - 1,
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
                .addLineGraph(g1SavingsPoints, Color.GREEN)
                // Drawing a line for the end result
//                .addFunction(x -> annualIncomeInt - 12 * dailyExpenseInt, Color.BLUE)
                .addLineGraph(g1EndPoints, Color.BLUE)
                // Labeling our y-axis points of interest
                .setYLabels(new Label[]{
                        new Label(savingsGoalInt, "Goal"),
                        new Label(annualIncomeInt, "$" + annualIncomeString),
                        new Label(annualIncomeInt / 2, "$" + String.valueOf(annualIncomeInt / 2)),
                        new Label(annualIncomeInt - 12 * monthlyExpenseInt, "$ Saved")})

                .build();
        GraphView graphView1 = view.findViewById(R.id.graph_view1);
        graphView1.setGraph(lineGraph);
        //setTitle("Empty Graph");
        TextView graph1Label = view.findViewById(R.id.graph_view_label1);
        graph1Label.setText("Income Graph per Month");



        // Graph 2 will be a bar chart of the expenses vs savings each month

        // This will be the $ spent each month
        Point[] g2PointsSpent = {
                new Point(0, 0), new Point(0, monthlyExpenseInt),
                new Point(1, monthlyExpenseInt), new Point(1, 0)
        };
        // This will be the $ saved goal for each month
        Point[] g2PointsSavedGoal = {
                new Point(1, 0), new Point(1, savingsGoalInt/12),
                new Point(2, savingsGoalInt/12), new Point(2, 0)
        };
        // This will be the $ saved actual for each month
        Point[] g2PointsSaved = {
                new Point(2, 0), new Point(2, (annualIncomeInt - 12 * monthlyExpenseInt)/12),
                new Point(3, (annualIncomeInt - 12 * monthlyExpenseInt)/12), new Point(3, 0)
        };
        double yMax = (double) Math.max(monthlyExpenseInt + monthlyExpenseInt / 10 + 1,
                ((annualIncomeInt - 12 * monthlyExpenseInt) / 12) + monthlyExpenseInt / 10);
        yMax = (double) Math.max(yMax, savingsGoalInt/12 + (savingsGoalInt/12) / 10);

        Graph barGraph = new Graph.Builder()
                .setYLabels(new Label[]{
                        new Label(savingsGoalInt/12, "Monthly Savings Goal"),
                        new Label(monthlyExpenseInt, "Monthly Expenses"),
                        new Label((annualIncomeInt - 12 * monthlyExpenseInt)/12, "Actual Savings")})
                .setXLabels(new Label[]{
                        new Label(0.5, "Expenses"),
                        new Label(1.5, "Savings Goal"),
                        new Label(2.5, "Actual Savings")})
                .addLineGraph(g2PointsSpent, Color.MAGENTA)
                .addLineGraph(g2PointsSavedGoal, Color.GREEN)
                .addLineGraph(g2PointsSaved, Color.BLUE)
                .setWorldCoordinates(-2, 3.2, -(yMax / 10) - 1,
                        yMax)
                .build();
        GraphView graphView2 = view.findViewById(R.id.graph_view2);
        graphView2.setGraph(barGraph);
        TextView graph2Label = view.findViewById(R.id.graph_view_label2);
        graph2Label.setText("Expenses vs Savings (Monthly)");

        // End of function
        return view;
    }

}
