package sorts.custom;

import main.ArrayVisualizer;
import sorts.templates.Sort;

// Grate Sort by lancewer (logan aka luna)
// variable name after original scratch project

final public class RendezvousSort extends Sort {
	public RendezvousSort(ArrayVisualizer arrayVisualizer) {
		super(arrayVisualizer);
        
        this.setSortListName("Rendezvous");
        this.setRunAllSortsName("Rendezvous Sort (by lancewer)");
        this.setRunSortName("Rendezvous Sort");
        this.setCategory("Exchange Sorts");
        this.setComparisonBased(true);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
	}
	
	@Override
	public void runSort(int[] array, int length, int base) {
		int i, j;           // main iterator variables
		int ticker, f, gap; // sub utility variables
		int len = length;
		while(len >= 1) {
			i = 0;
			j = len;
			while(length-1 >= j) {
				ticker = i;
				f = 0;
				while(i >= 0) {
					if(Reads.compareValues(array[j], array[i]) < 0) {
						Writes.swap(array, j, i, 1, true, false);
						f = 1;
						gap = 1 + (j-i);
						i -= gap;
						j -= gap;
					}
					
					else break;
				}
				
				i++; j++;
				if(f == 1) {
					len *= 4;
					i = 0;
					j = len;
				}
			}
			
			len /= 8;
		}
		i = 0;
		j = 1;
		while(length-1 >= j) {
			ticker = i;
			while(i >= 0) {
				if(Reads.compareValues(array[j], array[i]) < 0) {
					Writes.swap(array, j, i, 1, true, false);
					i--; j--;
				}
				
				else break;
			}
			
			i = ticker;
			j = ticker+1;
			i++; j++;
		}
	}
}