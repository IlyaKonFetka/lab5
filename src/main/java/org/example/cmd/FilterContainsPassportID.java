package org.example.cmd;

import org.example.Ask;
import org.example.CollectionManager;
import org.example.SmartInputParser;
import org.example.interfaces.Console;
import org.example.model.Person;

/**
 * Класс команды "filter_contains_passport_i_d".
 * Выводит все объекты коллекции, если их поле passportID содержит заданную подстроку
 */
public class FilterContainsPassportID extends Command{
    CollectionManager collectionManager;
    Console console;
    public FilterContainsPassportID(CollectionManager collectionManager, Console console) {
        super("filter_contains_passport_i_d", "вывести элементы, значение passportID которых содержит подстроку");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду
     * @param userCommandArgument - заданная подстрока passportID
     * @param isScript - была ли запущена команда из скрипта
     * @return объект-ответ
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        if(collectionManager.getCollection().isEmpty()) return new ExecutionResponse("Коллекция пуста");
        String subStr;
        if (userCommandArgument.isEmpty()) {
            if(!isScript)console.print("Введите подстроку: ");
            try {
                subStr = SmartInputParser.parseToString(console, "substring of passportID", isScript);
            } catch (Ask.AskBreak e) {
                return new ExecutionResponse(false, "Отмена метода...");
            }
        }else subStr = userCommandArgument;
        for (Person p: collectionManager.getCollection()){
            if(p.getPassportID().contains(subStr))console.println(p.toString());
        }
        return new ExecutionResponse("Объекты успешно выведены");
    }
}
