package pruebasproyecto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

public class Laptop extends javax.swing.JFrame {

    private static DefaultTableModel modelo;
    private static javax.swing.JButton BtnConectar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private static Laptop interfaz = new Laptop();

    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    static int contador = 0;
    static int contadorDeClientes = 0;
    public static String IPDest;

    static String estatus = "Conectado";

    static String IP;
    static List<Float> almacenamiento = new ArrayList<Float>();
    static String procesador;
    static Float RAM;
    static String Internet;

    public static List<String> Clientes = new ArrayList<String>();
    public static List<Float> ClienteABSort = new ArrayList<Float>();
    public static int mejorCliente = 0;

    public static DatosCliente[] datoscliente = new DatosCliente[20];

    private static Socket sCte = null;

    static DataOutputStream dos = null;
    static DataInputStream dis = null;

    public Laptop() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BtnConectar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(972, 460));
        setSize(new java.awt.Dimension(972, 460));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("Clientes Conectados");

        String datos[][] = {};
        String columna[] = {"IP", "% libre del procesador ", "Memoria libre (GB)", "Ancho de banda", "Almacenamiento Disponible (GB)", "Estatus"};
        modelo = new DefaultTableModel(datos, columna) {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jTable1.setModel(modelo);
        jTable1.setRowHeight(25);
        jTable1.setEnabled(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setFillsViewportHeight(true);

        jScrollPane1.setViewportView(jTable1);

        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(83);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(83);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(130);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(83);
        }
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        jTable1.getColumnModel().getColumn(0).setCellRenderer(tcr);
        jTable1.getColumnModel().getColumn(1).setCellRenderer(tcr);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(tcr);
        jTable1.getColumnModel().getColumn(3).setCellRenderer(tcr);
        jTable1.getColumnModel().getColumn(4).setCellRenderer(tcr);
        jTable1.getColumnModel().getColumn(5).setCellRenderer(tcr);

        BtnConectar.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        BtnConectar.setForeground(new java.awt.Color(51, 153, 0));
        BtnConectar.setText("Conectar");
        BtnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(88, 88, 88)
                                                .addComponent(BtnConectar, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(BtnConectar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    public static void main(String args[]) throws Exception {
        new Laptop().setVisible(true);
        BtnConectar.setVisible(false);
        ObtenerDatosLocales();
        BtnConectar.setVisible(true);
        CrearServer();
        
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        BtnConectar.setVisible(false);
        ColocarEnPosicion(0);
        Clientes.add("192.168.1.22");
        IPDest = Clientes.get(0);

        ConexionServer(Clientes.get(0));

    }

    public static void ConexionServer(String IPSocket) {
        contadorDeClientes = 1; //???????????????
        System.out.println("Servidor IP: " + IPSocket);

        try {
            sCte = new Socket(IPSocket, 2635);

            System.out.println(estatus + ": Se conecto exitosamente con el servidor " + IPSocket + ". Enviando datos...");

            scheduler.scheduleAtFixedRate(HiloxServer, 0, 10, TimeUnit.SECONDS);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void CrearServer() throws IOException {
        List<Float> ClienteAB = new ArrayList<Float>();
        Socket sSvr = null;
        ServerSocket ssSvr = new ServerSocket(2635);

        System.out.println("Servidor local en eschucha...");

        while (true) {
            try {
                
                sSvr = ssSvr.accept(); //Recibe datos de todos los clientes cada que pasen 10 segundos para cada cliente
                InetAddress ip = sSvr.getInetAddress();
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                /*            REFACTORIZANDO                   REFACTORIZANDO               REFACTORIZANDO            REFACTORIZANDO        REFACTORIZANDO    */
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                dos = new DataOutputStream(sSvr.getOutputStream());
                dis = new DataInputStream(sSvr.getInputStream());
                /*
                Primero hay que verificar...    Es asignacion de servidor = true           Es redireccionamiento de servidor = false
                 */
                boolean AsignacionDeServidor = dis.readBoolean();
                int numHilo = contadorDeClientes;
                
                System.out.println("Se conectaron desde " + ip);
                
                if (AsignacionDeServidor == true) {
                    
                    contadorDeClientes ++;
                    /*
                    Aqui necesito ir asignandoles un valor de numero de cliente, segun se vayan conectando.
                    Si la ip del cliente es igual a la propia, asignar numero de cliente = 0 (filaHilo = 0)

                    Si la ip del cliente es igual a la ip del cliente servidor, 

                     */
                    
                    //Verifica si la ip recibida es la misma que la del anterior servidor
                    //Si es asi cambia la conexion local hacia si mismo, y modifica el ip destino original
                    if (((ip.toString().replace("/", "")).equals(datoscliente[0].getIP())) == false) {
                        if ((ip.toString().replace("/", "")).equals(IPDest)) {
                            System.out.println("Se cambiará la direccion del servidor a la ip local...");
                            Clientes.clear();
                            IPDest = "0.0.0.0";

                            Clientes.add(0, datoscliente[0].getIP());
                            ConexionServer(datoscliente[0].getIP());

                        }
                    }
                        
                        
                    
                    
                    if ((ip.toString().replace("/", "")).equals(datoscliente[0].getIP())) {
                        numHilo = 0;
                        //IPDest = datoscliente[0].getIP();
                        contadorDeClientes--;
                        //System.out.println("El contador es 2? " + contadorDeClientes);
                    }
                    
                    
                    (new HiloxCliente(sSvr, ssSvr, numHilo)).start();
                }

                if (AsignacionDeServidor == false) {
                    String IPServidor = dis.readUTF();
                    ConexionServer(IPServidor);
                    System.out.println("Se cambio la direccion del servidor... ");
                    Clientes.clear();
                    Clientes.add(0, datoscliente[0].getIP());
                    
                    
                }

               
               

                

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                /*            REFACTORIZANDO                   REFACTORIZANDO               REFACTORIZANDO            REFACTORIZANDO        REFACTORIZANDO    */
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    static class HiloxCliente extends Thread {

        private Socket sSvr = null;
        private ServerSocket ssSvr = null;
        private List<Float> ClienteAB = new ArrayList<Float>();
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private int filaHilo;
        private int inecesario = 0;
        private boolean inecesario1;
                
        public HiloxCliente(Socket socketSvr, ServerSocket serversocketSvr, int numHilo) {
            this.sSvr = socketSvr;
            this.ssSvr = serversocketSvr;
            this.filaHilo = numHilo;
        }

        public void run() {
            String IP = null;
            List<Float> almacenamiento = new ArrayList<Float>();
            String procesador = null;
            Float RAM = null;
            String Internet = null;
            String estatus = "Conectado";
            try {

                InetAddress ips = sSvr.getInetAddress();
                System.out.println("Se conectaron desde la IP: " + ips);
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                /*            REFACTORIZANDO                   REFACTORIZANDO               REFACTORIZANDO            REFACTORIZANDO        REFACTORIZANDO    */
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                //Verifica si la ip recibida es la misma que la del anterior servidor
                //Si es asi cambia la conexion local hacia si mismo, y modifica el ip destino original
                /* 
                if ((ips.toString().replace("/", "")).equals(IPDest)) {
                    IPDest = "0.0.0.0";
                    Clientes.add(0, datoscliente[0].getIP());
                    ConexionServer(datoscliente[0].getIP());

                    System.out.println(datoscliente.length);

                }

                if ((ips.toString().replace("/", "")).equals(datoscliente[0].getIP())) {
                    filaHilo = 0;
                    IPDest = datoscliente[0].getIP();
                    contador--;
                    System.out.println("El contador es 2? " + contador);
                }
                */
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                /*            REFACTORIZANDO                   REFACTORIZANDO               REFACTORIZANDO            REFACTORIZANDO        REFACTORIZANDO    */
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                dis = new DataInputStream(sSvr.getInputStream());
                dos = new DataOutputStream(sSvr.getOutputStream());

                boolean conectado = true;
                int posicionIP = 0;
                while (conectado) {
                    try {
                        if (inecesario > 0) {
                            inecesario1 = dis.readBoolean();
                        }
                        inecesario ++;
                        
                        IP = ips.toString().replace("/", "");

                        procesador = dis.readUTF();
                        int cantLD = dis.readInt();
                        almacenamiento.removeAll(almacenamiento);
                        for (int i = 0; i < cantLD; i++) {
                            almacenamiento.add(dis.readFloat());
                        }
                        RAM = dis.readFloat();

                        Internet = dis.readUTF();

                        System.out.println("Se obtuvieron los datos del cliente " + IP);
                        datoscliente[filaHilo] = new DatosCliente(IP, almacenamiento, procesador, RAM, Internet);
                        System.out.println(datoscliente[filaHilo]);

                        try {
                            ColocarEnPosicion(filaHilo);
                        } catch (Exception e) {
                            System.out.println("No se pudo colocar en posicion");
                        }

                        System.out.println("Bien " + Clientes + " - " + modelo.getValueAt(0, 0));

                        if ((Clientes.get(0).equals(modelo.getValueAt(0, 0).toString())) == false) {
                            String NuevoMejorServer = modelo.getValueAt(0, 0).toString();
                            System.out.println("La ip original ya no es la mejor opcion (ahora es " + modelo.getValueAt(0, 0) + ")");
                            System.out.println("Eliminando Filas del modelo tabla actual");
                            
                            System.out.println("Enviando ips de todos los clientes");
                            for (int i = 1;i<Clientes.size();i++) {
                                //Se envia la ip del nuevo mejor server a todos los clientes menos el actual y el nuevoMejorServer
                                if ((NuevoMejorServer.equals(Clientes.get(i))) == false) {
                                    EnviarIP(Clientes.get(i), NuevoMejorServer);
                                }
                            }

                            //Eliminar filas del modelo actual
                            int rowCount = modelo.getRowCount();
                            for (int i = rowCount - 1; i >= 0; i--) {
                                modelo.removeRow(i);
                            }
                            System.out.println("Conectando al nuevo Cliente/Servidor " + NuevoMejorServer);
                            Clientes.clear();
                            Clientes.add(0, datoscliente[0].getIP());
                            ConexionServer(NuevoMejorServer);

                            
                            
                            

                            System.out.println("IP del servidor principal cambiada correctamente. Teminando este proceso...");
                            conectado = false;

                            //Eliminar registro de los clientes conextados
                            /*for (int i = 1; i < Clientes.size(); i++) {
                                Clientes.remove(i);
                            }*/
                        } else {
                            System.out.println("Sigue siendo la mejor opcion");
                        }

                    } catch (Exception ex) {
                        //System.out.println(ex);
                        estatus = "Desconectado";
                        System.out.println(estatus + ClienteAB);
                        //ClienteAB.set(filaHilo, 0.1f / filaHilo);

                        //CargarDatos(filaHilo, IP, procesador, RAM, Internet, almacenamiento, estatus, ClienteAB);
                        estatus = "Conectao";
                        System.out.println("Se cerro aqui" + ex);
                        conectado = false;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Se cerro aca");
            }
        }
    }

    
    //Se actualiza cada 10 segundos
    static Runnable HiloxServer = new Runnable() {
        @Override
        public void run() {

            DataOutputStream dosCte = null;

            try {
                dosCte = new DataOutputStream(sCte.getOutputStream());

                /*            REFACTORIZANDO                   REFACTORIZANDO               REFACTORIZANDO            REFACTORIZANDO        REFACTORIZANDO    */
                dosCte.writeBoolean(true);
                /*            REFACTORIZANDO                   REFACTORIZANDO               REFACTORIZANDO            REFACTORIZANDO        REFACTORIZANDO    */

            } catch (IOException ex) {
                System.out.println(ex);
            }
            
            
            

            try {
                Float persentage = 10.0f + new Random().nextFloat() * (100.0f - 10.0f);
                //System.out.println(persentage);

                List<Float> almacenamiento = new ArrayList<Float>();
                for (int i = 0; i < 2; i++) {
                    Float numeroFloat = 500.0f + new Random().nextFloat() * (1000.0f - 500.0f);
                    almacenamiento.add(numeroFloat);
                }
                String Internet = "20Mb/s";

                float RAM = 20.0f + new Random().nextFloat() * (50.0f - 20.0f);
                //RAM = RAM / 500;
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                try {
                    //s = new Socket("127.0.0.1",5432);
                    dosCte.writeUTF(persentage.toString());
                    //envio el tamaño del array de los discos logicos
                    dosCte.writeInt(2);
                    //envio el array dato por dato
                    for (int i = 0; i < 2; i++) {
                        Float numero = 500.0f + new Random().nextFloat() * (1000.0f - 500.0f);;
                        dosCte.writeFloat(numero);
                    }
                    dosCte.writeFloat(RAM);
                    dosCte.writeUTF(Internet);
                } catch (Exception ex) {
                    System.out.println("Error A: Socket cerrado");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("\nACTUALIZANDO CTE");
        }
    };

    public static void ColocarEnPosicion(int numHilo) {

        System.out.println("\n");
        System.out.println(numHilo + " " + "Actualizando: Colocando en posicion...");
        //Si aun no hay filas solo se agrega la Actual

        if (modelo.getRowCount() == 0) {
            System.out.println(numHilo + " " + "No hay filas existentes. Creando fila...");
            modelo.insertRow(0, datoscliente[numHilo].toTableRow().toArray());
            System.out.println(numHilo + " " + "Creada correctamente...");
        } else {
            //Verificar si ya esta esa ip o es nueva
            boolean YaExiste = false;
            int posicion = 0;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String actual = datoscliente[numHilo].IP_A;
                String IPaComparar = modelo.getValueAt(i, 0).toString();
                if (actual.equals(IPaComparar)) {
                    System.out.println(numHilo + " " + "Actualizando: La fila con la ip ya existe...");
                    YaExiste = true;
                    posicion = i;
                }
            }
            //Eliminarla si ya existe
            if (YaExiste == true) {
                System.out.println(numHilo + " " + "Eliminando para actualizar...");
                modelo.removeRow(posicion);
            } else {
                Clientes.add(numHilo, datoscliente[numHilo].getIP());
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////////// 
            //Colocarla en posicion 
            boolean posicionado = false;
            ///*
            try {
                for (int i = 0; i < modelo.getRowCount(); i++) {

                    Float RAMaComparar = Float.valueOf(modelo.getValueAt(i, 2).toString());

                    if (datoscliente[numHilo].getRAM() > RAMaComparar && (datoscliente[numHilo].getRAM().toString()) != (RAMaComparar.toString())) {

                        System.out.println(numHilo + " " + "El dato actual es mayor que alguno otro en la fila. Actualizando posiciones");
                        //Insertar fila vacia al final
                        modelo.addRow(new Object[]{});
                        //Bucle para ir recorriendo los datos de las filas
                        for (int j = modelo.getRowCount() - 1; j > i; j--) {

                            modelo.moveRow(j - 1, j - 1, j);

                        }
                        System.out.println(numHilo + " " + "Insertando en posicion correspondiente " + i);
                        modelo.insertRow(i, datoscliente[numHilo].toTableRow().toArray());
                        //Elimina fila vacia generada
                        modelo.removeRow(i + 1);
                        i = modelo.getRowCount() + 1;
                        System.out.println(numHilo + " " + "Finalizando. Bucle " + numHilo + " terminado");
                        posicionado = true;
                    }
                }

            } catch (Exception e) {
                System.out.println("Prueba " + e);
            }

            if (modelo.getRowCount() == 0) {
                System.out.println(numHilo + " " + "Actualizada. Volviendo a insertar fila");
                modelo.insertRow(0, datoscliente[numHilo].toTableRow().toArray());
                posicionado = true;
                System.out.println(numHilo + " " + "Insertada correctamente...");
            }

            if (posicionado == false) {
                System.out.println(numHilo + " " + "El dato nuevo para " + numHilo + ": " + datoscliente[numHilo].getRAM() + " es el menor. Insertando al final...");
                modelo.addRow(datoscliente[numHilo].toTableRow().toArray());
            }

        }
        //*/ 
        //////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    public static String obtenerIPv4() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = iface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // Verifica si es una dirección IPv4 y no es la dirección loopback
                    if (addr instanceof java.net.Inet4Address && !addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null; // Devuelve null si no se pudo obtener la dirección IPv4
    }

    public static void ObtenerDatosLocales() {
        try {
            Float persentageLocal = 10.0f + new Random().nextFloat() * (100.0f - 10.0f);
            //System.out.println(persentage);

            List<Float> almacenamientoLocal = new ArrayList<Float>();
            for (int i = 0; i < 2; i++) {
                Float numeroFloat = 500.0f + new Random().nextFloat() * (1000.0f - 500.0f);
                almacenamientoLocal.add(numeroFloat);
            }
            String InternetLocal = "20Mb/s";

            float RAMLocal = 20.0f + new Random().nextFloat() * (50.0f - 20.0f);

            datoscliente[0] = new DatosCliente(obtenerIPv4(), almacenamientoLocal, persentageLocal.toString(), RAMLocal, InternetLocal);
            contador++;
            contadorDeClientes++;
        } catch (Exception e) {
            System.out.println("Error en la obtencion de datos locales");
        }

    }

    //Conectarse a los clientes y enviar IP del nuevo servidor
    public static void EnviarIP(String IPCliente, String IPnuevoSvr) {
        System.out.println("Enviando nueva IP a: " + IPCliente);

        try {
            sCte = new Socket(IPCliente, 2635);

            System.out.println(estatus + ": Se conecto exitosamente con el cliente " + IPCliente + ". Enviando IP del nuevo servidor..." + IPnuevoSvr);
 
            DataOutputStream dosCte = null;

            try {
                dosCte = new DataOutputStream(sCte.getOutputStream());

                dosCte.writeBoolean(false);
                dosCte.writeUTF(IPnuevoSvr);

            } catch (IOException ex) {
                System.out.println(ex);
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}

















