package com.example.android.notepad.internal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.nio.ByteBuffer;

import static java.util.Arrays.fill;

/**
 * Created by Akash on 9/9/2014.
 */
public class Utilities implements IUtilities, SensorEventListener {

    private final static String HEX = "0123456789ABCDEF";
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private byte[] seedBuffer = new byte[12];
    private boolean seedUpdated = false;

    public Utilities(Context mContext) {
        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        byte[] sensorData;
        sensorData = ByteBuffer.allocate(4).putFloat(sensorEvent.values[0]).array();
        System.arraycopy(sensorData, 0, seedBuffer, 0, 4);
        sensorData = ByteBuffer.allocate(4).putFloat(sensorEvent.values[1]).array();
        System.arraycopy(sensorData, 0, seedBuffer, 4, 4);
        sensorData = ByteBuffer.allocate(4).putFloat(sensorEvent.values[2]).array();
        System.arraycopy(sensorData, 0, seedBuffer, 8, 4);
        seedUpdated = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void whiteoutData(byte[] data) {
        fill(data, (byte) 0);
    }

    public void whiteoutChar(char[] data) {
        fill(data, (char) 0);
    }

    public byte[] generateSeed() {
        seedUpdated = false;
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        while(!seedUpdated);
        sensorManager.unregisterListener(this);
        return seedBuffer;
    }

    public byte[] appendBytes(byte[] firstArray, byte[] secondArray) {
        byte[] result = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, result, 0, firstArray.length);
        System.arraycopy(secondArray, 0, result, firstArray.length, secondArray.length);
        return result;
    }

    public byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    public String toHex(byte[] buffer) {
        if (buffer == null)
            return "";
        StringBuilder result = new StringBuilder(2 * buffer.length);
        for (byte value : buffer) {
            appendHex(result, value);
        }
        return result.toString();
    }

    private static void appendHex(StringBuilder buf, byte ch) {
        buf.append(HEX.charAt((ch >> 4) & 0x0f)).append(HEX.charAt(ch & 0x0f));
    }
}