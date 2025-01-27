package org.example;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import java.lang.Math;
import java.util.Arrays;

public class FFT {
    public static void main(String[] args) {
        // Example audio sample data (time domain)
        // Replace with actual audio data
        // DFT transformation using FFT algorithm imolementation
        // Should be multiple of 2 
        double[] audioSampleDataTmp = {
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0
        };
        
        double[] audioSampleData = new double[128];
        // Number of samples to generate
        // Should be power of 2
        int N = 128;
        
        // generate audio data for 1khz 5khz 19khz of smpling 44.1khz
        for (int i = 0; i < N; i++) {
            audioSampleData[i] = Math.sin(2 * Math.PI * 1000 * i / 44100) + 
                         Math.sin(2 * Math.PI * 5000 * i / 44100) + 
                         Math.sin(2 * Math.PI * 19000 * i / 44100);
}

System.out.println("Generated Data");
System.out.println(Arrays.toString(audioSampleData));

        // Apply a window function (Hann window in this example)
        double[] windowedData = applyHannWindow(audioSampleData);
        //double[] windowedData = applyTukeyWindow(audioSampleData);

        // Perform FFT
        Complex[] fftResult = performFFT(windowedData);

        // Calculate magnitudes for the spectrogram
        double[] magnitudes = calculateMagnitudes(fftResult);

        double samplingRate = 44100; // For frequency bin calculation
        double[] frequencies = calculateFrequencies(audioSampleData.length, samplingRate);

        System.out.println("Generated array length");
        System.out.println(magnitudes.length);
        System.out.println(frequencies.length);

        System.out.println("Generated Frequency Domain Magnitudes");
        // Print magnitudes
        for (int i = 0; i < magnitudes.length; i++) {
            System.out.printf("Frequency: %.2f Hz, Magnitude: %.2f%n", frequencies[i], magnitudes[i]);
        }
    }

// May be wrong
// Apply a Tukey window to the audio data
private static double[] applyTukeyWindow(double[] data, double alpha) {
    double[] windowedData = new double[data.length];
    int N = data.length;

    for (int i = 0; i < N; i++) {
        double t = (double) i / (N - 1);
        if (t < alpha / 2) {
            // Rising cosine section
            windowedData[i] = data[i] * (0.5 * (1 + Math.cos(2 * Math.PI * (t - (alpha / 2)) / alpha)));
        } else if (t <= 1 - alpha / 2) {
            // Flat (rectangular) section
            windowedData[i] = data[i];
        } else {
            // Falling cosine section
            windowedData[i] = data[i] * (0.5 * (1 + Math.cos(2 * Math.PI * (t - 1 + (alpha / 2)) / alpha)));
        }
    }

    return windowedData;
}

// Method to calculate frequency bins
private static double[] calculateFrequencies(int dataLength, double samplingRate) {
    int halfLength = dataLength / 2; // Only consider the first half of the FFT result
    double[] frequencies = new double[halfLength];
    for (int i = 0; i < halfLength; i++) {
        frequencies[i] = i * samplingRate / dataLength;
    }
    return frequencies;
}

    // Apply a Hann window to the audio data
    private static double[] applyHannWindow(double[] data) {
        double[] windowedData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            windowedData[i] = data[i] * (0.5 * (1 - Math.cos(2 * Math.PI * i / (data.length - 1))));
        }
        return windowedData;
    }

    // Perform FFT on the windowed data
    private static Complex[] performFFT(double[] data) {
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        return fft.transform(data, TransformType.FORWARD);
    }

    // Calculate magnitudes from FFT result
    private static double[] calculateMagnitudes(Complex[] fftResult) {
        double[] magnitudes = new double[fftResult.length / 2];
        for (int i = 0; i < magnitudes.length; i++) {
            magnitudes[i] = fftResult[i].abs();
        }
        return magnitudes;
    }
}
