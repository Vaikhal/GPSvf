package app.com.mexitours.fragmentsMenu.transfers;

import android.os.Parcel;
import android.os.Parcelable;

public class traslados implements Parcelable {
    private String tipoTraslado;
    private String nombrePax;
    private String fecha; //Date
    private String hora;  //Time
    private String origen;
    private String destino;

    public traslados(String tipoTraslado, String nombrePax, String fecha, String hora, String origen, String destino) {
        this.tipoTraslado = tipoTraslado;
        this.nombrePax = nombrePax;
        this.fecha = fecha;
        this.hora = hora;
        this.origen = origen;
        this.destino = destino;
    }

    public traslados(){

    }

    /* ---------- GET METHODS ------- */
    public String getTipoTraslado() { return tipoTraslado; }
    public String getNombrePax() { return nombrePax; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }




    /* ---------- SET METHODS ------- */
    public void setTipoTraslado(String tipoTraslado) { this.tipoTraslado = tipoTraslado; }
    public void setNombrePax(String nombrePax) { this.nombrePax = nombrePax; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setHora(String hora) { this.hora = hora; }
    public void setOrigen(String origen) { this.origen = origen; }
    public void setDestino(String destino) { this.destino = destino; }




    /*----------- PARCELABLE METHODS   -------*/

    protected traslados(Parcel in) {
        tipoTraslado = in.readString();
        nombrePax = in.readString();
        fecha = in.readString();
        hora = in.readString();
        origen = in.readString();
        destino = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tipoTraslado);
        dest.writeString(nombrePax);
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeString(origen);
        dest.writeString(destino);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<traslados> CREATOR = new Parcelable.Creator<traslados>() {
        @Override
        public traslados createFromParcel(Parcel in) {
            return new traslados(in);
        }

        @Override
        public traslados[] newArray(int size) {
            return new traslados[size];
        }
    };
}



































//-------------------------     antiguo sin implements parcelable   --------------------------------------------------


/*

public class traslados{
    private String tipoTraslado;
    private String nombrePax;
    private String fecha; //Date
    private String hora;  //Time
    private String origen;
    private String destino;

    public traslados(String tipoTraslado, String nombrePax, String fecha, String hora, String origen, String destino) {
        this.tipoTraslado = tipoTraslado;
        this.nombrePax = nombrePax;
        this.fecha = fecha;
        this.hora = hora;
        this.origen = origen;
        this.destino = destino;
    }

    public traslados(){

    }

    */
/* ---------- GET METHODS ------- *//*

    public String getTipoTraslado() { return tipoTraslado; }
    public String getNombrePax() {
        return nombrePax;
    }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getOrigen() {
        return origen;
    }
    public String getDestino() {
        return destino;
    }




    */
/* ---------- SET METHODS ------- *//*

    public void setTipoTraslado(String tipoTraslado) {
        this.tipoTraslado = tipoTraslado;
    }
    public void setNombrePax(String nombrePax) {
        this.nombrePax = nombrePax;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public void setOrigen(String origen) {
        this.origen = origen;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
}
*/
