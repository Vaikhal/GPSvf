package app.com.mexitours.conexionSQL;

import android.content.Context;
import android.widget.Toast;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class selectDBPerfil {

    Context context;
    Integer auxUID, UIDpuesto;
    String Nombre, Apellido,z, Puesto;
    Boolean isSuccess;
    //byte[] fileBytes;
    byte[] fileBytes;





    public selectDBPerfil(Context context, Integer UID) {
        this.context = context;
        this.auxUID = UID;
    }

    public void getDBUserDetails(){
        ResultSet rs;
        conexionBD conn = new conexionBD(context);
        try {
            if (conn == null){
                z = "Verifica tu conexion a internet";
            }
            else {
                System.out.println("selectDBPerfil "+auxUID);
                PreparedStatement st = conn.ConxBD().prepareStatement("SELECT * FROM M_Empleados WHERE Id_Usuario_Sistema = ?");
                st.setInt(1, auxUID);
                rs = st.executeQuery();
                System.out.println("getDBUserDetails TRY.CATCH");
                while (rs.next()) {
                    UIDpuesto = rs.getInt("Id_Puesto");
                    System.out.println("getDBUserDetails " + UIDpuesto);
                    Nombre = rs.getString("Nombres");
                    Apellido = rs.getString("Apellido_Paterno");
                    fileBytes = rs.getBytes("Foto_Empleado");
                    isSuccess=true;
                    conn.ConxBD().close();

                }
                System.out.println(getUIDpuesto());
            }
        } catch (Exception ex) {
            isSuccess = false;
            z = ex.getMessage();
            Toast.makeText(context, z, Toast.LENGTH_SHORT).show();
        }
    }

    public void getDBUserPuesto(){
        ResultSet rs;
        conexionBD conn = new conexionBD(context);
        try {
            if (conn == null){
                z = "Verifica tu conexion a internet";
            }
            else {
                PreparedStatement st = conn.ConxBD().prepareStatement("SELECT * FROM M_Empleados_Puestos WHERE Id_Puesto = ?");
                st.setInt(1, UIDpuesto);
                rs = st.executeQuery();

                while (rs.next()) {
                    Puesto = rs.getString("Puesto");
                    isSuccess=true;
                    conn.ConxBD().close();
                }
            }
        } catch (Exception ex) {
            isSuccess = false;
            z = ex.getMessage();
            Toast.makeText(context, z, Toast.LENGTH_SHORT).show();
        }
    }






    public String getNombre() {
        return Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public String getPuesto() {
        return Puesto;
    }

    public Integer getUIDpuesto() { return UIDpuesto; }

    public byte[] getFileBytes() {
        return fileBytes;
    }
}
