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

import java.util.ArrayList;
import java.util.List;

public class ViewTeachers extends Activity {
    Spinner branch;
    ListView mylist;

    public void init()
    {
        branch=(Spinner)findViewById(R.id.att_branch);
        mylist=(ListView) findViewById(R.id.mylist);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teachers);
        init();

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ServerConnector server=new ServerConnector(getApplicationContext());
                server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
                    @Override
                    public void onServerResponded(String responce) {
                        List<OneStudent> allstudents=new ArrayList<>();
                        Gson gson=new Gson();
                        allstudents = gson.fromJson(responce, new TypeToken<List<OneStudent>>(){}.getType());
                        TeacherViewAdapter adapter=new TeacherViewAdapter(getApplicationContext(),allstudents,ViewTeachers.this);
                        mylist.setAdapter(adapter);
                    }

                    @Override
                    public void onServerRevoked() {
                        Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_SHORT).show();
                    }
                });
                server.connectServer("http://leomessi10.esy.es/allteachers.php?branch="+branch.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
