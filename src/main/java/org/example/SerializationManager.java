package org.example;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.example.interfaces.Console;
import org.example.model.Person;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * Класс отвечающий за запись коллекции в файл-хранилище и считывание из него
 */
public class SerializationManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    /**
     * Имя файла-хранилища
     */
    private final String fileName;
    /**
     * Объект консоли
     */
    private final Console console;

    public SerializationManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    /**
     * Запись коллекции в файл-хранилище
     * @param collection - коллекция
     * @return получилось/нет
     */
    public boolean writeCollection(Collection<Person> collection) {
        try (PrintWriter collectionPrintWriter = new PrintWriter(fileName)) {
            collectionPrintWriter.println(gson.toJson(collection));
            return true;
        } catch (IOException exception) {
            console.printWarning("Загрузочный файл не может быть открыт!");
            return false;
        }
    }

    /**
     * Имя файла-хранилища
     * @return имя
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Считывание коллекции из файла-хранилища
     * @return получилось/нет
     */
    public Collection<Person> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new FileReader(fileName)) {
                var collectionType = new TypeToken<Collection<Person>>() {}.getType();
                var reader = new BufferedReader(fileReader);

                var jsonString = new StringBuilder();

                String line;
                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        jsonString.append(line);
                    }
                }
                if (jsonString.isEmpty()) {
                    jsonString = new StringBuilder("[]");
                }

                return gson.fromJson(jsonString.toString(), collectionType);

            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден! Сохранение в файл невозможно");
            } catch (NoSuchElementException exception) {
                console.printWarning("Загрузочный файл пуст!");
            } catch (JsonParseException exception) {
                console.printWarning("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException | IOException exception) {
                console.printWarning("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printWarning("Аргумент командной строки с загрузочным файлом не найден!");
        }
        return new PriorityQueue<>();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Сериализатор для даты создания (класса LocalDataTime)
     */
    static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime date, Type typeOfSrc,
                                     JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // "yyyy-mm-dd"
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type type,
                                         JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
