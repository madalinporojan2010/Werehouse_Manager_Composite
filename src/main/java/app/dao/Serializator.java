package app.dao;

import app.bll.model.User;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Serializator implements Serializable{

    public void serialize(Object object, String serFile) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(serFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object deserialize(String serFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(serFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();

            fileInputStream.close();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Object was not serialized");
        }
        return null;
    }
}
