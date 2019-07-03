package app.com.mexitours.conexionSQL;

import android.content.Context;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class insertBD{


    public void insertarElementos(conexionBD conn, Context context){
        try{
            PreparedStatement pst = conn.ConxBD().prepareStatement("INSERT INTO registros (nombre,apellido,email,idioma,fecha) VALUES (?,?,?,?,?)");
            //pst.setString(1,nombre2);
            //pst.setString(2,apellido2);
            //pst.setString(3,email2);
            //pst.setString(4,idioma2);
            //pst.setString(5,fecha);
            //pst.executeUpdate();
            //Toast.makeText(context, "Datos enviados con exito", Toast.LENGTH_SHORT).show();
            conn.ConxBD().close();
        }catch(SQLException e){
            Toast.makeText(context, "Error al enviar datos", Toast.LENGTH_SHORT).show();
        }
    }

}
