import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.json.JSONObject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.rabbitmq.client.Channel;

@WebServlet(name = "SimpleServlet", value = "/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(SimpleServlet.class.getName());
    private final static String QUEUE_NAME = "LiftInfo";
    private String msg;
    private ObjectPool<Channel> pool;
    private JSONObject message;

    public void init() throws ServletException {
        super.init();

        try {
            ChannelFactory factory = new ChannelFactory();
            pool = new GenericObjectPool<>(factory);
        } catch (Exception e) {
            logger.info("Cannot create channel factory - " + e);
            e.printStackTrace();
        }
        // Initialization
        msg = "Hello World";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type to text
        response.setContentType("text/html");

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
            logger.info("null url path");
            return;
        }

        String[] urlParts = urlPath.split("/");
        if (UrlValidation.isCorrect(urlParts)) {
            String jsonStr = getPostRequestBodyStr(request, urlParts);
            logger.info("Correct url parts");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonStr);
                if (JsonValidation.isCorrect(jsonObject)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    this.message = jsonObject;
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("cannot create jsonObject from request");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            response.getWriter().write("Processed json");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Invalid URL");
        }
        response.getWriter().flush();

        if (!this.message.equals(null)) {
            sendMessage(this.message);
        }
    }

    public void destroy() {
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
        logger.info("request to string: " + sb.toString());
        return sb.toString();
    }

    private void sendMessage(JSONObject message) {
        try {
            Channel channel = pool.borrowObject();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.toString().getBytes());
            pool.returnObject(channel);
        } catch (Exception e) {
            logger.info("problem sending message to queue");
            e.printStackTrace();
        }
    }
}
