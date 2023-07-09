package org.ips;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Autenticar auth = new Autenticar();
        int iduser = auth.Login("0706323193","12345");
        if(iduser > 0){
            Hostip host = new Hostip();
            String dataAsString = Arrays.toString(host.dataConect(1));
            System.out.println(dataAsString);
        }
    }

}