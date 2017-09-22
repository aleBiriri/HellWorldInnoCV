package alejandrodovale.hellworldinnocv.view.details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import alejandrodovale.hellworldinnocv.R;
import alejandrodovale.hellworldinnocv.model.Entity;
import alejandrodovale.hellworldinnocv.model.UserEntity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    private static final String ARG_USUARIO = "usuario";

    private String nombre;
    private String fecha;

    private EditText nombreV;
    private EditText fechaV;
    private Button botonGuardar;

    private UserEntity usuario;

    private OnFragmentInteractionListener mListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Entity entity) {
        UserEntity u = (UserEntity) entity;

        DetailsFragment fragment = new DetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_USUARIO,u);

        fragment.setArguments(args);

        return fragment;
    }

    public static DetailsFragment newInstanceVoid() {
        return new DetailsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            usuario = (UserEntity) getArguments().getSerializable(ARG_USUARIO);
            nombre = usuario.getNombre();
            fecha = usuario.getFechaNacimiento();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_details, container, false);

        nombreV = (EditText) root.findViewById(R.id.nombre_details);
        fechaV = (EditText) root.findViewById(R.id.birthday_details);
        botonGuardar = (Button) root.findViewById(R.id.guardar_cambios);

        nombreV.setText(nombre);
        fechaV.setText(fecha);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = nombreV.getText().toString();
                String fecha  = fechaV.getText().toString();

                UserEntity usr = (usuario == null)? new UserEntity():usuario;
                if(nombre != null)
                    usr.setNombre(nombre);
                if(fecha != null)
                    usr.setFechaNacimiento(fecha);

                mListener.onFragmentInteraction(usr);
            }
        });
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(UserEntity user);
    }

    public UserEntity getUsuario(){
        return usuario;
    }
}
