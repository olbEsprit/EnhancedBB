package kampus.enhancedbb;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewBulletinActivity extends AppCompatActivity {
    EditText editText;
    EditText editTitle;
    EditText DateStart;
    EditText DateEnd;
    private int _Bulletin_ID=0;
    RestService restService;
    private MultiSelectionSpinner subDivMultiSpinner;
    private MultiSelectionSpinner profileMultiSpinner;
    private long[] ProfileIDs;
    private long[] SubdivisionIDs;
    private Account LocalAccount;
    public List<Profile> localProfiles;
    public List<Subdivision> localSubdivs;
    private TextView startDateView;
    private TextView endDateView;
    private int startYear, startMonth, startDay;
    private int endYear, endMonth, endDay;
    int STARTDIALOG_DATE = 2;
    int ENDDIALOG_DATE = 1;
    String addResponse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestService();
        setContentView(R.layout.activity_new_bulletin);
        LocalAccount = MainActivity.nowAccount;

        GetProfileList();
        GetSubdivsList();
        profileMultiSpinner = (MultiSelectionSpinner) findViewById(R.id.profSpinner);
        profileMultiSpinner.setEnabled(false);
        subDivMultiSpinner = (MultiSelectionSpinner) findViewById(R.id.subdivSpinner);
        subDivMultiSpinner.setEnabled(false);

        if(!(LocalAccount.isModerator))
        {
            Toast.makeText(this,"Вам запрещено добавлять объявление! Пожалуйста, вернитесь назад.", Toast.LENGTH_LONG).show();
        }

        final Calendar c = Calendar.getInstance();
        endYear =  c.get(Calendar.YEAR);
        endMonth = c.get(Calendar.MONTH);
        endDay = c.get(Calendar.DAY_OF_MONTH);
        startYear =  c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH);
        startDay = c.get(Calendar.DAY_OF_MONTH);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editText = (EditText) findViewById(R.id.editText);

        startDateView = (TextView) findViewById(R.id.textView3);
        endDateView = (TextView) findViewById(R.id.textView2);






        final Button exbutton = (Button) findViewById(R.id.ExitButton);
        final Button svbutton = (Button) findViewById(R.id.SaveButton);
        final Button endDateButton = (Button) findViewById(R.id.dateEndButton);
        final Button startDateButton = (Button) findViewById(R.id.dateStartButton);
        exbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        svbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddNewBulletin();
            }
        });
        endDateButton.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View v)
            {
                showDialog(ENDDIALOG_DATE);
            }
        });

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(STARTDIALOG_DATE);
            }
        });

        _Bulletin_ID = 0;
        Intent intent = getIntent();
        _Bulletin_ID = intent.getIntExtra("id", 0);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == ENDDIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, endDateCallBack, endYear, endMonth, endDay);
            return tpd;
        }
        if (id == STARTDIALOG_DATE)
        {
            DatePickerDialog tpd = new DatePickerDialog(this, startDateCallBack, startYear, startMonth, startDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener endDateCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                endYear =  year;
                endMonth = monthOfYear;
                endDay = dayOfMonth;
            endDateView.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    };

    DatePickerDialog.OnDateSetListener startDateCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear++;
            startYear =  year;
            startMonth = monthOfYear;
            startDay = dayOfMonth;
            startDateView.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    };


    public void AddNewBulletin() {
        NewBulletin bb = new NewBulletin();
        boolean isError = false;
        if (!(editTitle.getText().toString().equals(""))) {
            bb.subject = editTitle.getText().toString();
        }
        else
        {
            Toast.makeText(this, "Необходимо ввести тему объявления", Toast.LENGTH_LONG).show();
            isError = true;
        }

        if (!(editText.getText().toString().equals(""))) {
            bb.text = editText.getText().toString();
        }
        else
        {
            Toast.makeText(this, "Необходимо ввести текст объявления", Toast.LENGTH_LONG).show();
            isError = true;
        }

        if (!(startDateView.getText().toString().equals("Дата начала"))) {
            bb.startDate = startDateView.getText().toString();
        }
        else
        {
            Toast.makeText(this, "Необходимо указать дату начала", Toast.LENGTH_LONG).show();
            isError = true;
        }

        if (!(endDateView.getText().toString().equals("Дата конца")))
        {
            bb.endDate = endDateView.getText().toString();
        }
        else
        {
            Toast.makeText(this, "Необходимо указать дату конца", Toast.LENGTH_LONG).show();
            isError = true;
        }
        bb.userID = MainActivity.nowAccount.id;


        List<Integer> selectedProf = profileMultiSpinner.getSelectedIndices();
        long[] profIds = new long[selectedProf.size()];
        for(int i =0;i<selectedProf.size();i++){
            profIds[i] = ProfileIDs[selectedProf.get(i)];
        }
        bb.profIDs = profIds;

        List<Integer> selectedSubdivs = subDivMultiSpinner.getSelectedIndices();
        long[] subvidIds = new long[selectedSubdivs.size()];
        for(int i =0;i<selectedSubdivs.size();i++){
            subvidIds[i] = SubdivisionIDs[selectedSubdivs.get(i)];
        }
        bb.subdivIDs = subvidIds;

        if(!(isError))
        {

            restService = new RestService();
            restService.getService().addNewBulletin(bb, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    Toast.makeText(NewBulletinActivity.this, s, Toast.LENGTH_LONG).show();
                    finish();

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(NewBulletinActivity.this, error.toString() ,Toast.LENGTH_LONG);

                }
            });

        }
    }


    public String[] GetProfilesNames(List<Profile> input)
    {
        List<String> result = new LinkedList<String>();

        for(int i=0;i<input.size();i++)
        {
            result.add(input.get(i).getName());
        }
        return result.toArray(new String[result.size()]);
    }


    public long[] GetProfilesIDs(List<Profile> input)
    {
        //List<long> result = new LinkedList<long>();
        long[] result = new long[input.size()];

        for(int i=0;i<input.size();i++)
        {
            result[i]=(input.get(i).getId());
        }
        return result;
    }


    public String[] GetSubdivsNames(List<Subdivision> input)
    {
        List<String> result = new LinkedList<String>();

        for(int i=0;i<input.size();i++)
        {
            result.add(input.get(i).getName());
        }
        return result.toArray(new String[result.size()]);
    }


    public long[] GetSubdivsIDs(List<Subdivision> input)
    {
        //List<long> result = new LinkedList<long>();
        long[] result = new long[input.size()];

        for(int i=0;i<input.size();i++)
        {
            result[i]=(input.get(i).getId());
        }
        return result;
    }

    public void GetProfileList()
    {
        restService = new RestService();
        restService.getService().getProfileList(new Callback<List<Profile>>() {
            @Override
            public void success(List<Profile> profiles, Response response) {
                localProfiles = profiles;
                profileMultiSpinner.setItems(GetProfilesNames(localProfiles));
                ProfileIDs = GetProfilesIDs(localProfiles);
                profileMultiSpinner.setEnabled(true);

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(NewBulletinActivity.this, error.toString() ,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void GetSubdivsList(){
        restService = new RestService();
        restService.getService().getSubdivisonList(new Callback<List<Subdivision>>() {
            @Override
            public void success(List<Subdivision> subdivisions, Response response) {
                localSubdivs = subdivisions;
                subDivMultiSpinner.setItems(GetSubdivsNames(localSubdivs));
                SubdivisionIDs = GetSubdivsIDs(localSubdivs);
                subDivMultiSpinner.setEnabled(true);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(NewBulletinActivity.this, error.toString() ,Toast.LENGTH_LONG).show();


            }
        });
    }
}
