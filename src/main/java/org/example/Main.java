package org.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(laczenieZSerwerem("http://localhost:8080/usuwanie?typ=k&id=1"));
        //System.out.println(laczenieZSerweremPost("http://localhost:8080/wypisywanie", "{'aaa':'bbb'}"));

    }

    public static String laczenieZSerwerem(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "test");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    public static String laczenieZSerweremPost(String url, String dane_do_wyslania) throws IOException {
        byte[] postData = dane_do_wyslania.getBytes(StandardCharsets.UTF_8);
        HttpURLConnection con = null;
        try{
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "test");
            con.setRequestProperty("Content-Type","application/json");
            con.setDoOutput(true);
            try (DataOutputStream wr= new DataOutputStream(con.getOutputStream())){
                wr.write(postData);
            }
            StringBuilder content;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"))){
                String line;
                content = new StringBuilder();

                while ((line= br.readLine()) != null){
                    System.out.println(br);
                    content.append(line);
                    System.out.println(line);
                    content.append(System.lineSeparator());
                }
            }
            return content.toString();
        }finally{
            con.disconnect();
        }

    }

}