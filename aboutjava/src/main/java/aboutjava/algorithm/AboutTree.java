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

    private TreeNode<Integer> rootNode;

    // 二叉树的遍历  增删查
    public static void main(String[] args) {
        // 树的遍历
//        ergodicTree();

        // 树的增删改查
        aboutTreeDataDeal();
    }

    public static void aboutTreeDataDeal(){
        AboutTree aboutTree = new AboutTree();
        aboutTree.insertTree(33);
        aboutTree.insertTree(17);
        aboutTree.insertTree(50);
        aboutTree.insertTree(13);
        aboutTree.insertTree(18);
        aboutTree.insertTree(34);
        aboutTree.insertTree(58);
        aboutTree.insertTree(16);
        aboutTree.insertTree(25);
        aboutTree.insertTree(51);
        aboutTree.insertTree(66);
        aboutTree.insertTree(19);
        aboutTree.insertTree(27);
        aboutTree.insertTree(55);
        aboutTree.insertTree(55);
        aboutTree.insertTree(55);

        TreeNode findTreeNode = aboutTree.findTreeNode(58);
        aboutTree.deleteTreeNode(33);
        System.out.printf("1");
    }

    public static void ergodicTree(){
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

    /**
    * @Description: 树的插入
    * @Param: [data]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/12/8
    */
    private void insertTree(int data){
        if (this.rootNode == null){
            rootNode = new TreeNode(data);
            return;
        }
        TreeNode<Integer> p = rootNode;
        while (p!=null){
            Integer pData = p.data;
            if (data>=pData){
                if (p.rightNode == null){
                    p.rightNode = new TreeNode(data);
                    break;
                }
                p = p.rightNode;
            }else {
                if (p.leftNode == null){
                    p.leftNode = new TreeNode(data);
                    break;
                }
                p = p.leftNode;
            }
        }
    }

    /**
    * @Description: 树的查找
    * @Param: [data]
    * @return: aboutjava.algorithm.TreeNode
    * @Author: hu_pf
    * @Date: 2019/12/8
    */
    private TreeNode findTreeNode(int data){

        if (this.rootNode == null){
            return null;
        }

        TreeNode<Integer> p = rootNode;
        while (p != null){
            if (p.data == data) return p;
            if (data >= p.data) p = p.rightNode;
            else p = p.leftNode;
        }
        return null;
    }

    /**
    * @Description: 删除节点
    * @Param: [data]
    * @return: void
    * @Author: hu_pf
    * @Date: 2019/12/8
    */
    private void deleteTreeNode(int data){

        if (this.rootNode == null ) return;

        TreeNode<Integer> treeNode = rootNode;
        TreeNode<Integer> treeNodeParent = null;
        while (treeNode != null){
            if (treeNode.data == data) break;
            treeNodeParent = treeNode;
            if (data >= treeNode.data) treeNode = treeNode.rightNode;
            else treeNode = treeNode.leftNode;
        }

        // 没有找到节点
        if (treeNode == null) return;

        TreeNode<Integer> childNode = null;
        // 1. 删除节点没有子节点
        if (treeNode.leftNode == null && treeNode.rightNode == null){
            childNode = null;
            if (treeNodeParent.leftNode == treeNode) treeNodeParent.leftNode = childNode;
            else treeNodeParent.rightNode = childNode;
            return;
        }

        // 2. 删除节点只有一个节点
        if ((treeNode.leftNode !=null && treeNode.rightNode==null)||(treeNode.leftNode ==null && treeNode.rightNode!=null)){
            // 如果此节点是左节点
            if (treeNode.leftNode !=null)  childNode = treeNode.leftNode;
            // 如果此节点是右节点
            else childNode = treeNode.rightNode;
            if (treeNodeParent.leftNode == treeNode) treeNodeParent.leftNode = childNode;
            else treeNodeParent.rightNode = childNode;
            return;
        }


        // 3. 删除的节点有两个子节点都有,这里我们演示的是找到右子节点最小的节点
        if (treeNode.leftNode !=null && treeNode.rightNode!=null){
            TreeNode<Integer> minNode = treeNode.rightNode;
            TreeNode<Integer> minNodeParent = treeNode;
            while (minNode.leftNode!=null){
                minNodeParent = minNode;
                minNode = minNode.leftNode;
            }
            treeNode.data = minNode.data;
            if (minNodeParent.rightNode != minNode) minNodeParent.leftNode = minNode.rightNode;
            else minNodeParent.rightNode = minNode.rightNode;
        }
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
