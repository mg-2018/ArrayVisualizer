package sorts.templates;

import main.ArrayVisualizer;

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

public abstract class BogoSorting extends Sort {
    protected BogoSorting(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
    }
    
    private static int randomPosition(int length, int offset) {
        return (int) (Math.random() * (length - offset));
    }
    
    protected void bogoSwap(int[] array, int length, int offset){
        for(int i = offset; i < length; i++) {
            Writes.swap(array, i, BogoSorting.randomPosition(length, i) + i, 0, true, false);
        }
    }
    
    protected boolean bogoIsSorted(int[] array, int length) {
        for(int i = 0; i < length - 1; i++) {
            if(Reads.compareValues(array[i], array[i + 1]) == 1) {
                Highlights.markArray(1, i);
                if (getRunSortName().equals("Bogosort")) Delays.sleep(0.01);
                else if (getRunSortName().equals("Bogobogosort")) Delays.sleep(0.001);
                return false;
            }
        }
        Highlights.clearMark(1);
        return true;
    }
    
    protected boolean isMinSorted(int[] array, int length, int offset) {
        Highlights.clearAllMarks();
        
        //Highlights.markArray(2, offset);
        //Highlights.markArray(3, length);
        
        for(int i = offset + 1; i < length; i++) {
            Highlights.markArray(1, i);
            Delays.sleep(0.075);
            
            if(Reads.compareValues(array[offset], array[i]) == 1) {
                return false;
            }
        }
        return true;
    }
    
    protected boolean isMaxSorted(int[] array, int minIterator, int maxIterator) {
        Highlights.clearAllMarks();
        
        //Highlights.markArray(2, minIterator);
        //Highlights.markArray(3, maxIterator);
        
        for(int i = maxIterator; i >= minIterator; i--) {
            Highlights.markArray(1, i);
            Delays.sleep(0.075);
            
            if(Reads.compareValues(array[maxIterator], array[i]) == -1) {
                return false;
            }
        }
        return true;
    }
}