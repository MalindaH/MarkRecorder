package commalindah.httpsgithub.markrecorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class About extends AppCompatActivity {

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
        setContentView(R.layout.activity_about);
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
