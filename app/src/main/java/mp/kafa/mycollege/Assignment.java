package mp.kafa.mycollege;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class Assignment extends Fragment {
    AlertDialog.Builder alert;
    AppSettings settings;
    ProgressDialog progress;
    Spinner branch,sem;
    Spinner ta_branch,ta_sem,ta_sub,ta_tea;
    Button add,ta_add,sub_tt;
    TextView subject;

    public void init(View v)
    {
        alert=new AlertDialog.Builder(v.getContext());
        settings=new AppSettings(getActivity());
        progress=new ProgressDialog(getActivity());
        branch=(Spinner)v.findViewById(R.id.sub_branch);
        sem=(Spinner)v.findViewById(R.id.sub_semester);
        ta_sub=(Spinner)v.findViewById(R.id.ta_sub);
        ta_tea=(Spinner)v.findViewById(R.id.ta_tea);
        ta_branch=(Spinner)v.findViewById(R.id.ta_branch);
        ta_sem=(Spinner)v.findViewById(R.id.ta_semester);
        add=(Button)v.findViewById(R.id.sub_add);
        ta_add=(Button)v.findViewById(R.id.ta_add);
        sub_tt=(Button)v.findViewById(R.id.sub_tt);
        subject=(TextView)v.findViewById(R.id.sub_subject);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_assignment, container, false);
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

        ta_sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                downloadSubjects();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ta_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                downloadTeacher();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ta_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected_teacher=ta_tea.getSelectedItem().toString();
                String[] split=selected_teacher.split("-");
                storeTeacher(split[0]);
            }
        });


        sub_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(),TimeTab.class);
                startActivity(i);
            }
        });

        return rootView;
    }

    public void addTeacherId()
    {

    }

    public void uploadSubject()
    {
        ServerConnector server=new ServerConnector(getActivity());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {

                if (responce.length() == 4) {
                    settings.saveSettings("subid", responce);
                    progress.dismiss();
                } else {
                    progress.dismiss();
                    alert.setTitle("Registration Error");
                    alert.setMessage("Username is already registered");
                    alert.show();
                }
            }
            @Override
            public void onServerRevoked() {
                Toast.makeText(getActivity(), "Oops..! An error occured", Toast.LENGTH_SHORT).show();
            }
        });
        server.connectServer("http://leomessi10.esy.es/subupload.php?branch="+branch.getSelectedItem().toString()+"&name="+subject.getText().toString()+"&sem="+sem.getSelectedItem().toString());
    }

    public void downloadSubjects()
    {
        ServerConnector server=new ServerConnector(getActivity());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {
                List<Subject> subjects=new ArrayList<Subject>();
                Gson gson=new Gson();
                subjects = gson.fromJson(responce, new TypeToken<List<Subject>>(){}.getType());
                List<String> subjects_from_server=new ArrayList<String>();
                for (int i=0;i<subjects.size();i++)
                {
                    subjects_from_server.add(subjects.get(i).name);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, subjects_from_server);
                ta_sub.setAdapter(adapter);
            }

            @Override
            public void onServerRevoked() {

            }
        });
        server.connectServer("http://leomessi10.esy.es/allsubjects.php?branch="+ta_branch.getSelectedItem().toString()+"&sem="+ta_sem.getSelectedItem().toString());
    }

    public void downloadTeacher()
    {
        ServerConnector server=new ServerConnector(getActivity());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {
                List<LogIn> teachers=new ArrayList<LogIn>();
                Gson gson=new Gson();
                teachers = gson.fromJson(responce, new TypeToken<List<LogIn>>(){}.getType());
                List<String> teachers_from_server=new ArrayList<String>();
                for (int i=0;i<teachers.size();i++)
                {
                    teachers_from_server.add(String.valueOf(teachers.get(i).id)+"-"+teachers.get(i).fname);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, teachers_from_server);
                ta_tea.setAdapter(adapter);
            }

            @Override
            public void onServerRevoked() {

            }
        });
        server.connectServer("http://leomessi10.esy.es/allteachers.php?branch="+ta_branch.getSelectedItem().toString());
    }

    public void storeTeacher(String id)
    {
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();
        ServerConnector server=new ServerConnector(getActivity());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner() {
            @Override
            public void onServerResponded(String responce) {
                progress.dismiss();
                if(responce.length()==4)
                {
                    alert.setTitle("Success").setMessage("Teacher assigned").show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Some technical errors", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onServerRevoked() {
                progress.dismiss();
                Toast.makeText(getActivity(), "No internet connectivity", Toast.LENGTH_SHORT).show();
            }
        });
        server.connectServer("http://leomessi10.esy.es/addteacher.php?id="+id+"&branch="+ta_branch.getSelectedItem().toString()+"&name="+ta_sub.getSelectedItem().toString()+"&sem="+ta_sem.getSelectedItem().toString());
    }
}