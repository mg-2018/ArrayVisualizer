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

more about bucket sort on: https://en.wikipedia.org/wiki/Bucket_sort
 *
 */

final public class GenericBucketSort extends Sort {
	private InsertionSort insertSorter;
	private int BASE = 128;
	
	public GenericBucketSort(ArrayVisualizer arrayVisualizer) {
		super(arrayVisualizer);
		insertSorter = new InsertionSort(this.arrayVisualizer);
		
		this.setSortListName("Generic Bucket");
		this.setRunAllSortsName("Generic Bucket Sort, Base " + this.BASE);
		this.setRunSortName("Generic Bucket Sort");
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
		for(int i = start; i < end; i++)
		{
			if (array[i] > max)
				max = array[i];
		}
		
		return max;
	}
	
	private int getMin(int[] array, int start, int end) {
		int min = array[start];
		for(int i = start; i < end; i++)
		{
			if (array[i] < min)
				min = array[i];
		}
		
		return min;
	}
	
	private void sort(int[] array, int start, int end, int base) {
		int temp;
		int max = getMax(array, start, end);
		int min = getMin(array, start, end);
		int buckets = (max)/base + 1;
		
		int[] aux = new int[end-start+1];
		int[] offset = new int[buckets+1];
		int[] offsetmemory = new int[buckets+1];
		
		for(int i=start; i<end; i++) {
			Writes.write(aux, i-start, array[i], 0.75, false, true);
			Highlights.markArray(1, i);
			// array[i]/base + 1 to preserve first offset count to 0
			offset[array[i]/base + 1 - start]++;
		}
		
		for(int i=0; i<buckets; i++) {
			if (i != 0) offset[i] += offset[i-1];
			if (base != 1) System.out.printf("%d ", offset[i]);
		}
		if (base != 1) System.out.println();
		
		for(int i=0; i<end-start; i++) {
			temp = aux[i]/base;
			Writes.write(array, offset[temp-start]+offsetmemory[temp-start]+start, aux[i], 0.75, true, false);
			// temp - start required: out of bound exception
			offsetmemory[temp-start] += 1;
		}
		
		if(base == 1) return;
		
		for(int i=0; i<buckets; i++) {
			// if statement for handling uneven bucket size (e.g. 2048 items but bucket size is 200)
			if (i == buckets-1) {
				if(offset[i] == end) break;
				else {
					this.sort(array, offset[i], end, 1);
				}
			}
			
			else {
				int newstart = offset[i];
				int newend = offset[i+1];
				
				this.sort(array, newstart, newend, 1);
			}
		}
		
		// insertSorter.customInsertSort(array, 0, end, 5d / NUMBER_OF_BUCKETS, false);
		// insertion sort above was fallback when recursion was not available
	}
	
	@Override
    public void runSort(int[] array, int sortLength, int bucketCount) throws Exception {
        this.BASE = bucketCount;
        this.setRunAllSortsName("Generic Bucket Sort, Base " + this.BASE);
        
        this.sort(array, 0, sortLength, bucketCount);
    }
}