package alejandrodovale.hellworldinnocv.controller;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import alejandrodovale.hellworldinnocv.controller.connection.ConnectionAsyncTask;
import alejandrodovale.hellworldinnocv.controller.connection.ConnectionDriver;
import alejandrodovale.hellworldinnocv.controller.connection.ConnectionPerformer;
import alejandrodovale.hellworldinnocv.model.UserEntity;

import static android.content.ContentValues.TAG;

/**
 * Created by Alejandro Dovale on 19/09/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ControllerTest extends AndroidTestCase{

    public void setUp(){
        ConnectionPerformer.getInstance().setDriver(new ConnectionDriver() {
            @Override
            public void makeConnection(HttpURLConnection url) {
                ConnectionAsyncTask task = new ConnectionAsyncTask();
                ConnectionPerformer.getInstance().procesarRespuesta(task.fetchData(url));
            }
        });
    }

    @Test
    public void getAll(){
        setUp();

        FinishedConnectionListener l = new FinishedConnectionListener() {
            @Override
            public void onSuccess(String respuestaRaw) {
                //Convertir dato en JSON
                assertNotNull(respuestaRaw);
                List<UserEntity> usuarios = UserEntity.fromJSONArray(respuestaRaw);
                assertFalse(usuarios.size() == 0);
                Log.w(TAG,"La lista de usuarios "+ usuarios);
            }
            @Override
            public void onError(String error) {

            }
        };

        Controller.getInstance().getAllUsers(l);
    }

    @Test
    public void getUser(){
        setUp();

        FinishedConnectionListener l = new FinishedConnectionListener() {
            @Override
            public void onSuccess(String respuestaRaw) {
                //Convertir dato en JSON
                assertNotNull(respuestaRaw);
                UserEntity user = UserEntity.fromJSONObject(respuestaRaw);
                assertTrue(user.getNombre().equals("Arturo Menos"));
            }
            @Override
            public void onError(String error) {

            }
        };

        Controller.getInstance().getUser(1230,l);
    }

    @Test
    public void createUser(){
        setUp();

        FinishedConnectionListener l = new FinishedConnectionListener() {
            @Override
            public void onSuccess(String respuestaRaw) {
                //Convertir dato en JSON
                assertNotNull(respuestaRaw);
                Log.w(TAG,"Todo ok");
                UserEntity user = UserEntity.fromJSONObject(respuestaRaw);
                assertTrue(user.getNombre().equals("NUEVO USER3"));
            }
            @Override
            public void onError(String error) {

            }
        };

        Controller.getInstance().createUser(new UserEntity(-1,"NUEVO USER3","2017-09-16T19:49:04"),l);
    }

    @Test
    public void testTimeFormat(){
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getDefault());
        Log.w(TAG,"Date "+dateFormat.format(d));
    }
}
