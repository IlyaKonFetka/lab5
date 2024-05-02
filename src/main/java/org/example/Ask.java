package org.example;

import org.example.interfaces.Console;
import org.example.model.Color;
import org.example.model.Coordinates;
import org.example.model.Location;
import org.example.model.Person;

import java.util.NoSuchElementException;

/**
 * Класс реализующий запросы в консоль для инициализации различных сложных объектов, а также для обработки
 * некорректного ввода пользователя и различных вариантов ввода
 */
public class Ask{
    /**
     * Исключение, выбрасываемое в случае завершения команды путём ввода в консоль exit
     */
    public static class AskBreak extends Exception{}

    /**
     * Запрос объекта типа Person
     * @param console - объект консоли
     * @param id - новый id
     * @param nameOrNull - имя нового Person (в случае если null, имя будет запрошено у пользователя)
     * @param isRec - запущено из скрипта
     * @return новый Person
     * @throws AskBreak
     */
    public static Person askPerson(Console console, int id, String nameOrNull, boolean isRec)throws AskBreak{
        try {
            String name = nameOrNull==null?SmartInputParser.parseToString(console, "name", isRec):nameOrNull;
            var coordinates = askCoordinates(console, isRec);
            var location = askLocation(console, isRec);
            Long height = SmartInputParser.parseToLong(console, "height", true, isRec);

            Long weight = SmartInputParser.parseToLong(console, "weight", true, isRec);

            String passportID = SmartInputParser.parseToString(console, "passport ID", 4, isRec);
            Color hairColor;
            while (true){
                if(!isRec)console.print("hair color (green, blue, yellow): ");
                var line = console.readln().trim();
                if (!line.isEmpty()) {
                    switch (line) {
                        case "exit" -> throw new AskBreak();
                        case "null" -> hairColor = null;
                        case "green", "1" -> hairColor = Color.GREEN;
                        case "blue", "2" -> hairColor = Color.BLUE;
                        case "yellow", "3" -> hairColor = Color.YELLOW;
                        default -> {
                            if(!isRec)console.printWarning("Введите один из цветов или соответсвующий ему номер (1-3)");
                            continue;
                        }
                    }
                    break;
                } else if(!isRec)console.printWarning("Введите непустое значение");
            }
            return new Person(id, name, coordinates, location, height,weight,passportID, hairColor);
        }catch (NoSuchElementException | IllegalStateException e){
            if(!isRec)console.printError("Ошибка чтения!");
            return null;
        }
    }

    /**
     * Запрос объекта типа Coordinates
     * @param console - объект консоли
     * @param isRec - из скрипта?
     * @return новый Coordinates
     * @throws AskBreak
     */
    public static Coordinates askCoordinates(Console console, boolean isRec)throws AskBreak{
        try{
            int x;
            while (true){
                if(!isRec)console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (!line.isEmpty()) {


                    if(line.equals("exit")) throw new AskBreak();
                    else if (line.equals("null")) {
                        if(!isRec)console.printWarning("Не может быть null");
                    }
                    else try {
                        x = Integer.parseInt(line);
                        break;
                    }
                    catch(NumberFormatException e) {
                        if(!isRec){
                            console.printWarning("Число неверного формата");
                        };
                    }
                } else {
                    if(!isRec)console.printWarning("Введите непустое значение");
                }
            }
            float y;
            while (true) {
                if(!isRec)console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (!line.isEmpty()) {
                    if (line.equals("exit")) throw new AskBreak();
                    else if (line.equals("null")) {
                        if(!isRec)console.printWarning("Не может быть null");
                    }
                    else try {
                        y = Float.parseFloat(line);
                        break;
                    }
                    catch(NumberFormatException e) {
                        if(!isRec)console.printWarning("Число неверного формата");
                    }
                }else if(!isRec)console.printWarning("Введите непустое значение");
            }
            return new Coordinates(x,y);
        }catch (NoSuchElementException | IllegalStateException e) {
            if(!isRec)console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * Запрос объекта типа Location
     * @param console - объект консоли
     * @param isRec - из скрипта?
     * @return новый Location
     * @throws AskBreak
     */
    public static Location askLocation(Console console, boolean isRec)throws AskBreak{
        try{
            while (true) {
                if(!isRec)console.print("Создать Location? (yes/no) ");
                var line = console.readln().trim();
                if (!line.isEmpty()) {
                    if (line.equals("exit")) throw new AskBreak();
                    else if (line.equals("no")) return null;
                    else if (!line.equals("yes")) {
                        if(!isRec)console.printWarning("Введите \"yes\" или \"no\"");
                    }
                    else break;
                } else {
                    if(!isRec)console.printWarning("Введите непустое значение");
                }
            }
            float x;
            while (true){
                if(!isRec)console.print("location.x: ");
                var line = console.readln().trim();
                if (!line.isEmpty()) {
                    if(line.equals("exit")) throw new AskBreak();
                    else if (line.equals("null")) {
                        if(!isRec)console.printWarning("Не может быть null");
                    }
                    else try {
                        x = Float.parseFloat(line);
                        break;
                    }
                    catch(NumberFormatException e) {
                        if(!isRec)console.printWarning("Число неверного формата");
                    }
                } else {
                    if(!isRec)console.printWarning("Введите непустое значение");
                }
            }
            Integer y;
            while (true) {
                if(!isRec)console.print("location.y: ");
                var line = console.readln().trim();
                if (!line.isEmpty()){
                    if (line.equals("exit")) throw new AskBreak();
                    else if (line.equals("null")) {
                        if(!isRec)console.printWarning("Не может быть null");
                    }
                    else try {
                        y = Integer.valueOf(line);
                        break;
                    }
                    catch(NumberFormatException e) {
                        if(!isRec)console.printWarning("Число неверного формата");
                    }
                } else {
                    if(!isRec)console.printWarning("Введите непустое значение");
                }
            }
            String name = SmartInputParser.parseToString(console,"location.name", isRec);
            return new Location(x, y, name);
        }catch (NoSuchElementException | IllegalStateException e) {
            if(!isRec)console.printError("Ошибка чтения");
            return null;
        }
    }
}