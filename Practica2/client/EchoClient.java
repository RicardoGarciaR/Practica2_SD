package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;

public class EchoClient {
    //definimos el Stub del cliente
    private static EchoObjectStub ss;
    
    
    public static void main(String[] args) 
    {
        // revisamos que los argumentos para ejecutar el programa son correctos

        String Tarjeta,cvv,pago;
        
        if (args.length<2) {
            System.out.println("Para ejecutar , hazlo en este formato: Echo <nombre o IP del Equipo> <numero de puerto>");
            System.exit(1);
        }


        //instanciamos el STUB
        ss = new EchoObjectStub();
    // le asignamos al STUB el puerto y nombre del equipo HOST (el nombre del servidor) 
        ss.setHostAndPort(args[0],Integer.parseInt(args[1]));
        String input,output;
        try {  
                    //construyo un bucle infinito:

            while(true){
                Tarjeta="";
                cvv="";
                //preparo "apuntador" que es el lector de flujo para el teclado
                BufferedReader in =
                new BufferedReader(new InputStreamReader(System.in));

                System.out.println("Bienvendio a Emazon\n\n");

                while(true){
                    System.out.println("Ingrese el numero de su tarjeta: ");
                    Tarjeta=in.readLine();
                    if(Tarjeta.length()<16){
                        Tarjeta="";
                        System.out.println("Numero de tarjeta mal ingresado, intente de nuevo\n");
                    }else{break;}
        
                }
                while(true){
                    System.out.println("Ingrese su CVV: ");
                    cvv=in.readLine();
                    if(cvv.length()<3||cvv.length()>3){
                        cvv="";
                        System.out.println("Numero de cvv mal ingresado, intente de nuevo\n");
                    }
                    else{break;}
                }

                while(true){
                    System.out.println("Ingrese la cantidad a pagar: ");
                    pago=in.readLine();
                    if(Integer.parseInt(pago)<=0){
                        pago="";
                        System.out.println("Cantidad incorrecta, intente de nuevo\n");
                    }
                    else{break;}
                }

                System.out.println("\nSe le esta atendiendo, espere un momento..");

               // Invocar el stub con el metodo remoto echo e Imprimir .. 
                //por pantalla lo que regreso el metodo remoto echo
                if(ss.pago_cliente(Tarjeta,cvv,pago)==false){
                    System.out.println("Pago rechazado, intente con otro metodo\n\n");
                }
                else{
                    System.out.println("Pago exitoso\n\n");
                }

            }
        } 
        //catch (UnknownHostException e) {
            //System.err.println("Don't know about host: "+ args[0]);
        //} 
        catch (IOException e) {
            System.err.println("Falla conexion de E/S con el host:"+args[0]);
        }
    }
}
