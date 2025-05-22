package ec.edu.utpl.carreras.computacion.proava.clases.s1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Procesos {

    public static List<String> readUrls(String path) throws IOException {
        return new ArrayList<>(new HashSet<>(Files.readAllLines(Paths.get(path))));
    }

    public static void writeResults(String path, List<ProcesadorURL> procesadores) throws IOException {
        try (PrintWriter writer = new PrintWriter(path)) {
            writer.println("URL,CantidadInternas");
            for (ProcesadorURL p : procesadores) {
                writer.printf("%s,%d%n", p.getUrl(), p.getCantidadEnlaces());
            }
        }
    }

    public static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String dominio = uri.getHost();
            return dominio != null ? dominio.replace("www.", "") : "";
        } catch (Exception e) {
            return "";
        }
    }
}

