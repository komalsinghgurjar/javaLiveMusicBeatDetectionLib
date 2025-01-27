package org.example;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //testPCMAudioWaveformGenerator();
        //testPCMGeneratorAndWAVSaver();
        testStft();
    }
    
    
    public static void testStft(){
    	int[] frequencies = {8268, 1};
        int sampleCount = 1024; // Must be a power of 2
        int sampleRate = 44100; // Standard audio rate
        int bitDepth = 16;      // 16-bit PCM

        PCMAudioWaveformGenerator generator = new PCMAudioWaveformGenerator(frequencies, sampleCount, sampleRate, bitDepth);
        SampledAudio audio = generator.generateSampledAudio();
        
        int frameSize = 64;       // Number of samples per frame (should be power of 2)
        int hopSize = 32;         // Overlap size (frameSize - hopSize defines overlap)
        
        STFTOutput output = STFT.performSTFT(audio, frameSize, hopSize);
        List<double[]> spectrogram = output.getStftSpectrogram();
        
        // Print spectrogram (magnitudes for each frame)
        System.out.println("Spectrogram:");
        for (int frameIndex = 0; frameIndex < spectrogram.size(); frameIndex++) {
            System.out.print("Frame " + frameIndex + ": ");
            //for (double magnitude : spectrogram.get(frameIndex)) {
            for (int magIndex = 0; magIndex < spectrogram.get(frameIndex).length; magIndex++) {
            	double magnitude = spectrogram.get(frameIndex)[magIndex];
                double frequency = output.getFrequencies()[magIndex];  
                System.out.printf("%.3f Hz %.3f, ", frequency, magnitude);
            }
            System.out.println();
            System.out.println();
        }
    }
    
    public static void testPCMGeneratorAndWAVSaver() {
int[] frequencies = {4400, 800, 100, 2000}; // A4 and A5 notes
int sampleCount = 128*128*128;       // 47 approx second of audio at 44100 Hz
int sampleRate = 44100;        // Standard audio sampling rate
int bitDepth = 16;             // 16-bit audio

PCMAudioWaveformGenerator generator = new PCMAudioWaveformGenerator(frequencies, sampleCount, sampleRate, bitDepth);
SampledAudio audio = generator.generateSampledAudio();
SampledAudioFileSaver fileSaver= new SampledAudioFileSaver(audio);
String filePath = "/sdcard/output.wav";
try {
	fileSaver.saveAsWAV(filePath);
    System.out.println("WAV file saved successfully!");
} catch (IOException e) {
    e.printStackTrace();
}
    }
    
    public static void testPCMAudioWaveformGenerator() {
        // Example: Generate PCM data for two frequencies (440 Hz and 880 Hz)
        int[] frequencies = {15000};
        int sampleCount = 1024; // Must be a power of 2
        int sampleRate = 44100; // Standard audio rate
        int bitDepth = 8;      // 16-bit PCM

        PCMAudioWaveformGenerator generator = new PCMAudioWaveformGenerator(frequencies, sampleCount, sampleRate, bitDepth);
        SampledAudio audio = generator.generateSampledAudio();
        
        int[] pcmData = audio.getSamples();
        
        // Output the first few samples
        System.out.println("Generated PCM Audio Data:");
        for (int i = 0; i < pcmData.length; i++) {
            System.out.println(pcmData[i]);
        }
    }
}
