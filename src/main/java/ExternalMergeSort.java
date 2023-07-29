import java.util.ArrayList;
import java.util.List;

public class ExternalMergeSort {
    private static ExternalMergeSort instance;

    private ExternalMergeSort() {
    }

    //singleton объект
    public static ExternalMergeSort getInstance() {
        if (instance == null) {
            synchronized (ExternalMergeSort.class) {
                if (instance == null) {
                    instance = new ExternalMergeSort();
                }
            }
        }
        return instance;
    }

    //Сортировка слиянием generic
    public <T extends Comparable<T>> List<T> sortList(List<T> list, final String sortMode) {

        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<T> left = new ArrayList<>(list.subList(0, mid));
        List<T> right = new ArrayList<>(list.subList(mid, list.size()));

        left = sortList(left, sortMode);
        right = sortList(right, sortMode);

        return mergeList(left, right, sortMode);
    }

    //метод слияния сортировки
    public <T extends Comparable<T>> List<T> mergeList(List<T> left, List<T> right, final String sortMode) {

        boolean isAscending = sortMode.equals("-a");

        List<T> resultList = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {

            T leftElement = left.get(leftIndex);
            T rightElement = right.get(rightIndex);

            boolean shouldAddLeftElement = isAscending
                    ? (leftElement.compareTo(rightElement) <= 0)
                    : (leftElement.compareTo(rightElement) >= 0);

            if (shouldAddLeftElement) {
                resultList.add(leftElement);
                leftIndex++;
            } else {
                resultList.add(rightElement);
                rightIndex++;
            }
        }

        //когда один из массивов уже кончился, то заполняем результирующий из оставшегося
        while (leftIndex < left.size()) {
            resultList.add(left.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < right.size()) {
            resultList.add(right.get(rightIndex));
            rightIndex++;
        }
        return resultList;
    }
}