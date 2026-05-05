package com.example.passwordproject;

import android.os.Environment;
import android.os.StatFs;

public class StorageRule implements MainActivity.PasswordRule {
    @Override
    public String getHint() { return "Must include the first digit of your remaining GB of storage."; }
    @Override
    public boolean validate(String text) {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        long gbAvailable = bytesAvailable / (1024 * 1024 * 1024);
        String firstDigit = String.valueOf(String.valueOf(gbAvailable).charAt(0));
        return text.contains(firstDigit);
    }
}