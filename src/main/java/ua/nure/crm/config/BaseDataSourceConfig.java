package ua.nure.crm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class BaseDataSourceConfig {

    protected static final String JDBC_POSTGRES_SCHEMA = "jdbc:postgresql://";
    protected static final String POSTGRES_SCHEMA = "postgresql://";
    protected static final int POSTGRES_PORT = 5432;

    public static final String USER_INFO_SEPARATOR = ":";
    public static final int USER_NAME_INDEX = 0;
    public static final int PASSWORD_INDEX = 1;

    public abstract URI getDbUri() throws URISyntaxException;

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource getDataSource() throws URISyntaxException {
        String username = getDbUri().getUserInfo().split(USER_INFO_SEPARATOR)[USER_NAME_INDEX];
        String password = getDbUri().getUserInfo().split(USER_INFO_SEPARATOR)[PASSWORD_INDEX];
        String dbUrl = JDBC_POSTGRES_SCHEMA + getDbUri().getHost() + ':' + getDbUri().getPort() + getDbUri().getPath();
        return constructDataSource(username, password, dbUrl);
    }

    private DataSource constructDataSource(String username, String password, String dbUrl) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }
}
