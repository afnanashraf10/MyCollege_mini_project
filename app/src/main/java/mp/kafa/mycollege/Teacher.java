package mp.kafa.mycollege;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class Teacher extends Activity {
    AppSettings settings;
    TextView name,branch,dob,email,phone;
    Button m_att;
    Button m_int;

    public void init()
    {
        m_int=(Button)findViewById(R.id.m_int);
        m_att=(Button)findViewById(R.id.m_att);
        settings=new AppSettings(getApplicationContext());
        name=(TextView)findViewById(R.id.prof_name);
        branch=(TextView)findViewById(R.id.prof_branch);
        dob=(TextView)findViewById(R.id.prof_dob);
        email=(TextView)findViewById(R.id.prof_email);
        phone=(TextView)findViewById(R.id.prof_phone);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        getActionBar().hide();
        init();

        m_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Teacher.this,AddInternalMarks.class);
                startActivity(i);
            }
        });

        m_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateCheck();
                Intent i=new Intent(Teacher.this,AddAttendance.class);
                startActivity(i);
            }
        });


        ServerConnector server=new ServerConnector(getApplicationContext());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {
                Gson gson=new Gson();
                LogIn myprofile;
                myprofile=gson.fromJson(responce,LogIn.class);
                name.setText(myprofile.fname+" "+myprofile.lname);
                branch.setText(myprofile.branch);
                dob.setText(myprofile.dob);
                email.setText(myprofile.email);
                phone.setText(myprofile.phone);

            }

            @Override
            public void onServerRevoked() {

            }
        });
        server.connectServer("http://leomessi10.esy.es/profile.php?username="+settings.retriveSettings("username")+"&password="+settings.retriveSettings("password"));

    }
    public void dateCheck()
    {
        ServerConnector server=new ServerConnector(getApplicationContext());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {
            if  (responce.equals("0"))
                {
                    studentEntry();
                }
            }

            @Override
            public void onServerRevoked() {

            }
        });
        server.connectServer("http://leomessi10.esy.es/attendancedate.php");
    }
    public void studentEntry()
    {
        ServerConnector server=new ServerConnector(getApplicationContext());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {

                Toast.makeText(getApplicationContext(), "Tested", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServerRevoked() {

            }
        });
        server.connectServer("http://leomessi10.esy.es/studentsadd.php");
    }
}
