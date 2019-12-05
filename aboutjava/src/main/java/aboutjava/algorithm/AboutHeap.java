package aboutjava.algorithm;

import java.util.Arrays;

/**
 * @program: springBootPractice
 * @description: 关于堆的数据结构与算法（堆是一个特殊的树）
 *              1. 是一个完全二叉树
 *              2. 每个节点都大于等于（小于等于）其子节点
 *              3. 堆分为大顶堆（每个节点都大于等于其子节点）小顶堆（每个节点都小于等于其子节点）
 * @author: hu_pf
 * @create: 2019-12-05 19:55
 **/
public class AboutHeap {

    public static void main(String[] args) {
        Heap heap = new Heap(20);
        String num = "33,27,21,16,13,15,9,5,6,7,8,1,2,12";
        Arrays.asList(num.split(",")).forEach(s->{
            heap.insert(Integer.valueOf(s));
        });
        heap.removeMax();
        System.out.printf("1");
    }

}

class Heap{
    // 存放堆中的数据，用数组来装
    private int [] a;

    // 堆中能存放的最大数
    private int n;

    // 堆中已经存放的数量
    private int count;

    public Heap(int capacity){
        a = new int [capacity + 1];
        this.n = capacity;
        count = 0;
    }

    /**
    * @Description: 在堆中插入元素
    * @Param: [data]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/12/5
    */
    public void insert(int data){
        if (count >= n) return;

        count++;
        // 把数据放到数组中，这里为什么不先count++后a[count] = data,因为堆在数组中排放位置是从索引为1的位置开始的。
        a[count] = data;

        int index = count;
        // 开始进行比较，先判断跳出条件
        // 1. 首先能想到的是插入的数据满足了大（小）顶堆的数据要求
        // 2. 加上极值条件，及是一颗空树情况
        while (index/2>0 && a[index]>a[index/2]){
            swap(a,index,index/2);
            index = index/2;
        }
    }

    /**
    * @Description: 移除堆顶元素，自上而下比较可以避免产生数组空洞（即将数组末尾元素移到堆顶，然后再进行排序）
    * @Param: []
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/12/5
    */
    public void removeMax(){

        if (count == 0) return;

        // 将最后的元素移动到堆顶
        a[1] = a[count];

        count--;

        heapify(a,count,1);
    }

    /**
    * @Description: 自上而上对堆进行排序
    * @Param: [a, n, i]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/12/5
    */
    private void heapify(int [] a,int n,int i){
        // 定义什么时候结束，当前节点与其左右子节点进行比对，如果当前节点是最大的则跳出循环（代表当前节点已经是最大的）
        while (true){
            if (getMaxIndex(a,n,i) == i) break;
        }
    }

    /**
    * @Description: 找出最大节点的索引值
    * @Param: [a, i]
    * @return: int
    * @Author: hu_pf
    * @Date: 2019/12/5
    */
    private int getMaxIndex(int [] a,int count,int i){
        int maxIndex = i;
        if (2*i<= count && a[i]<a[2*i]) maxIndex = 2*i; // 判断当前节点,是否小于左节点
        if (2*i+1<= count && a[maxIndex]<a[2*i+1]) maxIndex = 2*i+1;// 判断最大节点是否小于右节点
        // 找到中最大值的位置，并交换位置
        swap(a,i,maxIndex);
        return maxIndex;
    }

    private void swap(int [] a, int i , int j){
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
