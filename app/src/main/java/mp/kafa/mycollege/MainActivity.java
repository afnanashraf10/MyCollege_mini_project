package mp.kafa.mycollege;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button login;
    EditText username,password;
    AppSettings settings;
    ProgressDialog progress;


    public void init()
    {
        login=(Button)findViewById(R.id.login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        settings=new AppSettings(getApplicationContext());
        progress=new ProgressDialog(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        init();

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progress.setTitle("Please wait..");
                progress.setMessage("Checking database");
                progress.setCancelable(false);
                progress.show();
                ServerConnector server=new ServerConnector(getApplicationContext());
                server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
                    @Override
                    public void onServerResponded(String responce)
                    {
                        progress.dismiss();
                        if (responce.length()==0)
                        {
                            Toast.makeText(MainActivity.this, "Username or Password error", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            settings.saveSettings("username",username.getText().toString());
                            settings.saveSettings("password",password.getText().toString());
                            String[] res=responce.split("____");
                            settings.saveSettings("id",res[0]);
                            settings.saveSettings("who",res[1]);
                            settings.saveSettings("branch",res[2]);
                            settings.saveSettings("sem",res[3]);
                            if (settings.retriveSettings("who").equals("admin"))
                            {
                                Intent x=new Intent(MainActivity.this,AdminHome.class);
                                startActivity(x);
                            }
                            else if (settings.retriveSettings("who").equals("student"))
                            {
                                Intent x=new Intent(MainActivity.this,Student.class);
                                startActivity(x);
                            }
                            else if (settings.retriveSettings("who").equals("teacher"))
                            {
                                Intent x=new Intent(MainActivity.this,Teacher.class);
                                startActivity(x);
                            }
                        }
                    }

                    @Override
                    public void onServerRevoked() {
                        Toast.makeText(MainActivity.this, "Oops..! No Internet Connection", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                });
                server.connectServer("http://leomessi10.esy.es/login.php?username="+username.getText().toString()+"&password="+password.getText().toString());

            }
        });
    }

}
