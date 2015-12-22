package kampus.enhancedbb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
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

    public static Account nowAccount;



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
                        getActualBulletins();
                        break;
                    }
                    case 3:
                    {
                        getBulletinsByProfile(nowAccount.profiles.get(0).id);
                        break;
                    }

                    case 4:
                    {
                        getBulletinsBySubdiv(nowAccount.subdivisions.get(0).id);
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
                    restService.getService().getAllBulletins(nowAccount.id, new Callback<List<Bulletin>>() {
                        @Override
                        public void success(List<Bulletin> bulletins, Response response) {
                            for (int i = 0; i < bulletins.size(); i++) {
                                TitleList.add(bulletins.get(i).subject);
                                BodyList.add(bulletins.get(i).text);
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
                    });
                }

    public void getActualBulletins(){
        TitleList.clear();
        BodyList.clear();
        restService = new RestService();
        restService.getService().getActualBulletins(nowAccount.id, new Callback<List<Bulletin>>() {
            @Override
            public void success(List<Bulletin> bulletins, Response response) {
                for (int i = 0; i < bulletins.size(); i++) {
                    TitleList.add(bulletins.get(i).subject);
                    BodyList.add(bulletins.get(i).text);
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
        });
    }

    public void getBulletinsByProfile(long prof_id){
        TitleList.clear();
        BodyList.clear();
        restService = new RestService();
        restService.getService().getBulletinsByProfile(prof_id, new Callback<List<Bulletin>>() {
            @Override
            public void success(List<Bulletin> bulletins, Response response) {
                for (int i = 0; i < bulletins.size(); i++) {
                    TitleList.add(bulletins.get(i).subject);
                    BodyList.add(bulletins.get(i).text);
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
        });
    }


    public void getBulletinsBySubdiv(long sub_id){
        TitleList.clear();
        BodyList.clear();
        restService = new RestService();
        restService.getService().getBulletinsBySubdiv(sub_id, new Callback<List<Bulletin>>() {
            @Override
            public void success(List<Bulletin> bulletins, Response response) {
                for (int i = 0; i < bulletins.size(); i++) {
                    TitleList.add(bulletins.get(i).subject);
                    BodyList.add(bulletins.get(i).text);
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
        });
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
                    return super.onOptionsItemSelected(item);
                }


                @SuppressWarnings("StatementWithEmptyBody")
                @Override
                public boolean onNavigationItemSelected (MenuItem item){
                    // Handle navigation view item clicks here.
                    int id = item.getItemId();

                    /*if (nowAccount.profiles.size()==0)
                    {
                        findViewById(R.id.nav_profile).setEnabled(false);
                    }
                    if (nowAccount.subdivisions.size()==0)
                    {
                        findViewById(R.id.nav_subdiv).setEnabled(false);
                    }*/




                    SpinProfileAdapter adapter = new SpinProfileAdapter(this, android.R.layout.simple_spinner_item, nowAccount.profiles);

                    Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinner);

                    dynamicSpinner.setAdapter(adapter);

                    dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            getBulletinsByProfile(id);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                        }
                    });

                    SpinSubdivAdapter adapter2 = new SpinSubdivAdapter(this, android.R.layout.simple_spinner_item, nowAccount.subdivisions);

                    Spinner dynamicSpinner2 = (Spinner) findViewById(R.id.dynamic_spinner2);

                    dynamicSpinner2.setAdapter(adapter2);

                    dynamicSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            getBulletinsBySubdiv(id);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                        }
                    });





                    if (id == R.id.nav_all) {
                        dynamicSpinner.setEnabled(false);
                        dynamicSpinner.setVisibility(View.GONE);
                        dynamicSpinner2.setEnabled(false);
                        dynamicSpinner2.setVisibility(View.GONE);
                        getAllBulletins();
                        page = 1;

                    } else if (id == R.id.nav_actual) {
                        dynamicSpinner.setEnabled(false);
                        dynamicSpinner.setVisibility(View.GONE);
                        dynamicSpinner2.setEnabled(false);
                        dynamicSpinner2.setVisibility(View.GONE);
                            getActualBulletins();
                            page = 2;

                    } else if (id == R.id.nav_profile) {
                        dynamicSpinner2.setEnabled(false);
                        dynamicSpinner2.setVisibility(View.GONE);
                        page = 3;
                            dynamicSpinner.setEnabled(true);
                            dynamicSpinner.setVisibility(View.VISIBLE);



                    } else if (id == R.id.nav_subdiv) {
                        dynamicSpinner.setEnabled(false);
                        dynamicSpinner.setVisibility(View.GONE);
                        dynamicSpinner2.setEnabled(true);
                        dynamicSpinner2.setVisibility(View.VISIBLE);
                        page = 4;

                    } else if (id == R.id.nav_logout) {
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    }
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }






    void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


            }


