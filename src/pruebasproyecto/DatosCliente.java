package pruebasproyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class DatosCliente {

    String IP_A;
    List<Float> almacenamiento_A = new ArrayList<Float>();
    String procesador_A;
    Float RAM_A;
    String Internet_A;

    public DatosCliente(String IP, List<Float> almacenamiento, String procesador, Float RAM, String Internet) {
        IP_A = IP;
        almacenamiento_A = almacenamiento;
        procesador_A = procesador;
        RAM_A = RAM;
        Internet_A = Internet;

    }

    //GETTERS
    public String getIP() {
        return IP_A;
    }

    public Object getAlmacenamiento() {
        return almacenamiento_A;
    }

    public String getprocesador() {
        return procesador_A;
    }

    public Float getRAM() {
        return RAM_A;
    }

    public String getInternet() {
        return Internet_A;
    }
    /////////////////////////////////////

    public String toString() {
        return "DatosEquipo{" +
            "IP='" + IP_A + '\'' +
            ", almacenamiento=" + almacenamiento_A +
            ", procesador='" + procesador_A + '\'' +
            ", RAM=" + RAM_A +
            ", Internet='" + Internet_A + '\'' +
            '}';
    }
    
    public List<Object> toTableRow() {
        return Arrays.asList(IP_A , procesador_A, RAM_A, Internet_A, almacenamiento_A, "Conectado");
    }

}
