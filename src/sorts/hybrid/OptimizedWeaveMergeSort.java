package sorts.hybrid;

import main.ArrayVisualizer;
import sorts.templates.Sort;

/*
 * 
MIT License

Copyright (c) 2020 aphitorite

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

final public class OptimizedWeaveMergeSort extends Sort {
    public OptimizedWeaveMergeSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        
        this.setSortListName("Opti. Weave Merge");
        this.setRunAllSortsName("Optimized Weave Merge Sort");
        this.setRunSortName("Optimized Weave Mergesort");
        this.setCategory("Hybrid Sorts");
        this.setComparisonBased(true);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }
	
	private void insertTo(int[] array, int a, int b) {
		int temp = array[a];
		while(a > b) Writes.write(array, a, array[(a--)-1], 0.25, true, false);
		Writes.write(array, b, temp, 0.25, true, false);
	}
	
	private void multiSwap(int[] array, int a, int b, int len) {
		for(int i = 0; i < len; i++)
			Writes.swap(array, a+i, b+i, 1, true, false);
	}
	
    private void rotate(int[] array, int a, int m, int b) {
        int l = m-a, r = b-m;
		
        while(l > 0 && r > 0) {
			if(r < l) {
				this.multiSwap(array, m-r, m, r);
				b -= r;
				m -= r;
				l -= r;
            }
            else {
				this.multiSwap(array, a, m, l);
				a += l;
				m += l;
				r -= l;
            }
        }
    }
    
	//special thanks to @Piotr Grochowski
	private void bitReversal(int[] array, int a, int b) {
		int len = b-a,
			log = (int)(Math.log(len)/Math.log(2));
		
		for(int i = 0; i < len; i++) {
			int j = 0;
			int k = i;
			
			for(int l = log; l > 0; l--){
				j *= 2;
				j += k % 2;
				k /= 2;
			}
			
			if(j > i && j < len)
				Writes.swap(array, a+i, a+j, 1, true, false);
		}
	}
	
	private void weaveInsert(int[] array, int a, int b) {
		int i = a, j = i+1;
		
		while(j < b) {
			Highlights.markArray(2, i);
			Highlights.markArray(3, j);
			while(i < j && Reads.compareIndices(array, i, j, 0, false) <= 0) i++;
			
			if(i == j) j++;
			else {
				this.insertTo(array, j, i++);
				j += 2;
			}
		}
		Highlights.clearMark(3);
	}
	
    private void weaveMerge(int[] array, int a, int b) {
		for(int e = b, f; e-a > 2; e = f) {
			int m = (a+e)/2,
				p = 1 << (int)(Math.log(m-a)/Math.log(2));
			
			this.rotate(array, m-p, m, e-p);
			m = e-p;
			f = m-p;
			
			this.bitReversal(array, f, m);
			this.bitReversal(array, m, e);
			this.bitReversal(array, f, e);
		}
    	Highlights.clearMark(2);
    	this.weaveInsert(array, a, b);
    }

	private void weaveMergeSort(int[] array, int a, int b) {
		if(b-a < 2) return;
		
		int m = (a+b)/2;
		this.weaveMergeSort(array, a, m);
		this.weaveMergeSort(array, m, b);
		this.weaveMerge(array, a, b);
	}

    @Override
    public void runSort(int[] array, int currentLength, int bucketCount) {
        this.weaveMergeSort(array, 0, currentLength);
    }
}