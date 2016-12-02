package ua.nure.crm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("default")
public class DefaultDatasourceConfig extends BaseDataSourceConfig{

    public static final String LOCALHOST = "localhost";

    @Value("${spring.datasource.username}")
    public String dbUserName;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.database.name}")
    private String dbName;

    @Bean
    public URI getDbUri() throws URISyntaxException {
        return new URI(POSTGRES_SCHEMA + dbUserName + ":" + dbPassword + "@"
                + LOCALHOST + ":" + POSTGRES_PORT + "/" + dbName);
    }

}
