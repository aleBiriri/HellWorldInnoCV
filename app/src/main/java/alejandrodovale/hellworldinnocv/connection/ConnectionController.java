package alejandrodovale.hellworldinnocv.connection;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import alejandrodovale.hellworldinnocv.Model.UserEntity;

import static android.content.ContentValues.TAG;

/**
 * Created by Alejandro Dovale on 18/09/2017.
 * Clase que se encarga de instanciar las AsynTask necesarias.
 */

public class ConnectionController {

    private static ConnectionController instance = null;
    /*Constantes para diferenciar las peticiones*/
    public static final String SERVER = "http://hello-world.innocv.com/api/";
    public static final String  GET_ALL_USERS = "user/getall";

    private ConnectionController(){}

    public static ConnectionController getInstance(){
        if(instance == null)
            instance = new ConnectionController();
        return instance;
    }

    public void getAllUsers(Context activity) {
        try {

            HttpURLConnection url = (HttpURLConnection) new URL(SERVER+GET_ALL_USERS).openConnection();
            url.setRequestMethod("GET");

            ConnectionPerformer.getInstance().perform(url, new FinishedConnectionListener() {

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

        } catch (MalformedURLException e) {
            handleError("URL Mal formada");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleError(String error) {
        Log.w(TAG,"Ha ocurrido un error "+error);
    }
}
