package programs;

import commons.TreeNode;

public class MergeTree {
    private static TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return null;
        }

        if (root1 == null) {
            return root2;
        }

        if (root2 == null) {
            return root1;
        }
        TreeNode result = new TreeNode(root1.val + root2.val);
        result.left = mergeTrees(root1.left, root2.left);
        result.right = mergeTrees(root1.right, root2.right);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(mergeTrees(null ,null));
        System.out.println(mergeTrees(null ,new TreeNode(1, null, null)).val);
    }
}
