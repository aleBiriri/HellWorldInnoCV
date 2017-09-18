package alejandrodovale.hellworldinnocv.controller;

import android.content.Context;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import alejandrodovale.hellworldinnocv.connection.ConnectionPerformer;
import alejandrodovale.hellworldinnocv.model.UserEntity;

import static android.content.ContentValues.TAG;

/**
 * Created by Alejandro Dovale on 18/09/2017.
 * Clase que se encarga de instanciar las AsynTask necesarias.
 */

public class Controller {

    private static Controller instance = null;
    /*Constantes para diferenciar las peticiones*/
    public static final String SERVER = "http://hello-world.innocv.com/api/";
    public static final String  GET_ALL_USERS = "user/getall";
    private static final String GET_USER = "user/get/";
    private static final String CREATE_USER = "user/create" ;

    private Controller(){}

    public static Controller getInstance(){
        if(instance == null)
            instance = new Controller();
        return instance;
    }

    public void getAllUsers(Context activity) {
        ConnectionPerformer.getInstance().perform(buildURL("GET",GET_ALL_USERS), new FinishedConnectionListener() {
            @Override
            public void onSuccess(String respuestaRaw) {
                //Convertir dato en JSON
                Log.w(TAG,"Se ha recibido con éxito la respuesta "+respuestaRaw);
                List<UserEntity> usuarios = UserEntity.fromJSONArray(respuestaRaw);
                Log.w(TAG,"La lista de usuarios "+usuarios);
            }
            @Override
            public void onError(String error) {
                //Mostrar mensaje de error
                Log.w(TAG,"Ha ocurrido un error con la respuesta");
            }
        });

    }

    public void getUser(int id){
        ConnectionPerformer.getInstance().perform(buildURL("GET",GET_USER+id), new FinishedConnectionListener() {
            @Override
            public void onSuccess(String respuestaRaw) {
                //Convertir dato en JSON
                Log.w(TAG,"Se ha recibido con éxito la respuesta "+respuestaRaw);
                UserEntity user = UserEntity.fromJSONObject(respuestaRaw);
                Log.w(TAG,"Se ha recibido el usuario "+user);
            }
            @Override
            public void onError(String error) {
                //Mostrar mensaje de error
                Log.w(TAG,"Ha ocurrido un error con la respuesta");
            }
        });
    }

    public void createUser(UserEntity u){
        final UserEntity aux = u;
        /*Debido a que se hace una operacion de red (con el DataOutputStream, debemos crear un nuevo hilo)*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection url = buildURL("POST",CREATE_USER);
                String params = aux.toPOSTParams();
                byte[] paramBytes = params.getBytes();
                url.setDoOutput(true);
                url.setInstanceFollowRedirects(false);
                url.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                url.setRequestProperty( "charset", "utf-8");
                url.setRequestProperty( "Content-Length", Integer.toString(paramBytes.length));
                url.setUseCaches(false);
                try {
                    DataOutputStream wr = new DataOutputStream( url.getOutputStream());
                    wr.write(paramBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                    url = null;
                }
                if(url!=null){
                    ConnectionPerformer.getInstance().perform(url, new FinishedConnectionListener() {
                        @Override
                        public void onSuccess(String respuestaRaw) {
                            //Convertir dato en JSON
                            Log.w(TAG,"Se ha recibido con éxito la respuesta "+respuestaRaw);
                            // UserEntity user = UserEntity.fromJSONObject(respuestaRaw);
                            //   Log.w(TAG,"Se ha recibido el usuario "+user);
                        }
                        @Override
                        public void onError(String error) {
                            //Mostrar mensaje de error
                            Log.w(TAG,"Ha ocurrido un error con la respuesta");
                        }
                    });
                }
            }
        }).start();
    }


    private HttpURLConnection buildURL(String method,String uri){
        HttpURLConnection url = null;
        try {
            url = (HttpURLConnection) new URL(SERVER+uri).openConnection();
            url.setRequestMethod(method);
        } catch (IOException e) {
            e.printStackTrace();
            handleError("URL Mal formada");
        }
        return url;
    }
    private void handleError(String error) {
        Log.w(TAG,"Ha ocurrido un error "+error);
    }
}
