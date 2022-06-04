package hcmute.edu.huynhnguyenkhang.foody_7.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.huynhnguyenkhang.foody_7.Activity.ProductActivity;
import hcmute.edu.huynhnguyenkhang.foody_7.DataSouce.Menu;
import hcmute.edu.huynhnguyenkhang.foody_7.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private final List<Menu> menuList;
    private final ProductClickHandler productClickHandler;

    public interface ProductClickHandler {
        void onMenuClicked(Menu menu);
    }
    public ProductAdapter(ProductClickHandler handler, List<Menu> menuList) {
        this.productClickHandler=handler;
        this.menuList = menuList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.menu_item_row, parent, false );

        return new MyViewHolder( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
            holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final Button addButton;
        public TextView name;
        public ImageView image;
        public TextView price;
        public TextView description;
        Context context;

        public MyViewHolder(View view) {
            super( view );
            name = view.findViewById( R.id.cart_name );
            image = view.findViewById( R.id.cart_image );
            price = view.findViewById( R.id.cart_price );
            description = view.findViewById( R.id.cart_description);
            addButton=view.findViewById(R.id.cart_btn_plus);
            context = view.getContext();
        }

        public void bind(int position) {
            final Menu menu = menuList.get( position );

            name.setText( menu.getName() );
            description.setText( menu.getDescription() );
            price.setText( menu.getPrice() );

            Glide.with( context ).load( menu.getPhotoUrl() ).into( image );

            addButton.setOnClickListener( view -> productClickHandler.onMenuClicked( menu ) );
        }
    }
}
