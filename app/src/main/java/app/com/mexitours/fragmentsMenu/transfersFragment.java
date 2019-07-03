package app.com.mexitours.fragmentsMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import app.com.mexitours.R;
import app.com.mexitours.conexionSQL.conexionBD;
import app.com.mexitours.conexionSQL.selectDBPerfil;
import app.com.mexitours.fragmentsMenu.transfers.adapterDatos;
import app.com.mexitours.fragmentsMenu.transfers.transfers_extended;
import app.com.mexitours.fragmentsMenu.transfers.traslados;
import app.com.mexitours.homeMenu;

public class transfersFragment extends Fragment {
    public Integer auxUIS, UIDpuesto;
    public static final String PREF_TRAS_OBJ ="state.OBJECT.transfer";
    public static final String PREF_TRAS_NOMB ="state.OBJECT.transfer.name";
    String z;
    Boolean isSuccess;
    ArrayList<traslados> transfers;
    RecyclerView recyclertraslados;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            auxUIS = getArguments().getInt(homeMenu.PREF_IUS, 0);
        }

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View inf = inflater.inflate(R.layout.fragment_transfers, container, false);

        transfers = new ArrayList<>();
        recyclertraslados = (RecyclerView) inf.findViewById(R.id.rvTransfers);
        recyclertraslados.setLayoutManager(new LinearLayoutManager(this.getContext()));
        getTraslados();
        adapterDatos adapter = new adapterDatos(transfers);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transfers_extended transfers_extended = new transfers_extended();
                //Toast.makeText(getContext(), "Seleccion: "+transfers.get(recyclertraslados.getChildAdapterPosition(view)).getNombrePax(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                //bundle.putString(PREF_TRAS_NOMB,transfers.get(recyclertraslados.getChildAdapterPosition(view)).getNombrePax());
                bundle.putParcelable(PREF_TRAS_OBJ, transfers.get(recyclertraslados.getChildAdapterPosition(view)));
                transfers_extended.setArguments(bundle);


                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frmLayoutContainer, transfers_extended);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        recyclertraslados.setAdapter(adapter);


        return inf;

    }

    private void getTraslados(){
        ResultSet rs;
        traslados tras = null;
        conexionBD conn = new conexionBD(this.getContext());



        selectDBPerfil perfil = new selectDBPerfil(this.getContext(), auxUIS);
        perfil.getDBUserDetails();
        UIDpuesto = perfil.getUIDpuesto();

        try {
            if (conn == null){
                z = "Verifica tu conexion a internet";
                Toast.makeText(this.getContext(), "pero que fue lo que pasho?", Toast.LENGTH_SHORT).show();
            }
            else {
                //PreparedStatement st = conn.ConxBD().prepareStatement("SELECT  M_Traslado.Tipo_Servicio, M_Traslado.Pax_Nombre, M_Traslados_Event.Fecha_Servicio, M_Traslado.Time_Book, M_Traslado.locStart, M_Traslado.locFinish FROM M_Traslado INNER JOIN M_Traslados_Event ON M_Traslado.Id_Transfer_Event = M_Traslados_Event.Id_Transfer_Event WHERE M_Traslados_Event.Id_Usuario_Sistema = ?; ");
                PreparedStatement st = conn.ConxBD().prepareStatement("SELECT  Servicio_Nombre ,Pax_Nombre, Fecha_Servicio, Time_Book, locStart, locFinish FROM M_Traslado_TServicio INNER JOIN M_Traslado ON M_Traslado_TServicio.Tipo_Servicio = M_Traslado.Tipo_Servicio INNER JOIN M_Traslados_Event ON M_Traslado.Id_Transfer_Event = M_Traslados_Event.Id_Transfer_Event WHERE M_Traslados_Event.Id_Usuario_Sistema = ?;");
                st.setInt(1, UIDpuesto);
                rs = st.executeQuery();
                while (rs.next()) {
                    tras = new traslados();

                    tras.setTipoTraslado(rs.getString("Servicio_Nombre"));
                    tras.setNombrePax(rs.getString("Pax_Nombre"));
                    Date date = rs.getDate("Fecha_Servicio");
                    tras.setFecha(date.toString());
                    Time time = rs.getTime("Time_Book");
                    tras.setHora(time.toString());
                    tras.setOrigen(rs.getString("locStart"));
                    tras.setDestino(rs.getString("locFinish"));

                    transfers.add(tras);
                    isSuccess=true;

                }
                conn.ConxBD().close();
            }
        } catch (Exception ex) {
            isSuccess = false;
            z = ex.getMessage();
            Toast.makeText(this.getContext(), z, Toast.LENGTH_SHORT).show();
        }



    }


}
