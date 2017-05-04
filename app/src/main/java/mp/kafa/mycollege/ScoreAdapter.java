package mp.kafa.mycollege;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Amrutha on 5/4/2017.
 */

public class ScoreAdapter extends BaseAdapter {
    Context context;
    List<LogIn> logIns;
    AppSettings settings;
    String branch,sem;

    public ScoreAdapter(Context context, List<LogIn> logIns, String branch, String sem) {
        this.context = context;
        this.logIns = logIns;
        this.branch = branch;
        this.sem = sem;
    }

    @Override
    public int getCount() {
        return logIns.size();
    }

    @Override
    public Object getItem(int position) {
        return logIns.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        settings=new AppSettings(context);

        View v=View.inflate(context,R.layout.singlemark,null);
        final TextView name=(TextView)v.findViewById(R.id.user);
        final EditText s1=(EditText)v.findViewById(R.id.series1);
        final EditText s2=(EditText)v.findViewById(R.id.series2);
        final EditText a1=(EditText)v.findViewById(R.id.assignment1);
        final EditText a2=(EditText)v.findViewById(R.id.assignment2);
        final Button upload=(Button)v.findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerConnector server=new ServerConnector(context);
                server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
                    @Override
                    public void onServerResponded(String responce) {
                        if (responce.length()==4)
                        {
                            Toast.makeText(context, "\uD83D\uDC4D", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context, "\uD83D\uDC4E", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onServerRevoked() {
                        Toast.makeText(context, "Unable to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
                server.connectServer("http://leomessi10.esy.es/addmark.php?studid="+logIns.get(position).id+"&teachid="+settings.retriveSettings("id")+"&s1="+s1.getText().toString()+"&s2="+s2.getText().toString()+"&a1="+a1.getText().toString()+"&a2="+a2.getText().toString()+"&branch="+branch+"&sem="+sem);
            }
        });

        name.setText(logIns.get(position).fname+" "+logIns.get(position).lname);
        return v;
    }
}
