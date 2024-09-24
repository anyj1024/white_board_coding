package src.sortAlgorithm;

import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        int[] nums = {1, 81, 23, 21, 12, 9, 3, 8};
        heapSort(nums);
        System.out.println(Arrays.toString(nums));
    }

    private static void heapSort(int[] nums) {
        int n = nums.length;

        for (int i = n - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;

            heapify(nums,  i, 0);
        }
    }

    private static void heapify(int[] nums, int n, int i) {
        int largest = i;
        int left = 2 * i + 1, right = 2 * i + 2;

        if (left < n && nums[left] > nums[largest]) {
            largest = left;
        }
        if (right < n && nums[right] > nums[largest]) {
            largest = right;
        }

        if (largest != i) {
            int temp = nums[i];
            nums[i] = nums[largest];
            nums[largest] = temp;

            heapify(nums, n, largest);
        }
    }
}
