package org.ips;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Hostip host = new Hostip();
        String dataAsString = Arrays.toString(host.dataConect(1));
        System.out.println(dataAsString);
    }

}