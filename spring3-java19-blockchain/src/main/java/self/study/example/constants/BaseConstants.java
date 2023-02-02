package self.study.example.constants;

public class BaseConstants {
    // leading zeroes like BTC
    public static final int DIFFICULTY = 4;
    // Generic algo
    public static final String HASH_ALGO = "SHA-256";
    // previous hash for 1st block is 0000000
    public static final String GENESIS_PREVIOUS_HASH = "0".repeat(64);
    // Reward for mining a block is 10 units of crypto currency
    public static final int MINER_REWARD = 10;

    // samples to show signing and verification of txn
    public static final String PRIVATE_KEY = "580ac9e16477403b30a1c842bdb109ed6ee65d3a23662f4429ae0224e56511ed";
    public static final String PUBLIC_KEY = "04cee40eff0d85d4c17c4c6a5a921cce67dd4f9fd704fd5ec84944c4007a1356c22bbef69fbb7dd1c28556a328cc0716a3fd3e0395dd1b17d0cc0c9dd1f21f3e02";
}
