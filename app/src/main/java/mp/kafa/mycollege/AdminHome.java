package mp.kafa.mycollege;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends Activity {
    Button newregistarion;
    Button vstudents;
    Button vteachers;

    public void init() {
        newregistarion = (Button) findViewById(R.id.new_reg);
        vstudents = (Button) findViewById(R.id.v_stu);
        vteachers = (Button) findViewById(R.id.v_tea);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        getActionBar().hide();
        init();
        newregistarion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent x=new Intent(AdminHome.this,Admin.class);
                startActivity(x);
            }
        });
    }
}
