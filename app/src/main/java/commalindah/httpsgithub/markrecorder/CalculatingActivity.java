package commalindah.httpsgithub.markrecorder;

import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;import android.os.Handler;

public class CalculatingActivity extends AppCompatActivity {

    private static int numStudentsAdded;
    private static Student[] studentsArray;

    private TextView cAverageResult;
    private ProgressBar cLoadingIndicator;

    /**
     * onCreate is the method that is run when we create an instance of this activity
     *
     * @param savedInstanceState is a Bundle object that allows the Activity to restore
     *                           itself to a previously saved instance
     * @return Nothing is returned
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculating);

        cAverageResult = (TextView) findViewById(R.id.tv_average_result);
        cLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator_2);

        //The next lines get studentsArray and numStudentsAdded from the MarkRecorderActivity using Extras
        numStudentsAdded = getIntent().getIntExtra("EXTRA_NumStudentsAdded", 0);

        studentsArray = new Student[numStudentsAdded];

        Bundle bundleObject = getIntent().getExtras();
        Parcelable[] parcelArray = bundleObject.getParcelableArray("StudentsArray");

        for(int i = 0; i < numStudentsAdded; i++)
        {
            studentsArray[i] = (Student) parcelArray[i];
        }
    }

    /**
     * calculateAverage checks whether there is no student added to the array when the button is clicked,
     * if this is not the case, it calls the CalculatingOperation to find the average mark and display it
     *
     * @param vw is the View that is related to this method
     * @return Nothing is returned
     */
    public void calculateAverage (View vw)
    {
        if( numStudentsAdded == 0 )
        {
            cAverageResult.setText("You must enter at least one student to calculate the average.");
        }
        else
        {
            new CalculatingOperation().execute();
        }

    }

    private class CalculatingOperation extends AsyncTask<String, Void, String> {

        /**
         * onPreExecute sets the visibility of the loading indicator to visible before calculating the average
         *
         * @param "" There are no parameters
         * @return Nothing is returned
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cLoadingIndicator.setVisibility(View.VISIBLE);
        }

        /**
         * doInBackground finds the average result by calling the findAverage method and returns it
         *
         * @param average is the average mark of the students
         * @return the calculated result
         */
        @Override
        protected String doInBackground(String[] average) {

            //This tries to sleep the thread for 700 milliseconds so that
            //the user can actually see the progress bar loading indicator
            try{
                Thread.sleep(700);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            double averageResult;
            averageResult = findAverage(studentsArray, numStudentsAdded);
            String result = String.valueOf(averageResult);
            return result;
        }

        /**
         * onPostExecute hides the loading indicator after the calculation is finished
         * and displays the average result
         *
         * @param result is the average mark of the students
         * @return Nothing is returned
         */
        @Override
        protected void onPostExecute(String result)
        {
            cLoadingIndicator.setVisibility(View.INVISIBLE);

            double averageResult;

            averageResult = Double.parseDouble(result);

            if(numStudentsAdded == 1)
            {
                cAverageResult.setText( String.format("The Average Mark of 1 student is %.2f", averageResult) );
            }
            else
            {
                cAverageResult.setText( "The Average Mark of " + numStudentsAdded + String.format(" students is %.2f", averageResult) );
            }
        }
    }

    /**
     * findAverage calculates the average mark using recursion
     *
     * @param studentsArray is the array of students the user has entered
     * @param n is the number of students added to the array
     * @return the average mark of the students
     */
    private double findAverage( Student[] studentsArray, int n )
    {
        double sum;
        double average;

        if( n == 1 )
        {
            average = Double.parseDouble(studentsArray[0].getMark());
        }
        else
        {
            double temp = Double.parseDouble(studentsArray[n-1].getMark());
            sum = temp + (n-1) * findAverage( studentsArray,n-1);
            average = sum / n;
        }
        return average;
    }

    /**
     * onBackPressed adds sliding transitions when back is pressed in addition to its ordinary functions
     *
     * @param "" There are no parameters
     * @return Nothing is returned
     */
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
    }
}




