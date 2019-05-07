package com.github.middleware.dubbo.monitor.config;

import com.google.common.base.Preconditions;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/13
 */
@Configurable
@ComponentScan
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {
    private static final String URL = "mongo.url";
    private static final String PORT = "mongo.port";
    private static final String USERNAME = "mongo.username";
    private static final String DATABASE = "mongo.database";
    private static final String PASSWORD = "mongo.password";

    @Autowired
    private Environment env;

    protected String getDatabaseName() {
        return env.getProperty(DATABASE);
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        final String url = Preconditions.checkNotNull(env.getProperty(URL));
        final int port = Integer.parseInt(env.getProperty(PORT, "27017"));
        final String username = env.getProperty(USERNAME);
        final String database = env.getProperty(DATABASE);
        final String password = env.getProperty(PASSWORD);

        return new MongoClient(singletonList(new ServerAddress(url, port)),
                singletonList(MongoCredential.createCredential(username, database, password.toCharArray())));
    }
}
