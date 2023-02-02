package self.study.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import self.study.example.dto.Block;
import self.study.example.dto.InputTransaction;
import self.study.example.dto.Transaction;
import self.study.example.services.BlockChainService;
import self.study.example.services.CryptoService;
import self.study.example.services.WalletService;

import java.util.List;
import java.util.Map;

// Demo to showcase the various functionalities
@RestController()
@RequestMapping()
public class DemoController {

    @Autowired
    private BlockChainService _blockService;
    @Autowired
    private WalletService _walletService;

    @Autowired
    private CryptoService _cryptoService;

    @PostMapping("/blocks/init")
    public Block initializeChain(@RequestBody() Transaction transaction) throws Exception {
        return _blockService.createGenesisBlock(transaction.getSignature());
    }

    @PostMapping("/blocks/add")
    public Block addBlock(@RequestBody() Transaction transaction) {
        return _blockService.createBlock(transaction.getSignature());
    }

    @GetMapping("/blocks")
    public List<Block> getChain() {
        return _blockService.getBlocks();
    }

    @PostMapping("/users")
    public int createUser() {
        return _walletService.createWallet();
    }

    @GetMapping("/users")
    public Map<Integer, String> getUsers() {
        return _walletService.getWallets();
    }

    @GetMapping("/mempool")
    public List<Transaction> getTransactions() {
        return _cryptoService.getTransactions();
    }

    @PostMapping("/users/{id}/transaction")
    public Transaction createTransaction(@PathVariable int id, @RequestBody() InputTransaction inputTransaction) {
        return _cryptoService.addTransaction(
          id,
          inputTransaction.getAmount(),
          inputTransaction.getReceiverAddress());
    }

    @PostMapping("/transaction/verify")
    public boolean verifyTransaction(@RequestBody() Transaction transaction) {
        return _cryptoService.verifyTransaction(transaction);
    }
}
