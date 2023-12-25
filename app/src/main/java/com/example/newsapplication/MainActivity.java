package com.example.newsapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerViewgrid;
    ArrayList<String> contentArraylist = new ArrayList<>();
    //    ArrayList<String> gridArraylist=new ArrayList<>();
    ArrayList<GetterSetter> getterSetterArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        recyclerView = findViewById(R.id.recylrId);
        recyclerViewgrid = findViewById(R.id.recylrIdgrid);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewgrid.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        contentArraylist.add("General");
        contentArraylist.add("Science");
        contentArraylist.add("Sports");
        contentArraylist.add("Technology");
        contentArraylist.add("Entertainment");
        contentArraylist.add("Politics");
        contentArraylist.add("Music");
        contentArraylist.add("Health");


        ApiCall("business");

        RecylerAdapter adapter = new RecylerAdapter(MainActivity.this, contentArraylist);
        recyclerView.setAdapter(adapter);

    }

    //===========================================================================================

    // API HITTING
    public void ApiCall(String concat) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://newsapi.org/v2/top-headlines?country=in&category=" + concat + "&apiKey=f163bdc4e26f4061b28423dbfec53c34";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                getterSetterArrayList.clear();

                try {
                    JSONObject object = new JSONObject(String.valueOf(response));
                    JSONArray jsonArray = object.getJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        GetterSetter pack = new GetterSetter();

                        JSONObject object1 = jsonArray.getJSONObject(i);

                        pack.setTitle(object1.getString("title"));
                        pack.setButtonurl(object1.getString("url"));
                        pack.setImgUrl(object1.getString("urlToImage"));

                        getterSetterArrayList.add(pack);


                    }


                    RecyclerGridAdapter adapter = new RecyclerGridAdapter(MainActivity.this, getterSetterArrayList);
                    recyclerViewgrid.setAdapter(adapter);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");

                return headers;
            }
        };


        queue.add(jsonObjectRequest);


    }

    class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.ViewHolder>{


        Context context;
        ArrayList<String> contentRecylerArraylist;

        public RecylerAdapter(Context context, ArrayList<String> contentRecylerArraylist) {
            this.context = context;
            this.contentRecylerArraylist = contentRecylerArraylist;
        }

        @NonNull
        @Override
        public RecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecylerAdapter.ViewHolder holder, int position) {


            if (position == 0) {
                holder.textView.setText("Home");
            } else {
                holder.textView.setText(contentRecylerArraylist.get(position));
            }
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    v.setBackgroundColor(Color.GRAY);
                    //       ((TextView)v).setTextColor(getResources().getColor(R.color.black,null));

                    ApiCall(contentRecylerArraylist.get(position).toLowerCase(Locale.ROOT));

                }
            });
        }

        @Override
        public int getItemCount() {
            return contentRecylerArraylist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textItemId);

            }
        }
    }

    //===============================================================================================

    //RecyclerViewAdapter for Grid layout


    public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.ViewHolder> {

        Context context;
        ArrayList<GetterSetter> gridAdapterArrayList = new ArrayList<GetterSetter>();

        public RecyclerGridAdapter(Context context, ArrayList<GetterSetter> gridAdapterArrayList) {
            this.context = context;
            this.gridAdapterArrayList = gridAdapterArrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            holder.textView.setText(getterSetterArrayList.get(position).getTitle());


            Intent intent;
            intent = new Intent(MainActivity.this, WebViewINtent.class);
            intent.putExtra("url", getterSetterArrayList.get(position).getButtonurl());

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
            //  holder.imageView.setImageResource(Integer.parseInt(gridAdapterArrayList.get(position)));
            Picasso.get().load(getterSetterArrayList.get(position).getImgUrl()).into(holder.imageView);


        }

        @Override
        public int getItemCount() {

            return getterSetterArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView textView;
            Button button;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView2);
                textView = itemView.findViewById(R.id.textView4);
                button = itemView.findViewById(R.id.button);

            }
        }
    }


}


