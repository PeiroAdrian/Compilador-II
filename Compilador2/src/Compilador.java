

import java.io.IOException;

public class Compilador {
    nodo p;
    
    public static void main(String[] args) throws IOException  {
        lexico b = new lexico (); 
        //Interfaz ven = new Interfaz();
        //ven.setVisible(true);
        if(!b.errorEncontrado)
        {
             System.out.println("\nAnalisis lexico terminado");
         
        }

        if (!b.errorEncontradoSintactico) {
            System.out.println("Analisis sintactico terminado");
        }
        

    }
}
