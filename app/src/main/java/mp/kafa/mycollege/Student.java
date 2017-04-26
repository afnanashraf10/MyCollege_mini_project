package mp.kafa.mycollege;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class Student extends Activity {
    AppSettings settings;
    WebView timetable;
    TextView name,branch,sem,dob,email,phone;
    Button stu_vtt;


    public void init()
    {
        settings=new AppSettings(getApplicationContext());
        name=(TextView)findViewById(R.id.prof_name);
        branch=(TextView)findViewById(R.id.prof_branch);
        sem=(TextView)findViewById(R.id.prof_sem);
        dob=(TextView)findViewById(R.id.prof_dob);
        email=(TextView)findViewById(R.id.prof_email);
        phone=(TextView)findViewById(R.id.prof_phone);
        stu_vtt=(Button)findViewById(R.id.stu_vtt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        getActionBar().hide();
        init();
        stu_vtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Student.this,TimeTable.class);
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
                sem.setText(myprofile.sem);
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
    }

