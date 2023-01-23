package basics;

import commons.TreeNode;

public class TreeTraversal {

    // Post Order
    private static void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.val);
    }

    // Pre Order
    private static void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    // In Order
    private static void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.println(root.val);
        inOrder(root.right);
    }

    // Level Order is BFS, check GraphTraversal.java

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1,
          new TreeNode(2,
            new TreeNode(4, null, null),
            new TreeNode(5, null, null)),
          new TreeNode(3,
            new TreeNode(6, null, null),
            new TreeNode(7, null, null)
          )
        );
        System.out.println("************ Post Order *************");
        postOrder(treeNode);
        System.out.println("**************************************");
        System.out.println("************* Pre Order *************");
        preOrder(treeNode);
        System.out.println("**************************************");
        System.out.println("*************** In Order *************");
        inOrder(treeNode);
        System.out.println("**************************************");
    }
}
