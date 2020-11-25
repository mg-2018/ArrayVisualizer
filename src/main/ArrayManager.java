package main;

import panes.JErrorPane;
import utils.Delays;
import utils.Highlights;
import utils.Shuffles;
import utils.Writes;

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

final public class ArrayManager {
    private int[] presortedArray;
    private utils.Shuffles[] shuffleTypes;
    private String[] shuffleIDs = {"Randomly", "Backwards", "Few Unique", "Almost Sorted", "Already Sorted", "Smaller Runs", "\"Triangular\"",
    		"Scrambled Head", "Perlin Noise", "Max Heapify", "Interlaced", "Poplar Heapify", "Pairwise Pass", "Bell Curve", "???",
    		"Half Rotated", "Pipe Organ", "Scrambled Tail", "Inv. Pipe Organ", "A133058", "Push Front"};
    
    private volatile boolean MUTABLE;

    private ArrayVisualizer ArrayVisualizer;
    private Delays Delays;
    private Highlights Highlights;
    private Shuffles Shuffles;
    private Writes Writes;
    
    public ArrayManager(ArrayVisualizer arrayVisualizer) {
        this.ArrayVisualizer = arrayVisualizer;
        this.presortedArray = new int[ArrayVisualizer.getMaximumLength()];
        
        this.Shuffles = utils.Shuffles.RANDOM;
        this.shuffleTypes = utils.Shuffles.values();
        
        this.Delays = ArrayVisualizer.getDelays();
        this.Highlights = ArrayVisualizer.getHighlights();
        this.Writes = ArrayVisualizer.getWrites();
        
        this.MUTABLE = true;
    }
    
    public boolean isLengthMutable() {
        return this.MUTABLE;
    }
    public void toggleMutableLength(boolean Bool) {
        this.MUTABLE = Bool;
    }
 
    //TODO: Fix minimum to zero
    public void initializeArray(int[] array) {
        int equalFactor = ArrayVisualizer.getEqualItems();
        for (int i = 0; i < array.length; i++) {
            array[i] = ((i / equalFactor) * equalFactor);
        }
    }
    
    public void initializePresortedArray() {
        for (int i = 0; i < this.presortedArray.length; i++) {
            this.presortedArray[i] = i;
        }
        
        for(int i = 0; i < Math.max((this.presortedArray.length / 10), 1); i++){
            Writes.swap(this.presortedArray, (int) (Math.random() * this.presortedArray.length), (int) (Math.random() * this.presortedArray.length), 0, true, false);
        }
    }
    
    public String[] getShuffleIDs() {
        return this.shuffleIDs;
    }
    public Shuffles[] getShuffles() {
        return this.shuffleTypes;
    }
    public Shuffles getShuffle() {
        return this.Shuffles;
    }
    public void setShuffle(Shuffles choice) {
        this.Shuffles = choice;
    }
    
    public void shuffleArray(int[] array, int currentLen, ArrayVisualizer ArrayVisualizer) {
        if(Shuffles != Shuffles.ALREADY) this.initializeArray(array);

        String tmp = ArrayVisualizer.getHeading();
        ArrayVisualizer.setHeading("Shuffling...");
        
        double speed = Delays.getSleepRatio();
        
        if(ArrayVisualizer.getSortingThread() != null && ArrayVisualizer.getSortingThread().isAlive()) {
            double sleepRatio;
            
            //TODO: sound lags when number is too small and delay is cancelled right after shuffle
            sleepRatio = currentLen/1024d;
            
            Delays.setSleepRatio(sleepRatio);
        }
        
        Shuffles.shuffleArray(array, this.ArrayVisualizer, Delays, Highlights, Writes);
        this.ArrayVisualizer.setShadowArray();
        
        Delays.setSleepRatio(speed);
        
        Highlights.clearAllMarks();
        ArrayVisualizer.setHeading(tmp);
    }
    
    public void refreshArray(int[] array, int currentLen, ArrayVisualizer ArrayVisualizer) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            JErrorPane.invokeErrorMessage(e);
        }
        
        ArrayVisualizer.resetAllStatistics();
        Highlights.clearAllMarks();
        
        ArrayVisualizer.setHeading("");
        this.shuffleArray(array, currentLen, ArrayVisualizer);
        
        Highlights.clearAllMarks();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            JErrorPane.invokeErrorMessage(e);
        }
        
        ArrayVisualizer.resetAllStatistics();
    }
}