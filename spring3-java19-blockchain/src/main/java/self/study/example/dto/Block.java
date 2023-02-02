package self.study.example.dto;

import lombok.Data;

@Data
public class Block {
    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;

    public Block(String data, String previousHash, long timeStamp) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
    }

    public Block(String data, String previousHash, long timeStamp, String hash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.hash = hash;
    }

    public Block(String data, int nonce, String previousHash, long timeStamp) {
        this.data = data;
        this.nonce = nonce;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
    }

}