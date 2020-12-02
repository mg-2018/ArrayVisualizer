package sorts.custom;

import main.ArrayVisualizer;
import sorts.templates.Sort;

// this version of shell sort is w0rthy's original implementation
// I think this is shell sort's original concept to be fair

final public class ShellSort2 extends Sort {
	public ShellSort2(ArrayVisualizer arrayVisualizer) {
		super(arrayVisualizer);
        
        this.setSortListName("Shell 2");
        this.setRunAllSortsName("Original Shell Sort's Concept");
        this.setRunSortName("Original Shell Sort's Concept");
        this.setCategory("Insertion Sorts");
        this.setComparisonBased(true);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
	}
	
	@Override
	public void runSort(int[] array, int length, int base) {
		int gap, gapcount = 0;
		int j, k, temp;
		int[] gapseq = {17962, 7983, 3548, 1577, 701, 301, 132, 57, 23, 10, 4, 1, 0};
		// extended ciura gap within 32768, 0 in the end to prevent out of bounds exception
		
		while(true) {
			gap = gapseq[gapcount];
			if(gap > length) {
				gapcount++;
				continue;
			}
			
			else if(gap == 0) break;
			
			else {
				for(int i=gap; i<(2*gap); i++) {
					j = i;
					while(j < length) {
						k = j;
						temp = array[k];
						while(k-gap >= 0) {
							if(Reads.compareValues(array[k-gap], temp) > 0) {
								Writes.write(array, k, array[k-gap], 1, true, false);
								k -= gap;
							}
							
							else break;
						}
						Writes.write(array, k, temp, 1, true, false);
						j += gap;
					}
				}
				gapcount++;
			}
		}
	}
}