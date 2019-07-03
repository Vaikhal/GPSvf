package app.com.mexitours.conexionSQL;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;


public class conexionBD {
    /*
    * Si se quiere correr en dispositivo con sistema operativo por debajo de 5.1 Lollipop, se debe bajar la version de SDK al numero 19 ya que las
    * librerias net.sourceforge no son compatibles con dicho numero de compilacion del SDK
    *
    * jtds-1.3.1 a partir de 5.1 y SDK correspondiente
    * jtds-1.3.0 por debajo de 5.1 y SDK correspondiente
    *
    *
    * */
    String url="jdbc:jtds:sqlserver://189.208.48.228:1433/";
    //String url="jdbc:mysql://marcopolo.mx:3306/";
    String db= "SIITOUR";
    String driver ="net.sourceforge.jtds.jdbc.Driver";
    //String driver ="com.mysql.jdbc.Driver";
    String username="sa";//user must have read-write permission to Database
    String password= "@Mexiadm##$$::FJF";
    Connection conn = null;
    Boolean status;
    Context contexto;

    public conexionBD(Context context){
        contexto = context;

    }
    /*
    Si se requiere el uso de toast es necesario pasar el contexto que tiene el activity principal o el fragment sobre el que corre
    * la clase de la siquienre forma: public Connection ConxBD(Context contexto)
    * y declarar el conexto el la actividad padre:  Context context = getApplicationContext();
    * por ultimo al momento de llamar a la funcion: conn.ConxBD(context);
    *
    * */
    public Connection ConxBD(){

        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url+db,username,password);
            System.out.println("Conexion exitosa 1");
            //Toast.makeText(contexto, "Conexion exitosa", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            //Toast.makeText(contexto, "Revise su conexión a internet", Toast.LENGTH_SHORT).show();
            //status = false;
            System.out.println("La conexion a la BD ha fallado, intendelo de nuevo mas tarde.");
        }
        return conn;
    }
}
