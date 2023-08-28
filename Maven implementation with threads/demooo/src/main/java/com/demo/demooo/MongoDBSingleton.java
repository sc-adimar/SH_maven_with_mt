package com.demo.demooo;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;

public class MongoDBSingleton {
    private static final String MONGODB_URI = "mongodb://localhost:27017"; // Update this with your MongoDB URI

    private static MongoDBSingleton instance;
    private MongoClient mongoClient;

    private MongoDBSingleton() {
        // Private constructor to prevent direct instantiation from outside the class.
        // The MongoDB connection is created here.

//    	
        mongoClient = MongoClients.create(MONGODB_URI);
//        MongoDatabase database = mongoClient.getDatabase("College");
//        MongoCollection<Document> collection = database.getCollection("SH");
//        MongoClientURI uri = new MongoClientURI(MONGODB_URI);
//        mongoClient = new MongoClient(uri);
    }

    public static MongoDBSingleton getInstance() {
        // Lazy initialization: Create the instance only when it's requested for the first time.
        if (instance == null) {
            synchronized (MongoDBSingleton.class) {
                if (instance == null) {
                    instance = new MongoDBSingleton();
                }
            }
        }
        return instance;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
