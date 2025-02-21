package gurjar.singh.komal.javaLiveMusicBeatDetectionLib;

import java.util.Arrays;
import java.lang.Math;

public class PCMAudioWaveformGenerator {
    private int[] frequencies; // Frequencies in Hz
    private int sampleCount;   // Number of audio samples to generate (must be a power of 2)
    private int sampleRate;    // Sampling rate in Hz (e.g., 44100 for standard audio)
    private int bitDepth;      // Bit depth for PCM encoding (e.g., 8, 16, 24, or 32)

    /**
     * Constructor for PCMAudioWaveformGenerator.
     *
     * @param frequencies Array of frequencies in Hz to generate.
     * @param sampleCount Number of audio samples to generate (must be a power of 2).
     * @param sampleRate  Sampling rate in Hz (e.g., 44100 for standard audio sampling rate).
     * @param bitDepth    Bit depth for PCM encoding (must be 8, 16, 24, or 32).
     */
    public PCMAudioWaveformGenerator(int[] frequencies, int sampleCount, int sampleRate, int bitDepth) {
        validateInputs(frequencies, sampleCount, sampleRate, bitDepth);
        this.frequencies = Arrays.copyOf(frequencies, frequencies.length);
        this.sampleCount = sampleCount;
        this.sampleRate = sampleRate;
        this.bitDepth = bitDepth;
    }

    /**
     * Validates the inputs for the constructor.
     */
    private void validateInputs(int[] frequencies, int sampleCount, int sampleRate, int bitDepth) {
        if (frequencies == null || frequencies.length == 0) {
            throw new IllegalArgumentException("Frequencies array must not be null or empty.");
        }
        if (sampleCount <= 0 || (sampleCount & (sampleCount - 1)) != 0) {
            throw new IllegalArgumentException("Sample count must be a positive power of 2.");
        }
        if (sampleRate <= 0) {
            throw new IllegalArgumentException("Sample rate must be a positive integer.");
        }
        if (bitDepth != 8 && bitDepth != 16 && bitDepth != 24 && bitDepth != 32) {
            throw new IllegalArgumentException("Bit depth must be 8, 16, 24, or 32.");
        }
    }

    /**
     * Generates PCM audio sample data as an array of integers.
     *
     * @return An integer array containing PCM-encoded audio sample data.
     */
    public int[] generateAudioData() {
        int[] audioSamples = new int[sampleCount];
        int maxAmplitude = (1 << (bitDepth - 1)) - 1; // Maximum value for given bit depth
        double twoPi = 2.0 * Math.PI;

        for (int i = 0; i < sampleCount; i++) {
            double sampleValue = 0.0;

            // Sum of sine waves for all frequencies
            for (int frequency : frequencies) {
                sampleValue += Math.sin(twoPi * frequency * i / sampleRate);
            }

            // Normalize and scale to the integer PCM range
            audioSamples[i] = (int) Math.round(sampleValue * maxAmplitude / frequencies.length);
        }

        return audioSamples;
    }
    
    
    public SampledAudio generateSampledAudio(){
    	// Create a SampledAudio object
        SampledAudio audio = new SampledAudio(this.generateAudioData(), sampleRate, bitDepth);
        return audio;
    }

    /**
     * Returns the frequencies used for audio generation.
     */
    public int[] getFrequencies() {
        return Arrays.copyOf(frequencies, frequencies.length);
    }

    /**
     * Sets the frequencies for audio generation.
     */
    public void setFrequencies(int[] frequencies) {
        if (frequencies == null || frequencies.length == 0) {
            throw new IllegalArgumentException("Frequencies array must not be null or empty.");
        }
        this.frequencies = Arrays.copyOf(frequencies, frequencies.length);
    }

    /**
     * Returns the number of audio samples.
     */
    public int getSampleCount() {
        return sampleCount;
    }

    /**
     * Sets the number of audio samples to generate.
     */
    public void setSampleCount(int sampleCount) {
        if (sampleCount <= 0 || (sampleCount & (sampleCount - 1)) != 0) {
            throw new IllegalArgumentException("Sample count must be a positive power of 2.");
        }
        this.sampleCount = sampleCount;
    }

    /**
     * Returns the sampling rate in Hz.
     */
    public int getSampleRate() {
        return sampleRate;
    }

    /**
     * Sets the sampling rate in Hz.
     */
    public void setSampleRate(int sampleRate) {
        if (sampleRate <= 0) {
            throw new IllegalArgumentException("Sample rate must be a positive integer.");
        }
        this.sampleRate = sampleRate;
    }

    /**
     * Returns the bit depth for PCM encoding.
     */
    public int getBitDepth() {
        return bitDepth;
    }

    /**
     * Sets the bit depth for PCM encoding.
     */
    public void setBitDepth(int bitDepth) {
        if (bitDepth != 8 && bitDepth != 16 && bitDepth != 24 && bitDepth != 32) {
            throw new IllegalArgumentException("Bit depth must be 8, 16, 24, or 32.");
        }
        this.bitDepth = bitDepth;
    }
}


// सोनू दोहरे so पहलवान दोहरे
// प्रभु दयाल so रामप्रसाद
// सुसपाल (उर्फ़ संदीप) so प्रभु दयाल
// अनिल so रामधनी
// बीरेंद्र गौतम so रामसनयी
// बुद्धे so दयाराम
// अतुल so बहादुर

// abhimanyu urf dimphal so arjun singh
// shyamkaran so ayudhi
