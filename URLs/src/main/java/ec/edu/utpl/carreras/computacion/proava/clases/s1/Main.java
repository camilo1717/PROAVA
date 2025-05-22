package ec.edu.utpl.carreras.computacion.proava.clases.s1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> urls = Procesos.readUrls("C:/Users/USUARIO/Downloads/urls_parcial1-1.txt");
        List<Thread> hilos = new ArrayList<>();
        List<ProcesadorURL> procesadores = new ArrayList<>();

        for (String url : urls) {
            ProcesadorURL p = new ProcesadorURL(url);
            Thread hilo = Thread.startVirtualThread(p);
            hilos.add(hilo);
            procesadores.add(p);
        }

        for (Thread t : hilos) {
            t.join();
        }

        Procesos.writeResults("resultados.csv", procesadores);
        System.out.println("Proceso realizado con exito");
        System.out.println("El archivo resultados.csv se ha generado correctamente.");
    }
}


