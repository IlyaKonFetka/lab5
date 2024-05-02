package org.example.cmd;

import org.example.CollectionManager;
import org.example.interfaces.Console;
import org.example.model.Person;

/**
 * Класс команды "remove_by_id".
 * Удаляет элемент из коллекции по его id
 */
public class RemoveByID extends Command{
    CollectionManager collectionManager;
    public RemoveByID(CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент коллекции по id");
        this.collectionManager = collectionManager;

    }

    /**
     * Выполняет команду
     * @param userCommandArgument - заданный id
     * @param isScript - была ли запущена команда из скрипта
     * @return объект-ответ
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        if(collectionManager.getCollection().isEmpty()) return new ExecutionResponse("Коллекция пуста");

        int id;
        try{
            id = Integer.parseInt(userCommandArgument);
        }catch (NumberFormatException e){
            return new ExecutionResponse(false,"Неверный формат id, введите целое число не меньше 0");
        }
        Person p = collectionManager.byId(id);
        if (p == null)
            return new ExecutionResponse(false, "Элемента с таким id нет в коллекции");
        if (collectionManager.remove(id))
            return new ExecutionResponse("Объект успешно удалён");
        else
            return new ExecutionResponse(false, "Непредвиденная ошибка, объект не удалён");
    }
}
