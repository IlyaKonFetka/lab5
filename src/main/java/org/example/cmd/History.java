package org.example.cmd;

import org.example.CollectionManager;
import org.example.CommandManager;
import org.example.interfaces.Console;

import java.util.List;

import static java.lang.Math.max;
/**
 * Класс команды "history".
 * Выводит список из 8 последних введённых команд
 */
public class History extends Command{
    CommandManager commandManager;
    Console console;
    public History(CommandManager commandManager, Console console) {
        super("history", "вывести последние 8 команд");
        this.commandManager = commandManager;
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
        List<String>arr = commandManager.getCommandHistory();
        if (arr.isEmpty())return new ExecutionResponse("История команд пуста");
        int start = max(arr.size() - 8, 0);
        for(int i = start; i < arr.size(); i++){
            console.println(arr.get(i));
        }
        return new ExecutionResponse("История команд успешно выведена");
    }
}
