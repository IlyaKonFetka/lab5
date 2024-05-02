package org.example.cmd;

/**
 * Класс команды "exit".
 * Прекращает выполнение программы
 */
public class Exit extends Command{
    public Exit() {
        super("exit","выйти из программы");
    }

    /**
     * Выполняет команду
     * @param userCommandArgument - аргумент команды (в консоли пишется сразу после имени команды)
     * @param isScript - была ли запущена команда из скрипта
     * @return объект-ответ
     */
    @Override
    public ExecutionResponse apply(String userCommandArgument, boolean isScript) {
        return new ExecutionResponse("exit");
    }
}
