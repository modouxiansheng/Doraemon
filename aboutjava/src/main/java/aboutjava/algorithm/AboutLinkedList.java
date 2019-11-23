package aboutjava.algorithm;

import lombok.Data;

import java.util.Arrays;

/**
 * @program: springBootPractice
 * @description: 链表
 * @author: hu_pf
 * @create: 2019-11-15 19:18
 **/
public class AboutLinkedList {

    private Node head = new Node();

    private Node tail = new Node();

    private int i=0 ;


    public static void main(String[] args) {

        AboutLinkedList aboutLinkedList = new AboutLinkedList();
        for (int i = 0; i < 6; i++) {
            Node node = new Node();
            node.setNum(i);
            aboutLinkedList.addNode(node);
        }

        AboutLinkedList one = new AboutLinkedList();
        AboutLinkedList two = new AboutLinkedList();
        Arrays.asList("1,2,4".split(",")).forEach(i ->{
            Node node = new Node();
            node.setNum(Integer.valueOf(i));
            one.addNode(node);
        });
        Arrays.asList("1,3,4".split(",")).forEach(i ->{
            Node node = new Node();
            node.setNum(Integer.valueOf(i));
            two.addNode(node);
        });

//        Node node = aboutLinkedList.mergeTwoListTwo(one.head.getNext(), two.head.getNext());
//        aboutLinkedList.printNode(node);

        Node nodeForCenter = aboutLinkedList.getNodeForCenter(aboutLinkedList.head.getNext());
        System.out.printf(nodeForCenter.getNum().toString());
    }

    public void printNode(Node next){
        while (next!=null){
            System.out.printf(Integer.toString(next.getNum()));
            next = next.getNext();
        }
    }

    /**
    * @Description: 在尾部新增节点
    * @Param: [node]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/11/18
    */
    public void addNode(Node node){
        if (head.getNext() == null){
            head.setNext(node);
            tail.setNext(node);
        }else {
            tail.getNext().setNext(node);
            tail.setNext(node);
        }
    }



    /**
    * @Description: 删除尾部节点
    * @Param: []
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/11/18
    */
    public void deleteNode(){
        Node begin = head.getNext();
        Node pre = null;
        while (begin != tail.getNext()){
            pre = begin;
            begin = begin.getNext();
        }
        if (pre == null){
            head.setNext(null);
            tail.setNext(null);
        }else {
            tail.setNext(pre);
            pre.setNext(null);
        }

    }

    /**
    * @Description: 删除指定节点
    * @Param: [node]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/11/18
    */
    public void deleteNode(Node node){
        Node begin = head.getNext();
        Node pre = null;
        while (!begin.equals(node)){
            pre = begin;
            begin = begin.getNext();
        }
        pre.setNext(begin.getNext());
        begin.setNext(null);
    }

    /**
    * @Description: 查询指定节点并返回
    * @Param: [num]
    * @return: aboutjava.algorithm.Node
    * @Author: hu_pf
    * @Date: 2019/11/18
    */
    public Node getNode(Integer num){
        Node begin = head.getNext();
        while (begin.getNum() != num){
            begin = begin.getNext();
        }
        return begin;
    }

    /**
    * @Description: 反转链表 - 链表反转 - 时间复杂度高
    * @Param: []
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/11/18
    */
    public Node reversalNodeOne(){
        AboutLinkedList aboutLinkedList = new AboutLinkedList();
        while (head.getNext()!=null){
            aboutLinkedList.addNode(tail.getNext());
            deleteNode();
        }
        return aboutLinkedList.head;
    }

    /**
    * @Description: 反转链表 - 链表反转 - 占用栈空间
    * @Param: [head]
    * @return: aboutjava.algorithm.Node
    * @Author: hu_pf
    * @Date: 2019/11/18
    */
    public Node reversalNodeTwo(Node head){

        if (head == null || head.getNext() == null){
            return head;
        }

        Node reHead = reversalNodeTwo(head.getNext());

        head.getNext().setNext(head);

        head.setNext(null);

        Node nodeHead = this.head;
        this.head = this.tail;
        this.tail = nodeHead;
        return reHead;

    }

