package fr.thedarven.traveliacite.utils;

import fr.thedarven.traveliacite.Cite;

import java.io.*;

public class ReadFile<T>{

    private final Cite main;
    private String fileName;

    public ReadFile(Cite main, String fileName) {
        this.main = main;
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public T readFile() {
        createFile();
        T element = null;
        ObjectInputStream is = null;

        try {
            is = new ObjectInputStream(new FileInputStream(this.main.getDataFolder()+"/"+fileName));
            element = (T) is.readObject();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return element;
    }

    public void writeFile(T element) {
        writeFile(element, true);
    }


    private void createFile() {
        try {
            File file = new File(this.main.getDataFolder(), fileName);
            if(!file.exists()) {
                file.createNewFile();
                writeFile(null, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile(T element, boolean withFileVerification) {
        if(withFileVerification)
            createFile();
        ObjectOutputStream os = null;
        try{
            os = new ObjectOutputStream(new FileOutputStream(this.main.getDataFolder()+"/"+fileName));
            os.writeObject(element);
            os.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        if(os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
