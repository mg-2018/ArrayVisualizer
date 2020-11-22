package sorts.custom;


import main.ArrayVisualizer;
import sorts.templates.Sort;

/*
Worse mergeMerge Sort 2020 Copyright (C) thatsOven
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

final public class WorseMergeMergeSort extends Sort {
    public WorseMergeMergeSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        
        this.setSortListName("Worse mergeMerge");
        this.setRunAllSortsName("thatsOven's Worse mergeMerge");
        this.setRunSortName("thatsOven's Worse mergeMerge");
        this.setCategory("Exchange Sorts");
        this.setComparisonBased(true);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(true);
        this.setUnreasonableLimit(64);
        this.setBogoSort(false);
    }
    
    public void worseMergeMergeFun(int[] arr, int l, int r) {
    	if (r-l > 2) {
            Highlights.clearAllMarks();
            int m = (l+(r-1))/2;
            this.worseMergeMergeFun(arr, l, m);
            this.worseMergeMergeFun(arr, m+1, r);
            for (int i = 1; l+i < r-i; i++) {
                this.worseMergeMergeFun(arr, l+i, r);
                this.worseMergeMergeFun(arr, l, r-i);
            }
        } else {
            Highlights.markArray(0, l);
            Highlights.markArray(1, r);
            if (Reads.compareValues(arr[l], arr[r]) > 0) {
                Writes.swap(arr, l, r, 1, true, false);
            }
        }
    }
	
    @Override
    public void runSort(int[] array, int currentLength, int bucketCount) {
        this.worseMergeMergeFun(array, 0, currentLength-1);
    }
}