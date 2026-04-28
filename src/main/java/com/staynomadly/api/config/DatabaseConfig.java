package com.staynomadly.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Value("${DB_URL:}")
    private String dbUrl;
    
    @Value("${DB_USERNAME:root}")
    private String dbUsername;

    @Value("${DB_PASSWORD:}")
    private String dbPassword;

    @Value("${MYSQLHOST:}")
    private String mysqlHost;

    @Value("${MYSQLPORT:3306}")
    private String mysqlPort;

    @Value("${MYSQLDATABASE:staynomadly_db}")
    private String mysqlDatabase;

    @Value("${MYSQLUSER:root}")
    private String mysqlUser;

    @Value("${MYSQLPASSWORD:}")
    private String mysqlPassword;

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        // First try to parse Railway's MYSQL_URL if user pasted it into DB_URL
        if (dbUrl != null && dbUrl.startsWith("mysql://")) {
            URI uri = new URI(dbUrl);
            String username = uri.getUserInfo() != null ? uri.getUserInfo().split(":")[0] : "root";
            String password = uri.getUserInfo() != null && uri.getUserInfo().split(":").length > 1 ? uri.getUserInfo().split(":")[1] : "";
            String jdbcUrl = "jdbc:mysql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath() + "?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
            return DataSourceBuilder.create()
                    .url(jdbcUrl)
                    .username(username)
                    .password(password)
                    .build();
        }
        
        // Second try: Use Railway's injected MYSQLHOST variables if they exist
        if (mysqlHost != null && !mysqlHost.isEmpty()) {
            String jdbcUrl = "jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "/" + mysqlDatabase + "?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true";
            return DataSourceBuilder.create()
                    .url(jdbcUrl)
                    .username(mysqlUser)
                    .password(mysqlPassword)
                    .build();
        }

        // Fallback for standard JDBC URL or local development
        String url = dbUrl.isEmpty() ? "jdbc:mysql://localhost:3306/staynomadly_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true" : dbUrl;
        if (!url.startsWith("jdbc:") && !url.isEmpty()) {
             url = "jdbc:" + url;
        }
        return DataSourceBuilder.create()
                .url(url)
                .username(dbUsername)
                .password(dbPassword)
                .build();
    }
}
