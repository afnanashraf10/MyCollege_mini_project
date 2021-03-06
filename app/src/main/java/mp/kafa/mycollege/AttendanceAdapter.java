package mp.kafa.mycollege;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class AttendanceAdapter extends BaseAdapter {
    String stat="P";
    List<LogIn> students;
    Context context;
    String hour;

    public AttendanceAdapter(List<LogIn> students, Context context, String hour) {
        this.students = students;
        this.context = context;
        this.hour = hour;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v=View.inflate(context,R.layout.singleattendance,null);
        final CheckBox attendance=(CheckBox)v.findViewById(R.id.student);

        attendance.setText(students.get(position).fname+" "+students.get(position).lname);
        attendance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                ServerConnector server=new ServerConnector(context);
                server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
                    @Override
                    public void onServerResponded(String responce) {

                    }

                    @Override
                    public void onServerRevoked() {

                    }
                });
                if(attendance.isChecked())
                {
                    stat="A";
                }
                else
                {
                    stat="P";
                }
                server.connectServer("http://leomessi10.esy.es/addattendance.php?studid="+String.valueOf(students.get(position).id)+"&period="+hour+"&stat="+stat);
            }
        });

        return v;
    }
    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
        String strDate = "Current Date : " + mdformat.format(calendar.getTime());
        return strDate;
    }
}
