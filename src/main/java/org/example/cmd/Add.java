package org.example.cmd;


import org.example.Ask;
import org.example.CollectionManager;
import org.example.interfaces.Console;
import org.example.model.Person;

/**
 * Класс - команды "add".
 * Команда добавляет в коллекцию новый элемент, последовательно считывая поля с консоли или из файла
 */
public class Add extends Command {
    private Console console;
    private CollectionManager collectionManager;

    /**
     * Конструктор
     * @param console - консоль ввода-вывода
     * @param collectionManager - класс, управляющий коллекцией
     */
    public Add(Console console, CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполнение логики команды
     * @param userCommandArgument - не учитывается для этой команды
     * @param isScript - режим скрипта
     * @return объект класса ответа
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        try {
            if(!isScript)console.println("Введите данные для нового Person: ");
            Person a = Ask.askPerson(console, collectionManager.getFreeId(),null, isScript);
            if (a != null && a.validate()) {
                collectionManager.append(a);
                collectionManager.setLastInitTime();
                return new ExecutionResponse(true, "Новый Person успешно добавлен!");
            } else
                return new ExecutionResponse(false, "Непредвиденная ошибка! Объект не создан");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отмена создания...");
        }
    }


}
