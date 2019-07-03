package app.com.mexitours.fragmentsMenu;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.com.mexitours.MainActivity;
import app.com.mexitours.R;
import app.com.mexitours.conexionSQL.selectDBPerfil;
import app.com.mexitours.homeMenu;


public class perfilFragment extends Fragment {
    Button logout;
    public Integer auxUIS;
    TextView nombre, puesto;
    byte[] auxfileBytes;
    de.hdodenhof.circleimageview.CircleImageView perfilFoto;







    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            auxUIS = getArguments().getInt(homeMenu.PREF_IUS, 0);
        }

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View inf = inflater.inflate(R.layout.fragment_perfil, container, false);

        logout = (Button) inf.findViewById(R.id.btnLogout);
        perfilFoto = (de.hdodenhof.circleimageview.CircleImageView) inf.findViewById(R.id.imgVPerfil);
        nombre = (TextView) inf.findViewById(R.id.txtVPerfil);
        puesto = (TextView) inf.findViewById(R.id.txtVPuesto);
        System.out.println("PERFIL FRAGMENT " +auxUIS);
        selectDBPerfil perfil = new selectDBPerfil(this.getContext(), auxUIS);

        perfil.getDBUserDetails();
        perfil.getDBUserPuesto();
        System.out.println(perfil.getNombre());

        auxfileBytes = perfil.getFileBytes();
        Bitmap bm = BitmapFactory.decodeByteArray(auxfileBytes, 0, auxfileBytes.length);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        perfilFoto.setMinimumHeight(dm.heightPixels);
        perfilFoto.setMinimumWidth(dm.widthPixels);
        perfilFoto.setImageBitmap(bm);


        nombre.setText(perfil.getNombre() + " " +perfil.getApellido());
        puesto.setText(perfil.getPuesto());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.changeState(getContext(), false);
                getActivity().finish();

            }
        });

        return inf;
    }


}
