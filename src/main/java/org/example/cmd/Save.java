package org.example.cmd;

import org.example.CollectionManager;

/**
 * Класс команды "save".
 * Сохраняет коллекцию из оперативной памяти в файл-хранилище
 */
public class Save extends Command{
    CollectionManager collectionManager;
    public Save(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @param userCommandArgument - не учитывается
     * @param isScript - была ли запущена команда из скрипта
     * @return объект-ответ
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        if (collectionManager.saveCollection()) {
            collectionManager.setLastSaveTime();
            return new ExecutionResponse("Коллекция успешно сохранена в файл");
        }
        else return new ExecutionResponse(false,"Файл не сохранён");
    }
}
