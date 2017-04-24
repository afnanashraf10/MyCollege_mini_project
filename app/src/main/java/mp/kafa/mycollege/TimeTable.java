package mp.kafa.mycollege;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TimeTable extends Activity {
    AppSettings settings;
    WebView timetable;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);


        settings=new AppSettings(getApplicationContext());
        timetable=(WebView)findViewById(R.id.timetable);
        timetable.getSettings().setBuiltInZoomControls(true);
        timetable.getSettings().setDisplayZoomControls(false);
        timetable.loadUrl("http://leomessi10.esy.es/showtimetable.php?branch="+settings.retriveSettings("branch")+"&sem="+settings.retriveSettings("sem"));


    }

}
