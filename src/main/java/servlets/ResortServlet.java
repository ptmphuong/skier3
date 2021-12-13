package servlets;

import Dao.ResortsDao;
import Dao.SkiersDao;
import validations.UrlValidation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet(name = "servlets.ResortServlet", value = "/servlets.ResortServlet")
public class ResortServlet extends HttpServlet {
    private ResortsDao resortsDao;
    private final static String RESORT_TABLE = "aggResort";

    public void init() throws ServletException {
        super.init();
        this.resortsDao = new ResortsDao(RESORT_TABLE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type to text
        response.setContentType("text/html");

        String urlPath = request.getPathInfo();
        String[] urlParts = urlPath.split("/");

        PrintWriter out = response.getWriter();
//        out.println(Arrays.toString(urlParts));

        if (UrlValidation.correctResortsGETUniqueSkiers(urlParts)) {
            int result = resortsDao.getUniqueSkierForResort(
                    Integer.parseInt(urlParts[1]),
                    Integer.parseInt(urlParts[3]),
                    Integer.parseInt(urlParts[5])
            );
            String displayText = String.format("Total unique skiers at resort %s is %d",
                    urlParts[1], result);
            response.setStatus(HttpServletResponse.SC_OK);
            out.write(displayText);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.write("invalid url");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