    /**
    * @Description: 反转链表 - 时间复杂度和空间复杂度相对比前两种好
    * @Param: [head]
    * @return: aboutjava.algorithm.Node
    * @Author: hu_pf
    * @Date: 2019/11/18
    */
    public Node reversalNodeThree(Node head) {
        if (head == null || head.getNext() == null){
            return head;
        }

        Node preNode = null;
        Node nextNode = null;
        while (head != null){
            nextNode = head.getNext();

            head.setNext(preNode);
            preNode = head;
            head = nextNode;
        }
        return preNode;
    }

    /**
    * @Description: 删除链表倒数第N个节点，利用快慢指针
    * @Param: [head, n]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/11/18
    */
    public void deleteNodeForN(Node head, int n){

        Node left = head;
        Node right = head;
        int i = 0;
        while (right!=null && i < n){
            right = right.getNext();
            i++;
        }

        while (right!=null){
            left = left.getNext();
            right = right.getNext();
        }

        deleteNode(left);

    }

    /**
    * @Description: 删除链表倒数第N个节点，利用递归
    * @Param: [head, n]
    * @return: aboutjava.algorithm.Node
    * @Author: hu_pf
    * @Date: 2019/11/19
    */
    public Node deleteNodeForNTwo(Node head, int n){

        if (head == null || head.getNext() == null){
            return head;
        }

        Node node = deleteNodeForNTwo(head.getNext(), n);

        this.i ++;

        if (this.i == n) deleteNode(node);

        return head;
    }

    /**
    * @Description: 两两交换节点
    * @Param: [node]
    * @return: aboutjava.algorithm.Node
    * @Author: hu_pf
    * @Date: 2019/11/19
    */
    public Node swapNode(Node node){
        if (node == null || node.getNext() == null){
            return node;
        }

        Node next = node.getNext();
        Node head = swapNode(node.getNext().getNext());

        node.getNext().setNext(node);

        node.setNext(head);

        return next;
    }

    /**
    * @Description: 合并两个有序链表 - 递归方法
    * @Param: [nodeOne, nodeTwo]
    * @return: aboutjava.algorithm.Node
    * @Author: hu_pf
    * @Date: 2019/11/21
    */
    public Node mergeTwoList(Node nodeOne, Node nodeTwo){
        if (nodeOne == null){
            return nodeTwo;
        } else if (nodeTwo == null){
            return nodeOne;
        }else if (nodeOne.getNum() > nodeTwo.getNum()){
            Node next = mergeTwoList(nodeOne.getNext(), nodeTwo);
            nodeOne.setNext(next);
            return nodeOne;
        }else {
            Node next = mergeTwoList(nodeOne, nodeTwo.getNext());
            nodeTwo.setNext(next);
            return nodeTwo;
        }
    }

    /**
    * @Description: 合并两个有序链表 - 迭代方法
    * @Param: [nodeOne, nodeTwo]
    * @return: aboutjava.algorithm.Node
    * @Author: hu_pf
    * @Date: 2019/11/21
    */
    public Node mergeTwoListTwo(Node nodeOne, Node nodeTwo){

        AboutLinkedList mergeTwoList = new AboutLinkedList();
        Node headNodeOne = nodeOne;
        Node headNodeTwo = nodeTwo;

        while (headNodeOne!=null || headNodeTwo!=null){

            if (headNodeOne == null || headNodeOne.getNum() > headNodeTwo.getNum()){
                mergeTwoList.addNode(headNodeTwo);
                Node pre = headNodeTwo;
                headNodeTwo = headNodeTwo.getNext();
                pre.setNext(null);
            }else {
                mergeTwoList.addNode(headNodeOne);
                Node pre = headNodeOne;
                headNodeOne = headNodeOne.getNext();
                pre.setNext(null);
            }
        }
        return mergeTwoList.head.getNext();
    }

    /**
    * @Description: 获得链表的中间节点 - 利用快慢指针
    * @Param: [head]
    * @return: aboutjava.algorithm.Node
    * @Author: hu_pf
    * @Date: 2019/11/23
    */
    public Node getNodeForCenter(Node head){
        if (head == null){
            return null;
        }else if (head.getNext() == null){
            return head;
        }
        Node slow = head;
        Node fast = head;
        while (fast!=null && fast.getNext()!=null){
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        return slow;
    }


}

@Data
class Node{

    private Integer num;

    private Node next;

    public boolean equals(Node obj)
    {
        return (this.getNum() == obj.getNum());
    }

}
