package com.nb.portfolio.cms.config;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.nb.portfolio.cms.repositories.admin",
        mongoTemplateRef = "adminMongoTemplate"
)
public class AdminMongoConfig {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Bean(name = "adminMongoTemplate")
    @Primary
    public MongoTemplate adminMongoTemplate(@Qualifier("adminMongoClient") MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, databaseName);
    }
}
