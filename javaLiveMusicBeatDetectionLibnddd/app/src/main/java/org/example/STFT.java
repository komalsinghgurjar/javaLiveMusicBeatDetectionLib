package org.example;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.ArrayList;
import java.util.List;

public class STFT {
    public static void main(String[] args) {
        // Example audio sample data (time domain)
        // Replace this with your actual PCM audio sample data
        double[] audioSampleData = {
            30, 111, 43, -19, 50, -74, 49, -54,
        41, 70, 26, 121, 7, -90, -13, -89,
        -31, -118, -44, 17, -51, 75, -50, 60,
        -42, -62, -27, -115, -8, 91, 12, 85,
        30, 106, 43, -34, 50, -93, 49, -76,
        41, 52, 26, 108, 7, -93, -13, -84,
        -31, -104, -44, 38, -51, 98, -50, 84,
        -42, -42, -27, -98, -8, 99, 12, 83

        };

        // STFT parameters
        int frameSize = 8;       // Number of samples per frame
        int hopSize = 4;         // Overlap size (frameSize - hopSize defines overlap)
        int samplingRate = 44100; // Sampling rate of the audio in Hz (optional for frequency labeling)

        // Perform STFT
        List<double[]> spectrogram = performSTFT(audioSampleData, frameSize, hopSize);

        // Print spectrogram (magnitudes for each frame)
        System.out.println("Spectrogram:");
        for (int frameIndex = 0; frameIndex < spectrogram.size(); frameIndex++) {
            System.out.print("Frame " + frameIndex + ": ");
            for (double magnitude : spectrogram.get(frameIndex)) {
                System.out.printf("%.3f ", magnitude);
            }
            System.out.println();
        }
    }
    
    public static STFTOutput performSTFT(SampledAudio audio, int frameSize, int hopSize) {
    	// Perform STFT
        List<double[]> spectrogram = performSTFT(convertIntArrayToDoubleArray(audio.getSamples()), frameSize, hopSize);
        
        return new STFTOutput(spectrogram, audio.getSampleRate(), frameSize);
    }
    
    // Perform STFT
    private static List<double[]> performSTFT(double[] audioData, int frameSize, int hopSize) {
        List<double[]> spectrogram = new ArrayList<>();
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);

        // Loop through the audio data in frames with overlap
        for (int start = 0; start + frameSize <= audioData.length; start += hopSize) {
            // Extract the frame
            double[] frame = new double[frameSize];
            System.arraycopy(audioData, start, frame, 0, frameSize);

            // Apply a window function (Hann in this case)
            double[] windowedFrame = applyHannWindow(frame);

            // Perform FFT on the frame
            Complex[] fftResult = fft.transform(windowedFrame, TransformType.FORWARD);

            // Calculate magnitudes (only take first half of FFT output due to symmetry)
            double[] magnitudes = calculateMagnitudes(fftResult);

            // Add magnitudes to the spectrogram
            spectrogram.add(magnitudes);
        }
        return spectrogram;
    }
    
    public static double[] convertIntArrayToDoubleArray(int[] intArray) {
    double[] doubleArray = new double[intArray.length];
    for (int i = 0; i < intArray.length; i++) {
        doubleArray[i] = (double) intArray[i];
    }
    return doubleArray;
}


    // Apply a Hann window to a frame
    private static double[] applyHannWindow(double[] frame) {
        int frameSize = frame.length;
        double[] windowedFrame = new double[frameSize];
        for (int i = 0; i < frameSize; i++) {
            windowedFrame[i] = frame[i] * (0.5 * (1 - Math.cos(2 * Math.PI * i / (frameSize - 1))));
        }
        return windowedFrame;
    }

    // Calculate magnitudes from FFT result
    private static double[] calculateMagnitudes(Complex[] fftResult) {
        int halfSize = fftResult.length / 2; // Take only the first half
        double[] magnitudes = new double[halfSize];
        for (int i = 0; i < halfSize; i++) {
            magnitudes[i] = fftResult[i].abs();
        }
        return magnitudes;
    }
}
