package codeinterviews.chapter2;

public class BinaryTree <Key extends Comparable<Key>,Value>{
    private Node root;
    private class Node {
        private Key key;
        private Value val;
        private Node left,right;
        private int N;
        private Node(Key key, Value val ,int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }

        public int size() {
            return size(root);
        }
        private int size(Node x) {
            if (x == null) return 0;
            else return x.N;
        }
    }
}
