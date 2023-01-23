package programs;

import commons.TreeNode;

public class SymmetricTree {
    private static boolean check(TreeNode root) {
        if (root == null) {
            return true;
        }
        return helper(root.left, root.right);
    }

    private static boolean helper(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        return helper(left.left, right.right) && helper(left.right, right.left);
    }

    public static void main(String[] args) {
        TreeNode node = new TreeNode(5,
          new TreeNode(4, null, null),
          new TreeNode(3, null, null));
        System.out.println("Symmetric: " + check(node));
    }
}
