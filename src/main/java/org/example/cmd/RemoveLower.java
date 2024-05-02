package org.example.cmd;

import org.example.Ask;
import org.example.CollectionManager;
import org.example.SmartInputParser;
import org.example.interfaces.Console;
import org.example.model.Person;

/**
 * Класс команды "remove_lover".
 * Удаляет все элементы перед заданным в алфавитном порядке имён
 */
public class RemoveLower extends Command{
    CollectionManager collectionManager;
    Console console;
    public RemoveLower(CollectionManager collectionManager, Console console) {
        super("remove_lover", "удалить все элементы меньше данного");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду
     * @param userCommandArgument - заданное имя (даже если такого нет)
     * @param isScript - была ли запущена команда из скрипта
     * @return объект-ответ
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        if(collectionManager.getCollection().isEmpty()) return new ExecutionResponse("Коллекция пуста");

        String newPersonName;
        if (userCommandArgument.isEmpty()) {
            if(!isScript)console.print("Введите имя Person, элементы меньше которого хотите удалить: ");
            try {
                newPersonName = SmartInputParser.parseToString(console, "name", isScript);
            } catch (Ask.AskBreak e) {
                return new ExecutionResponse(false, "Отмена метода...");
            }
        }else newPersonName = userCommandArgument;
        for(Person p: collectionManager.getCollection()){
            if(p.getName().compareTo(newPersonName) < 0 )
                collectionManager.remove(p);
        }
        return new ExecutionResponse("Все элементы успешно удалены");
    }
}
