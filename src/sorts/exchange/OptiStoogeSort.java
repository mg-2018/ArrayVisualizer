package sorts.exchange;

import main.ArrayVisualizer;
import sorts.templates.Sort;

/**
 * Optimized Stooge Sort:
 *
 * Faster, stable Stooge Sort that can run in O(n^2) worst case
 * and O(n) best case for nearly sorted data
 *
 * @author Anonymous0726 - Range flagging optimizations
 * @author aphitorite    - Sorting network optimizations
 * @author EilrahcF      - Key ideas / concepts
 */

final public class OptiStoogeSort extends Sort {
    public OptiStoogeSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        
        this.setSortListName("Opti. Stooge 2");
        this.setRunAllSortsName("Optimized Stooge Sort 2");
        this.setRunSortName("Optistooge Sort 2");
        this.setCategory("Exchange Sorts");
        this.setComparisonBased(true);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }
	
	private boolean compSwap(int[] array, int a, int b) {
		Highlights.markArray(1, a);
		Highlights.markArray(2, b);
		Delays.sleep(0.025);
		if(Reads.compareValues(array[a], array[b]) == 1) {
			Writes.swap(array, a, b, 0.075, false, false);
			return true;
		}
		return false;
	}
	
	private boolean stoogeSort(int[] array, int a, int m, int b, boolean merge) {
		if(a >= m)
			return false;
		if(b-a == 2)
			return this.compSwap(array, a, m);
		
		boolean lChange = false;
		boolean rChange = false;
		
		int a2 = (a+a+b)/3;
		int b2 = (a+b+b+2)/3;
		
		if(m < b2) {
			lChange = this.stoogeSort(array, a, m, b2, merge);
			
			if(merge) {
				rChange = this.stoogeSort(array, Math.max(a+b2-m, a2), b2, b, true);
				if(rChange) this.stoogeSort(array, a+b2-m, a2, 2*a2-a, true);
			}
			else {
				rChange = this.stoogeSort(array, a2, b2, b, false);
				if(rChange) this.stoogeSort(array, a, a2, 2*a2-a, true);
			}
		}
		else {
			rChange = this.stoogeSort(array, a2, m, b, merge);
			if(rChange) this.stoogeSort(array, a, a2, a2+b-m, true);
		}
		return lChange || rChange;
	}

    @Override
    public void runSort(int[] array, int currentLength, int bucketCount) {
		this.stoogeSort(array, 0, 1, currentLength, false);
    }
}