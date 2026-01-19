package com.nb.portfolio.cms.config;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.nb.portfolio.cms.repositories.pub",
        mongoTemplateRef = "publicMongoTemplate"
)
public class PublicMongoConfig {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Bean(name = "publicMongoTemplate")
    public MongoTemplate publicMongoTemplate(@Qualifier("publicMongoClient") MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, databaseName);
    }
}
