package com.lxwde.spaghetti.neuralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Network {
    private int numLayers;
    private List<Integer> sizes;
    private List<Double> biases = new ArrayList<Double>();
    private List<Double> weights = new ArrayList<Double>();

    public Network(List<Integer> sizes){
        this.numLayers = sizes.size();
        this.sizes = sizes;
        Random random = new Random();
        for(int i = 1; i < sizes.size(); i++) {
            biases.add(random.nextGaussian());
        }
    }
}
