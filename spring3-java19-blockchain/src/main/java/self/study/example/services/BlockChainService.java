package self.study.example.services;

import org.springframework.stereotype.Service;
import self.study.example.dto.Block;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static self.study.example.constants.BaseConstants.*;

@Service
public class BlockChainService {

    // in-memory to show example
    private final List<Block> blocks;

    public BlockChainService() {
        this.blocks = new ArrayList<>();
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    private String calculateBlockHash(Block block) {
        String dataToHash = block.getPreviousHash()
          + block.getTimeStamp()
          + block.getNonce()
          + block.getData();
        MessageDigest digest;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance(HASH_ALGO);
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
        StringBuilder buffer = new StringBuilder();
        if (bytes != null) {
            for (byte curr : bytes) {
                buffer.append(String.format("%02x", curr));
            }
        }
        return buffer.toString();
    }

    // previous Hash
    public String getPreviousHash() {
        return blocks.get(blocks.size() - 1).getHash();
    }

    // first block
    public Block createGenesisBlock(String data) throws Exception {
        if (blocks.size() != 0) {
            throw new Exception("Blockchain initialized");
        }
        Block block = new Block(data,
          1,
          GENESIS_PREVIOUS_HASH,
          System.currentTimeMillis());
        String hash = calculateBlockHash(block);
        block.setHash(hash);
        blocks.add(block);
        return block;
    }

    // mine block
    public Block mineBlock(Block block) {
        int nonce = 1;
        boolean isMined = false;
        while (!isMined) {
            block.setNonce(nonce);
            String hash = calculateBlockHash(block);
            if (hash.substring(0, DIFFICULTY)
              .equals("0".repeat(DIFFICULTY))) {
                isMined = true;
                block.setHash(hash);
            }
            nonce += 1;
        }
        block.setNonce(nonce);
        return block;
    }

    // create after mineBlock(), mine is only for POW
    public Block createBlock(String data) {
        String previousHash = getPreviousHash();
        Block block = new Block(data, previousHash, System.currentTimeMillis());
        Block hashedBlock = mineBlock(block);
        hashedBlock.setTimeStamp(System.currentTimeMillis());
        blocks.add(hashedBlock);
        return block;
    }

}
