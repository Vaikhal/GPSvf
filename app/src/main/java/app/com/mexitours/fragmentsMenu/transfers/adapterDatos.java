package app.com.mexitours.fragmentsMenu.transfers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


import app.com.mexitours.R;


public class adapterDatos extends RecyclerView.Adapter<adapterDatos.ViewHolderTraslados> implements View.OnClickListener{
    ArrayList<traslados> listaTraslados;
    private View.OnClickListener listener;

    public adapterDatos(ArrayList<traslados> listaTraslados) {
        this.listaTraslados = listaTraslados;
    }



    @Override
    public ViewHolderTraslados onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transfers, null, false);
        view.setOnClickListener(this);

        return new ViewHolderTraslados(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderTraslados holderTras, int position) {
        holderTras.ettipo.setText(listaTraslados.get(position).getTipoTraslado());
        holderTras.etnombre.setText(listaTraslados.get(position).getNombrePax());
        holderTras.etfecha.setText(listaTraslados.get(position).getFecha());
        holderTras.ethora.setText(listaTraslados.get(position).getHora());
        holderTras.etorigen.setText(listaTraslados.get(position).getOrigen());
        holderTras.etdestino.setText(listaTraslados.get(position).getDestino());
        //holderTras.etfoto.setImageResource(listaTraslados.get(position).getFoto());
    }


    @Override
    public int getItemCount() {
        return listaTraslados.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }


    public class ViewHolderTraslados extends RecyclerView.ViewHolder {
        TextView ettipo, etnombre, etorigen, etdestino, etfecha, ethora;


        public ViewHolderTraslados(@NonNull View itemView) {
            super(itemView);
            ettipo = (TextView) itemView.findViewById(R.id.txtVTipo_Servicio);
            etnombre = (TextView) itemView.findViewById(R.id.txtVNombre_Pasajero);
            etorigen = (TextView) itemView.findViewById(R.id.txtVOrigen);
            etdestino = (TextView) itemView.findViewById(R.id.txtVDestino);
            etfecha = (TextView) itemView.findViewById(R.id.txtVFecha_Servicio);
            ethora = (TextView) itemView.findViewById(R.id.txtVHora_Servicio);
        }
    }


}
