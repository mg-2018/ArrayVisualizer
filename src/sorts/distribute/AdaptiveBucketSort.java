package sorts.distribute;

import java.util.Arrays;
import main.ArrayVisualizer;
import sorts.templates.Sort;
import sorts.insert.InsertionSort;

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

final public class AdaptiveBucketSort extends Sort {
	private InsertionSort ins;
	
	public AdaptiveBucketSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        ins = new InsertionSort(this.arrayVisualizer);
        
        this.setSortListName("Adaptive Bucket");
        this.setRunAllSortsName("Adaptive Bucket Sort");
        this.setRunSortName("Adaptive Bucket Sort");
        this.setCategory("Distribution Sorts");
        this.setComparisonBased(false);
        this.setBucketSort(true);
        this.setRadixSort(true);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }
	
	private int getMax(int[] array, int start, int end) {
		int max = array[start];
		for(int i=start; i<end; i++)
		{
			if(array[i] > max) max = array[i];
		}
		
		return max;
	}
	
	private void Sort(int[] array, int sortLength, int base) {
		int max = getMax(array, 0, sortLength-1);
		int buckets = (max/base) + 2;
		
		int[] aux = new int[sortLength];
		int[] offset = new int[buckets];
		int[] offsetmemory = new int[buckets];
		
		for(int i=0; i<sortLength; i++) {
			Writes.write(aux, i, array[i], 0.75, true, true);
			offset[array[i]/base + 1]++;
		}
		
		for(int i=0; i<buckets; i++) {
			if(i != 0) offset[i] += offset[i-1];
			System.out.printf("%d ", offset[i]);
		}
		
		for(int i=0; i<sortLength; i++) {
			int temp = aux[i]/base;
			Writes.write(array, offset[temp]+offsetmemory[temp], aux[i], 0.75, true, false);
			offsetmemory[temp]++;
		}
		
		if(base <= 16)
			ins.customInsertSort(array, 0, sortLength, 0.4, false);
	}
	
	@Override
	public void runSort(int[] array, int sortLength, int bucketCount) throws Exception {
		int base = bucketCount;
		
		this.Sort(array, sortLength, base);
	}
}
