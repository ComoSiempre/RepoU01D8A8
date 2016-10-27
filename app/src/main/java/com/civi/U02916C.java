package com.civi;

import android.app.Activity;
import android.app.Notification;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.civi.Activities.FirstTimeActivity;
import com.civi.Activities.MainActivity;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Opendat.Registro_del_Tiempo.Clases_Genericas.Portico;
import Opendat.Registro_del_Tiempo.Clases_Genericas.EventoPuertaMagnetica;

/**
 * Clase que representa el webservice a utilizar.
 *
 * Modificaciones:
 * Fecha                    Autor                   Descripcion
 * -------------------------------------------------------------------------------------------------
 * 12.10.2016               Jonathan Vasquez        Creacion de Clase
 * 25.10.2016               Jonathan Vasquez        Creacion de objeto en diccionario y modificacion de nombre de ws a U02916C (Anteriormente U021E30).
 */

public class U02916C {
    private static final String TAG = "AppMRAT";
    private AlertDialog.Builder alertDialog = null;
    private ProgressBar miBarra = null;
    //variable que almacena el URL del Webservice desde los parametros del sistema.
    private String URL;
    private AsyncTask.Status estadoCall;
    private String NAMESPACE = "http://tempuri.org/";
    Globals global = Globals.getInstance();
    Activity origen; //variable usada en 'CallFechaHora_verificacionWS' cuyo objetivo es diferenciar la logica dependiendo del llamado.
    /**
     * Constructores.
     */
    public U02916C(){}
    public U02916C(String pstrURL) {
        this.URL = pstrURL;
        estadoCall = null;
    }

    //getter and setters


    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public AsyncTask.Status getEstadoCall() {
        return estadoCall;
    }

