package alejandrodovale.hellworldinnocv.view.details;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import alejandrodovale.hellworldinnocv.R;
import alejandrodovale.hellworldinnocv.view.getAll.UserFragment;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.OnFragmentInteractionListener{

    private static final String TAG = DetailsActivity.class.getName();
    private DetailsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView v = (ImageView) findViewById(R.id.borrar_usuario);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w(TAG,"Se va a borrar un usuario");
            }
        });

        initFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.borrar_usuario) {
            Log.w(TAG,"Se va a borrar un usuario");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initFragment() {
        fragment = new DetailsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_details, fragment)
                .commit();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
