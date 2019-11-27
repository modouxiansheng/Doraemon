package aboutjava.algorithm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @program: springBootPractice
 * @description: 关于树的数据结构与算法
 * @author: hu_pf
 * @create: 2019-11-24 08:30
 **/
public class AboutTree {

    // 二叉树的遍历  增删查
    public static void main(String[] args) {
        TreeNode<String> aTreeNode = new TreeNode<>("A");
        TreeNode<String> bTreeNode = new TreeNode<>("B");
        TreeNode<String> cTreeNode = new TreeNode<>("C");
        TreeNode<String> dTreeNode = new TreeNode<>("D");
        TreeNode<String> eTreeNode = new TreeNode<>("E");
        TreeNode<String> fTreeNode = new TreeNode<>("F");
        TreeNode<String> gTreeNode = new TreeNode<>("G");

        aTreeNode.leftNode = bTreeNode;
        aTreeNode.rightNode = cTreeNode;
        bTreeNode.leftNode = dTreeNode;
        bTreeNode.rightNode = eTreeNode;
        cTreeNode.leftNode = fTreeNode;
        cTreeNode.rightNode = gTreeNode;

        System.out.printf("前序遍历：");
        frontOrder(aTreeNode);
        System.out.printf("\n");
        System.out.printf("中序遍历：");
        middleOrder(aTreeNode);
        System.out.printf("\n");
        System.out.printf("后序遍历：");
        afterOrder(aTreeNode);

    }

    /**
    * @Description: 前序遍历
    * @Param: [treeNode]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/11/26
    */
    private static void frontOrder(TreeNode<String> treeNode){
        if (treeNode == null){
            return;
        }
        System.out.printf(treeNode.data);
        frontOrder(treeNode.leftNode);
        frontOrder(treeNode.rightNode);
    }

    /**
    * @Description: 中序遍历
    * @Param: [treeNode]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/11/26
    */
    private static void middleOrder(TreeNode<String> treeNode){
        if (treeNode == null){
            return;
        }
        middleOrder(treeNode.leftNode);
        System.out.printf(treeNode.data);
        middleOrder(treeNode.rightNode);
    }

    /**
    * @Description: 后序遍历
    * @Param: [treeNode]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/11/26
    */
    private static void afterOrder(TreeNode<String> treeNode){
        if (treeNode == null){
            return;
        }
        afterOrder(treeNode.leftNode);
        afterOrder(treeNode.rightNode);
        System.out.printf(treeNode.data);
    }





}

class TreeNode <T>{
    public T data;
    public TreeNode leftNode;
    public TreeNode rightNode;

    TreeNode(T data){
        this.data = data;
    }
}
