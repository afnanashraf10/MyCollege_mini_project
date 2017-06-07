package mp.kafa.mycollege;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Amrutha on 06-Jun-17.
 */

public class TeacherViewAdapter extends BaseAdapter {
    Context context;
    List<OneStudent> allteachers;
    Activity temp;
    AlertDialog.Builder alert;

    public TeacherViewAdapter(Context context, List<OneStudent> allteachers, Activity temp) {
        this.context = context;
        this.allteachers = allteachers;
        this.temp = temp;
        alert=new AlertDialog.Builder(temp);
    }

    @Override
    public int getCount() {
        return allteachers.size();
    }

    @Override
    public Object getItem(int position) {
        return allteachers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v=View.inflate(context,R.layout.one_teacher,null);
        TextView name=(TextView)v.findViewById(R.id.name);
        name.setText(allteachers.get(position).getFirstname()+" "+allteachers.get(position).getLastname());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.setTitle("Delete ?");
                alert.setMessage("Are you sure to delete this record?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ServerConnector server=new ServerConnector(context);
                        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner()
                        {
                            @Override
                            public void onServerResponded(String responce)
                            {
                                if (responce.equals("success"))
                                {
                                    Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onServerRevoked() {
                                Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
                            }
                        });
                        server.connectServer("http://leomessi10.esy.es/delete.php?id="+allteachers.get(position).getId());
                    }
                });
                alert.show();
                notifyDataSetChanged();
            }
        });
        return v;
    }
}
