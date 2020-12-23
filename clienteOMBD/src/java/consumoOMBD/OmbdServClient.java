package consumoOMBD;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.*;

/**
    * @author gabriela_figueroa
 */
public class OmbdServClient {
    //                                                  Uso de Api OMBD
    public OmbdServClient(){};
    
    /*
        entregaUrl recibe input realizada por el cliente, el cual es reemplazado en la url para realizar la busqueda de la pelicula
                                **Solo hace busqueda con el titulo de la pelicula -> s **
        En la Api OMBD la s recibe como parametro el titulo. 
    */
    
    public String entregaUrl(String input) {
        String apikey = "4473e337";
        String url = "http://www.omdbapi.com/?s=TITLE&apikey=APIKEY";
        
        if (input.contains(" ")) {
            input = input.replaceAll(" ", "+"); //Si la busqueda tiene espacio en blanco la url arrojará error, por ende debe ser encadenado con +
        }
        
        url = url.replaceAll("TITLE", input).replaceAll("APIKEY", apikey);
        return url;
    }
    
    public LinkedList<String> buscarPeliculaTitulo(String input) throws MalformedURLException, IOException {
        OmbdServClient ombd = new OmbdServClient();
        LinkedList<String> pelicula = new LinkedList<String>();
        String url_generada;
        url_generada = ombd.entregaUrl(input);
        URL url = new URL(url_generada);
        
        InputStream entrada = url.openStream();
        JsonReader reader = Json.createReader(entrada);
        JsonObject objeto = reader.readObject();
        
        //Guardamos el titulo de la pelicula en una lista enlazada
        for (int i = 0; objeto.getJsonArray("Search").size() > i; i++) {
            if (objeto.size() > 0) {
                String titulo_pelicula = objeto.getJsonArray("Search").getJsonObject(i).get("Title").toString();
                pelicula.add(i, titulo_pelicula.toLowerCase());
                //System.out.println("Agregando: "+objeto.getJsonArray("Search").getJsonObject(i).get("Title"));
            }
            else {
                System.out.println("Error. ");
            }        
        }
        
        return pelicula;

    }
    
    public LinkedList<String> entregaPoster(String input) throws MalformedURLException, IOException {
        LinkedList<String> poster_pelicula = new LinkedList<String>();
        OmbdServClient ombd = new OmbdServClient();
        String url_generada;
        url_generada = ombd.entregaUrl(input);
        
        URL url = new URL(url_generada);
        
        InputStream entrada = url.openStream();
        JsonReader reader = Json.createReader(entrada);
        JsonObject objeto = reader.readObject();
        
        //Guardamos el titulo de la pelicula en una lista enlazada
        for (int i = 0; objeto.getJsonArray("Search").size() > i; i++) {
            if (objeto.size() > 0) {
                String poster = objeto.getJsonArray("Search").getJsonObject(i).get("Poster").toString();
                poster_pelicula.add(i, poster);
               
            }
            else {
                System.out.println("Error. ");
            }        
        }
        
        return poster_pelicula;
        
    }
    
    public LinkedList<String> entregaYear(String input) throws MalformedURLException, IOException {
        LinkedList<String> year_pelicula = new LinkedList<String>();
        OmbdServClient ombd = new OmbdServClient();
        String url_generada;
        url_generada = ombd.entregaUrl(input);
        
        URL url = new URL(url_generada);
        
        InputStream entrada = url.openStream();
        JsonReader reader = Json.createReader(entrada);
        JsonObject objeto = reader.readObject();
        
        //Guardamos el año de la pelicula en una lista enlazada
        for (int i = 0; objeto.getJsonArray("Search").size() > i; i++) {
            if (objeto.size() > 0) {
                String year = objeto.getJsonArray("Search").getJsonObject(i).get("Year").toString();
                year_pelicula.add(i, year);
               
            }
            else {
                System.out.println("Error. ");
            }        
        }
        
        return year_pelicula;
    }
    
    public int entregaIndexPelicula(LinkedList<String> pelicula, String peliInput) throws IOException {
        OmbdServClient ombd = new OmbdServClient();
        int index = 0;
        char simbolo = '"';
        
        peliInput = peliInput.toLowerCase();
        peliInput = simbolo+peliInput+simbolo; // ejemplo: batman -> "batman"

        if(ombd.buscarPeliculaTitulo(peliInput).contains(peliInput) == true) {
            index = ombd.buscarPeliculaTitulo(peliInput).indexOf(peliInput);
        }
        else {
            index = -1;
        }
        
        return index;
        
    }
    
    public boolean existePelicula(String input_user) throws IOException {
        OmbdServClient ombd = new OmbdServClient();
        boolean existe = false;
        char simbolo = '"';
        
        input_user = input_user.toLowerCase();
        input_user = simbolo+input_user+simbolo; // ejemplo: batman -> "batman"
        System.out.println(input_user);
        
        if (ombd.buscarPeliculaTitulo(input_user).contains(input_user) == true) {
            existe = true;
        }
        else {
            existe = false;
        }
        
        return existe;
    }
    
    public static void main(String[] args) throws MalformedURLException, IOException {
        Scanner lector = new Scanner(System.in);
        OmbdServClient ombd = new OmbdServClient();
        String pelicula = "batman";
        int index = ombd.entregaIndexPelicula(ombd.buscarPeliculaTitulo(pelicula), pelicula);
        
        System.out.println("Se encuentran las siguientes películas: ");
        for(int i = 0; i < ombd.buscarPeliculaTitulo(pelicula).size(); i++){
            System.out.println(ombd.buscarPeliculaTitulo(pelicula).get(i));
            System.out.println("Año: "+ombd.entregaYear(pelicula).get(i));
        }
        
        System.out.println(ombd.existePelicula(pelicula));
        System.out.println(ombd.entregaIndexPelicula(ombd.buscarPeliculaTitulo(pelicula), pelicula));
        System.out.println(ombd.entregaPoster(pelicula).get(index));
        
        System.out.println("Se encuentran los siguientes posters: ");
        for(int i = 0; i < ombd.entregaPoster(pelicula).size(); i++){
            System.out.println(ombd.entregaPoster(pelicula).get(i));
        }
        
    }
     
}
