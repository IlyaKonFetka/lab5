package org.example.cmd;

import org.example.*;
import org.example.interfaces.Console;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Класс команды "execute_script".
 * Выполняет последовательность команд из файла, точно так же, как из если бы их вводили через консоль
 */
public class ExecuteScript extends Command{
    private Console console;
    private CommandManager commandManager;
    public ExecuteScript(Console console, CommandManager commandManager) {
        super("execute_script", "выполнить ряд команд из файла");
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @param userCommandArgument - путь к файлу-скрипту. Если пусто, команда попросит ввести его снова
     * @param isScript - была ли запущена команда из скрипта
     * @return объект-ответ
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        String stringPath;
        if (userCommandArgument.isEmpty()) {
            try {
                stringPath = SmartInputParser.parseToString(console,"file-script path", isScript);
            }catch (Ask.AskBreak e){
                return new ExecutionResponse(false,"Отменяем...");
            }
        }else stringPath = userCommandArgument;

        try {
            File file = new File(stringPath);
            console.selectFileScanner(new Scanner(file));
            if(commandManager.addScriptFileName(stringPath))
                new Runner(console, commandManager).interactiveMode();
            else return new ExecutionResponse(false, "Обнаружена рекурсия. Скрипт остановлен");


        } catch (FileNotFoundException e){
            if(!isScript)console.printError("Файл не найден");
            return new ExecutionResponse(false, "Команда не выполнена");
        } finally {
            console.selectConsoleScanner();
            commandManager.deleteLastScriptFileName(stringPath);
        }
        return new ExecutionResponse("Скрипт выполнен");
    }
}
