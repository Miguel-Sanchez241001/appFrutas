package com.example.proyectoappmovil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectoappmovil.entidades.Fruta;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FrutaItemPedidoAdapter extends BaseAdapter {
    public Context context;
    public int layout;
    public ArrayList<Fruta> frutas;
    public ArrayList<String> nombreFruta;
    public Double total;
    public TextView textView;
    public ArrayList<Fruta> getFrutas() {
        return frutas;
    }

    public void setFrutas(ArrayList<Fruta> frutas) {
        this.frutas = frutas;
    }

    public FrutaItemPedidoAdapter(Context context, int layout, ArrayList<Fruta> fruta) {
        nombreFruta = new ArrayList<>();
        this.context = context;
        this.layout = layout;
        this.frutas = fruta;
        this.total = 0.0;
    }
    public FrutaItemPedidoAdapter(Context context, int layout,TextView textView) {
        nombreFruta = new ArrayList<>();
        this.context = context;
        this.layout = layout;
        this.total = 0.0;
        this.textView = textView;

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
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.item_fruta_pedido,null);

        TextView nombre,precio;

        nombre =  v.findViewById(R.id.Pedi_FrutaName) ;
        precio =  v.findViewById(R.id.Pedi_txtPrecio) ;


        nombre.setText(frutas.get(i).getNombre());
        precio.setText("S/. "+frutas.get(i).getPrecio());

        ImageView img =  v.findViewById(R.id.Pedi_imgFruta) ;
        Picasso .with(context)
                .load(frutas.get(i).getLinkIMG())
                .into(img);
        EditText cantidad;
        cantidad = v.findViewById(R.id.Pedi_edtCantidad);
        ImageButton btnadd,btnless;
        btnadd  = v.findViewById(R.id.Pedi_btnAgregar);
        btnless  = v.findViewById(R.id.Pedi_btnRestar);

        btnadd.setOnClickListener(view1 -> Agregar(cantidad,precio,frutas.get(i).getPrecio()));
        btnless.setOnClickListener(view1 -> Restar(cantidad,precio,frutas.get(i).getPrecio()));

       // ActualizarTotal(0.0,Double.parseDouble(frutas.get(i).getPrecio()));
        return v;
    }

    private void Restar(EditText cantidad, TextView precio, String precioUnidad) {
        Double valorAnterior = Double.parseDouble(cantidad.getText().toString());
        Double aux = valorAnterior-1;
        if (aux<=0){
            cantidad.setText(String.valueOf(1.0));
            ActualizarPrecio(precio,precioUnidad,1.0);
        }else{
            cantidad.setText(String.valueOf(aux));
            ActualizarPrecio(precio,precioUnidad,aux);
        }

    }

    private void Agregar(EditText cantidad, TextView precio, String precioUnidad) {
        Double valorAnterior = Double.parseDouble(cantidad.getText().toString());
        cantidad.setText(String.valueOf(valorAnterior+1));


        ActualizarPrecio(precio,precioUnidad,valorAnterior+1);
    }

    private void ActualizarPrecio(TextView precio, String s, Double unidades) {
        Double Anterior = Double.valueOf(precio.getText().toString().replaceAll("S/. ",""));
        precio.setText("");
        Double preciounidad = Double.parseDouble(s);
        Double precioTotal = preciounidad*unidades;

        Log.i("pedidoTotal","TOTAL POR EL MOMENTO" + total);
        precio.setText("S/. "+precioTotal);

      //  ActualizarTotal(Anterior,precioTotal);
    }

    private void ActualizarTotal(Double montoAnterior,Double nuevoMonto) {
if (total==0){
    total = total + nuevoMonto;
}else {
    total = total - montoAnterior;
    total = total + nuevoMonto;
}
        textView.setText(total.toString());

    }
}
