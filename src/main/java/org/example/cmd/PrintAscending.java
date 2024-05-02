package org.example.cmd;

import org.example.CollectionManager;
import org.example.interfaces.Console;
import org.example.model.Person;

/**
 * Класс команды "print_ascending".
 * Выводит все элементы коллекции в алфавитном порядке имён
 */
public class PrintAscending extends Command{
    CollectionManager collectionManager;
    Console console;
    public PrintAscending(CollectionManager collectionManager, Console console) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
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
        for (Person p: collectionManager.getCollection()){
            console.println(p.toString());
        }
        return new ExecutionResponse("Коллекция выведена в порядке возрастания");
    }
}
