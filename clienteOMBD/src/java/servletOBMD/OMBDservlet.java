package servletOBMD;
import consumoOMBD.OmbdServClient;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
    * @author gabriela_figueroa
 */

public class OMBDservlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        OmbdServClient ombd = new OmbdServClient();
        String ingreso = request.getParameter("pelicula"); 
        ingreso = ingreso.toLowerCase();
        
        String accion, ref_imagen = null, año_pelicula = null;
        int index = ombd.entregaIndexPelicula(ombd.buscarPeliculaTitulo(ingreso), ingreso);
        char simbolo = '"';
        String centro = "center";
        centro = simbolo+centro+simbolo;
        
        if(ombd.existePelicula(ingreso) == true) {
           accion = "";
        }
        else {
          accion = "La pelicula no se encuentra :( ";
        }
        
        if (index != -1) {
            ref_imagen = ombd.entregaPoster(ingreso).get(index);
            año_pelicula = ombd.entregaYear(ingreso).get(index);
        }
 
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Películas</title>");
// ------------------------------  DISEÑO  ---------------------------
            out.println("<style>");
            out.println("body {");
            out.println("background-color: black;");
            out.println("}");
            out.println("#buscando {");
            out.println("color: gray;");
            out.println("text-align: left;");
            out.println("font-family: sans-serif;");
            out.println("font-weight: 900;");
            out.println("}");
            out.println("#titulo {");
            out.println("color: white;");
            out.println("text-align: center;");
            out.println("font-family: sans-serif;");
            out.println("font-weight: 900;");
            out.println("font-size: 50px;");
            out.println("}");
            out.println("#año {");
            out.println("color: white;");
            out.println("text-align: center;");
            out.println("font-family: sans-serif;");
            out.println("font-weight: 900;");
            out.println("font-size: 40px;");
            out.println("}");
            out.println("</style>");
// --------------------------  FIN DISEÑO  ------------------------------
            out.println("</head>");
            out.println("<body>");
            out.println("<div>");
            out.println("<h3 id=buscando> Resultados de la Búsqueda</h1>");
            out.println("<h1 id=titulo> "+ingreso.toUpperCase()+"</h1>");
            out.println("<h2 id=año> "+año_pelicula+"</h2>");
            out.println("</div>");
            out.println("<div align ="+centro+">");
            out.println("<img src="+ref_imagen+" img-align=center>");
            out.println("</div align>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
