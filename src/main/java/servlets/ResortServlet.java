package servlets;

import validations.UrlValidation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet(name = "servlets.ResortServlet", value = "/servlets.ResortServlet")
public class ResortServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type to text
        response.setContentType("text/html");

        String urlPath = request.getPathInfo();
        String[] urlParts = urlPath.split("/");

        PrintWriter out = response.getWriter();
        out.println(Arrays.toString(urlParts));

        if (UrlValidation.correctResortsGetUniqueSkiers(urlParts)) {
            out.println("<h1>" + "it works - get number of unique skiers at resort/season/day" + "</h1>");
        } else {
            out.println("<h1>" + "Invalid URL" + "</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
