package com.nb.portfolio.cms;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
public class MongoDBConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBConfiguration.class);

    @Value("${spring.data.mongodb.uri:}")
    private String publicMongoUri;

    @Value("${spring.data.mongodb.admin-uri:}")
    private String adminMongoUri;

    private CodecRegistry codecRegistry() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        return fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
    }

    @Bean(name = "publicMongoClient")
    public MongoClient publicMongoClient() {
        return createMongoClient(publicMongoUri, "public MongoDB (read-only)", "spring.data.mongodb.uri");
    }

    @Bean(name = "adminMongoClient")
    @Primary
    public MongoClient adminMongoClient() {
        return createMongoClient(adminMongoUri, "admin MongoDB (read/write)", "spring.data.mongodb.admin-uri");
    }

    private MongoClient createMongoClient(String mongoUri, String label, String propertyName) {
        if (mongoUri == null || mongoUri.isBlank()) {
            throw new IllegalStateException(
                    "Missing MongoDB connection string. Set '" + propertyName + "' (" + label + ")");
        }

        try {
            LOGGER.info("Connecting to {}...", label);
            return MongoClients.create(
                    MongoClientSettings.builder()
                            .applyConnectionString(new ConnectionString(mongoUri))
                            .codecRegistry(codecRegistry())
                            .build());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "Invalid MongoDB connection string in '" + propertyName + "'. " +
                            "If your password contains special characters (e.g. @, :, /, %), URL-encode it.",
                    e);
        }
    }
}
