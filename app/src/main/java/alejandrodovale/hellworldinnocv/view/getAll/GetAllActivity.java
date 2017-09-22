package alejandrodovale.hellworldinnocv.view.getAll;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import alejandrodovale.hellworldinnocv.R;
import alejandrodovale.hellworldinnocv.controller.Controller;
import alejandrodovale.hellworldinnocv.controller.FinishedConnectionListener;
import alejandrodovale.hellworldinnocv.model.UserEntity;
import alejandrodovale.hellworldinnocv.view.details.DetailsActivity;
import alejandrodovale.hellworldinnocv.view.OnListFragmentInteractionListener;
import alejandrodovale.hellworldinnocv.view.newuser.NewUserActivity;

public class GetAllActivity extends AppCompatActivity implements OnListFragmentInteractionListener {

    private static final String TAG = GetAllActivity.class.getName() ;
    private UserFragment fragment;
    private SwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
               */
                startActivity(new Intent(GetAllActivity.this,NewUserActivity.class));
            }
        });

        initInterface();
        initFragment();
        initSwipeLayout();

    }

    private void initSwipeLayout() {

        SwipeRefreshLayout.OnRefreshListener l = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        };

        layout = (SwipeRefreshLayout) findViewById(R.id.frame_fragment);
        layout.setOnRefreshListener(l);
    }


    private void initFragment() {
        fragment = new UserFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_fragment, fragment)
                .commit();
    }

    private void initInterface() {
        Log.w(TAG,"Iniciando petición");
        //Llamada no bloqueante
        fetchData();
//        getAllB = (Button) findViewById(R.id.getAll);
//        getAllB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.w(TAG,"Iniciando petición");
//                Controller.getInstance().getAllUsers(new FinishedConnectionListener() {
//                    @Override
//                    public void onSuccess(String respuestaRaw) {
//                        //Convertir dato en JSON
//                        List<UserEntity> usuarios = UserEntity.fromJSONArray(respuestaRaw);
//                        Log.w(TAG,"La lista de usuarios "+ usuarios);
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        //Mostrar mensaje de error
//                        Log.w(TAG,"Ha ocurrido un error con la respuesta");
//                    }
//                });
               // Controller.getInstance().getUser(1230);
                //Controller.getInstance().createUser(new UserEntity(-1,"NUEVO USER3","2017-09-16T19:49:04"));
                //Controller.getInstance().updateUser(new UserEntity(1256,"NUEVO USER3 CON NUEVO NOMBRE","2017-09-16T19:49:04"));
               // Controller.getInstance().removeUser(1254);

//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(fragment == null)
            initFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_all, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        return false;
    }


    private void fetchData() {
        Controller.getInstance().getAllUsers(
                new FinishedConnectionListener() {
                    @Override
                    public void onSuccess(String respuestaRaw) {
                        List<UserEntity> usuarios = UserEntity.fromJSONArray(respuestaRaw);
                        Log.w(TAG, "La lista de usuarios " + usuarios);
                        fragment.updateData(usuarios);
                        layout.setRefreshing(false);
                    }

                    @Override
                    public void onError(String error) {
                        //Mostrar mensaje de error
                        Log.w(TAG, "Ha ocurrido un error con la respuesta");
                    }
                }
        );
    }

    @Override
    public void onListFragmentInteraction(UserEntity item) {
        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra(DetailsActivity.EXTRA_USUARIO,item);
        startActivity(i);
    }

}
