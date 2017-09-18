package alejandrodovale.hellworldinnocv.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by Alejandro Dovale on 18/09/2017.
 */

public class UserEntity extends Entity {

    private final int id;
    private final String nombre;
    private final String fechaNacimiento;

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String FECHA = "birthdate";

    private static final String ATRIBUTO_CONSTANTE_KEY = "$type";
    private static final String ATRIBUTO_CONSTANTE_VALUE = "HelloWorld.Web.Models.User, HelloWorld.Web";

    public UserEntity(int identificador, String name, String fecha){
        id = identificador;
        nombre = name;
        fechaNacimiento = fecha;
    }

    public static List<UserEntity> fromJSONArray(String respuestaRaw) {
        List<UserEntity> usuarios = null;
        try {

            JSONArray array = new JSONArray(respuestaRaw);

            usuarios = new ArrayList<UserEntity>(array.length());

            for(int i = 0; i < array.length(); i++){
                usuarios.add(new UserEntity(
                                array.getJSONObject(i).getInt(ID),
                                array.getJSONObject(i).getString(NAME),
                                array.getJSONObject(i).getString(FECHA)
                                )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public static UserEntity fromJSONObject(String jsonObject) {
        UserEntity user = null;
        try {
            JSONObject object = new JSONObject(jsonObject);
            user = new UserEntity(object.getInt(ID),object.getString(NAME),object.getString(FECHA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String toPOSTParams(){
        return ATRIBUTO_CONSTANTE_KEY+"="+ATRIBUTO_CONSTANTE_VALUE+"&"+
                NAME+"="+nombre+"&"+FECHA+"="+fechaNacimiento;
    }

    public String toPOSTParamsWithId(){
        return ATRIBUTO_CONSTANTE_KEY+"="+ATRIBUTO_CONSTANTE_VALUE+"&"+
                ID+"="+id+"&"+NAME+"="+nombre+"&"+FECHA+"="+fechaNacimiento;
    }
    @Override
    public String toString() {
        return "Usuario {Id : "+id+" , Nombre : "+nombre+" , Fecha de Nacimiento "+fechaNacimiento+"}";
    }
}
