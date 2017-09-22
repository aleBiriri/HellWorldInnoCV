package alejandrodovale.hellworldinnocv.view.getAll;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import alejandrodovale.hellworldinnocv.R;
import alejandrodovale.hellworldinnocv.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserEntity> mValues;
    private final UserFragment.OnListFragmentInteractionListener mListener;

    public UserAdapter(List<UserEntity> items, UserFragment.OnListFragmentInteractionListener listener) {
        if(items == null)
            mValues = new ArrayList<>();
        else
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(holder.mItem.getId()));
        holder.mNombreView.setText(mValues.get(position).getNombre());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateValues(List<UserEntity> usuarios) {
        mValues.clear();
        mValues.addAll(usuarios);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNombreView;
        public UserEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mNombreView = (TextView) view.findViewById(R.id.nombre);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNombreView.getText() + "'";
        }
    }
}
