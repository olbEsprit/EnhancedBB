package kampus.enhancedbb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ListView;
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

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;

    final ArrayList<String> TitleList = new ArrayList<>();
    final ArrayList<String> BodyList = new ArrayList<>();

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





                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

                    setSupportActionBar(toolbar);

                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener()

                    {
                        @Override
                        public void onClick (View view){
                            //Тут нужно заново получить объявления
                            getAllBulletins();
                        }
                    }

                    );

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                    drawer.setDrawerListener(toggle);
                    toggle.syncState();

                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(this);
                }


                public void getAllBulletins(){

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
                                                       }
                                                       @Override
                                                       public void failure(RetrofitError error) {
                                                           Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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

                    //noinspection SimplifiableIfStatement
                    if (id == R.id.action_settings) {
                        return true;
                    }

                    return super.onOptionsItemSelected(item);
                }

                @SuppressWarnings("StatementWithEmptyBody")
                @Override
                public boolean onNavigationItemSelected (MenuItem item){
                    // Handle navigation view item clicks here.
                    int id = item.getItemId();

                    if (id == R.id.nav_camara) {
                        // Handle the camera action
                    } else if (id == R.id.nav_gallery) {

                    } else if (id == R.id.nav_slideshow) {

                    } else if (id == R.id.nav_manage) {

                    } else if (id == R.id.nav_share) {

                    } else if (id == R.id.nav_send) {

                    }

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            }
