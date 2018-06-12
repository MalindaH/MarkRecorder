/**
 * Name: Linhui Huang (Malinda)
 * Course: CS30S
 * Teacher: Mr. Hardman
 * Final Project, Program #1
 * Date Last Modified: 6/12/2018
 */
package commalindah.httpsgithub.markrecorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StartingActivity extends AppCompatActivity {

    private Button sGetStarted;

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
        setContentView(R.layout.activity_starting);

        sGetStarted = (Button) findViewById(R.id.btn_get_started);

        //This adds an explicit intent to go to the MarkRecorderActivity when the sGetStarted button is clicked
        sGetStarted.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent startingIntent = new Intent(StartingActivity.this, MarkRecorderActivity.class);
                startActivity(startingIntent);
                overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
            }
        });
    }

    /**
     * onCreateOptionsMenu inflates the about menu and returns true to display it
     *
     * @param menu is the about menu
     * @return a boolean of true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about, menu);
        return true;
    }

    /**
     * onOptionsItemSelected makes the app go to the About class when the about button is clicked
     *
     * @param item is the item of the menu
     * @return actions associated with the about button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent aboutIntent = new Intent(StartingActivity.this, About.class);
            startActivity(aboutIntent);
            overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
