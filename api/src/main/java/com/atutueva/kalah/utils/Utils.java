package com.atutueva.kalah.utils;

public class Utils {
    public static int subArraySum(int[] arr, int length) {
        if (length > arr.length) throw new ArrayIndexOutOfBoundsException();

        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += arr[i];
        }
        return sum;
    }
}
