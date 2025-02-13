package com.backend.services.random;

import java.util.Random;

public class RandomString {

    private StringBuilder result;
    private Random random;
    private int[] prohibitedСharacters;/* / (47), \ (92), : (58), * (42), ? (63), <> (60, 62), | (124) */ 

    public String noRestrictionsStr(int length) {
        this.random.setSeed(System.nanoTime());
        for (int i = 0; i < length; i++) {
            this.result.append((char) this.random.nextInt(33, 126));
        }
        return result.toString();
    }

    public String fileNameRandomStr(int length) {
        this.random.setSeed(System.nanoTime());
        int j;
        for (int i = 0; i < length; i++) {
            int randTmp = this.random.nextInt(33, 126);
            for (j = 0; j < this.prohibitedСharacters.length;j++) {

                if (randTmp == prohibitedСharacters[j]) {
                    randTmp = this.random.nextInt(33, 126);
                    j = 0;
                } 

            }
            this.result.append((char) randTmp);
        }
        return this.result.toString();

    }

    public RandomString() {
        this.result = new StringBuilder();
        this.random = new Random();
        this.prohibitedСharacters = new int[] { 47, 92, 58, 42, 63, 60, 62, 124 };
        
    }

}
