package it.celli.testgithub.view;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.celli.testgithub.R;
import it.celli.testgithub.data.Stargazer;
import it.celli.testgithub.di.PicassoInstance;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Stargazer> mStargazersList;
    private PicassoInstance mPicassoInstance;

    private OnBottomReachedListener onBottomReachedListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        ImageView mAvatarView;

        @BindView(R.id.username)
        TextView mUsernameView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public MainAdapter(PicassoInstance picassoInstance, List<Stargazer> list) {
        mStargazersList = list;
        this.mPicassoInstance = picassoInstance;
    }

    public void add( Stargazer item) {
        mStargazersList.add(item);
        notifyItemInserted(mStargazersList.size());
    }

    public void remove(int position) {
        mStargazersList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        int size = mStargazersList.size();
        if(size > 0) {
            for(int i=0; i<size; i++) {
                mStargazersList.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.stargazer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Stargazer item = mStargazersList.get(position);
        viewHolder.mUsernameView.setText(item.getUsername());
        mPicassoInstance.getInstance().load(item.getAvatar()).into(viewHolder.mAvatarView);

        if(position == mStargazersList.size()-1) {
            onBottomReachedListener.onBottomReached(position);
        }
    }

    @Override
    public int getItemCount() {
        return mStargazersList.size();
    }
}
