package mp.kafa.mycollege;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class TimeTableAdd extends Fragment {
    AppSettings settings;
    ProgressDialog progress;
    Spinner branch,sem;
    Button add;
    TextView subject;

    public void init(View v)
    {
        settings=new AppSettings(getActivity());
        progress=new ProgressDialog(getActivity());
        branch=(Spinner)v.findViewById(R.id.sub_branch);
        sem=(Spinner)v.findViewById(R.id.sub_semester);
        add=(Button)v.findViewById(R.id.sub_add);
        subject=(TextView)v.findViewById(R.id.sub_subject);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.timetableadd, container, false);
        init(rootView);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subject.getText().length()>0)
                {
                        progress.setTitle("Please wait");
                        progress.setMessage("Adding subjects...");
                        progress.setCancelable(false);
                        progress.show();
                        uploadSubject();
                }
                else
                {
                    Toast.makeText(getActivity(), "No subjects specified", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    public void uploadSubject()
    {
        ServerConnector server=new ServerConnector(getActivity());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {
                settings.saveSettings("subid",responce);
                progress.dismiss();
            }

            @Override
            public void onServerRevoked() {
                Toast.makeText(getActivity(), "Oops..! An error occured", Toast.LENGTH_SHORT).show();
            }
        });
        server.connectServer("http://leomessi10.esy.es/subupload.php?branch="+branch.getSelectedItem().toString()+"&name="+subject.getText().toString()+"&sem="+sem.getSelectedItem().toString());
    }

}