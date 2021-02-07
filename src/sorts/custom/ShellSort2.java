package sorts.custom;

import main.ArrayVisualizer;
import sorts.templates.Sort;

// this version of shell sort is w0rthy's original implementation

final public class ShellSort2 extends Sort {
	public ShellSort2(ArrayVisualizer arrayVisualizer) {
		super(arrayVisualizer);
        
        this.setSortListName("Shell 2");
        this.setRunAllSortsName("Shell Sort");
        this.setRunSortName("Shell Sort");
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
		int num=0;
		int j, k, temp;
		int[] gap = {17961, 7983, 3548, 1577, 701, 301, 132, 57, 23, 10, 4, 1, 0};
		int gapseq;
		
		while(true) {
			gapseq = gap[num];
			
			if(gapseq > length) {
				num++;
				continue;
			}
			
			else if(gapseq == 0) break;
			
			else {
				for(int i=gapseq; i<(2*gapseq); i++) {
					j = i;
					while(j < length) {
						k = j;
						temp = array[k];
						while(k-gapseq >= 0) {
							if(Reads.compareValues(array[k-gapseq], temp) > 0) {
								Writes.write(array, k, array[k-gapseq], 1, true, false);
								k -= gapseq;
							}
							
							else break;
						}
						Writes.write(array, k, temp, 1, true, false);
						j += gapseq;
					}
				}
				
				num++;
			}
		}
	}
}