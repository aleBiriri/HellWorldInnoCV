package alejandrodovale.hellworldinnocv.view.getAll;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import alejandrodovale.hellworldinnocv.R;
import alejandrodovale.hellworldinnocv.model.UserEntity;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class UserFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = UserFragment.class.getName() ;
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private UserAdapter adapter;
    private RecyclerView recycler;

    public UserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        recycler = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (mColumnCount <= 1) {
            recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }
        adapter = new UserAdapter(null, mListener);
        recycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateData(List<UserEntity> usuarios) {
        adapter.updateValues(usuarios);
    }

    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(UserEntity item);
    }
}
