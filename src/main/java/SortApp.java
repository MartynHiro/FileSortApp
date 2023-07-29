import exceptions.EmptyFieldException;
import exceptions.IncorrectFileName;
import exceptions.MethodsToCheck;
import exceptions.NotEnoughArguments;

import java.util.ArrayList;
import java.util.List;

public class SortApp {
    public static void main(String[] args) throws EmptyFieldException, IncorrectFileName, NotEnoughArguments {
        /* Проверяем, что передано достаточно аргументов
         * 1) -i/-s тип данных (обязательно)
         * 2) -a/-d сортировка (не обязательно, по умолчанию -a)
         * 3) out.txt файл записи (обязательно)
         * 4) in.txt файл/ы чтения (один обязательно)
         */
        if (args.length < 3) {
            throw new NotEnoughArguments("Недостаточно аргументов");
        }

        String sortMode = "-a";                         //режим сортировки по умолчанию -> по возрастанию
        String dataType = "";                           //тип данных в файлах
        String outputFileName = "";                     //имя файла для записи
        List<String> inputFiles = new ArrayList<>();    //имена файлов для чтения
        FileHandler fileHandler = FileHandler.getInstance();    //обработчик файлов

        for (String arg : args) {
            switch (arg) {
                case "-a", "-d" -> sortMode = arg;

                case "-s", "-i" -> dataType = arg;

                default -> {
                    if (arg.length() >= 5 && outputFileName.isEmpty()) { // o.txt (5 символов, min название файла)
                        outputFileName = arg;

                    } else if (arg.length() >= 5) {
                        inputFiles.add(arg);

                    } else {
                        throw new IncorrectFileName("Некорректное имя файла");
                    }
                }
            }
        }

        try {
            MethodsToCheck.checkDataType(dataType);
            MethodsToCheck.checkOutputFileName(outputFileName);
            MethodsToCheck.checkInputFiles(inputFiles);

        } catch (EmptyFieldException e) {
            throw new EmptyFieldException("Не достаточно входных данных для работы: " + e.getMessage());
        }

        fileHandler.readAllFiles(sortMode, inputFiles);

        fileHandler.createResultFile(sortMode, outputFileName);
    }
}
