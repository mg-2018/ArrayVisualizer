package sorts.custom;

import main.ArrayVisualizer;

// MSD Radix Merge Sort by _fluffy

import sorts.templates.Sort;

final public class MSDRadixMergeSort extends Sort {
    // private final double DELAY = 1;
    public MSDRadixMergeSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        this.setSortListName("MSD Radix Merge");
        this.setRunAllSortsName("MSD Radix Merge Sort");
        this.setRunSortName("MSD Radix Merge Sort");
        this.setCategory("Distribution Sorts");
        this.setComparisonBased(false);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    private void wrapper(int[] arr, int start, int stop, int d, boolean f) {
        if (stop - start >= 2 && d >= 0) {
            int mid = (stop - start) / 2 + start, i, j;
            this.wrapper(arr, start, mid, d, false);
            this.wrapper(arr, mid, stop, d, false);
            for (i = start; i < mid && !Reads.getBit(arr[i], d); i++);
            for (j = mid; j < stop && !Reads.getBit(arr[j], d); i++, j++)
                if (i != j)
                    Writes.swap(arr, i, j, 1, true, false);
            if (f) {
                this.wrapper(arr, start, i, d - 1, true);
                this.wrapper(arr, i, stop, d - 1, true);
            }
        }
    }

    @Override
    public void runSort(int[] arr, int length, int base) {
        this.wrapper(arr, 0, length,
            Reads.analyzeMaxLog(arr, length, 2, 0, false), true);
    }
}