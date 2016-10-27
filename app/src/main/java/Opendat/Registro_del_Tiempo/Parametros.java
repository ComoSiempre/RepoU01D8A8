package Opendat.Registro_del_Tiempo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Opendat.Registro_del_Tiempo.Clases_Genericas.EventoPuertaMagnetica;

/**
 * Clase utilizada para almacenar los atributos principales del dispositivos y utilizarlos como parametros del sistema.
 *
 * Modificaciones:
 * Fecha                    Autor                   Descripcion
 * -------------------------------------------------------------------------------------------------
 * 06.10.2016               Jonathan Vasquez        Creacion de Clase
 * 26.10.2016               Jonathan Vasquez        Modificacion de Tipo de manipulacion de Eventos, de ResultSet a List<EventoPuertaMagnetica>
 * 27.10.2016               Jonathan Vasquez        Implementacion de 'Parceable' para el traspaso de parametros entre activities.
 */
public class Parametros implements Parcelable{
    private String _Url_WebServices;
    private String _Id_Portico;
    private String _Base_Datos_IP;
    private String _Manipulacion_Puerta; //_Tiene_Chapa;
    private String _Id_Relacion;
    private String _Localizacion_Geografica;
    private String _Id_CredencialAdmin;
    private String _Disposicion;
    private List<EventoPuertaMagnetica> _Eventos;



    public Parametros(){
        _Url_WebServices = null;
        _Id_Portico = null;
        _Base_Datos_IP = null;
        _Manipulacion_Puerta = null;
        _Id_Relacion = "U027290";
        _Localizacion_Geografica = null;
        _Id_CredencialAdmin = null;
        _Disposicion = null;
        _Eventos = new ArrayList<>();
    }


    public String get_Url_WebServices() {
        return _Url_WebServices;
    }

    public void set_Url_WebServices(String _Url_WebServices) {
        this._Url_WebServices = _Url_WebServices;
    }

    public String get_Id_Portico() {
        return _Id_Portico;
    }

    public void set_Id_Portico(String _Id_Portico) {
        this._Id_Portico = _Id_Portico;
    }

    public String get_Base_Datos_IP() {
        return _Base_Datos_IP;
    }

    public void set_Base_Datos_IP(String _Base_Datos_IP) {
        this._Base_Datos_IP = _Base_Datos_IP;
    }

    public String get_Manipulacion_Puerta() {
        return _Manipulacion_Puerta;
    }

    public void set_Manipulacion_Puerta(String _Tiene_Chapa) {
        this._Manipulacion_Puerta = _Tiene_Chapa;
    }

    public String get_Id_Relacion() {
        return _Id_Relacion;
    }

    public void set_Id_Relacion(String _Id_Relacion) {
        this._Id_Relacion = _Id_Relacion;
    }

    public String get_Localizacion_Geografica() {
        return _Localizacion_Geografica;
    }

    public void set_Localizacion_Geografica(String _Localizacion_Geografica) {
        this._Localizacion_Geografica = _Localizacion_Geografica;
    }

    public String get_Id_CredencialAdmin() {
        return _Id_CredencialAdmin;
    }

    public void set_Id_CredencialAdmin(String _Id_CredencialAdmin) {
        this._Id_CredencialAdmin = _Id_CredencialAdmin;
    }

    public String get_Disposicion() {
        return _Disposicion;
    }

    public void set_Disposicion(String _Disposicion) {
        this._Disposicion = _Disposicion;
    }

    public List<EventoPuertaMagnetica> get_Eventos() {
        return _Eventos;
    }

    public void set_Eventos(List<EventoPuertaMagnetica> _Eventos) {
        this._Eventos = _Eventos;
    }

    public boolean isEmpty(){
        if(this._Disposicion.isEmpty() &&
                this._Id_CredencialAdmin.isEmpty() &&
                this._Localizacion_Geografica.isEmpty() &&
                this._Base_Datos_IP.isEmpty() &&
                this._Id_Portico.isEmpty() &&
                this._Id_Relacion.isEmpty() &&
                this._Manipulacion_Puerta.isEmpty() &&
                this._Url_WebServices.isEmpty() &&
                this._Eventos.size() == 0){
           return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this._Url_WebServices);
        dest.writeString(this._Id_Portico);
        dest.writeString(this._Base_Datos_IP);
        dest.writeString(this._Manipulacion_Puerta);
        dest.writeString(this._Id_Relacion);
        dest.writeString(this._Localizacion_Geografica);
        dest.writeString(this._Id_CredencialAdmin);
        dest.writeString(this._Disposicion);
        dest.writeTypedList(this._Eventos);
    }


    private Parametros(Parcel source) {
        this();
        this._Url_WebServices = source.readString();
        this._Id_Portico = source.readString();
        this._Base_Datos_IP = source.readString();
        this._Manipulacion_Puerta = source.readString();
        this._Id_Relacion = source.readString();
        this._Localizacion_Geografica = source.readString();
        this._Id_CredencialAdmin = source.readString();
        this._Disposicion = source.readString();
        source.readTypedList(this._Eventos, EventoPuertaMagnetica.CREATOR);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Parametros>() {
        public Parametros createFromParcel(Parcel source) {
            return new Parametros(source);
        }

        public Parametros[] newArray(int size) {
            return new Parametros[size];
        }
    };
}
