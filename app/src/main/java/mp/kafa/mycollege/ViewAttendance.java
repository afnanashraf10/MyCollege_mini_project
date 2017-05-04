package mp.kafa.mycollege;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ViewAttendance extends Activity {
AppSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attndance);
        getActionBar().hide();
        settings=new AppSettings(getApplicationContext());
        WebView attendance=(WebView)findViewById(R.id.attendance);
        attendance.loadUrl("http://leomessi10.esy.es/displayattendance.php?studid="+settings.retriveSettings("id"));
    }
}
