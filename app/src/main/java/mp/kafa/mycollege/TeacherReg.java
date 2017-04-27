package mp.kafa.mycollege;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


public class TeacherReg extends Fragment
{
    AlertDialog.Builder alert;
    ProgressDialog progress;
    AppSettings settings;
    EditText fname;
    EditText lname;
    EditText username;
    EditText password;
    EditText confirmpassword;
    TextView dob;
    EditText age;
    EditText emailid;
    EditText phone;
    RadioButton male,female;
    Button signup;
    Spinner branch;

    public void init(View v)
    {
        alert=new AlertDialog.Builder(v.getContext());
        branch=(Spinner)v.findViewById(R.id.sub_branch);
        progress=new ProgressDialog(getActivity());
        settings=new AppSettings(getActivity());
        fname=(EditText)v.findViewById(R.id.t_fname);
        lname=(EditText)v.findViewById(R.id.t_lname);
        username=(EditText)v.findViewById(R.id.t_username);
        password=(EditText)v.findViewById(R.id.t_password);
        confirmpassword=(EditText)v.findViewById(R.id.t_conpassword);
        age=(EditText)v.findViewById(R.id.t_age);
        emailid=(EditText)v.findViewById(R.id.t_emailid);
        phone=(EditText)v.findViewById(R.id.t_phone);
        male=(RadioButton)v.findViewById(R.id.t_male);
        female=(RadioButton)v.findViewById(R.id.t_female);
        dob=(TextView)v.findViewById(R.id.t_dob);
        signup=(Button)v.findViewById(R.id.t_signup);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.teacherreg, container, false);
        init(rootView);

        applyFilter(fname);
        applyFilter(lname);

        dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog picker=new DatePickerDialog();
                picker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
                    {
                        dob.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                    }
                });
                picker.show(getFragmentManager(),"");
            }
        });

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isAllFilled())
                {
                    progress.setTitle("Please wait..");
                    progress.setMessage("Registring new teacher");
                    progress.setCancelable(false);
                    progress.show();
                    uploadTeacher();
                }
            }
        });

        return rootView;
    }

    public void applyFilter(EditText e)
    {
        InputFilter filtertxt = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        e.setFilters(new InputFilter[]{filtertxt});
    }

    public boolean isAllFilled()
    {
        if (fname.getText().length()>0)
        {
            if (lname.getText().length()>0)
            {
                if (username.getText().length()>0)
                {
                    if (password.getText().length()>=5)
                    {
                        if (confirmpassword.getText().toString().equals(password.getText().toString()))
                        {
                            if (dob.getText().equals("click here"))
                            {
                                Toast.makeText(getActivity(), "DOB invalid", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            else
                            {
                                if (age.getText().length()>0)
                                {
                                    if (male.isChecked()||female.isChecked())
                                    {
                                        if (emailid.getText().length()>0&&emailid.getText().toString().contains("@"))
                                        {
                                            if (phone.getText().length()==10)
                                            {
                                                return true;
                                            }
                                            else
                                            {
                                                Toast.makeText(getActivity(), "Phone invalid", Toast.LENGTH_SHORT).show();
                                                return false;
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity(), "Email ID invalid", Toast.LENGTH_SHORT).show();
                                            return false;
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), "Gender not selected", Toast.LENGTH_SHORT).show();
                                        return false;
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Age invalid", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Password is not matching", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Password length must be atleast 6", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Username invalid", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else
            {
                Toast.makeText(getActivity(), "Name invalid", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else
        {
            Toast.makeText(getActivity(), "Name invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void uploadTeacher()
    {
        ServerConnector server=new ServerConnector(getActivity());
        server.setOnServerStatusListner(new ServerConnector.OnServerStatusListner()
        {
            @Override
            public void onServerResponded(String responce) {
                if (responce.length() == 4) {
                    settings.saveSettings("tid", responce);
                    progress.dismiss();
                    alert.setTitle("Registration Succesfull");
                    alert.setMessage("Succesfully registered account");
                    alert.show();
                } else {
                    progress.dismiss();
                    alert.setTitle("Registration Error");
                    alert.setMessage("Username is already registered");
                    alert.show();
                }

            }

            @Override
            public void onServerRevoked()
            {
                Toast.makeText(getActivity(), "Can't connect to server", Toast.LENGTH_SHORT).show();
            }
        });

        String gender;
        if (male.isChecked())
        {
            gender="male";
        }
        else
        {
            gender="female";
        }

        server.connectServer("http://leomessi10.esy.es/signup.php?fname="+fname.getText().toString()+"&lname="+lname.getText().toString()+"&username="+username.getText().toString()+"&password="+password.getText().toString()+"&who=teacher&dob="+dob.getText().toString()+"&age="+age.getText().toString()+"&gender="+gender+"&email="+emailid.getText().toString()+"&phone="+phone.getText().toString()+"&branch="+branch.getSelectedItem().toString());
    }

}
