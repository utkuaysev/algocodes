package heap;

public class BottomUp {
    static int count =0;
    static void buildMaxHeapBottomUp(int []a,int n){
        for(int i=  (n/2)-1;i>=0;i--){
            downHeap(a,i,n);
        }
    }

    static void downHeap(int[] a,int i,int n)
    {
        int j=i;
        int k = maxChildIndex(a,j,n);
        while(k!=0){
            int temp = a[k];
            a[k] = a[j];
            a[j] = temp;
            j=k;
            k= maxChildIndex(a,j,n);
        }

    }

    static int maxChildIndex(int[] a,int i,int n){
        int largest = i;
        int l = 2*i+1;
        int r = 2*i+2;

        if(l<n )
        {
            count ++;
            if( a[l]>a[largest]){
                largest = l;
            }
        }
        if(r<n) {
            count ++;
            if(a[r]>a[largest]){
                largest = r;
            }
        }
        if(largest == i){
            return 0;
        }
        return largest;
    }
    public static void main(String []args){
        int[] arr = {4, 2, 3, 1, 5, 8, 7, 6, 11, 10, 12, 9, 13, 14, 16, 15 };
        buildMaxHeapBottomUp(arr,arr.length);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println("\nNumber of comparisons: " + count);
    }
}


