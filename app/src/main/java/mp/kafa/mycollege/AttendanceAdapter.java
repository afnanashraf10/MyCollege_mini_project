package mp.kafa.mycollege;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.List;


public class AttendanceAdapter extends BaseAdapter {
    List<LogIn> students;
    Context context;

    public AttendanceAdapter(List<LogIn> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(context,R.layout.singleattendance,null);
        CheckBox attendance=(CheckBox)v.findViewById(R.id.student);

        attendance.setText(students.get(position).fname+" "+students.get(position).lname);

        attendance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ServerConnector server=new ServerConnector(context);
                server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
                    @Override
                    public void onServerResponded(String responce) {

                    }

                    @Override
                    public void onServerRevoked() {

                    }
                });
                server.connectServer("");
            }
        });

        return v;
    }
}
