package alejandrodovale.hellworldinnocv.connection;

import android.util.Log;

import java.net.HttpURLConnection;

import alejandrodovale.hellworldinnocv.controller.FinishedConnectionListener;

/**
 * Created by Alejandro Dovale on 18/09/2017.
 * Esta clase representa una abstracción entre el controlador y el "módulo" de conexión. De esta forma,
 * si en un futuro se decide cambiar el AsynTask por un Servicio, o se decide usar una librería para la
 * gestión de la conexión, como Volley, todo se puede cambiar sin necesidad de alterar el controlador.
 */
public class ConnectionPerformer {

    private static final String TAG = ConnectionPerformer.class.getName() ;
    public static final String ERROR = "ERROR";
    private static ConnectionPerformer p;

    private ConnectionPerformer(){};

    private FinishedConnectionListener listener;
    public static ConnectionPerformer getInstance(){
        if(p == null){
            p = new ConnectionPerformer();
        }
        return p;
    }

    public void perform(HttpURLConnection url, FinishedConnectionListener l) {
        listener = l;
        new ConnectionAsyncTask().execute(url);
    }

    public void procesarRespuesta(String respuesta){
        Log.w(TAG, "Se va a entregar la respuesta al controlador " +respuesta);

        if(respuesta.startsWith(ERROR))
            listener.onError(respuesta);
        else
            listener.onSuccess(respuesta);
        //if(respeusta != error)
        //listener.onSuccess()
        //else
        //listener.onError()
    }
}
