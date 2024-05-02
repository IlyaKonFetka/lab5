package org.example.cmd;

import org.example.Ask;
import org.example.CollectionManager;
import org.example.CommandManager;
import org.example.SmartInputParser;
import org.example.interfaces.Console;
import org.example.model.Person;

/**
 * Класс команды "add_if_max".
 * Добавляет элемент новый элемент в коллекцию если он превышает наибольший из уже имеющихся
 */
public class AddIfMax extends Command{
    /**
     * Менеджер коллекции
     */
    private CollectionManager collectionManager;
    /**
     * Класс коллекции
     */
    private Console console;

    /**
     * Конструктор
     * @param collectionManager - Менеджер коллекции
     * @param console - Класс коллекции
     */
    public AddIfMax(CollectionManager collectionManager , Console console) {
        super("add_if_max", "добавить элемент если он превышает максимальный элемент в коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполнение логики команды
     * @param userCommandArgument - аргумент команды: имя нового Person. Если окажется пусто - команда автоматически
     * снова попросит ввести имя
     * @param isScript - была ли запущена команда из скрипта
     * @return объект класса-ответа
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        String newPersonName;
        if (userCommandArgument.isEmpty()) {
            if(!isScript)console.print("Введите имя нового Person: ");
            try {
                newPersonName = SmartInputParser.parseToString(console, "name", isScript);
            } catch (Ask.AskBreak e) {
                return new ExecutionResponse(false, "Отмена метода...");
            }
        }else newPersonName = userCommandArgument;
        String maxName = "";
        for (Person p: collectionManager.getCollection()){
            if (p.getName().compareTo(maxName)>0)maxName = p.getName();
        }
        if (newPersonName.compareTo(maxName) > 0) {
            try {
                if(!isScript)console.println("Введите новые данные для этого Person: ");
                Person a = Ask.askPerson(console, collectionManager.getFreeId(), newPersonName, isScript);
                if (a != null && a.validate()) {
                    collectionManager.append(a);
                    collectionManager.setLastInitTime();
                    return new ExecutionResponse("Новый Person успешно добавлен!");
                } else
                    return new ExecutionResponse(false, "Непредвиденная ошибка! Данные не обновлены");
            } catch (Ask.AskBreak e) {
                return new ExecutionResponse(false, "Отмена команды...");
            }
        }else
            return new ExecutionResponse(false, "В коллекции уже есть элементы превышающие данный");
    }
}
