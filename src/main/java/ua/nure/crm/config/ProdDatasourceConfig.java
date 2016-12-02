package ua.nure.crm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("prod")
public class ProdDatasourceConfig extends BaseDataSourceConfig{

    @Override
    public URI getDbUri() throws URISyntaxException {
        return new URI(System.getenv("DATABASE_URL"));
    }
}
