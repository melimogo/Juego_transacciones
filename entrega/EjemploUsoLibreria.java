import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*-
 * UNIVERSIDAD DE MEDELLIN Libreria de Utilidades para la practica
 *
 * @author Sergio Alvarez
 * @version 1.3
 * -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*- -*-
 */


public class EjemploUsoLibreria extends Thread {

    /**
     * Usada para detener el Hilo
     */
    private volatile boolean stopRequested = false;
    private int cont=1;
    Thread th;
    /**
     * Marca de detección del hilo
     */
    public void requestStop() {
        stopRequested = true;
    }

    /**
     * Ciclo Para ejecutar el Hilo
     */
    @Override
    @SuppressWarnings("SleepWhileInLoop")
   public void run() {

        while (!stopRequested) {
            /*
             * Ejecucion del hilo, se ejecuta cada segundo, para que compre o venda en ese tiempo
             */
            System.out.println("Ejecución del Hilo (Este mensaje sale cada 1 segundos)");
            try {
              // Ejecucion del metodo que retorna el llamado al procedimiento del sql
                Precio_menor();
                // tiempo de espera
                th.sleep(1000);

            } catch (InterruptedException ex) {
                Logger.getLogger(EjemploUsoLibreria.class.getName()).log(Level.SEVERE, null, ex);
            }



            }
        }

    /**
     * Manejo de la conexion a la base de datos
     */
    public LibBaseDatos lbd = new LibBaseDatos();

    /**
     * Realiza la conexion a la base de datos
     *
     * @return verdadero si se conecto a la base de datos
     */
    public boolean conexion() {
        // Instancio la clase

        // realizo la conexion
        // *************************************
        // OJO : DEBE CONFIGURAR LA CONEXION A LA BASE DE DATOS
        // *************************************
        String host, SID, usuario, clave;
        boolean conexionUDEM = true; // Local o UDEM
        if (conexionUDEM) {
            host =  "172.60.0.6";;
            SID = "sistemas";
            usuario = "SALVAREZ_MMORALES";
            clave = "mel635472";
        } else {
            // ver esta pagina
            // http://docs.oracle.com/cd/E17781_01/appdev.112/e18805/getconn.htm
            host = "localhost";
            SID = "XE";
            usuario = "system"; // Usuario administrador
            clave = "manager";
        }
        return lbd.connect(host, "1521", SID, usuario, clave);
    }// Fin Constructor

    /**
     * Procedimiento principal.<br> Ejemplo de conexion
     */
    public static void main(String s[]) {
        EjemploUsoLibreria ejemplo = new EjemploUsoLibreria();

        if (ejemplo.conexion()) {
            /**
             * Aquí se inicia el Hilo o sub proceso
             * Notose el mensaje
             */
            //ejemplo.Precio_menor();

            ejemplo.start();
            /**
             * Aquí se inicia la ejecución del programa en el Hilo principal
             * Notese que son 2 Hilos
             */

            do {
                // EJEMPLO DE REALIZAR UNA CONSULTA
                System.out.println("Ejemplo 1:-Escriba frase");
                // Ejecuto la consulta
                ejemplo.lbd.printQuery(ejemplo.lbd.leerStringTeclado());

                // Marca fin para salir
                System.out.println("Escriba <t> para terminar");
            } while (!ejemplo.lbd.leerStringTeclado().equalsIgnoreCase("t"));
                ejemplo.tiempo_final();

            do {
                // EJEMPLO DE REALIZAR UNA CONSULTA
                System.out.println("Ejemplo 1:-Escriba una consulta");
                // Ejecuto la consulta
                ejemplo.lbd.printQuery(ejemplo.lbd.leerStringTeclado());

                // Marca fin para salir
                System.out.println("Escriba <fin> para terminar");
            } while (!ejemplo.lbd.leerStringTeclado().equalsIgnoreCase("fin"));
            ejemplo.lbd.disconnect();
        }

    } // Fin Main

    // Ejecuta el metodo de menor precio que esta en el usuario de oracle
      public  void Precio_menor() {
        try {
            CallableStatement stmt = lbd.con.prepareCall("{ call  Precio_menor() }");

            // aqui se ejecuta
            stmt.executeUpdate();

        } catch (SQLException ex) {
            lbd.printSQLException(ex);
        }
    }

      /* este metodo se ejecuta cuando se agrega una frase, es decir, cuando se esta acabando el tiempo,
        se da la instruccion de ejercutar este metodo, para colocar en promocion los productos.
      */
      public  void tiempo_final() {
        try {
            CallableStatement stmt = lbd.con.prepareCall("{ call  tiempo_final() }");


            // aqui se ejecuta
            stmt.executeUpdate();

            System.out.println("Tiempo final ejecutado");
        } catch (SQLException ex) {
            lbd.printSQLException(ex);
        }
    }

} // Fin Clase
