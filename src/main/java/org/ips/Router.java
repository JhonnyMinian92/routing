package org.ips;

import com.jcraft.jsch.*;

import java.io.InputStream;
import java.io.PrintStream;

public class Router {
    protected static boolean validarConexion(String[] data){
        boolean respuesta = false;
        try {
            Session session = conectarRouter(data);
            // verificar que la conexión sea exitosa
            respuesta = session.isConnected();
            //desconectar sesion
            session.disconnect();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    protected static boolean cambioClaveWifi(String[] data, String nuevaclave){
        boolean respuesta = false;
        try {
            Session session = conectarRouter(data);
            if(session.isConnected()){
                //cambiar a la frecuencia secundaria
                if(respuesta = ejecutarCambioClave(session, "set ssid index 5 password "+nuevaclave)){
                    //cambiar a la frecuencia primaria
                    if(respuesta = ejecutarCambioClave(session, "set ssid index 1 password "+nuevaclave)){
                        respuesta = true;
                    }
                }
            }
            session.disconnect();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    private static Session conectarRouter(String[] data){
        Session session = null;
        try {
            JSch jsch = new JSch();
            //iniciar la sesion
            session = jsch.getSession(data[1], data[0], 22);
            session.setPassword(data[2]);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(3000);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return session;
    }

    private static boolean ejecutarCambioClave(Session session, String comando) {
        boolean respuesta = false;
        try {

            Channel channel = session.openChannel("shell");
            channel.connect(3000);
            Thread.sleep(500);

            PrintStream out = new PrintStream(channel.getOutputStream());
            out.println(comando);
            out.flush();
            Thread.sleep(500);

            out.close();
            channel.disconnect();
            respuesta = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

    private static String obtenerRespuestaComandos(Session session, String comando) {
        String respuesta = "";
        try {


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return respuesta;
    }

}
