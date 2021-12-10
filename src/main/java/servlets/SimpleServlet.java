package servlets;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.json.JSONObject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import com.rabbitmq.client.Channel;
import rmqConnection.ChannelFactory;
import validations.PostBody;
import validations.SkierMessage;
import validations.UrlValidation;

@WebServlet(name = "servlets.SimpleServlet", value = "/servlets.SimpleServlet")
public class SimpleServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(SimpleServlet.class.getName());
    private final static String QUEUE_NAME = "LiftInfo";
    private final static String EXCHANGE_NAME = "LiftInfoExchange";
    private String msg;
    private GenericObjectPool<Channel> pool;
    private JSONObject message;

    public void init() throws ServletException {
        super.init();

        try {
            ChannelFactory factory = new ChannelFactory();
            this.pool = new GenericObjectPool<>(factory);
//            this.pool.setMaxTotal(10);
        } catch (Exception e) {
            logger.info("Cannot create channel factory - " + e);
            e.printStackTrace();
        }
//         Initialization
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
        out.println(Arrays.toString(urlParts));

        if (UrlValidation.correctSkiersGETTotalVerticalAtResort(urlParts)) {
            out.println("<h1>" + msg + "- get vertical at resort" + "</h1>");
        } else if (UrlValidation.correctSkiersGETTotalVerticalForSkiDay(urlParts)) {
            out.println("<h1>" + msg + "- get vertical for ski day" + "</h1>");
        } else {
            out.println("<h1>" + "Invalid URL" + "</h1>");
        }
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
        if (UrlValidation.correctSkiersPOST(urlParts)) {
            //
            String jsonString = getPostRequestBodyStr(request);
            logger.info("request body: " + jsonString);
            PostBody postBody = null;
            try {
                postBody = PostBody.fromJsonStr(jsonString);
                UrlValidation correctUrlParts = UrlValidation.skiersPOST(urlParts);
                SkierMessage message = new SkierMessage(correctUrlParts, postBody);
                this.message = message.toJson();
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("cannot create validations.PostBody object from request");
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

    private String getPostRequestBodyStr(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<String> params = new ArrayList<>();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
            params.add(s);
        }
        return sb.toString();
    }

    private void sendMessage(JSONObject message) {
        try {
            Channel channel = pool.borrowObject();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.basicPublish(EXCHANGE_NAME, "", null, message.toString().getBytes());
            logger.info("sent message to queue");
            pool.returnObject(channel);
        } catch (Exception e) {
            logger.info("problem sending message to queue");
            e.printStackTrace();
        }
    }
}
