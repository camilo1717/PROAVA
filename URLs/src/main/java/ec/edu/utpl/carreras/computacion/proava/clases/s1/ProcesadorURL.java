package ec.edu.utpl.carreras.computacion.proava.clases.s1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcesadorURL implements Runnable {
    private final String url;
    private int cantidadEnlaces;

    public ProcesadorURL(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        int cantidad = 0;

        try {
            URL urlObj = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlObj.openStream()));
            StringBuilder html = new StringBuilder();
            String linea;

            while ((linea = reader.readLine()) != null) {
                html.append(linea);
            }
            reader.close();

            String dominio = Procesos.getDomainName(url);
            Set<String> internos = new HashSet<>();

            Pattern pattern = Pattern.compile("href=[\"']([^\"'#]+)[\"']");
            Matcher matcher = pattern.matcher(html.toString());

            while (matcher.find()) {
                String encontrado = matcher.group(1);
                if (encontrado.startsWith("/")) {
                    internos.add(encontrado); // relativo
                } else {
                    try {
                        URI encontradoUri = new URI(encontrado);
                        String host = encontradoUri.getHost();
                        if (host != null && host.contains(dominio)) {
                            internos.add(encontrado);
                        }
                    } catch (Exception ignored) {}
                }
            }

            cantidad = internos.size();
        } catch (Exception e) {
            cantidad = -1;
        }

        this.cantidadEnlaces = cantidad;
    }

    public String getUrl() {
        return url;
    }

    public int getCantidadEnlaces() {
        return cantidadEnlaces;
    }
}


