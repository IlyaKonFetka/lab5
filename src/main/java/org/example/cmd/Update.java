package org.example.cmd;

import org.example.Ask;
import org.example.CollectionManager;
import org.example.SmartInputParser;
import org.example.interfaces.Console;
import org.example.model.Person;

/**
 * Класс команды "update".
 * Обновляет характеристики некоторого объекта из коллекции по его id
 */
public class Update extends Command{
    CollectionManager collectionManager;
    Console console;
    public Update(CollectionManager collectionManager, Console console) {
        super("update", "обновить элемент коллекции по id");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду
     * @param userCommandArgument - id изменяемого объекта
     * @param isScript - была ли запущена команда из скрипта
     * @return объект-ответ
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        long id;
        try{
            id = Integer.parseInt(userCommandArgument);
        }catch (NumberFormatException e){
            if(!isScript)console.printWarning("Неверный формат id");
            try {
                id = SmartInputParser.parseToLong(console,"id", true, isScript);
            } catch (Ask.AskBreak ex) {
                return new ExecutionResponse(false, "Отмена команды...");
            }
        }
        Person p = collectionManager.byId((int) id);
        if (p == null) {
            return new ExecutionResponse(false, "Элемента с таким id нет в коллекции");
        }
        try {
            if(!isScript)console.println("Введите новые данные для этого Person: ");
            Person a = Ask.askPerson(console, (int) id, null, isScript);
            if (a != null && a.validate()) {
                p = a;
                collectionManager.setLastInitTime();
                return new ExecutionResponse("Данные успешно обновлены!");
            } else
                return new ExecutionResponse(false, "Непредвиденная ошибка! Данные не обновлены");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена команды...");
        }
    }
}
