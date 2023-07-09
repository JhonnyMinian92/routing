package org.ips;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Autenticar {

    protected  static int Login(String usuario, String clave){
        String responseBody = "";
        int id = 0;
        try {
            String apirouter = "http://localhost/ips/autentication/";
            String datajson = "{\"usuario\":\""+usuario+"\",\"clave\":\""+clave+"\"}";
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post(apirouter)
                    .header("Content-Type", "text/plain")
                    .body(datajson)
                    .asString();
            responseBody = response.getBody();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(responseBody);
            boolean estado = (boolean) json.get("estado");
            if(estado){
                JSONObject dat = (JSONObject) json.get("data");
                id = Integer.parseInt((String) dat.get("iduser"));
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return id;
    }

}
