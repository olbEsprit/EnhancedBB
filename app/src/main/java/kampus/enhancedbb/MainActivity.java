package kampus.enhancedbb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RestService restService;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;

    final ArrayList<String> TitleList = new ArrayList<>();
    final ArrayList<String> BodyList = new ArrayList<>();

    int page=1;
    int UID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllBulletins();




        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(TitleList, BodyList);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (page) {
                    case 1: {
                        getAllBulletins();
                        break;
                    }
                    case 2:
                    {
                        getBBbyUID(UID);
                        break;
                    }
                    case 3:
                    {
                        getBBbyUID(UID);
                        break;
                    }
                }
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here
                startActivity(new Intent(v.getContext(), NewBulletinActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                    drawer.setDrawerListener(toggle);
                    toggle.syncState();

                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(this);
                }


                public void getAllBulletins(){
                    TitleList.clear();
                    BodyList.clear();
                    restService = new RestService();
                    restService.getService().getBB(new Callback<List<IdleBB>>() {
                         @Override
                                                       public void success(List<IdleBB> bbs, Response response) {
                                                           ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

                                                           for (int i = 0; i < bbs.size(); i++) {
                                                               HashMap<String, String> bb = new HashMap<String, String>();
                                                               bb.put("id", String.valueOf(bbs.get(i).id));
                                                               bb.put("title", String.valueOf(bbs.get(i).title));
                                                               bb.put("body", String.valueOf(bbs.get(i).body));

                                                               TitleList.add(bbs.get(i).title);
                                                               BodyList.add(bbs.get(i).body);
                                                               studentList.add(bb);
                                                           }
                             mAdapter.notifyDataSetChanged();
                             mAdapter = new RecyclerAdapter(TitleList, BodyList);
                             mRecyclerView.setAdapter(mAdapter);
                             onItemsLoadComplete();

                                                       }
                                                       @Override
                                                       public void failure(RetrofitError error) {
                                                           Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                           onItemsLoadComplete();
                                                       }
                                                   }
                    );
                }

                public void getBBbyUID(final int UserID)
                {
                    TitleList.clear();
                    BodyList.clear();
                    restService = new RestService();
                    restService.getService().getBB(new Callback<List<IdleBB>>() {
                                                       @Override
                                                       public void success(List<IdleBB> bbs, Response response) {
                                                           ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

                                                           for (int i = 0; i < bbs.size(); i++) {
                                                               HashMap<String, String> bb = new HashMap<String, String>();
                                                               bb.put("id", String.valueOf(bbs.get(i).id));
                                                               bb.put("title", String.valueOf(bbs.get(i).title));
                                                               bb.put("body", String.valueOf(bbs.get(i).body));
                                                               bb.put("UserId", String.valueOf(bbs.get(i).userId));
                                                               if (Integer.valueOf(bbs.get(i).userId) == UserID)
                                                               {
                                                                   TitleList.add(bbs.get(i).title);
                                                                   BodyList.add(bbs.get(i).body);
                                                                }
                                                               studentList.add(bb);
                                                           }
                                                           mAdapter.notifyDataSetChanged();
                                                           mAdapter = new RecyclerAdapter(TitleList, BodyList);
                                                           mRecyclerView.setAdapter(mAdapter);
                                                           onItemsLoadComplete();
                                                       }
                                                       @Override
                                                       public void failure(RetrofitError error) {
                                                           Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                           onItemsLoadComplete();
                                                       }
                                                   }
                    );

                    }


                @Override
                public void onBackPressed () {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        super.onBackPressed();
                    }
                }

                @Override
                public boolean onCreateOptionsMenu (Menu menu){
                    // Inflate the menu; this adds items to the action bar if it is present.
                    getMenuInflater().inflate(R.menu.main, menu);
                    return true;
                }

                @Override
                public boolean onOptionsItemSelected (MenuItem item){
                    // Handle action bar item clicks here. The action bar will
                    // automatically handle clicks on the Home/Up button, so long
                    // as you specify a parent activity in AndroidManifest.xml.
                    int id = item.getItemId();
                    switch (id){
                        case R.id.prof1:
                        {
                            UID = 1;
                            getBBbyUID(UID);
                        }
                        case R.id.prof2:
                        {
                            UID = 2;
                            getBBbyUID(UID);
                        }
                        case R.id.prof3:
                        {
                            UID = 3;
                            getBBbyUID(UID);
                        }
                        case R.id.prof4:
                        {
                            UID = 4;
                            getBBbyUID(UID);
                        }
                        case R.id.prof5:
                        {
                            UID = 5;
                            getBBbyUID(UID);
                        }
                        case R.id.prof6:
                        {
                            UID = 6;
                            getBBbyUID(UID);
                        }
                        case R.id.prof7:
                        {
                            UID = 7;
                            getBBbyUID(UID);
                        }
                        case R.id.prof8:
                        {
                            UID = 8;
                            getBBbyUID(UID);
                        }
                        case R.id.prof9:
                        {
                            UID = 9;
                            getBBbyUID(UID);
                        }
                        case R.id.prof10:
                        {
                            UID = 10;
                            getBBbyUID(UID);
                        }
                    }
                    return super.onOptionsItemSelected(item);
                }


                @SuppressWarnings("StatementWithEmptyBody")
                @Override
                public boolean onNavigationItemSelected (MenuItem item){
                    // Handle navigation view item clicks here.
                    int id = item.getItemId();

                    Spinner staticSpinner = (Spinner) findViewById(R.id.static_spinner);
                    ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                            .createFromResource(this, R.array.spinner_data,
                                    android.R.layout.simple_spinner_item);
                    staticAdapter
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    staticSpinner.setAdapter(staticAdapter);

                    staticSpinner.setEnabled(false);
                    staticSpinner.setVisibility(View.GONE);


                    Spinner staticSpinner2 = (Spinner) findViewById(R.id.static_spinner2);
                    ArrayAdapter<CharSequence> staticAdapter2 = ArrayAdapter
                            .createFromResource(this, R.array.spinner_data2,
                                    android.R.layout.simple_spinner_item);
                    staticAdapter2
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    staticSpinner2.setAdapter(staticAdapter2);

                    staticSpinner2.setEnabled(false);
                    staticSpinner2.setVisibility(View.GONE);



                    staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (position) {
                                case 1: {
                                    UID = 1;
                                    getBBbyUID(UID);
                                    break;
                                }
                                case 2: {
                                    UID = 2;
                                    getBBbyUID(UID);
                                    break;
                                }
                                case 3: {
                                    UID = 3;
                                    getBBbyUID(UID);
                                    break;
                                }
                                case 4: {
                                    UID = 4;
                                    getBBbyUID(UID);
                                    break;
                                }
                                case 5: {
                                    UID = 5;
                                    getBBbyUID(UID);
                                    break;
                                }
                                case 6: {
                                    UID = 6;
                                    getBBbyUID(UID);
                                    break;
                                }
                                case 7: {
                                    UID = 7;
                                    getBBbyUID(UID);
                                    break;
                                }
                                case 8: {
                                    UID = 8;
                                    getBBbyUID(UID);
                                    break;
                                }
                                case 9: {
                                    UID = 9;
                                    getBBbyUID(UID);
                                    break;
                                }
                                case 10: {
                                    UID = 10;
                                    getBBbyUID(UID);
                                    break;

                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    staticSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (position){
                                case 1:
                                {
                                    UID = 10;
                                    getBBbyUID(UID);
                                    break;
                                }
                                case 2:
                                {
                                    UID = 9;
                                    getBBbyUID(UID);
                                    break;

                                }
                                case 3:
                                {
                                    UID = 8;
                                    getBBbyUID(UID);
                                    break;

                                }
                                case 4:
                                {
                                    UID = 7;
                                    getBBbyUID(UID);
                                    break;

                                }
                                case 5:
                                {
                                    UID = 6;
                                    getBBbyUID(UID);
                                    break;

                                }
                                case 6:
                                {
                                    UID = 5;
                                    getBBbyUID(UID);
                                    break;

                                }
                                case 7:
                                {
                                    UID = 4;
                                    getBBbyUID(UID);
                                    break;

                                }
                                case 8:
                                {
                                    UID = 3;
                                    getBBbyUID(UID);
                                    break;

                                }
                                case 9:
                                {
                                    UID = 2;

                                    getBBbyUID(UID);

                                    break;
                                }
                                case 10:
                                {
                                    UID = 1;
                                    getBBbyUID(UID);

                                    break;
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    if (id == R.id.nav_all) {
                        staticSpinner.setEnabled(false);
                        staticSpinner.setVisibility(View.GONE);
                        staticSpinner2.setEnabled(false);
                        staticSpinner2.setVisibility(View.GONE);
                        getAllBulletins();
                        page = 1;
                    } else if (id == R.id.nav_profile) {
                        staticSpinner.setEnabled(true);
                        staticSpinner.setVisibility(View.VISIBLE);
                        staticSpinner2.setEnabled(false);
                        staticSpinner2.setVisibility(View.GONE);
                        getBBbyUID(UID);
                        page = 2;

                    } else if (id == R.id.nav_subdiv) {
                        UID = 10;
                        staticSpinner.setEnabled(false);
                        staticSpinner.setVisibility(View.GONE);
                        staticSpinner2.setEnabled(true);
                        staticSpinner2.setVisibility(View.VISIBLE);
                        getBBbyUID(UID);
                        page = 3;

                    } else if (id == R.id.nav_logout) {
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    }
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }



    void refreshItems() {
        // Load items
        // ...

        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }


            }


