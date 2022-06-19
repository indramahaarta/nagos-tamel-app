package com.code.nagostamelapp.dashboard.controller;

public class ConverterToRupiah {
    public static String convert(Float f) {
        String amountStr = ",00";
        String floatStr = f.toString();
        String real = "";

        for (var i =0; i<floatStr.length(); i++) {
            char x = floatStr.charAt(i);

            if (x == '.') {
                break;
            }

            real += x;
        }

        int c = 0;

        for (var i = real.length()-1; i >= 0; i-=1) {
            amountStr = real.charAt(i) + amountStr;
            c++;

            if (c ==3) {
                c = 0;
                amountStr = "." + amountStr;
            }
        }

        amountStr = "Rp" + amountStr;

        return amountStr;
    }
}
