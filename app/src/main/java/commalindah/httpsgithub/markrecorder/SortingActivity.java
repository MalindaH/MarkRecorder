package commalindah.httpsgithub.markrecorder;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class SortingActivity extends AppCompatActivity {

    private static int numStudentsAdded;
    private static Student[] studentsArray;

    private TextView oSortedList;
    private TextView oResults;
    private TextView oErrorMessage;
    private ProgressBar oLoadingIndicator;

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
        setContentView(R.layout.activity_sorting);

        oSortedList = (TextView) findViewById(R.id.tv_sorted_list);
        oResults = (TextView) findViewById(R.id.tv_results);
        oErrorMessage = (TextView) findViewById(R.id.tv_error_message_2);
        oLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator_1);

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
     * sortStudentsByLastNames checks if there is no student in the array, and if it is not the case,
     * it calls SortByLastNamesOperation to sort the students that the user entered using insertion sort
     * and display them in order of their last names
     *
     * @param vw is the View that is related to this method
     * @return Nothing is returned
     */
    public void sortStudentsByLastNames( View vw )
    {
        if( numStudentsAdded == 0 )
        {
            oErrorMessage.setVisibility(View.VISIBLE);
            oResults.setVisibility(View.INVISIBLE);
        }
        else
        {
            new SortByLastNamesOperation().execute();
        }
    }

    private class SortByLastNamesOperation extends AsyncTask<String, Void, String> {

        /**
         * onPreExecute sets the visibility of the loading indicator to visible and hides the
         * results TextView before sorting the students by last names
         *
         * @param "" There are no parameters
         * @return Nothing is returned
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            oLoadingIndicator.setVisibility(View.VISIBLE);
            oResults.setVisibility(View.INVISIBLE);
            oSortedList.setVisibility(View.INVISIBLE);
        }

        /**
         * doInBackground sorts students by last names by calling the insertionSort method
         *
         * @param list is the list of students
         * @return a String of sorted students
         */
        @Override
        protected String doInBackground(String[] list) {

            //This tries to sleep the thread for 700 milliseconds so that
            //the user can actually see the progress bar loading indicator
            try{
                Thread.sleep(700);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            String sortedList = "";

            insertionSort();

            return sortedList;
        }

        /**
         * onPostExecute hides the loading indicator after the sorting is finished
         * and displays the sorted list
         *
         * @param sortedList is a String of sorted students
         * @return Nothing is returned
         */
        @Override
        protected void onPostExecute(String sortedList)
        {
            oLoadingIndicator.setVisibility(View.INVISIBLE);
            oSortedList.setVisibility(View.VISIBLE);

            for( int i = 0; i < numStudentsAdded; i++ )
            {
                if( studentsArray != null)
                {
                    sortedList += String.format("First Name: %15s\nLast Name: %15s\nMark: %25s\n\n", studentsArray[i].getFirstName(), studentsArray[i].getLastName(), studentsArray[i].getMark() );
                }
            }

            oSortedList.setText(sortedList);
            oResults.setVisibility(View.VISIBLE);
        }
    }

    /**
     * insertionSort uses the Insertion Sort algorithm to sort the students' last names
     * in an ascending order
     *
     * @param "" There are no parameters
     * @return Nothing is returned
     */
    private void insertionSort()
    {
        Student key;
        int j;

        for( int i = 1; i < numStudentsAdded; i++)
        {
            key = studentsArray[i];
            j = i - 1;

            while( j >= 0 && ( studentsArray[j].getLastName() ).compareTo(key.getLastName()) > 0 )
            {
                studentsArray[j+1] = studentsArray[j];
                j = j - 1;
            }

            studentsArray[j+1] = key;
        }
    }

    /**
     * sortStudentsByMarks checks if there is no student in the array, and if it is not the case,
     * it calls SortByMarksOperation to sort the students that the user entered using selection sort
     * (or quick sort) and display them in order of their marks
     *
     * @param vw is the View that is related to this method
     * @return Nothing is returned
     */
    public void sortStudentsByMarks( View vw )
    {
        if( numStudentsAdded == 0 )
        {
            oErrorMessage.setVisibility(View.VISIBLE);
            oResults.setVisibility(View.INVISIBLE);
        }
        else
        {
            new SortByMarksOperation().execute();
        }
    }

    private class SortByMarksOperation extends AsyncTask<String, Void, String> {

        /**
         * onPreExecute sets the visibility of the loading indicator to visible and hides the
         * results before sorting the students by marks
         *
         * @param "" There are no parameters
         * @return Nothing is returned
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            oLoadingIndicator.setVisibility(View.VISIBLE);
            oResults.setVisibility(View.INVISIBLE);
            oSortedList.setVisibility(View.INVISIBLE);
        }

        /**
         * doInBackground sorts students by marks by calling the selectionSort (or quickSort) method
         *
         * @param list is the list of students
         * @return a String of sorted students
         */
        @Override
        protected String doInBackground(String[] list) {

            //This tries to sleep the thread for 700 milliseconds so that
            //the user can actually see the progress bar loading indicator
            try{
                Thread.sleep(700);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            String sortedList = "";

            selectionSort();
            //quickSort( studentsArray, 0, numStudentsAdded - 1);

            return sortedList;
        }

        /**
         * onPostExecute hides the loading indicator after the sorting is finished
         * and displays the sorted list
         *
         * @param sortedList is a String of sorted students
         * @return Nothing is returned
         */
        @Override
        protected void onPostExecute(String sortedList)
        {
            oLoadingIndicator.setVisibility(View.INVISIBLE);
            oSortedList.setVisibility(View.VISIBLE);

            for( int i = 0; i < numStudentsAdded; i++ )
            {
                if( studentsArray != null)
                {
                    sortedList += String.format("First Name: %15s\nLast Name: %15s\nMark: %25s\n\n", studentsArray[i].getFirstName(), studentsArray[i].getLastName(), studentsArray[i].getMark() );
                }
            }

            oSortedList.setText(sortedList);
            oResults.setVisibility(View.VISIBLE);
        }
    }

    /**
     * selectionSort uses the Selection Sort algorithm to sort the list of students in
     * a descending order of their marks
     *
     * @param "" There are no parameters
     * @return Nothing is returned
     */
    private void selectionSort()
    {
        int minIndex;
        Student temp;

        for( int i = 0; i < numStudentsAdded - 1; i++ )
        {
            minIndex = i;

            for( int j = i+1; j < numStudentsAdded; j++)
            {
                int jMark = Integer.parseInt(studentsArray[j].getMark());
                int minIndexMark = Integer.parseInt(studentsArray[minIndex].getMark());

                if( jMark > minIndexMark )
                {
                    minIndex = j;
                }
            }

            temp = studentsArray[minIndex];
            studentsArray[minIndex] = studentsArray[i];
            studentsArray[i] = temp;
        }
    }

    /**
     * quickSort uses the Quick Sort algorithm to sort the list of students in
     * a descending order of their marks
     *
     * @param studentsArray is the array we are sorting
     * @param low is the beginning index of the section of the array we would like to sort
     * @param high is the ending index of the section of the array we would like to sort
     * @return Nothing is returned
     */
    private void quickSort( Student[] studentsArray, int low, int high )
    {
        int middle;
        int i;
        int j;

        Student toSwap;

        if( low < high )
        {
            middle = low + (high-low)/2;

            i = low;
            j = high;

            int iMark = Integer.parseInt(studentsArray[i].getMark());
            int jMark = Integer.parseInt(studentsArray[j].getMark());
            int pivotMark = Integer.parseInt(studentsArray[middle].getMark());

            while( i <= j )
            {
                while( iMark > pivotMark )
                {
                    i++;
                    iMark = Integer.parseInt(studentsArray[i].getMark());
                }

                while( jMark < pivotMark )
                {
                    j--;
                    jMark = Integer.parseInt(studentsArray[j].getMark());
                }

                if( i <= j )
                {
                    toSwap = studentsArray[i];
                    studentsArray[i] = studentsArray[j];
                    studentsArray[j] = toSwap;
                    i++;
                    j--;
                }
            }

            if( low < j )
            {
                quickSort( studentsArray, low, j );
            }

            if( high > i )
            {
                quickSort( studentsArray, i, high );
            }
        }
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
