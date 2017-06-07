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

public class ViewStudents extends Activity {
    Spinner branch,sem;
    ListView mylist;

    public void init()
    {
        branch=(Spinner)findViewById(R.id.att_branch);
        sem=(Spinner)findViewById(R.id.att_semester);
        mylist=(ListView) findViewById(R.id.mylist);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        init();


        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ServerConnector server=new ServerConnector(getApplicationContext());
                server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
                    @Override
                    public void onServerResponded(String responce) {
                        List<OneStudent> allstudents=new ArrayList<>();
                        Gson gson=new Gson();
                        allstudents = gson.fromJson(responce, new TypeToken<List<OneStudent>>(){}.getType());
                        StudentViewAdapter adapter=new StudentViewAdapter(getApplicationContext(),allstudents,ViewStudents.this);
                        mylist.setAdapter(adapter);
                    }

                    @Override
                    public void onServerRevoked() {
                        Toast.makeText(ViewStudents.this, "Error occured", Toast.LENGTH_SHORT).show();
                    }
                });
                server.connectServer("http://leomessi10.esy.es/allstudents.php?branch="+branch.getSelectedItem()+"&sem="+sem.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
