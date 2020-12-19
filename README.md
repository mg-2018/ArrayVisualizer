# w0rthy's Array Visualizer, Re-Revamped

## How to run this program
- Method 1: run this batch in dist folder:

```
ant
java -cp bin;lib/classgraph-4.8.47.jar main.ArrayVisualizer
```
NOTE: after bin, use ;(semicolon) in windows and :(colon) in Mac/Linux

- Method 2: if method 1 doesn't work, simply import this project in Eclipse IDE for Java and run ArrayVisualizer.java

## Features
- Visualize over 90 algorithms with 12 different visual style
- Customize sound with your own soundfont (.sf2, .sfz, .dls format)
- Toggle end sweep animation (was inspired with Timo Bingmann's Sound of Sorting)
- Adjust speed and cancel delay
- Various inputs available (random, ascending, descending, etc.)
- Sort up to 32768 (2^15) items!
- ...and lots of more!

## Sorts List (2020/12/19)
Currently 79 sorts are in "Run All Sorts" queue

### Exchange Sorts
- Bubble / Optimized Bubble Sort
- Cocktail Shaker / Optimized Cocktail Shaker Sort
- Odd-Even Sort
- Gnome / Optimized Gnome Sort, Opti. Gnome Sort w/ Binary Search
- Comb Sort
- Circle Sort
- LL / LR / Dual-Pivot / Stable Quick Sort

### Selection Sorts
- Selection / Double Selection Sort
- Cycle Sort
- Max / Min / Flipped Min / Weak / Ternary Heap Sort
- Smooth Sort / Poplar Heap Sort
- Tournament Sort
- Pancake Sorting (this is classified as 'Miscellaneous Sort' when running all sorts)

### Insertion Sorts
- Insertion / Binary Insertion Sort
- Shell Sort (this has two different version)
- Patience Sort
- (Unbalanced) Tree Sort

### Merge Sorts
- Merge Sort (out-of-place)
- Bottom-Up Merge Sort
- In-Place Merge Sort
- Lazy Stable Sort
- Rotate Merge Sort (in-place)
- "Andrey Astrelin's In-Place Merge Sort"

### Distribution Sorts
- Counting / Pigeonhole Sort (currently the fastest **distributive sort**)
- Gravity(Bead) Sort
- American Flag Sort
- LSD / In-Place LSD / MSD Radix Sort
- Flash Sort
- Iterative / Recursive Binary Quick Sort
- ~~Shatter Sort~~ (this will almost not work in non-linear inputs)
- Simple Shatter Sort
- Time Sort

### Concurrent Sorts
- Iterative / Recursive Bitonic Sort
- Iterative / Recursive Odd-Even Merge Sort
- Iterative / Recursive Pairwise Sorting Network
- "Bose-Nelson Sorting Network" (recursive)

### Hybrid Sorts
- "Hybrid Comb Sort"
- "Introspective Circle Sort"
- Binary Merge Sort
- Weave Merge Sort (aka Merge-Insertion Sort)
- Tim Sort
- "Cocktail Merge Sort"
- Wiki / Grail / Sqrt Sort (block merge sorts!)
- Introspective Sort [std::sort]
- Optimized Bottom-Up Merge Sort [std::stable_sort]
- Optimized Dual-Pivot Quick Sort [Arrays.sort]
- Branched / Branchless Pattern Defeating Quick Sort (currently the fastest **comparative sort**)
- "Kota / Ecta Sort"

### *Impractical* Sorts
NOTE: Beware; even in a high speed, some of these sorts may not finish in reasonable time

- Bad Sort
- Stooge Sort
- Silly / Slow Sort
- Various "Bogo Sorts" (try these in less than 64 items please)

### Custom Sorts
These sorts are in sorts.custom package, check the custom added sorts in commit section!

## About
- Original project by: w0rthy
- Revamped by: MusicTheorist
- Re-Revamped by: mg-2018
- and check out this discord server: https://discord.com/invite/2xGkKC2
