package consumoOMBD;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Lenovo
 */
public class Main {
    public static void main(String[] args) throws MalformedURLException, IOException {
        //Variables a utilizar
        Scanner lector = new Scanner(System.in);
        String pelicula, url_especifica, apikey = "4473e337";
        OmbdServClient ombd = new OmbdServClient();
        String url_generica = "http://www.ombdapi.com/?s=TITLE&apikey=APIKEY";
        
        //Dejando la url lista para la busqueda de la pelicula
        System.out.println("Ingrese pelicula a buscar: ");
        pelicula = lector.next();
        url_especifica = url_generica.replaceAll("TITLE", pelicula).replaceAll("APIKEY", apikey);
        System.out.println("Url creada: "+url_especifica);
        
        URL url = new URL(url_especifica.replaceAll("\r?\n", ""));
        System.out.println("Consumiendo Api...");
        InputStream entrada = url.openStream();
        JsonReader reader = Json.createReader(entrada);
        JsonObject objeto = reader.readObject();
        
        for (int i = 0; objeto.getJsonArray("Search").size() > i; i++) {
            System.out.println("Buscando...");
            System.out.println("Titulo: "+objeto.getJsonArray("Search").getJsonObject(i).get("Title"));
        } 
    }
}
