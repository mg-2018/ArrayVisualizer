package sorts.distribute;

import java.util.Arrays;

import main.ArrayVisualizer;
import sorts.templates.Sort;

/*
 * 
MIT License

Copyright (c) 2019 w0rthy

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

final public class CountingSort extends Sort {
	public CountingSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        
        this.setSortListName("Counting");
        this.setRunAllSortsName("Counting Sort");
        this.setRunSortName("Counting Sort");
        this.setCategory("Distribution Sorts");
        this.setComparisonBased(false);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    @Override
    public void runSort(int[] array, int sortLength, int bucketCount) throws Exception {
        int min = array[0];
        int max = array[0];
        int finalpass = 0;
        
        arrayVisualizer.toggleAnalysis(true);
        for(int i=0; i<sortLength; i++) {
        	if(min > array[i]) min = array[i];
        	if(max < array[i]) max = array[i];
        	Highlights.markArray(1, i);
        	Delays.sleep(1);
        }
        arrayVisualizer.toggleAnalysis(false);
        
        int[] counts = new int[max-min+1];
        
        for(int i=0; i<sortLength; i++)
        {
        	Writes.write(counts, array[i]-min, counts[array[i]-min]+1, 1, false, true);
        	Highlights.markArray(1, i);
        }
        
        for(int i=0; i<max-min+1; i++)
        {
        	while(counts[i] != 0)
        	{
        		Writes.write(array, finalpass++, i+min, 1, true, false);
        		counts[i]--;
        		Writes.changeAuxWrites(1);
        	}
        }
    }
}
