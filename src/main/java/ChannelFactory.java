import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ChannelFactory extends BasePooledObjectFactory<Channel> {

    private final Connection connection;

    ChannelFactory() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        this.connection = connectionFactory.newConnection();
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
