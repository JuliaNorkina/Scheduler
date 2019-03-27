package com.example.jobservice;

public final class MathUtil {
    private MathUtil() { }

    public static int calculateFactorial(int number) {
        int fact = 1;
        for (int count = number; count > 1; count--) {
            fact = fact * count;
        }
        return fact;
    }
}