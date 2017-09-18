package alejandrodovale.hellworldinnocv.Model;

import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;

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
}
