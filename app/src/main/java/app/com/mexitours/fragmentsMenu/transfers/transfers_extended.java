package app.com.mexitours.fragmentsMenu.transfers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.mexitours.R;


public class transfers_extended extends Fragment {
    TextView txtvTipoServicio, txtname, txtvFecha, txtvHora, txtvOrigen, txtvdestino, txtvObservaciones;
    traslados trasladoObj;
    String name;
    public static final String PREF_TRAS_OBJ ="state.OBJECT.transfer";
    //public static final String PREF_TRAS_NOMB ="state.OBJECT.transfer.name";

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            //name = getArguments().getString(transfersFragment.PREF_TRAS_NOMB, "");
            trasladoObj = getArguments().getParcelable(PREF_TRAS_OBJ);
        }

    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.transfers_extended, container, false);
        txtvTipoServicio = (TextView) inf.findViewById(R.id.txtVTipo_Servicio2);
        txtname = (TextView) inf.findViewById(R.id.txtVNombre_Pasajero2);
        txtvFecha = (TextView) inf.findViewById(R.id.txtVFecha_Servicio2);
        txtvHora = (TextView) inf.findViewById(R.id.txtVHora_Servicio2);
        txtvOrigen = (TextView) inf.findViewById(R.id.txtVOrigen2);
        txtvdestino = (TextView) inf.findViewById(R.id.txtVDestino2);
        txtvObservaciones = (TextView) inf.findViewById(R.id.txtvObservaciones2);

        txtvTipoServicio.setText(trasladoObj.getTipoTraslado());
        txtname.setText(trasladoObj.getNombrePax());
        txtvFecha.setText(trasladoObj.getFecha());
        txtvHora.setText(trasladoObj.getHora());
        txtvOrigen.setText(trasladoObj.getOrigen());
        txtvdestino.setText(trasladoObj.getDestino());
        txtvObservaciones.setText("Dar carta de bienvenida al lobby, mencionarle su itinerario y recordarle los numeros de emergencia.");

        return inf;
    }
}
