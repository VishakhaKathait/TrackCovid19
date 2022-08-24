package com.example.trackcovid19.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trackcovid19.R;
import com.example.trackcovid19.ui.country.CovidCountry;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class HomeFragment extends Fragment {

    private TextView tvTotalConfirmed, tvTotalDeaths, tvTotalRecovered;
    private ProgressBar progressBar;

    /*  private FragmentHomeBinding binding;

      public View onCreateView(@NonNull LayoutInflater inflater,
                               ViewGroup container, Bundle savedInstanceState) {
          HomeViewModel homeViewModel =
                  new ViewModelProvider(this).get(HomeViewModel.class);

          binding = FragmentHomeBinding.inflate(inflater, container, false);
          View root = binding.getRoot();

          final TextView textView = binding.textHome;
          homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
          return root;
      }

      @Override
      public void onDestroyView() {
          super.onDestroyView();
          binding = null;
      }
  }*/
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //call view

        // bind with xml nd java
        tvTotalConfirmed = root.findViewById(R.id.tvTotalConfirmed);
        // call view
        tvTotalDeaths = root.findViewById(R.id.tvTotalDeaths);
        tvTotalRecovered = root.findViewById(R.id.tvTotalRecovered);
        progressBar = root.findViewById(R.id.progress_circular_home);
//call volley

//        tvTotalConfirmed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(root.getContext(), CovidCountry.class);
//                startActivity(intent);
//            }
//        });

        getData();

        return root;
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue= Volley.newRequestQueue(getActivity());

        String url = "https://api.covid19api.com/summary";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject newResponse = response.getJSONObject("Global");
                    tvTotalConfirmed.setText(newResponse.getString("TotalConfirmed"));
                    tvTotalDeaths.setText(newResponse.getString("TotalDeaths"));
                    tvTotalRecovered.setText(newResponse.getString("TotalRecovered"));
                } catch (JSONException e) {
                    Log.e("ERROR", e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                Log.e("Error Response",error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }
}

