package utils;

import java.text.DecimalFormat;

import main.ArrayVisualizer;

final public class Statistics {
	private int similar;
	
    private String sortCategory;
    private String sortHeading;
    private String sortExtraHeading;
    private String arrayLength;
    
    private String sortDelay;
    private String visualTime;
    private String estSortTime;
    
    private String comparisonCount;
    private String swapCount;
    private String reversalCount;
    
    private String mainWriteCount;
    private String auxWriteCount;
    
    private String runs;
    
    private DecimalFormat formatter;
    
    public Statistics(ArrayVisualizer ArrayVisualizer) {
        this.formatter = ArrayVisualizer.getNumberFormat();
        this.updateStats(ArrayVisualizer);
    }
    
    // get runs function. (a.k.a. get segments function)
    // credits to arrayV 4.0 contributors in The Studio
    // thatsOven, Gaming32, aphitorite, _fluffy
    public int[] getRuns(int[] array, int length) {
    	int runs = 1;
    	int correct = 0;
    	int[] res = new int[2];
    	for(int i=0; i<length-1; i++) {
    		if(array[i] > array[i+1]) runs++;
    		else correct++;
    	}
    	correct = (int) ((((double) correct) / (length - 1)) * 100);
    	res[0] = runs;
    	res[1] = correct;
    	
    	return res;
    }

    public void updateStats(ArrayVisualizer ArrayVisualizer) {
    	this.similar = (ArrayVisualizer.getCurrentLength() - 1) / ArrayVisualizer.getEqualItems() + 1;
    	
        this.sortCategory = ArrayVisualizer.getCategory();
        this.sortHeading = ArrayVisualizer.getHeading();
        this.sortExtraHeading = ArrayVisualizer.getExtraHeading();
        if(this.similar == 1) {
        	this.arrayLength = this.formatter.format(ArrayVisualizer.getCurrentLength()) + " Numbers, All similar";
        }
        else {
        	this.arrayLength = this.formatter.format(ArrayVisualizer.getCurrentLength()) + " Numbers, " +
                               this.formatter.format(this.similar) + " Unique";
        }
        
        this.sortDelay = "Delay: " + ArrayVisualizer.getDelays().displayCurrentDelay() + "ms";
        this.visualTime = "Visual Time: " + ArrayVisualizer.getTimer().getVisualTime();
        this.estSortTime = "Sort Time: " + ArrayVisualizer.getTimer().getRealTime();
        
        this.comparisonCount = ArrayVisualizer.getReads().getStats();
        this.swapCount = ArrayVisualizer.getWrites().getSwaps();
        this.reversalCount = ArrayVisualizer.getWrites().getReversals();
        
        this.mainWriteCount = ArrayVisualizer.getWrites().getMainWrites();
        this.auxWriteCount = ArrayVisualizer.getWrites().getAuxWrites();
        
        // also this credits to arrayV 4.0 contributors
        int shadowarray[] = ArrayVisualizer.getArray();
        int[] runCount = this.getRuns(shadowarray, ArrayVisualizer.getCurrentLength());
        this.runs = String.valueOf(runCount[1]) + "% Sorted, " + String.valueOf(runCount[0]) + " Run(s)";
    }
    
    public String getSortIdentity() {
        return this.sortCategory + ": " + this.sortHeading;
    }
    public String getArrayLength() {
        return this.arrayLength + this.sortExtraHeading;
    }
    public String getSortDelay() {
        return this.sortDelay;
    }
    public String getVisualTime() {
        return this.visualTime;
    }
    public String getEstSortTime() {
        return this.estSortTime;
    }
    public String getComparisonCount() {
        return this.comparisonCount;
    }
    public String getSwapCount() {
        return this.swapCount;
    }
    public String getReversalCount() {
        return this.reversalCount;
    }
    public String getMainWriteCount() {
        return this.mainWriteCount;
    }
    public String getAuxWriteCount() {
        return this.auxWriteCount;
    }
    public String getRuns() {
        return this.runs;
    }
}