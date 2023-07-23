package org.ips;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Hostip {

    protected static String[] dataConect(int iduser) {
        String[] data = {"", "", ""};
        try {
            if(iduser != 0){
                String gateway = obtenerGateway();
                if(!gateway.equals("Error al buscar la ip")){
                    data[0] = gateway;
                }
                String responseBody = ServicioProveedor(iduser);
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(responseBody);
                boolean estado = (boolean) json.get("estado");
                if (estado){
                    JSONObject dat = (JSONObject) json.get("data");
                    data[1] = (String) dat.get("usuario");
                    data[2] = (String) dat.get("clave");
                }
            }
        } catch (Exception e){ System.out.println(e.getMessage()); }
        return data;
    }

    protected  static String ServicioProveedor(int iduser){
        String responseBody = "";
        try {
            //obtener el usuario y contraseña del router
            String apirouter = "http://localhost/ips/routerdata/";
            String idjson = "{\"iduser\":"+iduser+"}";
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post(apirouter)
                    .header("Content-Type", "text/plain")
                    .body(idjson)
                    .asString();
            responseBody = response.getBody();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return responseBody;
    }

    protected static String obtenerGateway(){
        String iphost = obtenerIp();
        String gateway = "Error al buscar la ip";
        if(!iphost.equals("")) {
            gateway = iphost.substring(0, iphost.length() - 1) + "1";
        }
        return gateway;
    }

    protected static String obtenerIp(){
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();

                if (iface.isUp()) {
                    Enumeration<InetAddress> addresses = iface.getInetAddresses();

                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();

                        if (!addr.isLoopbackAddress() && !addr.isLinkLocalAddress() && !addr.isMulticastAddress()) {
                            return getHostLocal(iface);
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getHostLocal(NetworkInterface iface) {
        Enumeration<InetAddress> addresses = iface.getInetAddresses();
        while (addresses.hasMoreElements()) {
            InetAddress addr = addresses.nextElement();
            if (!addr.isLoopbackAddress() && !addr.isLinkLocalAddress() && !addr.isMulticastAddress()) {
                return addr.getHostAddress();
            }
        }
        return null;
    }

}