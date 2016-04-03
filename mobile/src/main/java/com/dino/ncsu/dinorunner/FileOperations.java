package com.dino.ncsu.dinorunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Kevin-Lenovo on 3/2/2016.
 * <p>
 * Contains the operations to convert
 * serialiable objects to byte arrays and vice versa
 */
public class FileOperations {
    /**
     * Writes raw byte array to project metadata
     *
     * @param raw the raw data to translate to metadata
     * @return the dinosaur object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static public Object bytes2Object(byte raw[])
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(raw);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }

    /**
     * Writes meta data to byte array
     *
     * @param obj the general object to convert
     * @return object written in byte array
     * @throws IOException
     */
    public static byte[] object2Bytes(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(obj);
        return baos.toByteArray();
    }
}
