package com.raghu.cloudstorage.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "dropboxclone";
    }

    public @Bean com.mongodb.client.MongoClient mongoClient() {
        return com.mongodb.client.MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(getDatabaseName());
        return GridFSBuckets.create(database);
    }

    @Bean
    public GridFsTemplate gridFsTemplate(MongoConverter mongoConverter) {
        return new GridFsTemplate(mongoDbFactory(), mongoConverter);
    }
}
