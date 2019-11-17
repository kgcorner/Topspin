package com.kgcorner.topspin.util;

/*
Description : DBUtility Class
Author: kumar
Created on : 11/11/19
*/

import com.kgcorner.crypto.Hasher;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DBUtil {
    public static Document createDocument(Document document, String collection) {
        MongoClient mongoClient = MongoClients.create(PropertiesUtil.getValue("mongodb.server"));
        MongoDatabase authDb = mongoClient.getDatabase(PropertiesUtil.getValue("db.name"));
        if(authDb == null) {
            throw new RuntimeException("Unable to create DB");
        }
        MongoCollection<Document> docCollection = authDb.getCollection(collection);
        System.out.println("Creating Test user");
        docCollection.insertOne(document);
        mongoClient.close();
        return document;
    }
}