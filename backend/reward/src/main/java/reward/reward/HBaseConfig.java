package reward.reward;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HBaseConfig {

    @Bean
    public Connection hbaseConnection() throws Exception {
        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "your-hbase-server-hostname");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        return ConnectionFactory.createConnection(config);
    }
}


