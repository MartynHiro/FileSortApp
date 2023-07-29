import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileHandler {
    /* Ограничение количества строк для устойчивости к большим файлам.
     * 200 я выбрал так как память BufferedReader по умолчанию составляет 8192 символа
     * т.е примерно по 41 символу на строку
     */
    public static final int MAX_LINES_IN_CHUNK = 200;
    private static FileHandler instance;
    ExternalMergeSort externalMergeSort;
    List<List<String>> chunksList;
    List<String> chunk;
    List<String> resultList;

    private FileHandler() {
        this.chunksList = new ArrayList<>();
        this.chunk = new ArrayList<>();
        this.resultList = new ArrayList<>();
        this.externalMergeSort = ExternalMergeSort.getInstance();
    }

    //singleton объект
    public static FileHandler getInstance() {
        if (instance == null) {
            synchronized (FileHandler.class) {
                if (instance == null) {
                    instance = new FileHandler();
                }
            }
        }
        return instance;
    }

    public void readAllFiles(final String sortMode, final List<String> inputFiles) {

        for (String file : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    chunk.add(line);

                    if (chunk.size() == MAX_LINES_IN_CHUNK) {
                        sortAndSaveChunk(sortMode, chunksList, chunk);
                    }
                }

                if (!chunk.isEmpty()) {
                    sortAndSaveChunk(sortMode, chunksList, chunk);
                }
            } catch (IOException e) {
                System.out.println("Файл " + file + " не был найден!");
            }
        }
    }

    private void sortAndSaveChunk(final String sortMode, List<List<String>> chunksList, List<String> chunk) {

        List<String> listOfStrings = externalMergeSort.sortList(chunk, sortMode)
                .stream()
                .filter(a -> !a.contains(" "))
                .toList();

        chunksList.add(listOfStrings);
        chunk.clear();
    }

    public void createResultFile(final String sortMode, String fileName) {

        for (List<String> list : chunksList) {
            resultList = externalMergeSort.mergeList(resultList, list, sortMode);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            resultList.forEach(data -> {
                try {
                    writer.write(data);
                    writer.newLine();

                } catch (IOException e) {
                    System.out.println("Невозможно произвести запись данных в файл: " + fileName);
                }
            });

        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + fileName);
        }
    }
}