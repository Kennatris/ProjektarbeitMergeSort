import java.util.ArrayList;

public class Sorter {

    public void sort(ArrayList<AggregatedRow> rows) {
        if (rows == null || rows.size() < 2) {
            return;
        }
        mergeSort(rows, 0, rows.size() - 1);
    }

    private void mergeSort(ArrayList<AggregatedRow> rows, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            mergeSort(rows, left, middle);
            mergeSort(rows, middle + 1, right);

            merge(rows, left, middle, right);
        }
    }

    private void merge(ArrayList<AggregatedRow> rows, int left, int middle, int right) {
        int sizeLeft = middle - left + 1;
        int sizeRight = right - middle;

        AggregatedRow[] leftArray = new AggregatedRow[sizeLeft];
        AggregatedRow[] rightArray = new AggregatedRow[sizeRight];

        for (int i = 0; i < sizeLeft; i++) {
            leftArray[i] = rows.get(left + i);
        }
        for (int j = 0; j < sizeRight; j++) {
            rightArray[j] = rows.get(middle + 1 + j);
        }

        int i = 0, j = 0;
        int k = left;

        while (i < sizeLeft && j < sizeRight) {
            if (leftArray[i].range >= rightArray[j].range) { // DESCENDING
                rows.set(k, leftArray[i]);
                i++;
            } else {
                rows.set(k, rightArray[j]);
                j++;
            }
            k++;
        }

        while (i < sizeLeft) {
            rows.set(k, leftArray[i]);
            i++;
            k++;
        }

        while (j < sizeRight) {
            rows.set(k, rightArray[j]);
            j++;
            k++;
        }
    }
}
