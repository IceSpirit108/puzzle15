package com.example.puzzle15;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;

public class Utils {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_PLAYER_NAME = "player_name";
    private static final String KEY_RECORD_MOVES = "record_moves";
    private static final String KEY_RECORD_TIMES = "record_times";
    private static final String KEY_RECORD_NAMES = "record_names";

    public static void savePlayerName(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_PLAYER_NAME, name).apply();
    }

    public static String getPlayerName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_PLAYER_NAME, "");
    }

    public static void initRecordsIfNeeded(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (!prefs.contains(KEY_RECORD_MOVES)) {

            int[] moves = new int[10];
            int[] times = new int[10];
            Arrays.fill(moves, Integer.MAX_VALUE);
            Arrays.fill(times, Integer.MAX_VALUE);
            saveRecords(context, moves, times);
        }
    }

    public static void saveRecords(Context context, int[] moves, int[] times) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_RECORD_MOVES, arrayToString(moves))
                .putString(KEY_RECORD_TIMES, arrayToString(times))
                .apply();
    }

    public static int[] getRecordMoves(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return stringToArray(prefs.getString(KEY_RECORD_MOVES, ""));
    }

    public static int[] getRecordTimes(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return stringToArray(prefs.getString(KEY_RECORD_TIMES, ""));
    }

    public static void saveRecordNames(Context context, String[] names) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_RECORD_NAMES, arrayToString(names))
                .apply();
    }

    public static String[] getRecordNames(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return stringToStringArray(prefs.getString(KEY_RECORD_NAMES, ""));
    }

    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(",");
        }
        return sb.toString();
    }

    private static String arrayToString(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(",");
        }
        return sb.toString();
    }

    private static String[] stringToStringArray(String s) {
        if (s == null || s.isEmpty()) {
            String[] emptyArr = new String[10];
            Arrays.fill(emptyArr, "---");
            return emptyArr;
        }
        return s.split(",");
    }


    private static int[] stringToArray(String s) {
        if (s == null || s.isEmpty()) {
            return new int[10];
        }
        String[] parts = s.split(",");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        return arr;
    }


    public static void addNewRecord(Context context, int moves, int timeInSeconds) {
        int[] movesArr = getRecordMoves(context);
        int[] timesArr = getRecordTimes(context);
        String[] namesArr = getRecordNames(context);
        String currentName = getPlayerName(context);

        for (int i = 0; i < 10; i++) {
            if (timeInSeconds < timesArr[i] || moves < movesArr[i]) {
                for (int j = 9; j > i; j--) {
                    timesArr[j] = timesArr[j - 1];
                    movesArr[j] = movesArr[j - 1];
                    namesArr[j] = namesArr[j - 1];
                }
                timesArr[i] = timeInSeconds;
                movesArr[i] = moves;
                namesArr[i] = currentName;
                break;
            }
        }

        saveRecords(context, movesArr, timesArr);
        saveRecordNames(context, namesArr);
    }
}