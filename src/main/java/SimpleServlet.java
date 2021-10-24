import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet(name = "SimpleServlet", value = "/SimpleServlet")
public class SimpleServlet extends HttpServlet {

    private String msg;

    public void init() throws ServletException {
        // Initialization
        msg = "Hello World";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set response content type to text
        response.setContentType("text/html");

        // sleep for 1000ms. You can vary this value for different tests
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String urlPath = request.getPathInfo();
        String[] urlParts = urlPath.split("/");

        // Send the response
        PrintWriter out = response.getWriter();
        out.println("<h1>" + msg + "</h1>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // validate URL
        String urlPath = request.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("missing parameters");
            return;
        }

        String[] urlParts = urlPath.split("/");
        // and now validate url path and return the response status code
        // (and maybe also some value if input is valid)

        if (UrlValidation.isCorrect(urlParts)) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Post request success");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Invalid URL");
        }
    }

    public void destroy() {
        // nothing to do here
    }

}
