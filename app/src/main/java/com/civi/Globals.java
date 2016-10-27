package com.civi;

import java.util.ArrayList;

import Opendat.Registro_del_Tiempo.Parametros;

/**
 * Clase con patron singleton utilizada para almacenar tanto variables globales nesesarias para el funcionamiento del sistema
 * como tambien las funciones de sincronizacion usadas por los threads de los llamados a ws.
 *
 * Modificaciones:
 * Fecha                    Autor                   Descripcion
 * -------------------------------------------------------------------------------------------------
 * 13.10.2016               Jonathan Vasquez        Creacion de Clase
 * 20.10.2016               Jonathan Vasquez        Implementacion de funcion de sincronizacion 'decrementThread'
 * 22.10.2016               Jonathan Vasquez        Implementacion de funcion de sincronizacion 'incrementThread'
 */
public class Globals {
    private static Globals instance;

    private String Dato; //hora servidor.
    private int numThreads; //variable que controla la cantidad de hilos en paralelo en un periodo de tiempo.
    private  Parametros  parametrosSistema = new Parametros(); //variable global de los parametros del sistema.
    private String pathData; //variable global para almacenar la direccion de los datos del sistema en el dispositivo.


    private Globals(){
    }

    public static synchronized Globals getInstance(){
        if(instance == null){
            instance = new Globals();
        }
        return instance;
    }



    public String getDato() {
        return Dato;
    }

    public void setDato(String _dato) {
        this.Dato = _dato;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public void setNumThreads(int numThreads) {
        this.numThreads = numThreads;
    }

    public Parametros getParametrosSistema() {
        return parametrosSistema;
    }

    public void setParametrosSistema(Parametros parametrosSistema) {
        this.parametrosSistema = parametrosSistema;
    }

    public String getPathData() {
        return pathData;
    }

    public void setPathData(String pathData) {
        this.pathData = pathData;
    }


    /**
     * Funcion utilizada por los hilos en ejecucion.
     * El objetivo es controlar la ejecucion de multiples hilos, para asi conocer el ultimo hilo en realizar su tarea
     * y el cual ejecutara la logica que sigue despues de estas tareas.
     */
    public synchronized void decrementThread(){
        this.numThreads = this.numThreads -1;
        if(this.numThreads < 0)
            this.numThreads = 0;
    }

    /**
     * Funcion utilizada por los hilos en ejecucion.
     * El objetivo es controlar la ejecucion de multiples hilos, para asi conocer el ultimo hilo en realizar su tarea
     * y el cual ejecutara la logica que sigue despues de estas tareas.
     */
    public synchronized  void incrementThread(){
        this.numThreads = this.numThreads +1;

    }

}
