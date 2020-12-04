package sorts.custom;

import main.ArrayVisualizer;
import sorts.templates.Sort;

// Grate Sort by lancewer (logan)
// variable name after original scratch project

final public class GrateSort extends Sort {
	public GrateSort(ArrayVisualizer arrayVisualizer) {
		super(arrayVisualizer);
        
        this.setSortListName("Grate");
        this.setRunAllSortsName("Grate Sort (by lancewer)");
        this.setRunSortName("Grate Sort");
        this.setCategory("Exchange Sorts");
        this.setComparisonBased(true);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(true);
        this.setUnreasonableLimit(512);
        this.setBogoSort(false);
	}
	
	@Override
	public void runSort(int[] array, int length, int base) {
		int m=0, i=0;
		int j = 0;
		while(length-2 >= m) {
			i = -1;
			for(int a=0; a<length-1; a++) {
				i++;
				j = length-1;
				m++;
				while(j >= (i+1)) {
					if(Reads.compareIndices(array, j, i, 0.05, true) >= 0)
					j--;
					
					else break;
				}
				
				if(Reads.compareIndices(array, j, i, 0.05, true) < 0) {
					Writes.swap(array, j, i, 0.05, true, false);
					j = i;
					m = 0;
				}
			}
		}
	}
}