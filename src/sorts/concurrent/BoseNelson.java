package sorts.concurrent;

import main.ArrayVisualizer;
import sorts.templates.Sort;

final public class BoseNelson extends Sort {
	public BoseNelson(ArrayVisualizer arrayVisualizer)
	{
		super(arrayVisualizer);
		
		this.setSortListName("Bose-Nelson");
        this.setRunAllSortsName("Bose-Nelson Sorting Network");
        this.setRunSortName("Bose-Nelson Sorting Network");
        this.setCategory("Concurrent Sorts");
        this.setComparisonBased(true);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
	}
	
	private void compareSwap(int[] array, int start, int end, double sleep) {
	    if (Reads.compareValues(array[start], array[end]) == 1) {
	        Writes.swap(array, start, end, sleep, true, false);
	    }
	    Highlights.markArray(1, start);
	    Highlights.markArray(2, end);
	    Delays.sleep(sleep);
	}

	private void boseNelson(int[] array, int start, int length, double sleep) {
	    if (length > 1) {
	        int mid = length / 2;
	        boseNelson(array, start, mid, sleep);
	        boseNelson(array, start + mid, length - mid, sleep);
	        boseNelsonMerge(array, start, mid, start + mid, length - mid, sleep);
	    }
	}

	private void boseNelsonMerge(int[] array, int start1, int len1, int start2, int len2, double sleep) {
	    if (len1 == 1 && len2 == 1) {
	        compareSwap(array, start1, start2, sleep);
	    } else if (len1 == 1 && len2 == 2) {
	        compareSwap(array, start1, start2 + 1, sleep);
	        compareSwap(array, start1, start2, sleep);
	    } else if (len1 == 2 && len2 == 1) {
	        compareSwap(array, start1, start2, sleep);
	        compareSwap(array, start1 + 1, start2, sleep);
	    } else {
	        int mid1 = len1 / 2;
	        int mid2 = len1 % 2 == 1 ? len2 / 2 : (len2 + 1) / 2;
	        boseNelsonMerge(array, start1, mid1, start2, mid2, sleep);
	        boseNelsonMerge(array, start1 + mid1, len1 - mid1, start2 + mid2, len2 - mid2, sleep);
	        boseNelsonMerge(array, start1 + mid1, len1 - mid1, start2, mid2, sleep);
	    }
	}
	
	public void runSort(int[] array, int length, int base) {
		boseNelson(array, 0, length, 0.2);
	}
}
