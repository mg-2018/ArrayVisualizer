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

final public class RunCustomQueue extends MultipleSortThread {
    private Sort test;
    
    public RunCustomQueue(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        this.sortCount = 11;
        this.categoryCount = this.sortCount;
        
        test = new IntroSort(this.arrayVisualizer);
    }

    @Override
    protected synchronized void executeSortList(int[] array) throws Exception {
    	arrayVisualizer.setEqualItems(1);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(2);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(4);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(8);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(16);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(32);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(64);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(128);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(256);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(512);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
    	arrayVisualizer.setEqualItems(1024);
    	RunCustomQueue.this.runIndividualSort(test, 0, array, 2048, 1, false);
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
                    	RunCustomQueue.this.sortNumber = current;
                    	RunCustomQueue.this.sortCount = total;
                    }
                    else {
                    	RunCustomQueue.this.sortNumber = 1;
                    }

                    arrayManager.toggleMutableLength(false);

                    arrayVisualizer.setCategory("Custom Queue");

                    RunCustomQueue.this.executeSortList(array);
                    
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