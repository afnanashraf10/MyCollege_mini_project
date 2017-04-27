package mp.kafa.mycollege;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TimeTab extends Activity {

    AlertDialog.Builder alert;
    Spinner tt_branch, tt_sem, tt_hr1, tt_hr2, tt_hr3, tt_hr4, tt_hr5, tt_hr6, tt_hr7, tt_day;
    AppSettings settings;
    Button tt_add;
    ProgressDialog progress;


    public void init() {
        alert=new AlertDialog.Builder(this);
        progress=new ProgressDialog(getApplicationContext());
        settings=new AppSettings(getApplicationContext());
        tt_branch = (Spinner) findViewById(R.id.tt_branch);
        tt_sem = (Spinner) findViewById(R.id.tt_semester);
        tt_day = (Spinner) findViewById(R.id.tt_day);
        tt_hr1 = (Spinner) findViewById(R.id.tt_hr1);
        tt_hr2 = (Spinner) findViewById(R.id.tt_hr2);
        tt_hr3 = (Spinner) findViewById(R.id.tt_hr3);
        tt_hr4 = (Spinner) findViewById(R.id.tt_hr4);
        tt_hr5 = (Spinner) findViewById(R.id.tt_hr5);
        tt_hr6 = (Spinner) findViewById(R.id.tt_hr6);
        tt_hr7 = (Spinner) findViewById(R.id.tt_hr7);
        tt_add=(Button)findViewById(R.id.tt_add);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_tab);
        init();
        tt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadTimeTable();
            }
        });

        tt_sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                downloadSubjects();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void downloadSubjects() {
        ServerConnector server = new ServerConnector(getApplicationContext());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {
                List<Subject> subjects = new ArrayList<Subject>();
                Gson gson = new Gson();
                subjects = gson.fromJson(responce, new TypeToken<List<Subject>>() {
                }.getType());
                List<String> subjects_from_server = new ArrayList<String>();
                for (int i = 0; i < subjects.size(); i++) {
                    subjects_from_server.add(subjects.get(i).name);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, subjects_from_server);
                tt_hr1.setAdapter(adapter);
                tt_hr2.setAdapter(adapter);
                tt_hr3.setAdapter(adapter);
                tt_hr4.setAdapter(adapter);
                tt_hr5.setAdapter(adapter);
                tt_hr6.setAdapter(adapter);
                tt_hr7.setAdapter(adapter);

            }

            @Override
            public void onServerRevoked() {

            }
        });
        server.connectServer("http://leomessi10.esy.es/allsubjects.php?branch=" + tt_branch.getSelectedItem().toString() + "&sem=" + tt_sem.getSelectedItem().toString());
    }

    public void uploadTimeTable() {
        ServerConnector server = new ServerConnector(getApplicationContext());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {


                if (responce.length() == 28)
                {
                    progress.dismiss();
                }
                else
                {
                    progress.dismiss();
                    alert.setTitle("Oops..! An error occurred");
                    alert.setMessage("Row already added");
                    alert.show();
                }
            }

            @Override
            public void onServerRevoked() {

            }
        });
        server.connectServer("http://leomessi10.esy.es/timetableadd.php?branch=" + tt_branch.getSelectedItem().toString() + "&sem=" + tt_sem.getSelectedItem().toString() + "&day=" + tt_day.getSelectedItem().toString() + "&p1=" + tt_hr1.getSelectedItem().toString() + "&p2=" + tt_hr2.getSelectedItem().toString() + "&p3=" + tt_hr3.getSelectedItem().toString() + "&p4=" + tt_hr4.getSelectedItem().toString() + "&p5=" + tt_hr5.getSelectedItem().toString() + "&p6=" + tt_hr6.getSelectedItem().toString() + "&p7=" + tt_hr7.getSelectedItem().toString());

    }
}

