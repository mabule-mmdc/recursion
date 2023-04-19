import java.util.Arrays;

public class Recursive {
    public static void main(String[] args) {
        /**
         * To make our program a bit more flexible, we're declaring our unsorted numbers
         * array of integers based on the length of our program arguments.
         * 
         * This way, we can pass an arbitrary amount of numbers in any particular order
         * to our program for it to be sorted using merge sort.
         * 
         * Try running `java Recursive 8 3 4 1 9 7 2 5 6` after compiling.
         */
        int[] unsortedNumbers = new int[args.length];

        /**
         * All program arguments are strings by default. We want to parse them to integers
         * so we can process them properly.
         * 
         * Note that we are using .trim() to avoid any whitespace to be parsed by our
         * .parseInt() method.
         */
        for (int i = 0; i < args.length; i++) {
            unsortedNumbers[i] = Integer.parseInt(args[i].trim());
        }

        /**
         * Declare an array of integers to assign the return value of merge sort
         * 
         * Do note that you can also opt not to return any value and do the merge
         * by manipulating the same array.
         */
        int[] sortedNumbers = mergeSort(unsortedNumbers);

        /** Print sorted numbers in the console */
        System.out.println("Arranged in ascending order " + Arrays.toString(sortedNumbers));
    }

    public static int[] mergeSort(int[] numbers) {
        /**
         * If our numbers array has a length of anything less than 1 or equal to 1, return it
         * as it can't be sorted using merge sort.
         */
        if (numbers.length <= 1) return numbers;

        /** Get the size of the numbers array */
        int size = numbers.length;
        /**
         * To split the array in half, get the middle index
         *
         * Note that dividing odd numbers by 2 would still yield an integer by default.
         * It will calculate the actual amount and round it down.
         * 
         * Ex. 9 / 2 = 4
         */
        int half = size / 2;
        /** Check if the size is an even number or odd number for proper splitting */
        boolean isEven = size % 2 == 0;

        /** Declare the left subarray with the size of half the original array */
        int[] left = new int[half];
        /**
         * Declare the right subarray depending if the original array's size is odd or even.
         * If it is even, use the half value. If it's odd, always add 1 to account for the 
         * last element.
         */
        int[] right = new int[isEven ? half : half + 1];

        /** Assign the values of the original array to the left and right subarrays */
        for (int i = 0; i < size; i++) {
            /**
             * If the index is less than half, you know it should go to the left subarray
             * 
             * You can use the same index here.
             */
            if (i < half) {
                left[i] = numbers[i];
            } else {
                /**
                 * If the index is equal or more than half, it should go to the right subarray
                 * 
                 * To get the correct index for the right subarray, we just subtract the value
                 * of half to the value of i.
                 * 
                 * Ex.
                 * i = 4
                 * half = 4
                 * 
                 * 4 is not less than 4, therefore 4 - 4 = 0 -- that's the first index of the right
                 * subarray.
                 * 
                 * i = 5
                 * half = 4
                 * 
                 * 5 is not less than 4, therefore 5 - 4 = 1 -- that's the second index of the right
                 * subarray.
                 */
                right[i - half] = numbers[i];
            }
        }

        /** Debuggers to check if you split the original array in half */
        System.out.println("Left " + Arrays.toString(left));
        System.out.println("Right " + Arrays.toString(right));

        /**
         * THIS IS WHERE THE RECURSION HAPPENS
         * 
         * Both left and right subarrays will then go through the same process of merge sort, individually,
         * using the same function.
         */
        int[] sortedLeft = mergeSort(left);
        int[] sortedRight = mergeSort(right);
        /** And then both sorted subarrays will then be merged */
        int[] merged = merge(sortedLeft, sortedRight);

        /** Debugger to check the merged arrays after recursion happens */
        System.out.println("Merged " + Arrays.toString(merged));

        return merged;
    }

    public static int[] merge(int[] left, int[] right) {
        /** Declare a new array with the size of both the left and right subarrays */
        int[] sorted = new int[left.length + right.length];
        /** Declare an index cursor for the left subarray */
        int lastLeftIndex = 0;
        /** Declare an index cursor for the right subarray */
        int lastRightIndex = 0;
        /** Declare an index cursor for the new array which will hold the sorted values */
        int sortIndex = 0;

        /**
         * This loop will run until one of the subarrays are fully processed. Fully processed
         * means that the index cursor for the subarray is equal to the length of the subarray
         * itself.
         */
        while(lastLeftIndex != left.length || lastRightIndex != right.length) {
            /**
             * If the element on the left subarray is less than the element on the right subarray,
             * select that and assign it to the current element of the sorted array.
             */
            if (left[lastLeftIndex] < right[lastRightIndex]) {
                sorted[sortIndex] = left[lastLeftIndex];
                
                /**
                 * Increase the index cursor of the left subarray indicating that the current
                 * element in the left subarray has been processed.
                 */
                lastLeftIndex++;
            } else {
                /**
                 * Same process above goes here for the right subarray in case its element is less
                 * than the one in the left subarray.
                 */
                sorted[sortIndex] = right[lastRightIndex];
                lastRightIndex++;
            }

            /**
             * Regardless of what element from which subarray is processed, always move the sorted
             * array cursor.
             */
            sortIndex++;

            /**
             * Before the next iteration, check if any of the two subarrays have been fully processed.
             * If so, break the loop. It means the rest of the numbers in the remaining subarray can be
             * safely merged in the sorted array as it is already sorted.
             */
            if (lastLeftIndex == left.length || lastRightIndex == right.length) break;
        }

        /**
         * If the left subarray has been fully processed and the right subarray still has elements
         * that's needed to be added in the sorted array, process them all using their respective
         * index cursors.
         */
        if (lastLeftIndex == left.length && lastRightIndex != right.length) {
            while (lastRightIndex != right.length) {
                sorted[sortIndex] = right[lastRightIndex];
                lastRightIndex++;
                sortIndex++;
            }
        } else if (lastRightIndex == right.length && lastLeftIndex != left.length) {
            /** Same process goes for the left subarray if the right has been fully processed */
            while (lastLeftIndex != left.length) {
                sorted[sortIndex] = left[lastLeftIndex];
                lastLeftIndex++;
                sortIndex++;
            }
        }

        /** Return sorted */
        return sorted;
    }
}