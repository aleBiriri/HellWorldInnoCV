package alejandrodovale.hellworldinnocv.view.newuser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import alejandrodovale.hellworldinnocv.R;
import alejandrodovale.hellworldinnocv.Utils;
import alejandrodovale.hellworldinnocv.controller.Controller;
import alejandrodovale.hellworldinnocv.controller.FinishedConnectionListener;
import alejandrodovale.hellworldinnocv.model.UserEntity;
import alejandrodovale.hellworldinnocv.view.details.DetailsFragment;

public class NewUserActivity extends AppCompatActivity implements DetailsFragment.OnFragmentInteractionListener  {

    private static final String MENSAJE_CORRECTO = "Nuevo usuario creado correctamente" ;
    private static final String MENSAJE_WARNING = "Advertencia : Fecha con formato incorrecto. Usuario creado con fecha de nacimiento predeteminada." ;
    private static final String TAG = NewUserActivity.class.getName() ;
    private DetailsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_actvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        initFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    private void initFragment() {

        fragment = DetailsFragment.newInstanceVoid();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_new_user, fragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(UserEntity user) {


        boolean warningFecha = false;

        if(!formatoCorrectoFecha(user.getFechaNacimiento())){
            user.setFechaNacimiento(Utils.getFecha());
            warningFecha = true;
        }

        final boolean auxWarningFecha = warningFecha;
        FinishedConnectionListener l = new FinishedConnectionListener() {
            @Override
            public void onSuccess(String respuestaRaw) {
                Log.w(TAG,"Se ha creado correctamente el usuario");
                String mensaje = auxWarningFecha?(MENSAJE_WARNING):MENSAJE_CORRECTO;
                Toast.makeText(NewUserActivity.this,mensaje,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(NewUserActivity.this,"Error al crear el usuario",Toast.LENGTH_LONG).show();
            }
        };


        Controller.getInstance().createUser(user,l);
    }

    private boolean formatoCorrectoFecha(String fechaNacimiento) {
        Date date = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Utils.DATE_FORMAT);
            date = sdf.parse(fechaNacimiento);
            if (!fechaNacimiento.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return date != null;
    }
}
