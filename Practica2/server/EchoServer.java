
package server;
import java.net.*;
import java.io.*;
public class EchoServer {
	// Objeto remoto ( es el Stub del servidor, conocido como Skeleton)
    private static EchoObjectSkeleton eo = new EchoObjectSkeleton();
    private static String myURL="localhost";
    private static ServerSocket serverSocket =null;
    private static Socket clientSocket = null;
    private static BufferedReader is = null;
    private static PrintWriter os = null;
    private static String inputline = new String();

    public static String [][]datos_banco={{"1234567891234567","9876543219876543"},{"123","321"},{"500","50"}};
    
    public static void main(String[] args) {
        try {
            // obtengo el nombre de mi computadora
            myURL=InetAddress.getLocalHost().getHostName(); 
        } catch (UnknownHostException e) {
            System.out.println("Host Desconocido  :" + e.toString());
            System.exit(1);
        }
        try {
            // abro el socket para el servidor ( para que este a la escucha) en el puerto 5000
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            System.out.println(myURL + ": no puedo escuchar en el puerto: 5000, " +e.toString());
            System.exit(1);
        }
        System.out.println(myURL + ": EchoServer esta escuchando en el  puerto: 5000");
       // Servidor esta ahora a la escucha ( esperando conexiones)
        try {
            boolean listening = true;
            while(listening) // bucle infinito
            {
                clientSocket = serverSocket.accept();// aceptamos conexion de un client
                // preparamos para leer  del socket
                is = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
                //preparamos para escribir en el socket
                os = new PrintWriter(clientSocket.getOutputStream());
                // revisamos que en el socket el cliente ha enviado algun texto
                while ((inputline = is.readLine()) != null) 
                {
                    System.out.println("Atendiendo cliente...\n");
                    os.println(eo.pago_server(operacion_banco(inputline)));
                    os.flush();//limpiamos el socket
                }
                os.close();
                is.close();
                clientSocket.close();
                System.out.println("\n\n");
            }
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error enviando/recibiendo" + e.getMessage());
            e.printStackTrace();
        }
    }
    public static Boolean operacion_banco(String datos_cliente){
        String Tarjeta,cvv,saldo;
        String[] datos_individuales=datos_cliente.split(","); //datos_inviduales[1]=Tarjeta ,datos_inviduales[2]=cvv,datos_inviduales[3]=pago
        boolean pago_aceptado=false;

        int i=0,k=0;



        for(i = 0; i<datos_banco[0].length; i++){
           if(datos_banco[0][i].equals(datos_individuales[0])){
               k=i;
               pago_aceptado=true;
               System.out.println("se llego a pago y se encontro");
               break;
           }
        }
        if(pago_aceptado==true){
                if(datos_banco[1][k].equals(datos_individuales[1])){
                    pago_aceptado=true;
                    System.out.println("se llego a cvv y se encontro");
                }else{
                    pago_aceptado=false;
                    System.out.println("se llego a cvv y no se encontro");
                }
        }
        if(pago_aceptado==true){

                if(Integer.parseInt(datos_banco[2][k])>=Integer.parseInt(datos_individuales[2])){
                    int aux=Integer.parseInt(datos_banco[2][k]);
                    aux=aux-Integer.parseInt(datos_individuales[2]);
                    datos_banco[2][k]=String.valueOf(aux);
                    pago_aceptado=true;
                    System.out.println("se llego a pago y paso");
                }else{
                    System.out.println("se llego a pago y no paso");
                    pago_aceptado=false;
                }
        }

        return pago_aceptado;
    }
}
