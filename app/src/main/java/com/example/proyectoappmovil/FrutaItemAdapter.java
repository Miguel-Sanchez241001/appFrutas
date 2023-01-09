package com.example.proyectoappmovil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.proyectoappmovil.entidades.Fruta;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FrutaItemAdapter extends BaseAdapter {
    public Context context;
    public int layout;
    public ArrayList<Fruta> frutas;
    public ArrayList<String> nombreFruta;

    public ArrayList<Fruta> getFrutas() {
        return frutas;
    }

    public void setFrutas(ArrayList<Fruta> frutas) {
        this.frutas = frutas;
    }

    public FrutaItemAdapter(Context context, int layout, ArrayList<Fruta> fruta) {
        nombreFruta = new ArrayList<>();
        this.context = context;
        this.layout = layout;
        this.frutas = fruta;

    }

    public FrutaItemAdapter(FrutasCatalogo context, int item_fruta) {
        nombreFruta = new ArrayList<>();
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return frutas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //FrutaItemAdapter.this.notifyDataSetChanged();
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.item_fruta,null);
        TextView letra =  v.findViewById(R.id.item_nom) ;
        letra.setText(frutas.get(i).getNombre());
        TextView tel =  v.findViewById(R.id.item_precio) ;
        tel.setText("S/. "+frutas.get(i).getPrecio()+" KG");
        ImageView img =  v.findViewById(R.id.item_img) ;
        ProgressBar p = v.findViewById(R.id.item_proIMG);
        Picasso
                .with(context)
                .load(frutas.get(i).getLinkIMG())
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (p != null) {
                            p.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
        ImageButton btn = v.findViewById(R.id.item_btnAgregarFruta);
        btn.setOnClickListener(view1 -> Agregar(frutas.get(i).getNombre()));
        return v;
    }

    private void Agregar(String nombre) {
        nombreFruta.add(nombre);
        Log.i("AdapterFruta",nombre+"Fruta agregada" + nombreFruta.size());

    }
}
