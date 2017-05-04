package mp.kafa.mycollege;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class AddInternalMarks extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_internal_marks);
        getActionBar().hide();

        final Spinner branch=(Spinner)findViewById(R.id.att_branch);
        final ListView studentslist=(ListView)findViewById(R.id.studentslist);
        final Spinner sem=(Spinner)findViewById(R.id.att_semester);

        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
                        ScoreAdapter adapter=new ScoreAdapter(getApplicationContext(),students,branch.getSelectedItem().toString(),sem.getSelectedItem().toString());
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
}
