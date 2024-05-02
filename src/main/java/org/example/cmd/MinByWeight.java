package org.example.cmd;

import org.example.CollectionManager;
import org.example.interfaces.Console;
import org.example.model.Person;

/**
 * Класс команды "min_by_weight".
 * Выводит объект с минимальным weight из коллекции
 */
public class MinByWeight extends Command{
    CollectionManager collectionManager;
    Console console;
    public MinByWeight(CollectionManager collectionManager, Console console) {
        super("min_by_weight", "вывести элемент с минимальным weight");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполянет команду
     * @param userCommandArgument - заданный weight
     * @param isScript - была ли запущена команда из скрипта
     * @return объект-ответ
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        if(collectionManager.getCollection().isEmpty()) return new ExecutionResponse("Коллекция пуста");

        Long minWeight = Long.MAX_VALUE;
        Person minP = null;
        for(Person p: collectionManager.getCollection()){
            if (p.getWeight() < minWeight){
                minP = p;
                minWeight = p.getWeight();
            }
        }
        assert minP != null;
        console.println(minP.toString());
        return new ExecutionResponse("Person с минимальным weight успешно выведен");
    }
}
