package sorts.custom;

import main.ArrayVisualizer;
import sorts.templates.Sort;

final public class ModuloSort extends Sort {
    public ModuloSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        
        this.setSortListName("Modulo");
        this.setRunAllSortsName("Modulo Sort (By McDude_73)");
        this.setRunSortName("Modulo Sort");
        this.setCategory("Distribution Sorts");
        this.setComparisonBased(false);
        this.setBucketSort(true);
        this.setRadixSort(true);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }
    private void moduloSort(int[] array, int len, int modulo, boolean askAgain) {
        int[] aux = new int[len];
        while(modulo <= len) {
            for(int init=0;init<len;init++) {
                Writes.write(aux, init, array[init], 1, true, true);
            }
            
            for(int i=0,j=0,k=-1;j<modulo;i++) {
                if(aux[i] % modulo == j) {
                    Writes.write(array, ++k, aux[i], 1, true, false);
                }
                if(i + 1 >= len) {
                    j++;
                    i = -1;
                }
            }
            if(modulo * 2 >= len && askAgain) {
                askAgain = false;
                modulo = len;
            } else {
                modulo *= 2;
            }
        }
    }
    
    @Override
    public void runSort(int[] array, int length, int bucketCount) {
        if(bucketCount <= 1) bucketCount = 4;
        if(bucketCount >= length) bucketCount = length;
        moduloSort(array, length, bucketCount, bucketCount == length ? false : true);
    }
}
