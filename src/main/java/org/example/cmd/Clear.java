package org.example.cmd;

import org.example.CollectionManager;
import org.example.interfaces.Console;

/**
 * Класс команды "clear".
 * Очищает контейнер коллекции
 */
public class Clear extends Command {
    private CollectionManager collectionManager;

    /**
     * Конструктор
     * @param collectionManager - менеджер коллекции
     */
    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @param userCommandArgument - не учитывается
     * @param isScript - была ли запущена команда из скрипта
     * @return объект класса-ответа
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        collectionManager.cleanCollection();
        return new ExecutionResponse("Коллекция успешно очищена!");
    }
}
