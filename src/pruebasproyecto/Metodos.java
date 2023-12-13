package pruebasproyecto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Metodos {
    static String anchoDeBanda = "";
    
    public static String ComandoCMD(String comando) throws InterruptedException {
        String respuesta = "";
        boolean primeraLineaLeida = false; // Variable para controlar si la primera línea ya se ha leído
        try {
            Process process = Runtime.getRuntime().exec(comando); 
            BufferedReader lector = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!primeraLineaLeida) {
                    primeraLineaLeida = true; 
                } else {
                    if (linea.length() != 0) {
                        respuesta = respuesta.concat(linea + ", ");
                        respuesta = respuesta.replace("    ", "");
                        respuesta = respuesta.replace("   ", "");
                        respuesta = respuesta.replace("                  ", "");
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }
        public static String Internet() {
        String urlStr = "https://www.youtube.com/";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            long startTime = System.currentTimeMillis();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream in = connection.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                long totalBytes = 0;
                while ((bytesRead = in.read(buffer)) != -1) {
                    totalBytes += bytesRead;
                }
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                double anchoDeBandaMbps = ((totalBytes / 8) / elapsedTime * 1000)/10000;
                anchoDeBanda = String.format("%.2f", anchoDeBandaMbps) + " Mbps"; 
            } else {
                System.err.println("La solicitud no fue exitosa. Código de respuesta: " + responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return anchoDeBanda;
    }
 }
