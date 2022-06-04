package hcmute.edu.huynhnguyenkhang.foody_7.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.huynhnguyenkhang.foody_7.DataSouce.Store;
import hcmute.edu.huynhnguyenkhang.foody_7.R;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
    private final List<Store> storeList;
    private final StoreClickHandler storeClickHandler;

    public StoreAdapter(StoreClickHandler handler, List<Store> storeList) {
        storeClickHandler = handler;
        this.storeList = storeList;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.store_item_row, parent, false );

        return new MyViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.bind( position );
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    // The interface that receives onClick messages.
    public interface StoreClickHandler {
        void onStoreClicked(Store store);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        public TextView name;
        public ImageView image;
        Context context;

        public MyViewHolder(View view) {
            super( view );

            name = view.findViewById( R.id.title );
            image = view.findViewById( R.id.thumbnail );
            cardView = view.findViewById( R.id.storeCart );
            context = view.getContext();
        }

        public void bind(int position) {
            final Store store = storeList.get( position );
            name.setText( store.getName() );
            Glide.with( context ).load( store.getPhotoUrl() ).into( image );
            cardView.setOnClickListener( view -> {
                storeClickHandler.onStoreClicked(store);
            });
        }
    }
}
