package mp.kafa.mycollege;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class AddAttendance extends Activity {
    ListView studentslist;
    Spinner branch,sem,hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);
        getActionBar().hide();
        studentslist=(ListView)findViewById(R.id.studentslist);
        branch=(Spinner)findViewById(R.id.att_branch);
        sem=(Spinner)findViewById(R.id.att_semester);
        hour=(Spinner)findViewById(R.id.att_hour);

        hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ServerConnector server=new ServerConnector(getApplicationContext());
                server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner()
                {
                    @Override
                    public void onServerResponded(String responce)
                    {
                        List<LogIn> students;
                        Gson gson=new Gson();
                        students = gson.fromJson(responce, new TypeToken<List<LogIn>>(){}.getType());
                        AttendanceAdapter adapter=new AttendanceAdapter(students,getApplicationContext(),convertFunction(hour));
                        studentslist.setAdapter(adapter);
                    }

                    @Override
                    public void onServerRevoked() {

                    }
                });
                server.connectServer("http://leomessi10.esy.es/studentsbybranch.php?branch="+branch.getSelectedItem().toString()+"&sem="+sem.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public String convertFunction(Spinner myspinner)
    {
        String text=myspinner.getSelectedItem().toString();
        switch (text)
        {
            case "Period 1" : return "A1";
            case "Period 2" : return "A2";
            case "Period 3" : return "A3";
            case "Period 4" : return "A4";
            case "Period 5" : return "A5";
            case "Period 6" : return "A6";
            case "Period 7" : return "A7";
            default: return "error";
        }
    }

}
