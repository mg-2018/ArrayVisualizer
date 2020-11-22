package sorts.custom;

import main.ArrayVisualizer;
import sorts.templates.Sort;

/*
 * 
MIT License

Copyright (c) 2020 mg-2018

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 *
 */

final public class LessPancakeSort extends Sort {
	public LessPancakeSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        
        this.setSortListName("Less Pancake");
        this.setRunAllSortsName("Less Pancake Sort");
        this.setRunSortName("Less Pancake Sort");
        this.setCategory("Selection Sorts");
        this.setComparisonBased(true);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }
	
	private boolean isSorted(int[] array, int length) {
		for(int i=0; i<length; i++) {
			Reads.setComparisons(Reads.getComparisons() + 1);
			if(array[i] > array[i+1]) return false;
		}
		
		return true;
	}
	
	@Override
	public void runSort(int[] array, int sortLength, int bucketCount) throws Exception {
		int maxindex;
		
		for(int i=sortLength; i>0; i--) {
			maxindex = 0;
			for(int j=1; j<i; j++) {
				if(Reads.compareValues(array[maxindex], array[j]) <= 0) maxindex = j;
				Highlights.markArray(1, maxindex);
				Highlights.markArray(2, j);
				Delays.sleep(0.025);
			}
			
			if(isSorted(array, i)) break;
			else if(maxindex != i) Writes.reversal(array, maxindex, i-1, 0.05, true, false);
		}
	}
}
