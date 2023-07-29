package exceptions;

import java.util.List;

public class MethodsToCheck {
    public static void checkDataType(String dataType) throws EmptyFieldException {
        if (dataType.isEmpty()) {
            throw new EmptyFieldException("Не указан тип данных");
        }
    }

    public static void checkOutputFileName(String outputFileName) throws EmptyFieldException {
        if (outputFileName.isEmpty()) {
            throw new EmptyFieldException("Не указано имя файла для записи");
        }
    }

    public static void checkInputFiles(List<String> inputFiles) throws EmptyFieldException {
        if (inputFiles.isEmpty()) {
            throw new EmptyFieldException("Не указано имя файла для чтения");
        }
    }
}