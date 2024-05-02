package org.example;

import org.example.model.Person;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Класс отвечающий за весь функционал коллекции
 */
public class CollectionManager {
    /**
     * С этим id будет создан новый объект коллекции. Увеличивается на 1 с каждым новым созданием
     */
    private int currentId = 1;
    /**
     * Коллекция для быстрого поиска объектов по значению
     */
    private HashMap<Integer, Person> fastCollection = new HashMap<>();
    /**
     * Коллекция собственной персоной
     */
    private TreeSet<Person> collection = new TreeSet<>();
    /**
     * Время последней инициализации
     */
    private LocalDateTime lastInitTime;
    /**
     * Время последнего сохранения
     */
    private LocalDateTime lastSaveTime;
    /**
     * Менеджер сериализации/ десериализации
     */
    private final SerializationManager serializationManager;

    public CollectionManager(SerializationManager serializationManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.serializationManager = serializationManager;
    }

    /**
     * Компаратор по имени
     */
    Comparator<Person>passportIDComparator = Comparator.comparing(Person::getName);

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }
    public void setLastInitTime() {
        this.lastInitTime = LocalDateTime.now();
    }

    public void setLastSaveTime() {
        this.lastSaveTime = LocalDateTime.now();
    }

    public TreeSet<Person> getCollection() {
        return collection;
    }

    /**
     * Поиск объекта по имени
     * @param id имя
     * @return объект
     */
    public Person byId(int id) {
        return fastCollection.get(id);
    }

    /**
     * Содержится ли данный объект в коллекции
     * @param e объект
     * @return да/нет
     */
    public boolean isСontain(Person e) {
        return e != null && byId(e.getID()) != null;
    }

    /**
     * Генерация свободного id путём перебора всех существующих
     * @return id
     */
    public int getFreeId() {
        while (byId(currentId) != null)currentId++;
        return currentId;
    }

    public boolean append(Person a) {
        if (isСontain(a)) return false;
        fastCollection.put(a.getID(), a);
        collection.add(a);
        return true;
    }


    public boolean remove(Person a) {
        if (!isСontain(a)) return false;
        fastCollection.remove(a.getID());
        collection.remove(a);
        return true;
    }

    public boolean remove(int id) {
        var a = byId(id);
        if (a == null) return false;
        fastCollection.remove(id);
        collection.remove(a);
        return true;
    }

    public boolean cleanCollection(){
        fastCollection.clear();
        collection.clear();
        return true;
    }

    /**
     * Возвращает имя файла-хранилища
     * @return имя
     */
    public String getFileName(){
        return this.serializationManager.getFileName();
    }

    /**
     * Сохраняет коллекцию в файл-хранилище
     * @return получилось/нет
     */
    public boolean saveCollection() {
        if(serializationManager.writeCollection(collection)){
            lastSaveTime = LocalDateTime.now();
            return true;
        }else return false;
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var p : collection) {
            info.append(p).append("\n\n");
        }
        return info.toString().trim();
    }

    /**
     * Загружает коллекцию из файла-хранилища в программу
     * @return получилось/нет
     */
    public boolean loadCollection() {
        collection = new TreeSet<Person>(serializationManager.readCollection());
        fastCollection =
                (HashMap<Integer, Person>)
                        collection.stream().collect(Collectors.toMap(Person::getID, person -> person));
        lastInitTime = LocalDateTime.now();
        return !collection.isEmpty();
    }
}
