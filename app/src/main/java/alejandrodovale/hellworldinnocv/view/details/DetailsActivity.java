package alejandrodovale.hellworldinnocv.view.details;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import alejandrodovale.hellworldinnocv.R;
import alejandrodovale.hellworldinnocv.controller.Controller;
import alejandrodovale.hellworldinnocv.controller.FinishedConnectionListener;
import alejandrodovale.hellworldinnocv.model.UserEntity;
import alejandrodovale.hellworldinnocv.view.getAll.UserFragment;
import alejandrodovale.hellworldinnocv.view.newuser.NewUserActivity;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.OnFragmentInteractionListener{

    private static final String TAG = DetailsActivity.class.getName();
    public static final String EXTRA_USUARIO = "usr" ;
    private DetailsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        ImageView v = (ImageView) findViewById(R.id.borrar_usuario);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w(TAG,"Se va a borrar un usuario");
                displayDialog();
            }
        });

        initFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    private void displayDialog() {
        Log.w(TAG,"Dialogo 2");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm_borrar);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.w(TAG,"Confirmado que se quiere borrar");
                borrarUsuario();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void borrarUsuario() {

        FinishedConnectionListener l = new FinishedConnectionListener() {
            @Override
            public void onSuccess(String respuestaRaw) {
                Toast.makeText(DetailsActivity.this,"Usuario eliminado correctamente",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onError(String error) {

            }
        };

        Controller.getInstance().removeUser(fragment.getUsuario().getId(),l);
    }

    private void initFragment() {
        UserEntity usuario = (UserEntity) getIntent().getExtras().getSerializable(EXTRA_USUARIO);

        fragment = DetailsFragment.newInstance(usuario);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_details, fragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(UserEntity user) {

        FinishedConnectionListener l = new FinishedConnectionListener() {
            @Override
            public void onSuccess(String respuestaRaw) {
                Toast.makeText(DetailsActivity.this,"Usuario actualizado correctamente", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(DetailsActivity.this,"Ha habido un error actualizando el usuario", Toast.LENGTH_LONG).show();
            }
        };

        Controller.getInstance().updateUser(user,l);
    }

}
