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

## About *Holy Grail Sort*
Currently this is mock algorithm of Grail Sort, but its project is working in progress!!
More discussion is going in this discord server: https://discord.com/invite/2xGkKC2
(Feel free to visit here; this server is not only for holygrail project, but also for free subjects!)

## **Official** Sorts List
Currently 79 sorts are in "Run All Sorts" queue
This list includes all sorts were already in arrayV v3.5
Sorts added later than v3.5 is listed in CUSTOM.md

### Exchange Sorts (14)
- Bubble / Optimized Bubble Sort
- Cocktail Shaker / Optimized Cocktail Shaker Sort
- Odd-Even Sort
- Gnome / Optimized Gnome Sort, Opti. Gnome Sort w/ Binary Search
- Comb Sort
- Circle Sort
- LL / LR / Dual-Pivot / Stable Quick Sort

### Selection Sorts (12)
- Selection / Double Selection Sort
- Cycle Sort
- Max / Min / Flipped Min / Weak / Ternary Heap Sort
- Smooth Sort / Poplar Heap Sort
- Tournament Sort
- Pancake Sorting (this is classified as 'Miscellaneous Sort' when running all sorts)

### Insertion Sorts (5)
- Insertion / Binary Insertion Sort
- Shell Sort
- Patience Sort
- (Unbalanced) Tree Sort

### Merge Sorts (5)
- Merge Sort (out-of-place)
- Bottom-Up Merge Sort
- In-Place Merge Sort
- Lazy Stable Sort
- Rotate Merge Sort (in-place)

### Distribution Sorts (13)
- Counting / Pigeonhole Sort (currently the fastest **distributive sort**)
- Gravity(Bead) Sort
- American Flag Sort
- LSD / In-Place LSD / MSD Radix Sort
- Flash Sort
- Iterative / Recursive Binary Quick Sort
- ~~Shatter Sort~~ (this will almost not work in non-linear inputs)
- Simple Shatter Sort
- Time Sort

### Concurrent Sorts (6)
- Iterative / Recursive Bitonic Sort
- Iterative / Recursive Odd-Even Merge Sort
- Iterative / Recursive Pairwise Sorting Network

### Hybrid Sorts (14)
- Hybrid Comb Sort
- Introspective Circle Sort
- Binary Merge Sort
- Weave Merge Sort (aka Merge-Insertion Sort)
- Tim Sort
- Cocktail Merge Sort
- Wiki / Grail / Sqrt Sort (block merge sorts!)
- Introspective Sort [std::sort]
- Optimized Bottom-Up Merge Sort [std::stable_sort]
- Optimized Dual-Pivot Quick Sort [Arrays.sort]
- Branched / Branchless Pattern Defeating Quick Sort (currently the fastest **comparative sort**)

### *Impractical* Sorts (10)
NOTE: Beware; even in a high speed, some of these sorts may not finish in reasonable time
Try these in less than item count inside parentheses

- Bad Sort (2048)
- Stooge Sort (1024)
- Silly / Slow Sort (256)
- Exchange Bogo / Bubble Bogo Sort (512)
- Less Bogo / Cocktail Bogo Sort (512)
- Bogo Sort (10)
- Bogo Bogo Sort (6)

## About
- Original project by: w0rthy
- Revamped by: MusicTheorist
- Re-Revamped by: mg-2018
