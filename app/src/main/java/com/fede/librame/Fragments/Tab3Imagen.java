package com.fede.librame.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fede.librame.Activities.BookDetails;
import com.fede.librame.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3Imagen extends Fragment {

    private BookDetails activity;
    private ProgressBar progressBar;
    
    public Tab3Imagen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab3_imagen, container, false);
        activity = (BookDetails)getActivity();
        progressBar = v.findViewById(R.id.progressImg);
        progressBar.setVisibility(View.VISIBLE);

        try{
            ImageView listview_image = v.findViewById(R.id.imgPortada);
            String auxruta = activity.libroactual.getRutaportada().toString().replace("-M.jpg", "-L.jpg");
            Picasso.with(getContext()).load(Uri.parse(auxruta)).error(R.drawable.noimage).into(listview_image);
            progressBar.setVisibility(View.INVISIBLE);
        }catch (Exception e){
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(activity, "Error al cargar imagen", Toast.LENGTH_SHORT).show();
        }
        return v;
    }

}
