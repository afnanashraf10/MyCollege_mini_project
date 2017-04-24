package mp.kafa.mycollege;

import android.app.Activity;
import android.os.Bundle;

public class Teacher extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        getActionBar().hide();
    }
}
