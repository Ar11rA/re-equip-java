package commons;

public class MatrixPair {
    public int i;
    public int j;
    public String word;

    public int start;
    public MatrixPair(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public MatrixPair(int i, int j, String word) {
        this.i = i;
        this.j = j;
        this.word = word;
    }

    public MatrixPair(int i, int j, String word, int start) {
        this.i = i;
        this.j = j;
        this.word = word;
        this.start = start;
    }
}
