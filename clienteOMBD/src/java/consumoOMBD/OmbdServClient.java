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
    //                                                  Uso de Api OMBD
public class OmbdServClient {

    //Constructor
    public OmbdServClient(){}; 
    
    /*
        entregaUrl recibe input realizada por el usuario, el cual es reemplazado en la url para realizar la busqueda de la pelicula
                                **Solo hace busqueda con el titulo de la pelicula -> s **
        En la Api OMBD la s recibe como parametro el titulo. 
        
        El input es el realizado por el usuario al buscar una película, se entrega en el servlet. 
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
            }
            else {
                System.out.println("Error. ");
            }        
        }
        
        return pelicula;

    }
    
    /*
        entregaPoster recibe input del usuario para poder ser reemplazado en la url, este busca
        el poster de la película requerida. 
        **Se utilizará como relacion 1-1 junto a buscarPeliculaTitulo para poder buscar de manera correcta. 
    */
    public LinkedList<String> entregaPoster(String input) throws MalformedURLException, IOException {
        LinkedList<String> poster_pelicula = new LinkedList<String>();
        OmbdServClient ombd = new OmbdServClient();
        String url_generada;
        url_generada = ombd.entregaUrl(input);
        
        URL url = new URL(url_generada);
        
        InputStream entrada = url.openStream();
        JsonReader reader = Json.createReader(entrada);
        JsonObject objeto = reader.readObject();
        
        //Guardamos el poster de la pelicula en una lista enlazada
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
    /*
        Entrega el año de la pelicula. 
    */
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
    
    /*
        entregaIndexPelicula recibe una lista enlazada con las peliculas, y el input, con este se determina si existe la pelicula o no,
        y para ello la pelicula debe de estar en la lista. 
        Retorna el indice si lo encuentra, y si no entrega un -1. 
    */
    public int entregaIndexPelicula(LinkedList<String> pelicula, String peliInput) throws IOException {
        OmbdServClient ombd = new OmbdServClient();
        int index = 0;
        char simbolo = '"';
        
        peliInput = peliInput.toLowerCase();
        peliInput = simbolo+peliInput+simbolo; // ejemplo: batman -> "batman" (así se guarda en la lista)

        if(ombd.buscarPeliculaTitulo(peliInput).contains(peliInput) == true) {
            index = ombd.buscarPeliculaTitulo(peliInput).indexOf(peliInput);
        }
        else {
            index = -1;
        }
        
        return index;
        
    }
    
    /*
        existePelicula nos indica si se encuentra la pelicula en la biblioteca de la Api. 
        Retorna true o false. 
    */
    
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
    
                 //Main de prueba para ver si los métodos creados funcionan de manera correcta antes de implementar. 
    
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
