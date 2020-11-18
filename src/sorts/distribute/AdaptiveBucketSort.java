package sorts.distribute;

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
	
	private int getMin(int[] array, int start, int end) {
		int min = array[start];
		for(int i=start; i<end; i++)
		{
			if(array[i] < min) min = array[i];
		}
		
		return min;
	}
	
	private void MiniCountingSort(int[] array, int start, int end) {
		int i, j=start;
		
		int max = getMax(array, start, end);
		int min = getMin(array, start, end);
		int range = (max-min)+1;
		
		int[] counts = new int[range];
		for(i=start; i<end; i++) {
			Writes.write(counts, array[i]-min, counts[array[i]-min]+1, 0.75, false, true);
			Highlights.markArray(1, i);
		}
		
		for(i=0; i<range; i++) {
			while(counts[i]-- > 0) {
				Writes.write(array, j++, i+min, 0.75, true, false);
				Writes.changeAuxWrites(1);
			}
		}
	}
	
	private void Sort(int[] array, int sortLength, int base) {
		int max = getMax(array, 0, sortLength-1);
		int buckets = (max/base) + 2;
		
		int[] aux = new int[sortLength];
		int[] offset = new int[buckets];
		int[] offsetmemory = new int[buckets];
		
		// Step 1: write entire elements in auxiliary array and count the bucket offset
		for(int i=0; i<sortLength; i++) {
			Writes.write(aux, i, array[i], 0.75, true, true);
			offset[array[i]/base + 1]++;
		}
		
		// Step 2: calculate the bucket offset by adding offset count one by one
		for(int i=1; i<buckets; i++) {
			offset[i] += offset[i-1];
		}
		
		// Step 3: overwrite elements in auxiliary array to each corresponding bucket
		for(int i=0; i<sortLength; i++) {
			int temp = aux[i]/base;
			Writes.write(array, offset[temp]+offsetmemory[temp], aux[i], 0.75, true, false);
			offsetmemory[temp]++;
		}
		
		// Step 4: if base <= 16 run insertion on entire array...
		if(base <= 16)
			ins.customInsertSort(array, 0, sortLength, 0.4, false);
		
		// ..else run 'counting sort' in each bucket
		else {
			for(int i=0; i<buckets-1; i++)
			{
				if(offset[i+1]-offset[i] == 0) continue;
				
				else MiniCountingSort(array, offset[i], offset[i+1]);
			}
		}
	}
	
	@Override
	public void runSort(int[] array, int sortLength, int bucketCount) throws Exception {
		int base = bucketCount;
		this.Sort(array, sortLength, base);
	}
}
