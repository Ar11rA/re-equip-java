package programs;

import java.util.*;

public class ZigZag {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int val) {this.val = val;}

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private static List<Integer> largestValues(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int max = Integer.MIN_VALUE;
            List<TreeNode> next = new ArrayList<>();
            int currentSize = queue.size();
            while (currentSize > 0) {
                currentSize--;
                TreeNode tmp = queue.poll();
                if (tmp == null) {
                    continue;
                }
                if (tmp.val > max) {
                    max = tmp.val;
                }
                if (tmp.left != null) {
                    next.add(tmp.left);
                }
                if (tmp.right != null) {
                    next.add(tmp.right);
                }
            }
            result.add(max);
            next.forEach(queue::offer);
        }
        return result;
    }

    private static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayList<List<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        boolean direction = false;

        if (root == null) return ans;

        q.add(root);

        while (!q.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            int size = q.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = q.peek();
                q.poll();
                list.add(node.val);

                if (node.left != null)
                    q.add(node.left);

                if (node.right != null)
                    q.add(node.right);

                if (i == size - 1 && direction)
                    Collections.reverse(list);
            }
            ans.add(list);
            direction = !direction;
        }
        return ans;
    }

    public static void main(String[] args) {
        TreeNode node = new TreeNode(3,
          new TreeNode(9, null, null),
          new TreeNode(20,
            new TreeNode(15, null, null),
            new TreeNode(7, null, null)));
        List<List<Integer>> answer = zigzagLevelOrder(node);
        for (List<Integer> item : answer) {
            item.forEach(System.out::print);
            System.out.println();
        }
        List<Integer> max = largestValues(node);
        max.forEach(System.out::println);
        System.out.println();
    }
}
