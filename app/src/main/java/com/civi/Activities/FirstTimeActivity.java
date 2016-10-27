package com.civi.Activities;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.net.NetworkInfo;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.civi.Globals;
import com.civi.R;
import com.civi.U02916C;

import java.net.InetAddress;

import Opendat.Registro_del_Tiempo.Configuracion;
import Opendat.Registro_del_Tiempo.Parametros;

public class FirstTimeActivity extends AppCompatActivity {

    private static final String TAG = "AppMRAT";

    private AlertDialog.Builder alertDialog = null;
    ProgressBar miBarra = null;

    //U021E30 theU021E30 = new U021E30("http://www.opendat.cl/umbral/ayt/U021E30.asmx");
    U02916C theU02916C = new U02916C();
    Globals global = Globals.getInstance();
    boolean conet;

    EditText idPortico, ipBD, ipWS;
    Button btnIni, btnCancel;
    ImageView faroConnection;

    /**
     * Listener del boton de Inicio.
     * Obtiene los datos para la construccion del archivo config.xml, el ucal contiene los parametros del sistema.
     */
    private OnClickListener IniciarConfiguracion = new OnClickListener() {
        @Override
        public void onClick(View v) {
            try{

                if(idPortico.getText().toString().equals("") || ipBD.getText().toString().equals("") || ipWS.getText().toString().equals("")){
                    Notificacion("Favor no dejar campos en blanco");
                } else {
                    theU02916C.setURL(ipWS.getText().toString());
                    global.setNumThreads(3);
                    miBarra = (ProgressBar) findViewById(R.id.PB_Loading);
                    miBarra.setVisibility(View.VISIBLE);
                    theU02916C.setMiBarra(miBarra);

                    //new isConnectedTask().execute("10.0.167.55","ipBD");
                    new isConnectedTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ipBD.getText().toString(),"ipBD");
                    theU02916C.verificarWS(FirstTimeActivity.this, idPortico.getText().toString());
                    //theU021E30.Buscar_Portico(FirstTimeActivity.this, idPortico.getText().toString());

                }

            }catch (Exception ex) {
                Log.e(TAG, "Error al iniciar configuracion (FirstTimeActivity):: " + ex.getMessage());
                Notificacion("Error al iniciar configuracion (FirstTimeActivity):: " + ex.getMessage());
            }
        }
    };

    private OnClickListener CerrarAplicacion = new OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

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

    private class isConnectedTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            //String ip = "10.0.167.55";
            boolean con = false;
            String [] lista = new String[2];
            try{
                con = InetAddress.getByName(params[0]).isReachable(5000);
                Log.i(TAG, "Resultado ping:"+con);
                if(con == false) cancel(true);
            }catch (Exception ex){
                Log.e(TAG, "Error en realizar ping (background):: "+ex.getMessage());
                cancel(true);
            }
            lista[0] = ""+ con;
            lista[1]= params[1];
            return lista;
        }

        @Override
        protected void onCancelled(String [] result){
            if(miBarra != null){
                miBarra.setVisibility(View.INVISIBLE);
            }
            Log.i(TAG, "Ingreso a funcion onCancelled de ip. ");
            Notificacion("No se ha podido realizar la comunicacion con la direccion IP de la base de datos ingresada");
        }

        @Override
        protected void onPostExecute(String [] result) {
            try{
                //Iniciando Activity
                if(result[0].equals("true") && result[1].equals("")){
                    faroConnection.setImageResource(R.drawable.online_icon);
                    Log.i(TAG, "Cambio a faro Conectado");

                }else if(result[0].equals("false") && result[1].equals("")){
                    faroConnection.setImageResource(R.drawable.offline_icon);
                    Log.i(TAG, "Cambio a faro Desconectado");

                }else if(result[0].equals("false") && result[1].equals("ipBD")){
                    Notificacion("No ha podido establecerse coneccion a la base de datos.");
                    faroConnection.setImageResource(R.drawable.offline_icon);
                    conet=false;
                }else if(result[0].equals("true") && result[1].equals("ipBD")) {//verificando coneccion.
                    conet=true;
                }
                if(result[1].equals("ipBD")){//solo en el caso de verificacion para generar config se decrementa el contador de hilo.
                    global.decrementThread();

                    if(global.getNumThreads() == 0) {
                        Log.i(TAG, "ultimo thread fue el de ping");
                        //miBarra.setVisibility(View.INVISIBLE);

                        configuracionSistema();
                    }
                }
            }catch(Exception ex){
                Log.e(TAG, "Error en coneccion (post):: "+ ex.getMessage());
            }
        }
    }

    public void configuracionSistema(){
        Log.w(TAG, "Comienzo de logica de generacion...");
        global.setNumThreads(4);

        try{
            //obtengo disponibilidad de chapa electrica.
            theU02916C.Disponibilidad_Puerta_Magnetica(FirstTimeActivity.this, idPortico.getText().toString());

            //obtengo la disposicion del portico.
            theU02916C.Verificar_Portico(FirstTimeActivity.this, idPortico.getText().toString());

            //obtengo la id de credencial del administrador del sistema.
            theU02916C.Credencial_Admin(FirstTimeActivity.this);

            //obtengo la localizacion geografica del portico.
            theU02916C.Localizacion_Geografica(FirstTimeActivity.this, idPortico.getText().toString());

            //obtengo los eventos asignados a la manipulacion de puerta magnetica.
            //theU02916C.Eventos_Con_Puerta_Magnetica(FirstTimeActivity.this, idPortico.getText().toString());
        }catch (Exception ex) {
            Notificacion("Error en proceso de configuracion de sistema primario");
        }
    }

    public void generateXML() {

        try{

            //se asigna los parametros principales a los parametros del sistema.
            global.getParametrosSistema().set_Url_WebServices(ipWS.getText().toString());
            global.getParametrosSistema().set_Base_Datos_IP(ipBD.getText().toString());
            global.getParametrosSistema().set_Id_Portico(idPortico.getText().toString());

            LogParametros();

            //se genera el archivo xml de configuracion.
            boolean res = Configuracion.ReescrituraConfig(global.getPathData()+"/config"
                    , global.getParametrosSistema().get_Url_WebServices()
                    , global.getParametrosSistema().get_Base_Datos_IP()
                    , global.getParametrosSistema().get_Id_Portico()
                    , global.getParametrosSistema().get_Manipulacion_Puerta()
                    , global.getParametrosSistema().get_Disposicion()
                    , global.getParametrosSistema().get_Id_CredencialAdmin()
                    , global.getParametrosSistema().get_Eventos()
                    , global.getParametrosSistema().get_Localizacion_Geografica());

            if(!res) Notificacion("Se produjo un problema en la creacion del archivo config.xml, no la sido posible su creacion");

            GoToMainActivity(global.getParametrosSistema());


        }catch (Exception ex) {
            Notificacion("Error en funcion 'GenerateXML':: "+ex.getMessage());
        }

    }

    private void LogParametros(){
        Log.i(TAG,"parametros finales:" );
        Log.i(TAG,"webservice: "+global.getParametrosSistema().get_Url_WebServices());
        Log.i(TAG,"bd: "+global.getParametrosSistema().get_Base_Datos_IP());
        Log.i(TAG,"id portico: "+global.getParametrosSistema().get_Id_Portico());
        Log.i(TAG,"Manipulacion puerta: "+global.getParametrosSistema().get_Manipulacion_Puerta());
        Log.i(TAG,"id relacion: "+global.getParametrosSistema().get_Id_Relacion());
        Log.i(TAG,"Localizacion geo: "+global.getParametrosSistema().get_Localizacion_Geografica());
        Log.i(TAG,"id credencial admin: "+global.getParametrosSistema().get_Id_CredencialAdmin());
        Log.i(TAG,"disposicion: "+global.getParametrosSistema().get_Disposicion());
        Log.i(TAG,"Eventos:: ");
        if(global.getParametrosSistema().get_Manipulacion_Puerta().equals("Z0B9C4B")){
            if(global.getParametrosSistema().get_Eventos().size()!=0){
                for(int i=0; i<global.getParametrosSistema().get_Eventos().size(); i++){
                    Log.i(TAG,"id evento: "+global.getParametrosSistema().get_Eventos().get(i).get_U01B3F3());
                    Log.i(TAG,"nombre: "+global.getParametrosSistema().get_Eventos().get(i).get_DESTINO());
                }
            }else{
                Log.i(TAG,"sin eventos asignados..");
            }

        }
    }

    /**
     * Funcion que verifica el estado de coneccion a internet del dispositivo.
     * @param ctx el contexto den el cual es llamada la funcion (Activity actual).
     */
    private void verificacionConexion(Context ctx){
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        //verifico la coneccion del pool de conecciones del dispositivo {Redes[1] red wifi, Redes[7] red ethernet}
        if(redes[1].getState() == NetworkInfo.State.CONNECTED || redes[7].getState() == NetworkInfo.State.CONNECTED){
            bConectado=true;
        }

        if(bConectado){
            faroConnection.setImageResource(R.drawable.online_icon);
            Log.i(TAG, "Cambio a faro Conectado");
        }else{
            faroConnection.setImageResource(R.drawable.offline_icon);
            Log.i(TAG, "Cambio a faro Desconectado");
        }
    }


    private void GoToMainActivity(Parametros pParams){
        try{
            Intent in = getIntent();
            in.putExtra("RESULTADO",pParams);
            setResult(RESULT_OK, in);

            finish();
        }catch(Exception ex){
            Notificacion("Error al intentar navegar hacia el MainActivity");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        //asocio un parametro a las variables globales.
        Parametros p = new Parametros();
        Globals.getInstance().setParametrosSistema(p);
        alertDialog = new AlertDialog.Builder(FirstTimeActivity.this);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface alertDialog, int which) {
                alertDialog.cancel();
            }
        });
        theU02916C.setAlertDialog(alertDialog);

        idPortico= (EditText)findViewById(R.id.ET_IDPortico);
        ipWS = (EditText)findViewById(R.id.ET_IPWS);
        ipBD =(EditText)findViewById(R.id.ET_IPBD);
        btnIni = (Button)findViewById(R.id.BT_IniciarConf);
        btnCancel = (Button) findViewById(R.id.BT_Cancelar);
        faroConnection = (ImageView) findViewById(R.id.IV_FaroConectionFT);

        //verificacion coneccion.
        //new isConnectedTask().execute("10.0.167.55","");
        verificacionConexion(this);

        //se relaciona los listenes (contenedor de funciones).
        btnIni.setOnClickListener(IniciarConfiguracion);
        btnCancel.setOnClickListener(CerrarAplicacion);

    }


}
