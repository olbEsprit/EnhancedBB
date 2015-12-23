package kampus.enhancedbb;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestService();
        setContentView(R.layout.activity_new_bulletin);
        long testid = MainActivity.nowAccount.id;
        String testids = String.valueOf(testid);
        LocalAccount = MainActivity.nowAccount;

        subDivMultiSpinner = (MultiSelectionSpinner) findViewById(R.id.subdivSpinner);
        subDivMultiSpinner.setItems(GetSubdivsNames(LocalAccount.subdivisions));
        SubdivisionIDs = GetSubdivsIDs(LocalAccount.subdivisions);

        profileMultiSpinner = (MultiSelectionSpinner) findViewById(R.id.profSpinner);
        profileMultiSpinner.setItems(GetProfilesNames(LocalAccount.profiles));
        ProfileIDs = GetProfilesIDs(LocalAccount.profiles);

        final Button exbutton = (Button) findViewById(R.id.ExitButton);
        final Button svbutton = (Button) findViewById(R.id.SaveButton);
        exbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        svbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddNewBulletin();
                finish();
            }
        });

        _Bulletin_ID = 0;
        Intent intent = getIntent();
        _Bulletin_ID = intent.getIntExtra("id", 0);
    }

    public void AddNewBulletin()
    {
        NewBulletin bb = new NewBulletin();

          //bb.subject = editTitle.getText().toString();

 //          bb.text = editText.getText().toString();
        //bb.userID = MainActivity.nowAccount.id;
          //  bb.startDate = DateStart.getText().toString();
            //bb.endDate = DateEnd.getText().toString();

        bb.text = "Hell";
        bb.subject = "My";

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

        Toast.makeText(this, String.valueOf(bb.subdivIDs[0]), Toast.LENGTH_LONG).show();




        /**/
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
}
