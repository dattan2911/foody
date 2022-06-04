package hcmute.edu.huynhnguyenkhang.foody_7.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hcmute.edu.huynhnguyenkhang.foody_7.Activity.ProductActivity;
import hcmute.edu.huynhnguyenkhang.foody_7.Adapter.StoreAdapter;
import hcmute.edu.huynhnguyenkhang.foody_7.MyApplication;
import hcmute.edu.huynhnguyenkhang.foody_7.R;
import hcmute.edu.huynhnguyenkhang.foody_7.DataSouce.Store;


public class HomeFragment extends Fragment implements StoreAdapter.StoreClickHandler {

    AlertDialog alertDialog;
    TextView listLocation;


    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final String URL = "https://www.foody.vn/__get/Place/HomeListPlace?t=1652290811087&page=1&lat=10.823099&lon=106.629664&count=48&districtId=15&cateId=&cuisineId=&isReputation=&type=1";
    private List<Store> list;
    private StoreAdapter mAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        listLocation= view.findViewById(R.id.listLocation);
        listLocation.setOnClickListener(v->listDialog());
        // Inflate the layout for this fragment
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        list = new ArrayList<>();
        mAdapter = new StoreAdapter(this, list);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        fetchStoreItems();
        return view;
    }
    void listDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Chọn vị trí");
        final CharSequence[] locations={"TP HCM","Long An","Tien Giang","TP Thu Duc","Binh Duong","Vung Tau" };
        builder.setItems(locations, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listLocation.setText(locations[i].toString());


            }
        });
        alertDialog=builder.create();
        alertDialog.show();

    }
    private void fetchStoreItems() {
        @SuppressLint("NotifyDataSetChanged")
        JsonObjectRequest request = new JsonObjectRequest( URL,
                response -> {
                    if (response == null) {
                        Toast.makeText( getActivity(), "Couldn't fetch the " +
                                "store items! Please try again.", Toast.LENGTH_LONG ).show();
                        return;
                    }

                    try {
                        JSONObject jsonObject = new JSONObject( response.toString() );

                        JSONArray jSONArray = jsonObject.getJSONArray( "Items" );
                        ArrayList<Store> items = new ArrayList<>();
                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONObject store = jSONArray.getJSONObject( i );
                            String storeId = store.getString( "Id" );
                            String name = store.getString( "Name" );
                            String address = store.getString( "Address" );
                            String photoUrl = store.getString( "PhotoUrl" );
                            boolean isDelivery= store.getBoolean("IsDelivery");
                            if(isDelivery){

                                items.add(new Store(storeId,name,address,photoUrl));
                            }

                        }
                        list.clear();
                        list.addAll(items);
                        mAdapter.notifyDataSetChanged();
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
                params.put( "X-Requested-With", "XMLHttpRequest" );
                params.put( "Cookie", "gcat=food; fd.res.view.217=990502; " +
                        "__ondemand_sessionid=0plb3jqny0w21mwzcuj0ggxe; floc=217; flg=vn" );

                return params;
            }
        };

        MyApplication.getInstance().addToRequestQueue( request );
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void onStoreClicked(Store store) {
        Intent intent = new Intent( getActivity(), ProductActivity.class );
        intent.putExtra( "storeId", store.getStoreId() );
        intent.putExtra( "storeImage", store.getPhotoUrl() );
        intent.putExtra( "storeAddress", store.getAddress() );
        intent.putExtra( "storeName", store.getName() );
        startActivity( intent );
    }

}