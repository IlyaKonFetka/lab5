package org.example.cmd;

import org.example.CollectionManager;
import org.example.interfaces.Console;
import org.example.model.Person;

/**
 * Класс команды "show".
 * Выводит все элементы коллекции в порядке их id
 */
public class Show extends Command{
    CollectionManager collectionManager;
    Console console;
    public Show(CollectionManager collectionManager, Console console) {
        super("show", "вывести элементы коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду
     * @param userCommandArgument - не учитывается
     * @param isScript - была ли запущена команда из скрипта
     * @return объект-ответ
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        if(collectionManager.getCollection().isEmpty()) return new ExecutionResponse("Коллекция пуста");
        for(Person p: collectionManager.getCollection())
            console.println(p.toString());
        return new ExecutionResponse("Коллекция успешно выведена");
    }
}
