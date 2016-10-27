package Opendat.Registro_del_Tiempo.Clases_Genericas;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase generica utilizada para almacenar los datos de los eventos asignados a manipulacion de puerta magnetica, que son obtenidos
 * desde webservice y utilizarlos en el sistema.
 *
 * Modificaciones:
 * Fecha                    Autor                   Descripcion
 * -------------------------------------------------------------------------------------------------
 * 26.10.2016               Jonathan Vasquez        Creacion de Clase
 * 27.10.2016               Jonathan Vasquez        Eliminacion de atributos no utilizados en el sistema y implementacion de 'Parceable' para traspaso de parametros entre activities
 *
 */

public class EventoPuertaMagnetica implements Parcelable {
    //private String _U01B3F1;
    //private String _U01B3F2;
    private String _U01B3F3;
    //private String _U01A005;
    //private String _U01A006;
    //private String _ORIGEN;
    private String _DESTINO;
    //private String _U01A008;
    //private int _U01A009;
    //private String _U01A00A;
    //private int _U01A00B;
    //private String _U01A00C;
    //private String _U01A00D;
    //private String _NOMBRE;


    public String get_U01B3F3() {
        return _U01B3F3;
    }

    public void set_U01B3F3(String _U01B3F3) {
        this._U01B3F3 = _U01B3F3;
    }

    public String get_DESTINO() {
        return _DESTINO;
    }

    public void set_DESTINO(String _DESTINO) {
        this._DESTINO = _DESTINO;
    }


    public EventoPuertaMagnetica(){

    }

    public EventoPuertaMagnetica(Parcel source){
        this._U01B3F3 = source.readString();
        this._DESTINO = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._U01B3F3);
        dest.writeString(this._DESTINO);
    }

    public static final Parcelable.Creator<EventoPuertaMagnetica> CREATOR = new Parcelable.Creator<EventoPuertaMagnetica>() {
        public EventoPuertaMagnetica createFromParcel(Parcel source) {
            return new EventoPuertaMagnetica(source);
        }

        public EventoPuertaMagnetica[] newArray(int size) {
            return new EventoPuertaMagnetica[size];
        }
    };


}
