package alejandrodovale.hellworldinnocv.controller;

/**
 * Created by Alejandro Dovale on 18/09/2017.
 * Clase que ejecuta una tarea cuando se completa una petición.
 */
public interface FinishedConnectionListener {

    //Ejecuta una tarea con la entidad pasada como parámetro
    public void onSuccess(String respuestaRaw);

    public void onError(String error);
}
