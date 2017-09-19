package alejandrodovale.hellworldinnocv.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.List;

import alejandrodovale.hellworldinnocv.R;
import alejandrodovale.hellworldinnocv.controller.Controller;
import alejandrodovale.hellworldinnocv.controller.FinishedConnectionListener;
import alejandrodovale.hellworldinnocv.model.UserEntity;
import alejandrodovale.hellworldinnocv.view.fragments.OnListFragmentInteractionListener;
import alejandrodovale.hellworldinnocv.view.fragments.UserFragment;
import alejandrodovale.hellworldinnocv.view.fragments.dummy.DummyContent;

import static android.content.ContentValues.TAG;

public class GetAllActivity extends AppCompatActivity implements OnListFragmentInteractionListener {

    private static final String TAG = GetAllActivity.class.getName() ;
    private Button getAllB;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initInterface();

    }

    private void initInterface() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
