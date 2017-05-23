package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordsLoaderAndSaver implements Serializable {
    private transient final static File file = new File("records.ser");
    private List<Record> records;
    private transient static RecordsLoaderAndSaver instance;

    private RecordsLoaderAndSaver() {
        records = new ArrayList<>();
        load();
    }

    public static RecordsLoaderAndSaver getInstance() {
        if (instance == null) instance = new RecordsLoaderAndSaver();
        return instance;
    }

    public void add(String name, int newScore) {
        if (records.size() < 10) {
            records.add(new Record(name, newScore));
        } else if (Collections.min(records).getScore() < newScore) {
            records.remove(0);
            records.add(new Record(name, newScore));
        }
        Collections.sort(records);
        save();
    }

    public List<Record> getRecords() {
        return records;
    }

    public void save() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(records);
            objectOutputStream.close();
        } catch (IOException e) {
        }
    }

    public void load() {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            records = (ArrayList<Record>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
        }
    }

    public class Record implements Comparable, Serializable {
        private String name;
        private Integer score;

        Record(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(Object o) {
            Record obj = (Record) o;
            return score.compareTo(obj.getScore());
        }
    }
}

