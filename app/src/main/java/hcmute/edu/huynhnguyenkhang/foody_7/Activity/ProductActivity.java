package hcmute.edu.huynhnguyenkhang.foody_7.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hcmute.edu.huynhnguyenkhang.foody_7.Adapter.ProductAdapter;
import hcmute.edu.huynhnguyenkhang.foody_7.DataSouce.Menu;
import hcmute.edu.huynhnguyenkhang.foody_7.MyApplication;
import hcmute.edu.huynhnguyenkhang.foody_7.R;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.ProductClickHandler {
    private static final String TAG = ProductActivity.class.getSimpleName();
    // Current URL: Thu Duc
    private static final String URL = "https://gappapi.deliverynow.vn/api/dish/get_delivery_dishes?id_type=1&request_id=";
    private String storeId;
    private String storeName;
    private String storeAddress;
    private String storeImage;
    private ArrayList<Menu> productList;
    private ProductAdapter adapter;
    TextView Name,Address;
    Button turnBack;
    ImageView storeImageView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_product);
        Reference();
        storeId= getIntent().getStringExtra("storeId");
        storeName=getIntent().getStringExtra("storeName");
        storeAddress=getIntent().getStringExtra("storeAddress");
        storeImage=getIntent().getStringExtra("storeImage");


        productList = new ArrayList<>();
        recyclerView = findViewById( R.id.recycler_view_product );
        adapter = new ProductAdapter(this , productList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( this, 1 );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setAdapter( adapter );
        recyclerView.setNestedScrollingEnabled( false );

        turnBack.setOnClickListener(v->{
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        });

        setInformation();
        fetchStoreItems();
    }
    void Reference(){
        Name=findViewById(R.id.txtNameStore);
        Address=findViewById(R.id.store_location);
        turnBack=findViewById(R.id.btnTurnBack);
        storeImageView=(ImageView)findViewById(R.id.store_image);


    }
    void setInformation(){
        Name.setText(storeName);
        Address.setText(storeAddress);
        Glide.with( this ).load( storeImage ).into( storeImageView );

    }
    @SuppressLint({"RestrictedApi", "NotifyDataSetChanged"})
    private void fetchStoreItems() {
       JsonObjectRequest request = new JsonObjectRequest( URL + storeId,
                response -> {
                    if (response == null) {
                        Toast.makeText( this, "Couldn't fetch the " +
                                        "menu items! Please try again.",
                                Toast.LENGTH_LONG ).show();
                        return;
                    }

                    try {
                        JSONObject jsonObject = new JSONObject( response.toString());

                        JSONArray jSONArray = jsonObject
                                .getJSONObject( "reply" )
                                .getJSONArray( "menu_infos" );

                        productList.clear();

                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONArray dishes = jSONArray
                                    .getJSONObject( i )
                                    .getJSONArray( "dishes" );

                            for (int j = 0; j < dishes.length(); j++) {
                                JSONObject dish = dishes.getJSONObject( j );
                                String name = dish.getString( "name" );
                                String description = dish
                                        .getString(
                                                "description" );
                                String photoUrl = dish
                                        .getJSONArray( "photos" )
                                        .getJSONObject( 1 )
                                        .getString( "value" );
                                String price = dish.getJSONObject( "price" )
                                        .getString( "text" );
                                if(!photoUrl.equals("https://images.foody.vn/default/s180x180/shopeefood-deli-dish-no-image.png")){

                                productList.add( new Menu( storeName, name, price,
                                        description, photoUrl ) );
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.getStackTrace();
                    }
                    },
                error -> {
                    // error in getting json
                    Log.e( TAG, error.getMessage() );
                } ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put( "x-foody-client-type", "1" );
                params.put( "x-foody-client-id", "" );
                params.put( "x-foody-client-language", "vi" );
                params.put( "x-foody-client-version", "3.0.0" );
                params.put( "x-foody-access-token", "" );
                params.put( "x-foody-api-version", "1" );
                params.put( "x-foody-app-type", "1004" );

                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue( request );
    }


    @Override
    public void onMenuClicked(Menu menu) {

    }
}