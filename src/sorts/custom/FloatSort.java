package sorts.custom;

import main.ArrayVisualizer;
import sorts.templates.Sort;

// Float Sort by lancewer (logan)
// variable name after original scratch project

final public class FloatSort extends Sort {
	public FloatSort(ArrayVisualizer arrayVisualizer) {
		super(arrayVisualizer);
        
        this.setSortListName("Float");
        this.setRunAllSortsName("Float Sort (by lancewer)");
        this.setRunSortName("Float Sort");
        this.setCategory("Exchange Sorts");
        this.setComparisonBased(true);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(true);
        this.setUnreasonableLimit(16384);
        this.setBogoSort(false);
	}
	
	@Override
	public void runSort(int[] array, int length, int base) {
		int h, i, j;
		boolean done = false;
		while(!done) {
			h = 0;
			done = true;
			for(int a=0; a<length-1; a++) {
				i = h;
				j = h+1;
				while(i >= 0) {
					if(Reads.compareIndices(array, i, j, 1, true) > 0) {
						Writes.swap(array, i, j, 1, true, false);
						i--;
						j--;
						done = false;
					}
					else break;
				}
				if(i > 0) {
					i++;
					j++;
					while(length-1 >= j) {
						if(Reads.compareIndices(array, i, j, 1, true) > 0) {
							Writes.swap(array, i, j, 1, true, false);
							i++;
							j++;
							done = false;
						}
						else break;
					}
				}
				
				h++;
			}
		}
	}
}