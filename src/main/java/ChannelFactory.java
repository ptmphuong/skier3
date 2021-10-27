import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class ChannelFactory extends BasePooledObjectFactory<Channel> {

    private final static Logger logger = Logger.getLogger(ChannelFactory.class.getName());
    private final String LOCAL_HOST = "localhost";
    private String HOST;
    private static String RABBIT_USERNAME;
    private static String RABBIT_PASSWORD;
    private final Connection connection;

    ChannelFactory() throws IOException, TimeoutException {
        setProperties();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);
        connectionFactory.setUsername(RABBIT_USERNAME);
        connectionFactory.setPassword(RABBIT_PASSWORD);
        this.connection = connectionFactory.newConnection();
    }

    private void setProperties() {
        Properties prop = ReadProperty.load();
        HOST = prop.getProperty("ip");
        if (!HOST.equals(LOCAL_HOST)) {
            RABBIT_USERNAME = prop.getProperty("rabbit_username");
            RABBIT_PASSWORD = prop.getProperty("rabbit_password");
        }
    }

    @Override
    public Channel create() throws IOException {
        return this.connection.createChannel();
    }

    @Override
    public PooledObject<Channel> wrap(Channel channel) {
        return new DefaultPooledObject<>(channel);
    }

}
