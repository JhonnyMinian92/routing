package org.ips;

public class Main {

    public static void main(String[] args) {
        Autenticar auth = new Autenticar();
        int iduser = auth.Login("0706323193","12345");
        if(iduser > 0){
            Hostip host = new Hostip();
            String[] data = host.dataConect(iduser);
            if(data[0].equals("")){
                System.out.println("Error al obtener datos de router");
            } else {
                Router r = new Router();
                if(r.validarConexion(data)){
                    if(r.cambioClaveWifi(data,"pruebaultimajava")){
                        System.out.println("La clave de wifi fue cambiada");
                    } else {
                        System.out.println("Error al cambiar clave del wifi");
                    }
                } else {
                    System.out.println("Error en la conexion");
                }
            }
        }
    }

}