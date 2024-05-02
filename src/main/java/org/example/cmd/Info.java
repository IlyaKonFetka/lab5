package org.example.cmd;

import org.example.CollectionManager;
import org.example.interfaces.Console;
import org.example.model.Person;

/**
 * Класс команды "info".
 * Выводит текущую информацию о коллекции: даты последних обновления и инициализации, кол-во элементов и т.д.
 */
public class Info extends Command{
    CollectionManager collectionManager;
    Console console;
    public Info(CollectionManager collectionManager, Console console) {
        super("info", "вывести текущую информацию о коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду
     * @param userCommandArgument - не уитывается
     * @param isScript - была ли запущена команда из скрипта
     * @return объект-ответ
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        console.println("информация о коллекции");
        console.println("_____________________________________________________________");
        console.println("тип коллекции: " + collectionManager.getCollection().getClass());
        console.println("количество элементов: " + collectionManager.getCollection().size());
        console.println("время последней инициализации: " + collectionManager.getLastInitTime().format(Person.timeFormatter));
        console.println("время последнего сохранения: " + collectionManager.getLastSaveTime().format(Person.timeFormatter));
        console.println("адрес файла-хранилища: " + collectionManager.getFileName());
        return new ExecutionResponse(true,"информация выведена");
    }
}
