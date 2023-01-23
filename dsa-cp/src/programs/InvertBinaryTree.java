package programs;

import com.sun.source.tree.Tree;
import commons.TreeNode;

public class InvertBinaryTree {

    private static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        Helper(root);
        return root;
    }

    private static void Helper(TreeNode root) {
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        invertTree(root.left);
        invertTree(root.right);
    }

    public static void main(String[] args) {
        TreeNode node = new TreeNode(1,
          new TreeNode(2, null, null),
          new TreeNode(3, null, null));
        TreeNode inverted = invertTree(node);
        System.out.println(inverted.val);
        System.out.println(inverted.left.val);
        System.out.println(inverted.right.val);
    }
}


