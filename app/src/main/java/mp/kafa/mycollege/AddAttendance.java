package mp.kafa.mycollege;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class AddAttendance extends Activity {
    ListView studentslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);
        getActionBar().hide();
        studentslist=(ListView)findViewById(R.id.studentslist);



        ServerConnector server=new ServerConnector(getApplicationContext());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {
                List<LogIn> students;
                Gson gson=new Gson();
                students = gson.fromJson(responce, new TypeToken<List<LogIn>>(){}.getType());
                AttendanceAdapter adapter=new AttendanceAdapter(students,getApplicationContext());
                studentslist.setAdapter(adapter);
            }

            @Override
            public void onServerRevoked() {

            }
        });
        server.connectServer("http://leomessi10.esy.es/studentsbybranch.php?branch=CSE&sem=S6");

    }
}
