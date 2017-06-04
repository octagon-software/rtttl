/*
 * Copyright (c) 2017 Octagon Software, LLC
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.octagonsoftware.rtttl;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for ToneSequence class.
 */
public class ToneSequenceTest {
    @Test
    public void testGeneralConstructor() {
        List<Tone> toneList = createToneList();
        String toneName = "name";
        ToneSequence seq = new ToneSequence(toneName, toneList);
        Assert.assertEquals(toneName, seq.name);
        Assert.assertEquals(toneList, seq.toneList);
        Assert.assertEquals(ToneSequence.DEFAULT_OCTAVE, seq.defaultOctave);
        Assert.assertEquals(ToneSequence.DEFAULT_DURATION, seq.defaultDuration);
        Assert.assertEquals(ToneSequence.DEFAULT_BEATS_PER_MINUTE, seq.beatsPerMinute);
    }

    @Test
    public void testSpecificConstructor() {
        List<Tone> toneList = createToneList();
        String toneName = "name";
        int octave = ToneSequence.DEFAULT_OCTAVE + 1;
        Duration duration = Duration.HALF;
        int beatsPerMinute = ToneSequence.DEFAULT_BEATS_PER_MINUTE + 1;
        ToneSequence seq = new ToneSequence(toneName, toneList, octave, duration, beatsPerMinute);
        Assert.assertEquals(toneName, seq.name);
        Assert.assertEquals(toneList, seq.toneList);
        Assert.assertEquals(octave, seq.defaultOctave);
        Assert.assertEquals(duration, seq.defaultDuration);
        Assert.assertEquals(beatsPerMinute, seq.beatsPerMinute);
    }

    @Test
    public void testInvalidOctave() {
        List<Tone> toneList = createToneList();
        String toneName = "name";
        Duration duration = Duration.HALF;
        int beatsPerMinute = ToneSequence.DEFAULT_BEATS_PER_MINUTE + 1;
        try {
            int octave = -1;
            new ToneSequence(toneName, toneList, octave, duration, beatsPerMinute);
            Assert.fail("Should have failed with IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
        try {
            int octave = 9;
            new ToneSequence(toneName, toneList, octave, duration, beatsPerMinute);
            Assert.fail("Should have failed with IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testInvalidBeatsPerMinute() {
        List<Tone> toneList = createToneList();
        String toneName = "name";
        int octave = ToneSequence.DEFAULT_OCTAVE + 1;
        Duration duration = Duration.HALF;
        try {
            int beatsPerMinute = 0;
            new ToneSequence(toneName, toneList, octave, duration, beatsPerMinute);
            Assert.fail("Should have failed with IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    private List<Tone> createToneList() {
        List<Tone> result = new ArrayList<Tone>();
        result.add(new Tone(Note.A4, Duration.QUARTER));
        result.add(new Tone(null, Duration.QUARTER));
        return result;
    }
}