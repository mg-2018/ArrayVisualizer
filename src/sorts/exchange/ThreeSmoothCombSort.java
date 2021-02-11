package sorts.exchange;

import main.ArrayVisualizer;
import sorts.templates.Sort;

// In wikipedia, setting the gap sequence of combsort to 3-smooth numbers makes combsort itself a sorting network
// Thus this sort's time complexity is O(n log^2 n)

final public class ThreeSmoothCombSort extends Sort {
	public ThreeSmoothCombSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        
        this.setSortListName("3-smooth Comb");
        this.setRunAllSortsName("3-smooth Comb Sort");
        this.setRunSortName("3-smooth Comb Sort");
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
		int[] gap = {31104, 27648, 26244, 24576, 23328, 20736, 19683, 18432, 17496, 16384, 15552, 
				     13824, 13122, 12288, 11664, 10368, 9216, 8748, 8192, 7776, 6912, 6561, 6144, 5832,
				     5184, 4608, 4374, 4096, 3888, 3456, 3072, 2916, 2592, 2304, 2187, 2048,
				     1944, 1728, 1536, 1458, 1296, 1152, 1024, 972, 864, 768, 729, 648, 576,
				     512, 486, 432, 384, 324, 288, 256, 243, 216, 192, 162, 144, 128, 108, 96, 81,
				     72, 64, 54, 48, 36, 32, 27, 24, 18, 16, 12, 9, 8, 6, 4, 3, 2, 1, 0};
		
		int i = 0;
		
		while(gap[i] > 0) {
			if(gap[i] >= length) {
				i++;
				continue;
			}
			
			else {
				for(int j=0; j+gap[i] < length; j++) {
					if(Reads.compareIndices(array, j, j+gap[i], 0.25, true) > 0)
					{
						if(gap[i] == 1) Writes.swap(array, j, j+gap[i], 2, true, false);
						
						else Writes.swap(array, j, j+gap[i], 0.75, true, false);
					}
				}
				
				i++;
			}
		}
	}
}
