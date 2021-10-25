import org.json.JSONObject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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
        if (urlPath == null || urlPath.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("missing parameters");
            return;
        }

        String[] urlParts = urlPath.split("/");
        if (UrlValidation.isCorrect(urlParts)) {
            String jsonStr = getPostRequestBodyStr(request, urlParts);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonStr);
                if (JsonValidation.isCorrect(jsonObject)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            response.getWriter().write("Processed json");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Invalid URL");
        }

        response.getWriter().flush();
    }

    public void destroy() {
        // nothing to do here
    }

    private String getPostRequestBodyStr(HttpServletRequest request, String[] urlParts) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<String> params = new ArrayList<>();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
            params.add(s);
        }
        String skierID = ",\"skierID\":" + UrlValidation.getSkierID(urlParts) + "}";
        sb.deleteCharAt(sb.length()-1);
        sb.append(skierID);
        return sb.toString();
    }

}
