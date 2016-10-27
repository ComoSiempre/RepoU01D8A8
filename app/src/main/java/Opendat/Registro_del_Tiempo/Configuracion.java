package Opendat.Registro_del_Tiempo;

import android.graphics.Path;
import android.util.Log;

import com.civi.Globals;
import com.civi.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import Opendat.Registro_del_Tiempo.Clases_Genericas.EventoPuertaMagnetica;

/**
 * Created by jvasquez on 06-10-2016.
 */

/**
 * Clase utilizada para las funciones de configuracion del archivo config.
 *
 * Modificaciones:
 * Fecha                    Autor                   Descripcion
 * -------------------------------------------------------------------------------------------------
 * 06.10.2016               Jonathan Vasquez        Creacion de Clase
 * 07.10.2016               Jonathan Vasquez        Implementacion de funcion 'ReescrituraConfig'
 * 27.10.2016               Jonathan Vasquez        Implementacion de funcion 'ConfiguracionParametros', 'ExistConfigXML'
 *
 */

public class Configuracion {
    private static String TAG = "AppMRAT";


    /**
     * Funcion que lee el archivo de configuracion XML.
     * @param ruta direccion de ubicacion del archivo config.xml
     * @return los parametros del sistema
     */
    public static Parametros ConfiguracionParametros(String ruta){
        try{
            Parametros theParametros = new Parametros();
            List<EventoPuertaMagnetica> eventoPM = new ArrayList<>();
            File theXmlFile = new File(ruta + "config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(theXmlFile);
            doc.getDocumentElement().normalize();

            String url_ws= doc.getDocumentElement().getElementsByTagName("Url_WebServices").item(0).getTextContent();
            String id_port = doc.getDocumentElement().getElementsByTagName("Id_Portico").item(0).getTextContent();
            String ip_bd = doc.getDocumentElement().getElementsByTagName("Base_Datos_IP").item(0).getTextContent();
            String mani_puerta_chapa = doc.getDocumentElement().getElementsByTagName("Tiene_Chapa").item(0).getTextContent();
            String dispo = doc.getDocumentElement().getElementsByTagName("Disposicion").item(0).getTextContent();
            String crede_admin = doc.getDocumentElement().getElementsByTagName("Id_Crede_Administrador").item(0).getTextContent();
            String loc_geo = doc.getDocumentElement().getElementsByTagName("Localizacion_Geografica").item(0).getTextContent();

            NodeList events = doc.getDocumentElement().getElementsByTagName("Evento"); //0 o mas ...
            for(int i=0; i< events.getLength(); i++){
                EventoPuertaMagnetica event = new EventoPuertaMagnetica();

                Node nodo = events.item(i);
                NodeList caracteristicasEvento = nodo.getChildNodes();

                String id_evento = caracteristicasEvento.item(0).getTextContent();
                String nombre_evento = caracteristicasEvento.item(1).getTextContent();

                event.set_U01B3F3(id_evento);
                event.set_DESTINO(nombre_evento);

                eventoPM.add(event);
            }
            theParametros.set_Eventos(eventoPM);

            return theParametros;

        }catch (Exception ex){
            return null;
        }
    }

    /**
     * Funcion que Reescribe el archivo XML de configuracion.
     *
     * @param ruta ruta del archivo.
     * @param ws direccion Url de webservice
     * @param ip_bd direccion ip de la base de datos
     * @param id_portico identificacion del portico
     * @param chapa disponibilidad de chapa magnetica
     * @param dispo disposicion de portico
     * @param idAdmin identificacion de administrador (Tarjeta RFID)
     * @param eventos listado de eventos de disposicion del portico (IJ, IC, TC, TJ)
     * @param loc_geo localizacion geografica del portico
     * @return
     */
    public static boolean ReescrituraConfig(String ruta, String ws, String ip_bd, String id_portico, String chapa, String dispo, String idAdmin, List<EventoPuertaMagnetica> eventos, String loc_geo){
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            //creacion de elementos
            Element rootElement = doc.createElement("parametros");
            Element urlWS = doc.createElement("Url_WebServices");
            Element idPort = doc.createElement("Id_Portico");
            Element ipBD = doc.createElement("Base_Datos_IP");
            Element cha = doc.createElement("Tiene_Chapa");
            Element disp = doc.createElement("Disposicion");
            Element idAdm = doc.createElement("Id_Crede_Administrador");
            Element lg = doc.createElement("Localizacion_Geografica");

            doc.appendChild(rootElement); //ingreso de cabecera.

            //ingreso de datos a elementos
            urlWS.appendChild(doc.createTextNode(ws));
            idPort.appendChild(doc.createTextNode(id_portico));
            ipBD.appendChild(doc.createTextNode(ip_bd));
            cha.appendChild(doc.createTextNode(chapa));
            disp.appendChild(doc.createTextNode(dispo));
            idAdm.appendChild(doc.createTextNode(idAdmin));
            lg.appendChild(doc.createTextNode(loc_geo));

            //ingresos de elementos a cabecera
            rootElement.appendChild(urlWS);
            rootElement.appendChild(idPort);
            rootElement.appendChild(ipBD);
            rootElement.appendChild(cha);
            rootElement.appendChild(disp);
            rootElement.appendChild(idAdm);
            rootElement.appendChild(lg);

            if(chapa.equals("Z0B9C4B")){
                for(int i=0; i<eventos.size(); i++){
                    Element event = doc.createElement("Evento");
                    Element idEvent = doc.createElement("Id_Evento");
                    Element nombreEvent = doc.createElement("Nombre_Evento");
                    idEvent.appendChild(doc.createTextNode(eventos.get(i).get_U01B3F3()));
                    nombreEvent.appendChild(doc.createTextNode(eventos.get(i).get_DESTINO()));
                    event.appendChild(idEvent);
                    event.appendChild(nombreEvent);
                    rootElement.appendChild(event);
                }

            }

            //transformacion a archivo XML.

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer trans = transFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File dir = new File(ruta);

            Log.i(TAG, "Se verifica la existencia del archivo apra su creacion");
            if(dir.exists()){
                //elimino el archivo xml junto con el folder.

                File [] archivo = dir.listFiles();//data/data/com.civi/config/config.xml
                if(archivo.length != 0){//existen archivos.
                    Log.i(TAG, "Archivo config existe, eliminando...");
                    if(archivo[0].delete()){
                        Log.i(TAG, "Archivo Config eliminado");
                        if(dir.delete()){//elimino el folder.
                            Log.i(TAG, "Folder config eliminado");
                        }else{
                            Log.i(TAG, "No fue posible la eliminacion del folder");
                        }
                    }else{
                        Log.i(TAG, "NO fue posible la eliminacion del archivo");
                    }
                }else{
                    Log.i(TAG, "Archivo Config no existe. elimino solo el folder");
                    if(dir.delete()){
                        Log.i(TAG, "Folder eliminado");
                    }
                }

            }

            //Creacion de folder.
            if(dir.mkdirs()){
                StreamResult result = new StreamResult(new File(dir, "config.xml"));
                trans.transform(source, result);
            }
            Log.i(TAG, "Generacion de config.xml realizada en "+ruta);

        } catch(Exception ex){
            return false;
        }
        return true;
    }


    public static boolean ExistsConfigXML(String ruta){
        try{
            File file = new File(ruta);

            if(!file.exists()){
                return false;
            }

        }catch(Exception ex) {
            Log.e(TAG, "Error en Configuracion --> ExistConfigXML:: "+ex.getStackTrace());
        }
        return true;
    }
}
