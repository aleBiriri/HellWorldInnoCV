package alejandrodovale.hellworldinnocv.controller;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import alejandrodovale.hellworldinnocv.controller.connection.ConnectionAsyncTask;
import alejandrodovale.hellworldinnocv.controller.connection.ConnectionPerformer;
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
    private static final String CREATE_USER = "user/create";
    private static final String UPDATE_USER = "user/update";
    private static final String REMOVE_USER = "user/remove/" ;

    private Controller(){}

    public static Controller getInstance(){
        if(instance == null)
            instance = new Controller();
        return instance;
    }

    public void getAllUsers(FinishedConnectionListener l){
        Log.w(TAG,"Pidiendo los usuarios");
        ConnectionPerformer.getInstance().setDriver(new ConnectionAsyncTask()).perform(buildURL("GET",GET_ALL_USERS),l);
    }

    public void getUser(int id, FinishedConnectionListener l){
        ConnectionPerformer.getInstance().setDriver(new ConnectionAsyncTask()).perform(buildURL("GET",GET_USER+id),l);
    }

    public void createUser(UserEntity u, FinishedConnectionListener l){
        final UserEntity aux = u;
        final FinishedConnectionListener li = l;
        Log.w(TAG,"Creando usuario");
        /*Debido a que se hace una operacion de red (con el DataOutputStream, debemos crear un nuevo hilo)*/
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection url = buildURL("POST",CREATE_USER);
                String params = aux.toPOSTParams();
                url = configPOSTUrl(url,params);

                if(url!=null){
                    ConnectionPerformer.getInstance().setDriver(new ConnectionAsyncTask()).perform(url, li);
                }
            }
        });
        t.start();
        //FOR TESTING
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    public void updateUser(UserEntity u,FinishedConnectionListener l){
        final UserEntity aux = u;
        final FinishedConnectionListener auxL = l;
        /*Debido a que se hace una operacion de red (con el DataOutputStream, debemos crear un nuevo hilo)*/
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection url = buildURL("POST",UPDATE_USER);
                String params = aux.toPOSTParamsWithId();
                url = configPOSTUrl(url,params);

                if(url!=null){
                    ConnectionPerformer.getInstance().setDriver(new ConnectionAsyncTask()).perform(url,auxL);
                }

            }
        }).start();
    }

    public void removeUser(int id,FinishedConnectionListener l){
        ConnectionPerformer.getInstance().setDriver(new ConnectionAsyncTask()).perform(buildURL("GET",REMOVE_USER+id),l);
    }

    private HttpURLConnection configPOSTUrl(HttpURLConnection url, String params) {
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
        return url;
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