    public AlertDialog.Builder getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog.Builder alertDialog) {
        this.alertDialog = alertDialog;
    }

    public ProgressBar getMiBarra() {
        return miBarra;
    }

    public void setMiBarra(ProgressBar miBarra) {
        this.miBarra = miBarra;
    }




    /**
     * Funcion usaba tanto para obtener la hora del servidor como para la verificacion de la existencia del webservice.
     */
    public void verificarWS(Activity llamado, String pstrId_Portico){
        this.origen = llamado;
        //no hay parametros, no va PropertyInfo.
        Log.i(TAG, "Ejecutando hilo llamado a ws...");
        new CallFechaHora_verificacionWS().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"ObtenerFechaHora",llamado.getLocalClassName(), pstrId_Portico);
        //call.execute("ObtenerFechaHora");
    }

    public void Buscar_Portico(Activity llamado, String pstrId_Portico){
        Log.i(TAG, "Llamado 'Buscar_Portico' realizado");
        this.origen = llamado;
        //new CallWSDato().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Buscar_Portico", pstrId_Portico);
        new CallWSBuscar_Portico().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pstrId_Portico);
    }

    public void Disponibilidad_Puerta_Magnetica(Activity llamado, String pstrId_Portico){
        Log.i(TAG, "Llamado 'Disponibilidad chapa' realizada");
        this.origen = llamado;
        //new CallWSDato().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"Get_DisponibilidadChapaElectrica", pstrId_Portico);
        new CallWSDisponibilidad_Puerta_Magnetica().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pstrId_Portico);
    }

    public void Verificar_Portico(Activity llamado, String pstrId_Portico){
        Log.i(TAG, "Llamado 'Verificar Disponibilidad Portico' realizada");
        this.origen = llamado;
        new CallWSVerificar_Portico().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pstrId_Portico);

    }

    public void Credencial_Admin(Activity llamado){
        Log.i(TAG, "Llamado 'Credencial_Admin' realizada");
        this.origen = llamado;

        new CallWSCredencial_Admin().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void Localizacion_Geografica(Activity llamado, String pstrId_Portico){
        Log.i(TAG, "Llamado 'Localizacion_Geografica' realizada");
        this.origen = llamado;

        new CallWSLocalizacion_Geografica().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pstrId_Portico);
    }

    public void Eventos_Con_Puerta_Magnetica(Activity llamado, String pstrId_Portico){
        this.origen = llamado;

        new CallWSEventos_Puerta_Magnetica().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pstrId_Portico);

    }

    //threads
    private class CallFechaHora_verificacionWS extends AsyncTask<String, Void, String[]> {
        String idPortico;
        @Override
        protected String [] doInBackground(String... params){
            Log.i(TAG, "CallFechaHora_verificacionWS en backGround...: "+NAMESPACE+params[0]+ "URL: "+URL);
            estadoCall = this.getStatus();//obtengo el estado.
            String [] result = new String [3];

            List<HeaderProperty> headers = new ArrayList<HeaderProperty>();//lista que guarda la autenticacion.
            headers.add(new HeaderProperty("Authorization", "Basic dW1icmFsOjEyMzQ=")); //dW1icmFsOjEyMzQ= es encriptacion 64 de umbral:1234. (64 encode).
            SoapObject request = new SoapObject(NAMESPACE, params[0]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try{
                transporte.call(NAMESPACE + params[0], envelope, headers);
                SoapPrimitive resSoap = (SoapPrimitive)envelope.getResponse();
                result[0] = resSoap.toString(); //resultado del llamado (Fecha)
                result[1] = params[0]; //nombre de la funcion.
                result[2] = params[1]; //nombre de la clase en donde fue llamado. (FirstTimeActivity, MainActivity).
                idPortico = params[2]; //id de portico

            }catch (SoapFault sf){

                String faultString = "FAULT: Code: " + sf.faultcode + "\nString: " +
                        sf.faultstring;
                Log.d(TAG , "fault : " + faultString);

            }
            catch (IOException e) {//entra aqui cuando no logra realizar la coneccion.
                //e.printStackTrace();
                Log.e(TAG, "Excepcion de IOE (ws): " + e.getMessage());
                result = null;
                cancel(true);

            } catch (XmlPullParserException e) {
                //e.printStackTrace();
                Log.e(TAG, "Excepcion de XmlPullParser");
                result = null;
                cancel(true);
            }
            Log.i(TAG, "Saliendo de background...");
            return result;
        }

        @Override
        protected void onCancelled(String [] result){
            if(miBarra != null){
                miBarra.setVisibility(View.INVISIBLE);
            }
            Log.i(TAG, "Entra a funcion onCancelled de verificacion ws");
            Notificacion("No es posible conectarse a WS");
        }

        @Override
        protected void onPostExecute(String [] resultado){
            Log.i(TAG, "Entra a funcion PostExecute de verificacion ws");
            estadoCall = this.getStatus();

            Log.i(TAG, "Resultado final: "+resultado[0]);
            global.setDato(resultado[0]);
            //se verifica la existencia del portico.
            new CallWSBuscar_Portico().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, idPortico);
            //new CallWSDato().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Get_LocalizacionGeografica", idPortico);

            Log.i(TAG, "resto el hilo de verificacion de ws");
            global.decrementThread();
            if (global.getNumThreads() == 0){
                Log.i(TAG, "ultimo hilo: Verificacion WS");
                //miBarra.setVisibility(View.INVISIBLE);
                if(resultado[2].equals("Activities.FirstTimeActivity")){//recordar agregar condicionantes por cada activity que utiliza este hilo de verificacion.
                    //origen.GeneracionConfig();
                    ((FirstTimeActivity)origen).configuracionSistema();
                }
            }

        }
    }

    private class CallWSVerificar_Portico extends AsyncTask<String, Void, List<Portico>>{
        List<Portico> listaPortico;

        private String nombreFuncion = "Verificar_Portico";
        @Override
        protected List<Portico> doInBackground(String... params) {
            Log.i(TAG, "comienzo de ejecucion en background de hilo 'Verificar_portico'");
            String result;

            List<HeaderProperty> headers = new ArrayList<HeaderProperty>();//lista que guarda la autenticacion.
            headers.add(new HeaderProperty("Authorization", "Basic dW1icmFsOjEyMzQ=")); //dW1icmFsOjEyMzQ= es encriptacion 64 de umbral:1234. (64 encode).

            SoapObject request = new SoapObject(NAMESPACE, nombreFuncion);
            //parametros.
            request.addProperty("pstrCod_Portico", params[0]); //sensible a nombre de parametros.

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(NAMESPACE + nombreFuncion, envelope, headers);
                SoapObject resSoap = (SoapObject) envelope.getResponse();

                //se interpreta la lista.
                listaPortico = new ArrayList<>();
                for(int i = 0; i < resSoap.getPropertyCount(); i++){
                    SoapObject ic = (SoapObject) resSoap.getProperty(i);
                    Portico p = new Portico();
                    p.set_ubicacion(ic.getProperty(0).toString());
                    p.set_descripcion_ubicacion(ic.getProperty(1).toString());
                    p.set_site(ic.getProperty(2).toString());
                    p.set_cod_centro_trabajo(ic.getProperty(3).toString());
                    p.set_descripcion_centro_trabajo(ic.getProperty(4).toString());
                    p.set_tipo_dependencia(ic.getProperty(5).toString());
                    p.set_exclusividad_cdt(ic.getProperty(6).toString());
                    p.set_empresa_exclusiva(ic.getProperty(7).toString());
                    p.set_cod_portico(ic.getProperty(8).toString());
                    p.set_descripcion_portico(ic.getProperty(9).toString());
                    p.set_tipo_portico(ic.getProperty(10).toString());
                    p.set_coordenadas(ic.getProperty(11).toString());
                    p.set_tipo_disposicion(ic.getProperty(12).toString());
                    p.set_clase_credencializacion(ic.getProperty(13).toString());
                    p.set_tipo_dispositivo(ic.getProperty(14).toString());
                    p.set_identificacion_dispositivo(ic.getProperty(15).toString());
                    p.set_direccion_ip(ic.getProperty(16).toString());
                    p.set_mascara_ip(ic.getProperty(17).toString());
                    p.set_get_way(ic.getProperty(18).toString());
                    listaPortico.add(p);
                }

            } catch (IOException e) {//no encuentra respuesta (No existe o no Existe la direccion a webservice).
                //e.printStackTrace();
                listaPortico = null;
                cancel(true);
            } catch (XmlPullParserException e) {
                //e.printStackTrace();
                listaPortico = null;
                cancel(true);
            }
            return listaPortico;

        }

        @Override
        protected void onCancelled(List<Portico> result) {
            if(miBarra != null){
                miBarra.setVisibility(View.INVISIBLE);
            }
            Log.i(TAG, "Se ha entrado a funcion onCancelled de verificar_portico");
            Notificacion("No ha sido posible obtener la informacion sobre el portico");

        }

        @Override
        protected void onPostExecute(List<Portico> respuesta){
            Log.i(TAG, "Ingreso a funcion onPOst de verificar_portico");

            /*for(int i=0; i<respuesta.size(); i++){
                Log.i(TAG, respuesta.get(i).get_ubicacion()+", "+respuesta.get(i).get_descripcion_ubicacion()
                + ", "+ respuesta.get(i).get_site());

            }*/
            //asigno a parametro de sistema al tipo de disposisicion del dispositivo.
            global.getParametrosSistema().set_Disposicion(respuesta.get(0).get_tipo_disposicion());

            global.decrementThread();
            if(global.getNumThreads() == 0){
                Log.i(TAG, "Ultimo hilo: verificar_portico");
                if(miBarra != null){
                    miBarra.setVisibility(View.INVISIBLE);
                }
                if(origen instanceof FirstTimeActivity){//se que sera para configuracion.
                    Log.i(TAG, "retorno a FirstTimeActivity");

                    //envio al siguiente bloque logico.
                    //((FirstTimeActivity)origen).continuacion();
                    ((FirstTimeActivity)origen).generateXML();
                }else if(origen instanceof MainActivity){
                    Log.i(TAG, "retorno a MainActivity");
                }
            }
        }
    }

    private class CallWSBuscar_Portico extends AsyncTask<String, Void, String>{
        private String nombreFuncion = "Buscar_Portico";
        @Override
        protected String  doInBackground(String... params){
            Log.i(TAG, "Entro a hilo Buscar_Portico");
            String result;

            List<HeaderProperty> headers = new ArrayList<HeaderProperty>();//lista que guarda la autenticacion.
            headers.add(new HeaderProperty("Authorization", "Basic dW1icmFsOjEyMzQ=")); //dW1icmFsOjEyMzQ= es encriptacion 64 de umbral:1234. (64 encode).

            SoapObject request = new SoapObject(NAMESPACE, nombreFuncion);
            //parametros.
            request.addProperty("idPortico", params[0]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(NAMESPACE + nombreFuncion, envelope, headers);
                SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();
                result = resSoap.toString();

            } catch (IOException e) {//no encuentra respuesta (No existe o no Existe la direccion a webservice).
                //e.printStackTrace();
                result = null;
                cancel(true);
            } catch (XmlPullParserException e) {
                //e.printStackTrace();
                result = null;
                cancel(true);
            }
            return result;
        }

        @Override
        protected void onCancelled(String result){
            Log.i(TAG, "Entra a funcion onCancelled de Buscar_portico");
            Notificacion("No es posible obtener la informacion del portico ingresado");
        }

        @Override
        protected void onPostExecute(String resultado){
            Log.i(TAG, "Entro a funcion onPost de funcion Buscar_Portico, resultado: "+resultado);
            global.decrementThread();
            if(global.getNumThreads() == 0){
                Log.i(TAG, "Ultimo hilo: Buscar_portico");
                if(miBarra != null){
                    miBarra.setVisibility(View.INVISIBLE);
                }
                if(origen instanceof FirstTimeActivity){//se que sera para configuracion.
                    Log.i(TAG, "retorno a FirstTimeActivity");
                    ((FirstTimeActivity)origen).configuracionSistema();
                }else if(origen instanceof MainActivity){
                    Log.i(TAG, "retorno a MainActivity");
                }
            }
        }
    }

    private class CallWSDisponibilidad_Puerta_Magnetica extends AsyncTask<String, Void, String> {
        private String nombreFuncion = "Get_DisponibilidadChapaElectrica";
        private String idPortico;
        @Override
        protected String doInBackground(String... params){
            Log.i(TAG, "Entro al hilo de disponibilidad de puerta magnetica");
            String result;

            List<HeaderProperty> headers = new ArrayList<HeaderProperty>();//lista que guarda la autenticacion.
            headers.add(new HeaderProperty("Authorization", "Basic dW1icmFsOjEyMzQ=")); //dW1icmFsOjEyMzQ= es encriptacion 64 de umbral:1234. (64 encode).

            SoapObject request = new SoapObject(NAMESPACE, nombreFuncion);
            //parametros.
            //request.addProperty("IdPortico", params[1]);
            request.addProperty("idPortico", params[0]);
            idPortico = params[0];

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(NAMESPACE + nombreFuncion, envelope, headers);
                SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();
                result = resSoap.toString();

            } catch (IOException e) {//no encuentra respuesta (No existe o no Existe la direccion a webservice).
                //e.printStackTrace();
                result = null;
                cancel(true);
            } catch (XmlPullParserException e) {
                //e.printStackTrace();
                result = null;
                cancel(true);
            }
            return result;


        }

        @Override
        protected void onCancelled(String result){
            if(miBarra != null){
                miBarra.setVisibility(View.INVISIBLE);
            }
            Log.i(TAG, "Entra a funcion onCancelled de Get_DisponibilidadChapaElectrica");
            Notificacion("No es posible obtener la informacion de la disponibilidad de puerta magnetica");
        }

        @Override
        protected void onPostExecute(String resultado){
            Log.i(TAG, "Entro a funcion onPost de funcion Get_DisponibilidadChapaElectrica, resultado: "+resultado);
            //asigno a parametros del sistema
            global.getParametrosSistema().set_Manipulacion_Puerta(resultado);

            if(global.getParametrosSistema().get_Manipulacion_Puerta().equals("Z0B9C4B")){//SI
                //se incrementa por la utilizacion de un nuevo hilo
                global.incrementThread();
                //obtengo los eventos asignados a la manipulacion de puerta magnetica.
                new CallWSEventos_Puerta_Magnetica().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, idPortico);
            }

            global.decrementThread();
            if(global.getNumThreads() == 0){
                if(miBarra != null){
                    Log.i(TAG, "Ultimo hilo: Disponibilidad_Puerta");
                    miBarra.setVisibility(View.INVISIBLE);
                }
                if(origen instanceof FirstTimeActivity){//se que sera para configuracion.
                    Log.i(TAG, "retorno a FirstTimeActivity");

                    //envio a siguiente bloque logico.
                    ((FirstTimeActivity)origen).generateXML();
                }else if(origen instanceof MainActivity){
                    Log.i(TAG, "retorno a MainActivity");
                }
            }
        }
    }

    private class CallWSCredencial_Admin extends AsyncTask<Void, Void, String>{
        private String nombreFuncion = "Get_CredencialAdmin";
        @Override
        protected String doInBackground(Void... params) {
            Log.i(TAG, "Entro al hilo de Credencial_Admin");
            String result;

            List<HeaderProperty> headers = new ArrayList<HeaderProperty>();//lista que guarda la autenticacion.
            headers.add(new HeaderProperty("Authorization", "Basic dW1icmFsOjEyMzQ=")); //dW1icmFsOjEyMzQ= es encriptacion 64 de umbral:1234. (64 encode).

            SoapObject request = new SoapObject(NAMESPACE, nombreFuncion);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(NAMESPACE + nombreFuncion, envelope, headers);
                SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();
                result = resSoap.toString();

            } catch (IOException e) {//no encuentra respuesta (No existe o no Existe la direccion a webservice).
                //e.printStackTrace();
                result = null;
                cancel(true);
            } catch (XmlPullParserException e) {
                //e.printStackTrace();
                result = null;
                cancel(true);
            }
            return result;

        }

        @Override
        protected void onCancelled(String result){
            if(miBarra != null){
                miBarra.setVisibility(View.INVISIBLE);
            }
            Log.i(TAG, "Entra a funcion onCancelled de Credencial_Admin");
            Notificacion("No es posible obtener la informacion del codigo de la credencial asignada al administrador");
        }

        @Override
        protected void onPostExecute(String resultado){
            Log.i(TAG, "Entro a funcion onPost de funcion Credencial_Admin, resultado: "+resultado);

            //asigno a parametro del sistema.
            global.getParametrosSistema().set_Id_CredencialAdmin(resultado);

            global.decrementThread();
            if(global.getNumThreads() == 0){
                Log.i(TAG, "Ultimo Hilo: Credencial_Admin");
                if(miBarra != null){
                    miBarra.setVisibility(View.INVISIBLE);
                }
                if(origen instanceof FirstTimeActivity){//se que sera para configuracion.
                    Log.i(TAG, "retorno a FirstTimeActivity");

                    //envio a siguiente bloque logico.
                    //((FirstTimeActivity)origen).continuacion();
                    ((FirstTimeActivity)origen).generateXML();
                }else if(origen instanceof MainActivity){
                    Log.i(TAG, "retorno a MainActivity");
                }
            }
        }
    }

    private class CallWSLocalizacion_Geografica extends AsyncTask<String, Void, String> {
        private String nombreFuncion = "Get_LocalizacionGeografica";
        @Override
        protected String doInBackground(String... params){
            Log.i(TAG, "Entro al hilo de localizacion geografica");
            String result;

            List<HeaderProperty> headers = new ArrayList<HeaderProperty>();//lista que guarda la autenticacion.
            headers.add(new HeaderProperty("Authorization", "Basic dW1icmFsOjEyMzQ=")); //dW1icmFsOjEyMzQ= es encriptacion 64 de umbral:1234. (64 encode).

            SoapObject request = new SoapObject(NAMESPACE, nombreFuncion);
            //parametros.
            //request.addProperty("IdPortico", params[1]);
            request.addProperty("idPortico", params[0]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(NAMESPACE + nombreFuncion, envelope, headers);
                SoapPrimitive resSoap = (SoapPrimitive) envelope.getResponse();
                result = resSoap.toString();

            } catch (IOException e) {//no encuentra respuesta (No existe o no Existe la direccion a webservice).
                //e.printStackTrace();
                result = null;
                cancel(true);
            } catch (XmlPullParserException e) {
                //e.printStackTrace();
                result = null;
                cancel(true);
            }
            return result;


        }

        @Override
        protected void onCancelled(String result){
            if(miBarra != null){
                miBarra.setVisibility(View.INVISIBLE);
            }
            Log.i(TAG, "Entra a funcion onCancelled de Localizacion_Geografica");
            Notificacion("No es posible obtener la informacion de la localizacion geografica del dispositivo ingresado. Imposible la configuracion");
        }

        @Override
        protected void onPostExecute(String resultado){
            Log.i(TAG, "Entro a funcion onPost de funcion Localizacion_geografica, resultado: "+resultado);

            //asigno como parametro del sistema
            global.getParametrosSistema().set_Localizacion_Geografica(resultado);

            global.decrementThread();
            if(global.getNumThreads() == 0){
                if(miBarra != null){
                    Log.i(TAG, "Ultimo hilo: Localizacion_geografica");
                    miBarra.setVisibility(View.INVISIBLE);
                }
                if(origen instanceof FirstTimeActivity){//se que sera para configuracion.
                    Log.i(TAG, "retorno a FirstTimeActivity");

                    //envio a siguiente bloque logico.
                    //((FirstTimeActivity)origen).continuacion();
                    ((FirstTimeActivity)origen).generateXML();
                }else if(origen instanceof MainActivity){
                    Log.i(TAG, "retorno a MainActivity");
                }
            }
        }
    }

    private class CallWSEventos_Puerta_Magnetica extends AsyncTask<String, Void, List<EventoPuertaMagnetica>>{
        private String nombreFuncion = "BuscarEventosChapa";
        private String relacion = global.getParametrosSistema().get_Id_Relacion();//nesesario que este aqui.
        List<EventoPuertaMagnetica> listaEventos;
        @Override
        protected List<EventoPuertaMagnetica> doInBackground(String... params) {
            Log.i(TAG, "comienzo de ejecucion en background de hilo 'Eventos_Puerta_Magnetica'");
            String result;

            List<HeaderProperty> headers = new ArrayList<HeaderProperty>();//lista que guarda la autenticacion.
            headers.add(new HeaderProperty("Authorization", "Basic dW1icmFsOjEyMzQ=")); //dW1icmFsOjEyMzQ= es encriptacion 64 de umbral:1234. (64 encode).

            SoapObject request = new SoapObject(NAMESPACE, nombreFuncion);
            //parametros.
            request.addProperty("rel", relacion); //sensible a nombre de parametros.
            request.addProperty("cod_Portico", params[0]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(NAMESPACE + nombreFuncion, envelope, headers);
                SoapObject resSoap = (SoapObject) envelope.getResponse();

                //se interpreta la lista.
                listaEventos = new ArrayList<>();
                for(int i = 0; i < resSoap.getPropertyCount(); i++){
                    SoapObject ic = (SoapObject) resSoap.getProperty(i);
                    EventoPuertaMagnetica evento = new EventoPuertaMagnetica();
                    evento.set_U01B3F3(ic.getProperty(2).toString());
                    evento.set_DESTINO(ic.getProperty(6).toString());
                    listaEventos.add(evento);
                }

                return listaEventos;

            } catch (IOException e) {//no encuentra respuesta (No existe o no Existe la direccion a webservice).
                //e.printStackTrace();
                listaEventos = null;
                cancel(true);
            } catch (XmlPullParserException e) {
                //e.printStackTrace();
                listaEventos = null;
                cancel(true);
            }
            return listaEventos;
        }

        @Override
        protected void onCancelled(List<EventoPuertaMagnetica> result) {
            if(miBarra != null){
                miBarra.setVisibility(View.INVISIBLE);
            }
            Log.i(TAG, "Se ha entrado a funcion onCancelled de Eventos_Puerta_Magnetica");
            Notificacion("No ha sido posible obtener la informacion sobre los eventos asignados a la manipulacion de puerta magnetica en este dispositivo");

        }

        @Override
        protected void onPostExecute(List<EventoPuertaMagnetica> respuesta){
            Log.i(TAG, "Ingreso a funcion onPOst de Eventos_Puerta_Magnetica");

            /*for(int i=0; i<respuesta.size(); i++){//ciclo que muestra en log la ista de eventos obtenida desde ws .
                Log.i(TAG, respuesta.get(i).get_NOMBRE()+", "+respuesta.get(i).get_DESTINO()
                + ", "+ respuesta.get(i).get_U01A005());

            }*/

            //asigno a parametros del sistema
            global.getParametrosSistema().set_Eventos(respuesta);

            global.decrementThread();
            if(global.getNumThreads() == 0){
                Log.i(TAG, "Ultimo hilo: Eventos_Puerta_Magnetica");
                if(miBarra != null){
                    miBarra.setVisibility(View.INVISIBLE);
                }
                if(origen instanceof FirstTimeActivity){//se que sera para configuracion.
                    Log.i(TAG, "retorno a FirstTimeActivity");

                    //envio al siguiente bloque logico.
                    //((FirstTimeActivity)origen).continuacion();
                    ((FirstTimeActivity)origen).generateXML();
                }else if(origen instanceof MainActivity){
                    Log.i(TAG, "retorno a MainActivity");
                }
            }
        }
    }

    private void Notificacion(String message){
        try{
            alertDialog.setTitle(message);
            alertDialog.show();
            if(miBarra != null){
                miBarra.setVisibility(View.INVISIBLE);
            }
        }catch (Exception ex){
            Log.e(TAG, "Error al enviar mensaje (FirstTimeActivity):: "+ex.getMessage());
        }

    }




}
