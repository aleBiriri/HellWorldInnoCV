package alejandrodovale.hellworldinnocv.view.getAll;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import alejandrodovale.hellworldinnocv.R;
import alejandrodovale.hellworldinnocv.controller.Controller;
import alejandrodovale.hellworldinnocv.controller.FinishedConnectionListener;
import alejandrodovale.hellworldinnocv.model.UserEntity;
import alejandrodovale.hellworldinnocv.view.details.DetailsActivity;
import alejandrodovale.hellworldinnocv.view.newuser.NewUserActivity;

public class GetAllActivity extends AppCompatActivity implements UserFragment.OnListFragmentInteractionListener {

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
                .replace(R.id.frame_fragment, fragment)
                .commit();
    }

    private void initInterface() {
        Log.w(TAG,"Iniciando petición");
        //Llamada no bloqueante
        fetchData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       final AlertDialog dialogo = new AlertDialog.Builder(this).create();

        dialogo.setTitle(R.string.title_dialogo_buscar);

        dialogo.setMessage(getString(R.string.message_dialogo_buscar));

        final EditText input = new EditText(this);
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER);

       final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.TEXT_ALIGNMENT_CENTER);
        input.setLayoutParams(lp);
        dialogo.setView(input);

        dialogo.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.buscar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                Log.w(TAG,"Se va a buscar el usuario");
                //Poner spinner
                //Poner dialogo con progressbar
                final ProgressBar p = new ProgressBar(GetAllActivity.this);
                p.setLayoutParams(lp);
                final AlertDialog dialogoSpinner = new AlertDialog.Builder(GetAllActivity.this).create();
                dialogoSpinner.setTitle(R.string.espere_un_momento);
                dialogoSpinner.setView(p);
                dialogoSpinner.show();
                //Buscar
                FinishedConnectionListener l = new FinishedConnectionListener() {
                    @Override
                    public void onSuccess(String respuestaRaw) {
                        UserEntity u = UserEntity.fromJSONObject(respuestaRaw);

                        p.setVisibility(ProgressBar.GONE);
                        dialogoSpinner.dismiss();
                        if(u != null){
                            iniciarActivityDetalle(u);
                        }
                        else {
                            Toast.makeText(GetAllActivity.this, R.string.no_existe_usuario, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(GetAllActivity.this,R.string.error_buscar_usuario,Toast.LENGTH_LONG).show();
                    }
                };

                Controller.getInstance().getUser(Integer.valueOf(input.getText().toString()),l);

            }
        });

        //Cambiar color del botón de Buscar
        dialogo.setOnShowListener( new DialogInterface.OnShowListener() {
                                      @Override
                                      public void onShow(DialogInterface arg0) {
                                          dialogo.getButton(AlertDialog.BUTTON_POSITIVE)
                                                  .setTextColor(getResources().getColor(R.color.colorSecundarioGrisOscuro));
                                      }
                                  });
        dialogo.show();

        return super.onOptionsItemSelected(item);
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
                        Log.w(TAG, "Ha ocurrido un error al cargar los datos");
                        Toast.makeText(GetAllActivity.this,R.string.error_cargar_datos,Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    @Override
    public void onListFragmentInteraction(UserEntity item) {
        iniciarActivityDetalle(item);
    }

    public void iniciarActivityDetalle(UserEntity item){
        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra(DetailsActivity.EXTRA_USUARIO,item);
        startActivity(i);
    }

}
