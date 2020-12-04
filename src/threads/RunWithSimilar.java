package threads;

import main.ArrayVisualizer;
import panes.JErrorPane;
import sorts.hybrid.*;
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

final public class RunWithSimilar extends MultipleSortThread {
    private Sort test;
    
    public RunWithSimilar(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        this.sortCount = 4;
        this.categoryCount = this.sortCount;
        
        test = new GrailSort(this.arrayVisualizer);
    }

    @Override
    protected synchronized void executeSortList(int[] array) throws Exception {
    	arrayVisualizer.setEqualItems(1);
    	RunWithSimilar.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(64);
    	RunWithSimilar.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(256);
    	RunWithSimilar.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(683);
    	RunWithSimilar.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(1);
    }
    
    @Override
    protected synchronized void runThread(int[] array, int current, int total, boolean runAllActive) throws Exception {
        if(arrayVisualizer.getSortingThread() != null && arrayVisualizer.getSortingThread().isAlive())
            return;

        Sounds.toggleSound(true);
        arrayVisualizer.setSortingThread(new Thread() {
            @Override
            public void run() {
                try{
                    if(runAllActive) {
                    	RunWithSimilar.this.sortNumber = current;
                    	RunWithSimilar.this.sortCount = total;
                    }
                    else {
                    	RunWithSimilar.this.sortNumber = 1;
                    }

                    arrayManager.toggleMutableLength(false);

                    arrayVisualizer.setCategory("Sorts running with similar items");

                    RunWithSimilar.this.executeSortList(array);
                    
                    if(!runAllActive) {
                        arrayVisualizer.setCategory("Run Sorts");
                        arrayVisualizer.setHeading("Done");
                    }
                    
                    arrayManager.toggleMutableLength(true);
                }
                catch (Exception e) {
                    JErrorPane.invokeErrorMessage(e);
                }
                Sounds.toggleSound(false);
                arrayVisualizer.setSortingThread(null);
            }
        });

        arrayVisualizer.runSortingThread();
    }
}