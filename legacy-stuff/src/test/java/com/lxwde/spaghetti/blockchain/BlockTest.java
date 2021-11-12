package com.lxwde.spaghetti.blockchain;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTest {

    @Test
    public void test() {
        String[] genesisTransaction = {"Received $500", "Send $10"};
        Block genesisBlock = new Block(0, genesisTransaction);

        String[] block2Transaction = {"Received $20", "Send $5"};
        Block block2 = new Block(genesisBlock.getBlockHash(), block2Transaction);

        String[] block3Transaction = {"Received $1000", "Send $50"};
        Block block3 = new Block(block2.getBlockHash(), block3Transaction);

        System.out.println("Hash of genesis block: " + genesisBlock.getBlockHash());
        System.out.println("Hash of block2: " + block2.getBlockHash());
        System.out.println("Hash of block3: " + block3.getBlockHash());
    }

}