package alejandrodovale.hellworldinnocv.connection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Alejandro Dovale on 18/09/2017.
 * El AsyncTask que se conecta a la red y devuelve un String que es la respuesta del servidor.
 * Si ocurre un error, será debidamente tratado.
 *
 */

public class ConnectionAsyncTask extends AsyncTask<HttpURLConnection,Void,String>{


    private static final String TAG = ConnectionAsyncTask.class.getName() ;

    @Override
    protected String doInBackground(HttpURLConnection... urls) {
        //Realizar conexión
        HttpURLConnection url = urls[0];
        String respuesta = null;
        try {
            url.connect();
            Log.w(TAG,"Se ha abierto y cerrado la conexión");
            respuesta = getData(url);
            url.disconnect();
        } catch (IOException e) {
            respuesta = ConnectionPerformer.ERROR + " : Ha ocurrido un error con la conexión";
            e.printStackTrace();
        }
        return respuesta;
    }

    private String getData(HttpURLConnection urlConnection) throws IOException {

        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;

        if (inputStream == null) {
            return null;
        }

        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }

        if (buffer.length() == 0) {
            return null;
        }

        return buffer.toString();
    }

    @Override
    protected void onPostExecute(String respuestaRaw) {
        super.onPostExecute(respuestaRaw);
        Log.w(TAG,"Se ha terminado la conexión");
        ConnectionPerformer.getInstance().procesarRespuesta(respuestaRaw);
    }
}
