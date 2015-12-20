package kampus.enhancedbb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewBulletinActivity extends AppCompatActivity {
    EditText editText;
    EditText editTitle;
    private int _Bulletin_ID=0;
    RestService restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restService = new RestService();
        setContentView(R.layout.activity_new_bulletin);

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
            }
        });

        _Bulletin_ID = 0;
        Intent intent = getIntent();
        _Bulletin_ID = intent.getIntExtra("id", 0);
    }

    public void AddNewBulletin()
    {
        IdleBB bb = new IdleBB();
        bb.title = editTitle.getText().toString();
        bb.body = editText.getText().toString();
        bb.userId = 10;
        bb.id = _Bulletin_ID;
        if (_Bulletin_ID == 0) {
            restService.getService().addBB(bb, new Callback<IdleBB>(){
                @Override
                public void success (IdleBB bb, Response response)
                {
                    Toast.makeText(NewBulletinActivity.this, "Объявление добавлено", Toast.LENGTH_LONG).show();
                    finish();
                }
                @Override
                public void failure (RetrofitError error)
                {
                    Toast.makeText(NewBulletinActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            restService.getService().updateBBById(_Bulletin_ID, bb, new Callback<IdleBB>() {
                @Override
                public void success(IdleBB bb, Response response) {
                    Toast.makeText(NewBulletinActivity.this, "Объявление добавлено", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(NewBulletinActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
