package com.griddynamics.jsondatabase.repository;

import com.griddynamics.jsondatabase.server.messages.OutputMessages;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
public class JSONDatabaseModel {
    private Map<String, String> database;
    private final JSONFileManager jsonFileManager;
    private final ReentrantReadWriteLock readWriteLock;
    private final ReentrantReadWriteLock.WriteLock writeLock;
    private final ReentrantReadWriteLock.ReadLock readLock;
    public JSONDatabaseModel() {
        this.database = new HashMap<>();
        this.jsonFileManager = new JSONFileManager();
        this.readWriteLock = new ReentrantReadWriteLock();
        this.writeLock = readWriteLock.writeLock();
        this.readLock = readWriteLock.readLock();
    }
    public void setData(String key, String value) {
        writeLock.lock();
        database = jsonFileManager.updateDatabase();
        try {
            database.put(key, value);
        } finally {
            jsonFileManager.updateJSON(database);
            writeLock.unlock();
        }
    }
    public String getData(String key) {
        readLock.lock();
        database = jsonFileManager.updateDatabase();
        try {
            return database.getOrDefault(key, OutputMessages.ERROR);
        } finally {
            readLock.unlock();
        }

    }
    public String deleteData(String key) {
        writeLock.lock();
        database = jsonFileManager.updateDatabase();
        try {
            if (database.containsKey(key)) {
                database.remove(key);
                jsonFileManager.updateJSON(database);
                return OutputMessages.OK;
            } else {
                return OutputMessages.ERROR;
            }
        } finally {
            writeLock.unlock();
        }
    }
}
