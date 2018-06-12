package commalindah.httpsgithub.markrecorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MarkRecorderActivity extends AppCompatActivity {

    private static Student[] studentsArray;
    private static int numStudentsAdded;

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mMark;
    private TextView mErrorMessage;
    private ListView mOptions;

    private String firstName;
    private String lastName;
    private String mark;

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
        setContentView(R.layout.activity_mark_recorder);

        mFirstName = (EditText) findViewById(R.id.et_first_name);
        mLastName = (EditText) findViewById(R.id.et_last_name);
        mMark = (EditText) findViewById(R.id.et_mark);
        mErrorMessage = (TextView) findViewById(R.id.tv_error_message_1);
        mOptions = (ListView) findViewById(R.id.lv_options);

        studentsArray = new Student[100];
        numStudentsAdded = 0;

        /**
         * The next lines add explicit intents to go to the SortingActivity or the Calculating Activity
         * when the corresponding items in the ListView are clicked, and add an implicit intent to let
         * the user choose to go to another app when the Contact Us item is clicked
         */
        String[] options = new String[]{"Sort Students", "Calculate Average", "Contact Us"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.mytextview, options);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                Intent sortingIntent = new Intent( MarkRecorderActivity.this, SortingActivity.class );
                Intent calculatingIntent = new Intent( MarkRecorderActivity.this, CalculatingActivity.class);
                Intent contactUsIntent;

                if( position == 0 )
                {
                    sortingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    sortingIntent.putExtra("StudentsArray", studentsArray);
                    sortingIntent.putExtra("EXTRA_NumStudentsAdded", numStudentsAdded);
                    startActivity( sortingIntent );
                    overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
                }
                else if(position == 1)
                {
                    calculatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    calculatingIntent.putExtra("StudentsArray", studentsArray);
                    calculatingIntent.putExtra("EXTRA_NumStudentsAdded", numStudentsAdded);
                    startActivity( calculatingIntent );
                    overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
                }
                else if(position == 2)
                {
                    contactUsIntent = new Intent(Intent.ACTION_SEND);
                    contactUsIntent.setType("text/plain");
                    contactUsIntent.putExtra(Intent.EXTRA_EMAIL, "malindahlh.mh@gmail.com");
                    contactUsIntent.putExtra(Intent.EXTRA_TEXT, "I want to say this about Mark Recorder: \n");

                    startActivity(Intent.createChooser(contactUsIntent, "Please select an app..."));
                    overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
                }
            }
        };

        mOptions.setAdapter(arrayAdapter);
        mOptions.setOnItemClickListener(itemClickListener);
    }

    /**
     * record adds a new student to the studentsArray we will need to sort
     *
     * @param vw is the View that is related to this method
     * @return Nothing is returned
     */
    public void record(View vw)
    {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        Student student;

        if ( mFirstName.length() == 0 && mLastName.length() == 0 )
        {
            mErrorMessage.setText("You must enter a name to record a student.");
        }
        else
        {
            if (numStudentsAdded >= studentsArray.length)
            {
                mErrorMessage.setText("You cannot add any more students.");
            }
            else
            {
                firstName = mFirstName.getText().toString();
                lastName = mLastName.getText().toString();
                mark = mMark.getText().toString();

                student = new Student( firstName, lastName, mark );
                studentsArray[numStudentsAdded] = student;
                numStudentsAdded += 1;

                mFirstName.setText("");
                mLastName.setText("");
                mMark.setText("");

                if (inputManager != null)
                {
                    inputManager.hideSoftInputFromWindow(vw.getApplicationWindowToken(), 0);
                }

                if(numStudentsAdded == 1)
                {
                    mErrorMessage.setText("1 student successfully recorded.");
                }
                else
                {
                    mErrorMessage.setText( numStudentsAdded + " students successfully recorded.");
                }
            }
        }
    }

    /**
     * onCreateOptionsMenu inflates the restart menu and returns true to display it
     *
     * @param menu is the restart menu
     * @return a boolean of true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restart, menu);
        return true;
    }

    /**
     * onOptionsItemSelected clears the studentsArray when the restart button is clicked
     *
     * @param item is the item of the menu
     * @return actions associated with the restart button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_restart) {
            studentsArray = new Student[100];
            numStudentsAdded = 0;
            mErrorMessage.setText("");
            return true;
        }

        return super.onOptionsItemSelected(item);
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
