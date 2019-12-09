package aboutjava.algorithm;

/**
 * @program: springBootPractice
 * @description: 数据结构与算法——关于排序算法
 * @author: hu_pf
 * @create: 2019-12-06 15:39
 **/
public class AboutSort {

    public static void main(String[] args) {
        int[] noSortNumArray = new int[]{0,2,5,6,3,1,43,6,75,7,45,64,34,87};
        quickSortC(noSortNumArray,0,noSortNumArray.length-1);
        System.out.printf("1");

    }


    /**
    * @Description: 快排
    * @Param: [a, p, r]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/12/6
    */
    public static void quickSortC(int[] a , int p , int r){
        if (p>=r) return;

        int q = quickSort(a,p,r);
        quickSortC(a,p,q-1);
        quickSortC(a,q+1,r);
    }

    private static int quickSort(int[] a , int begin , int end){
        int prior = a[end];
        int i = begin;
        for (int j = begin; j < end; j++) {
            if (a[j]<prior){
                swap(a,i,j);
                i++;
            }
        }
        swap(a,i,end);
        return i;
    }

    private static void swap(int[] a ,int i,int j){
        int swapNum = a[i];
        a[i] = a[j];
        a[j] = swapNum;
    }
}
