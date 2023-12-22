package heap;

public class TopDown {
    static int topDown(int[] arr) {
        int cnt = 0;
        for (int i = 1; i < arr.length; i++) {
            cnt += upHeap(arr, i);
        }
        return cnt;

    }

    private static int upHeap(int[] arr, int i) {
        int cnt = 0;
        while (i > 1) {
            cnt++;
            if (arr[i] > arr[i / 2]) {
                swap(arr, i, i / 2);
                i = i / 2;
            }
            else
                break;
        }
        return cnt;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] numbers = new int[17];

        for (int i = 1; i < 17; i++) {
            numbers[i] = i ;
        }

        // Print the elements of the array
        for (int i = 1; i < 17; i++) {
            System.out.print(numbers[i] + " ");
        }
        var cnt = topDown(numbers);
        System.out.println();
        for (int i = 1; i < 17; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
        System.out.println(cnt);
    }
}
