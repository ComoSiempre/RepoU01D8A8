package com.civi.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.civi.Globals;
import com.civi.R;
import com.civi.U02916C;
import com.civi.jni.PeripheralsJNI;
import com.civi.jni.TypeConvertion;
import com.civintec.CVAPIV01_DESFire;

import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

import Opendat.Registro_del_Tiempo.Configuracion;
import Opendat.Registro_del_Tiempo.Parametros;

public class MainActivity extends Activity {

    private static final String TAG = "AppMRAT";
    private static final int CODIGO_ACTIVIDAD = 1; //VARIABLE NESESARIA PARA NAVEGAR A FIRSTTIMEACTIVITY.


    CVAPIV01_DESFire cv = new CVAPIV01_DESFire();

    U02916C theU02916C = new U02916C("http://www.opendat.cl/umbral/ayt/U021E30.asmx");
    Globals global = Globals.getInstance();
    boolean connection; //variable usada en el hilo de conexion para guardar el estado.

    Timer timer; //usado para la deteccion constante de tarjeta RFID.
    MyTask myTask = null; //tarea nesesaria para el autorun de la lectura RFID.(trabaja junto con el timer).

    TextView tprueba;
    ImageView faro;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        global.setPathData(getApplicationInfo().dataDir);

        if(!VerificarConfigucarion(global.getPathData()+"/config/config.xml")){
            //se navega a FirstTimeActivity
            startActivityForResult(new Intent(MainActivity.this, FirstTimeActivity.class), CODIGO_ACTIVIDAD);
        }else{
            load();
        }




    }

    /**
     * Funcion que inicializa los perifericos e inicializa los datos nesesarios para el funcionamiento del sistema.
     */
    private void load(){
        tprueba = (TextView) findViewById(R.id.TV_pruebas);
        faro = (ImageView) findViewById(R.id.IV_faroConection);

        try {
            //se lee el archivo de configuracion y se guardan los parametros.
            global.setParametrosSistema(Configuracion.ConfiguracionParametros(global.getPathData()+"/config/"));
            new isConected().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, global.getParametrosSistema().get_Base_Datos_IP());

        }catch (Exception ex){

        }

        inicioRFID();

        //global.setNumThreads(1);
        //theU021E30.ObtenerFechaHora();

        activacionAutoRun();

        //Opendat.Registro_del_Tiempo.Configuracion.ConfiguracionParametros(PathData+"/xml");
        /*boolean flag = Opendat.Registro_del_Tiempo.Configuracion.ActualizacionConfig(PathData+ File.separator+"xml"
                , "http://10.0.167.27/MRAT_UQA/U021E30.asmx"
                , "10.0.167.55"
                , "G040D99A5"
                , "Z0B9C4C"
                , "Z0B994E"
                , "FEF94097"
                , null
                , "-23.6553434317698 -70.39867470014138");

        if (flag){
            tp.setText("Exito");
        }else{
            tp.setText("desastre");
        }*/
        //tp.setText(hora);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if(resultCode == RESULT_OK){
            Parametros p = data.getExtras().getParcelable("RESULTADO");
            global.setParametrosSistema(p);
            load();
        }
    }

    @Override
    protected void onPause(){
        CVAPIV01_DESFire.AutoRead_Stop();
        if (myTask != null) {
            myTask.cancel();
        }
        super.onPause();
    }

    /**
     * Funcion usada para confirmar la existencia del archivo Config.xml, ademas de verificar la existencia de parametros en el caso de que el sistema provenga
     * desde la interfaz de configuracion de dispositivo.
     * @return boolean: true Configuracion existente; FALSE configuracion no existente
     */
    private boolean VerificarConfigucarion(String ruta){
        boolean conf = false;
        try{
            boolean b = Configuracion.ExistsConfigXML(ruta);
            return b;
        }catch(Exception ex){
            Log.e(TAG, "Error de verificacion de Configuracion:: "+ex.getMessage());
        }
        return conf;
    }


    /**
     * Funcion que activa el periferico de RFID y Finger.
     */
    private void inicioRFID(){
        //activacion de modulo RFID y Finger
        byte [] data = new byte[1];
        int fd = PeripheralsJNI.openRFDev();
        int retval = PeripheralsJNI.rfReadState(fd, data, 1);
        String ret = TypeConvertion.Bytes2HexString(data, 1);
        if (ret.equals("01 ")) {
            //System.out.println("modulos RFID activado");
            Log.i(TAG,"modulos RFID activado");
        }else{
            //System.out.println("modulos RFID no Activados, activando...");
            Log.i(TAG, "modulos RFID no Activados, activando...");
            PeripheralsJNI.rfControl(fd, 1, 1); //se cambia el estado del modulo (Activacion).
            Log.i(TAG,"modulos RFID activado");
        }

        PeripheralsJNI.closeRFDev(fd);
    }

    /**
     * Funcion que activa el sistema de autoDeteccion de tarjeta RFID.
     */
    private void activacionAutoRun() {
        try{
            activacionComunicacion();

            cv.SetOnAutoRead();
            timer = new Timer();//se inicializa el timer para la deteccion a traves del tiempo.
            CVAPIV01_DESFire.AutoRead_Run();
            myTask = new MyTask(); //se inicializa la tarea que verifica la deteccion de tarjeta.
            timer.schedule(myTask, 1, 100); // se realiza la deteccion cada 100 ms.
            Log.i(TAG, "Inicio de Lectura..");
        }catch(Exception ex){
            Log.e(TAG,"Error en activar AutoRun:: "+ ex.getMessage());
        }


    }

    /**
     * Funcion usada para activar la comunicacion de datos desde RFID.
     */
    private void activacionComunicacion(){
        int nRet = 1;
        String strComName = "com1";

        //se abre puerto de comunicacion.
        nRet = CVAPIV01_DESFire.OpenComm(strComName.getBytes(), 115200, 0);
        if(nRet == 0){
            //System.out.println("Open Port " + strComName + " OK!");
            Log.i(TAG, "Open Port " + strComName + " OK!");
        } else {
            //System.out.println("Open Port " + strComName + " FAIL!");
            Log.i(TAG, "Open Port " + strComName + " FAIL!");
        }

        //se selecciona tipo de tarjeta
        byte blockNum = 0;
        nRet = 0;
        byte CardType = 0; //0--Mifare card  1--Mifare Plus   2--Desfire EV1
        int nDeviceAddr = 0;

        nRet = AutoRead_SelectCard(nDeviceAddr, CardType, blockNum);
        if(nRet == 0){
            //System.out.println("AutoRead_SelectCard successful!");
            Log.i(TAG, "AutoRead_SelectCard successful!");
        } else {
            //System.out.println("AutoRead_SelectCard failed!");
            Log.i(TAG, "AutoRead_SelectCard failed!");
        }

    }

    private class MyTask extends TimerTask{
        @Override
        public void run() {
            //Message message = new Message();
            //message.what = 1;
            //mHandler.sendMessage(message);
            if(!cv.UID.equals("")){
                //tprueba.setText(cv.UID.toString());
                Log.i(TAG,"deteccion de Tarjeta: " + cv.UID.toString());
                CVAPIV01_DESFire.AutoRead_Stop();
                this.cancel();

            }

        }
    }

    private class isConected extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String ip = params[0];
            boolean con = false;
            try{
                con = InetAddress.getByName(ip).isReachable(5000);
            }catch (Exception ex){
                Log.e(TAG, "Error en coneccion (back):: "+ex.getMessage());
            }
            return con;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            try{
                if(result){
                    faro.setImageResource(R.drawable.led_green_th);
                    Log.i(TAG, "Cambio a faro Conectado");

                }else{
                    faro.setImageResource(R.drawable.led_red_th);
                    Log.i(TAG, "Cambio a faro Desconectado");
                }
                connection = result;

            }catch(Exception ex){
                Log.e(TAG, "Error en coneccion (post):: "+ ex.getMessage());
            }
        }
    }

    /**
     * Funcion originaria de demo SerialTest
     * @param DeviceAddr
     * @param CardType
     * @param blockNum
     * @return resultado del llamado de API WiegandMode. (Mas informacion ir a CNReader API Reference V3.61.pdf)
     */
    private int AutoRead_SelectCard(int DeviceAddr, byte CardType, byte blockNum) {
        byte data[] = new byte[20];
        int nRet = -1;

        data[0] = 0x00;
        data[1] = blockNum;
        data[2] = 0x26;
        data[3] = 0x12;
        data[4] = 0x01;
        data[5] = 0x0e;		//DATA[5]: output select
        data[6] = 0x00;
        data[7] = CardType;
        data[8] = 0x00;
        data[9] = 0x00;
        data[10] = 0x00;

        nRet = CVAPIV01_DESFire.WiegandMode(DeviceAddr, data);

        return nRet;
    }
}
