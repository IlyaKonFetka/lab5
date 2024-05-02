package org.example.cmd;

import org.example.CommandManager;
import org.example.interfaces.Console;

import javax.swing.text.html.parser.Entity;
import java.util.Map;

/**
 * Класс команды "help".
 * Выводит список всех доступных команд с краткой информацией о каждой
 */
public class Help extends Command{
    private CommandManager commandManager;
    private Console console;
    public Help(CommandManager commandManager, Console console) {
        super("help", "вывести информацию о доступных командах");
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
        if (commandManager.getCommands().isEmpty())
            return new ExecutionResponse(true, "Список доступных команд пуст");
        for (Map.Entry<String, Command> entry : commandManager.getCommands().entrySet() ){
            if(!isScript)console.println(entry.getValue().getName() + " — " + entry.getValue().getDescription());
        }
        return new ExecutionResponse(true, "Список доступных команд успешно выведен");
    }
}
