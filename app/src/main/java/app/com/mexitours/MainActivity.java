package app.com.mexitours;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import app.com.mexitours.conexionSQL.conexionBD;


public class MainActivity extends AppCompatActivity {
    RelativeLayout rellay1, rellay2;
    CheckBox checkBox;
    Button login,forgot;
    ProgressBar carga;
    Boolean saveProfile;
    private  final String SHRD_PREF = "app.com.mexitours";
    private  final String PREF_STATE_LOG ="state.log.user";
    private  final String PREF_USER_NUSAV ="state.log.user.nusav";
    private  final String PREF_IUS ="state.log.user.ius";




    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(getsavSess()){
                Intent intent = new Intent (getBaseContext(), homeMenu.class);
                startActivityForResult(intent, 0);
                finish();
            }else{
                rellay1.setVisibility(View.VISIBLE);
                rellay2.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkBox = (CheckBox) findViewById(R.id.checkTerms);
        login = (Button) findViewById(R.id.btn_login);
        forgot = (Button) findViewById(R.id.btn_forget);
        final EditText etxtUsuario = (EditText) findViewById(R.id.etxtUsuario);
        final EditText etxtPass = (EditText) findViewById(R.id.etxtPass);
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        carga = (ProgressBar) findViewById(R.id.pbCarga);
        handler.postDelayed(runnable, 2000); //Tiempo de espera del splashScreen


        checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(etxtUsuario.getText().toString().isEmpty() & etxtPass.getText().toString().isEmpty()){
                    etxtUsuario.setError("Email o usuario inválido");
                    etxtPass.setError("Contraseña no válida");
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etxtUsuario.getText().toString().isEmpty() & etxtPass.getText().toString().isEmpty()){
                    etxtUsuario.setError("Email o usuario inválido");
                    etxtPass.setError("Contraseña no válida");
                }else{
                    final String usuario = etxtUsuario.getText().toString();
                    final String password = etxtPass.getText().toString();
                    loginCheck checklogin = new loginCheck(usuario, password);
                    checklogin.execute();

                }
            }
        });

    }


    public void cerrar_sesion(){


    }
    public class loginCheck extends AsyncTask<Void, Integer, Boolean> {

        String UIDaux, UPASSaux, z, b64UID, b64UPASS;
        conexionBD conn = null;
        Boolean  result, isSuccess;
        ResultSet rs;
        Integer IDU;



        public loginCheck(String usuario, String password){
            UIDaux = usuario;
            UPASSaux = password;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            carga.setVisibility(View.VISIBLE);
            carga.setMax(100);
            carga.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            conn = new conexionBD(MainActivity.this);
            publishProgress(0);
                try
                {
                    if (conn == null) {
                        z = "Check Your Internet Access!";
                        publishProgress(100);
                    }
                    else {
                        publishProgress(40);
                        b64UID = Base64.encodeToString(UIDaux.getBytes(), Base64.DEFAULT);
                        b64UPASS = Base64.encodeToString(UPASSaux.getBytes(), Base64.DEFAULT);
                        b64UID= b64UID.replaceAll("(\n|\r)", "");
                        b64UPASS= b64UPASS.replaceAll("(\n|\r)", "");
                        PreparedStatement st = conn.ConxBD().prepareStatement("SELECT * FROM M_Usuarios WHERE (Usuario  =? OR Email=?) AND Password =?");

                        //PreparedStatement st = conn.prepareStatement("SELECT Usuario, Email, Password FROM M_Usuarios WHERE Usuario  =?) AND Password =?");
                        st.setString(1, b64UID);
                        st.setString(2, UIDaux);
                        st.setString(3, b64UPASS);

                        rs = st.executeQuery();

                        System.out.println(result);

                        publishProgress(60);
                        if(rs.next()){
                            z = "Login successful";
                            IDU = rs.getInt("Id_Usuario_Sistema"); //IDTable
                            System.out.println("MainActivity "+IDU);
                            isSuccess=true;
                            publishProgress(80);
                            conn.ConxBD().close();
                            publishProgress(100);
                        }
                        else{
                            z = "Invalid Credentials!";
                            isSuccess = false;
                            publishProgress(0);

                        }
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }


            System.out.println(z);


            return isSuccess;
        }



        @Override
        protected void onPostExecute(Boolean isSuccess2) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(isSuccess){
                savSess();
                System.out.println("End AsyncTask");
                Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                if(getsavSess()){
                    SharedPreferences preferences = getSharedPreferences(SHRD_PREF, MODE_PRIVATE);
                    preferences.edit().putString(PREF_USER_NUSAV,b64UID).apply();
                    preferences.edit().putInt(PREF_IUS,IDU).apply();
                    System.out.println("On postexecute "+IDU);
                }

                SharedPreferences preferences = getSharedPreferences(SHRD_PREF, MODE_PRIVATE);
                preferences.edit().putInt(PREF_IUS,IDU).apply();
                Intent intent = new Intent (getBaseContext(), homeMenu.class);
                startActivityForResult(intent, 0);
                finish();
            }
            if(z=="Invalid Credentials!"){
                Toast.makeText(getApplicationContext(), "Usuario, correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
            if (networkInfo != null && networkInfo.isConnected()) {
                System.out.println("Network connected");
            } else {
                Toast.makeText(getApplicationContext(), "Verifica tu conexion a internet", Toast.LENGTH_SHORT).show();
                carga.setProgress(0);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            carga.setProgress(progress[0].intValue());
        }




    }
    public static void changeState(Context c, boolean b){
        SharedPreferences preferences = c.getSharedPreferences("app.com.mexitours", MODE_PRIVATE);
        preferences.edit().putBoolean("state.log.user",b).apply();
    }

    public void savSess(){
        SharedPreferences preferences = getSharedPreferences(SHRD_PREF, MODE_PRIVATE);
        preferences.edit().putBoolean(PREF_STATE_LOG,checkBox.isChecked()).apply();
    }

    public boolean getsavSess(){
        SharedPreferences preferences = getSharedPreferences(SHRD_PREF, MODE_PRIVATE);
        return preferences.getBoolean(PREF_STATE_LOG,false);
    }
}

