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

import java.util.List;

/**
 * Sequence of tones, each of which has a note and duration.
 */
public class ToneSequence {
    /** Assumed beats per minute if not specified using a control pair. */
    public static final int DEFAULT_BEATS_PER_MINUTE = 63;

    /** Assumed duration if not specified using a control pair. */
    public static final int DEFAULT_DURATION = 4;

    /** Assumed defaultOctave if not specified using a control pair. */
    public static final int DEFAULT_OCTAVE = 6;

    /** Name of this ring tone */
    public final String name;

    /** Default octave (used when encoding RTTTL strings) */
    public final int defaultOctave;

    /** Default duration (used when encoding RTTTL strings) */
    public final int defaultDuration;

    /** Default beats per minute (used when encoding RTTTL strings) */
    public final int defaultBeatsPerMinute;

    /** Sequential list of tones that make up this ring tone */
    public final List<Tone> toneList;

    /**
     * Creates a new ring tone, using the default octave ({@link #DEFAULT_OCTAVE}), default duration ({@link #DEFAULT_DURATION})
     * and default beats per minute ({@link #DEFAULT_BEATS_PER_MINUTE}).
     *
     * @param name The name of the ring tone
     * @param toneList The list of tones in this ring tone.
     * @throws IllegalArgumentException If either the name or toneList is null.
     */
    public ToneSequence(String name, List<Tone> toneList) {
        this(name, toneList, DEFAULT_OCTAVE, DEFAULT_DURATION, DEFAULT_BEATS_PER_MINUTE);
    }

    /**
     * Creates a new ring tone.
     *
     * @param name The name of the ring tone
     * @param toneList The list of tones in this ring tone.
     * @param defaultOctave The default octave to use when converting this tone list into an RTTTL string. Must be an int
     *                      between 0 and 8, inclusive.
     * @param defaultDuration The default duration to use when converting this tone list into an RTTTL string. The value 4
     *                        represents a quarter note. Must be one of 1, 2, 4, 8, 16, or 32.
     * @param defaultBeatsPerMinute The default beats per minute to use when converting this tone list into an RTTTL string.
     *                              Must be greater than 0.
     * @throws IllegalArgumentException If either the name or toneList is null.
     */
    public ToneSequence(String name, List<Tone> toneList, int defaultOctave, int defaultDuration,
        int defaultBeatsPerMinute)
    {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }
        if (toneList == null) {
            throw new IllegalArgumentException("tone cannot be null.");
        }
        if (defaultOctave < 0 || defaultOctave > 8) {
            throw new IllegalArgumentException("octave must be between 0-8, inclusive.");
        }
        if (defaultDuration != 1 && defaultDuration != 2 && defaultDuration != 4 && defaultDuration != 8 && defaultDuration != 16
            && defaultDuration != 32)
        {
            throw new IllegalArgumentException("duration must be 1, 2, 4, 8, 16 or 32.");
        }
        if (defaultBeatsPerMinute <= 0) {
            throw new IllegalArgumentException("beats per minute must be > 0.");
        }
        this.name = name;
        this.toneList = toneList;
        this.defaultOctave = defaultOctave;
        this.defaultDuration = defaultDuration;
        this.defaultBeatsPerMinute = defaultBeatsPerMinute;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ToneSequence that = (ToneSequence) o;

        if (defaultOctave != that.defaultOctave) {
            return false;
        }
        if (defaultDuration != that.defaultDuration) {
            return false;
        }
        if (defaultBeatsPerMinute != that.defaultBeatsPerMinute) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }
        return toneList.equals(that.toneList);
    }

    @Override public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + defaultOctave;
        result = 31 * result + defaultDuration;
        result = 31 * result + defaultBeatsPerMinute;
        result = 31 * result + toneList.hashCode();
        return result;
    }

    @Override public String toString() {
        return "ToneSequence{" +
            "name='" + name + '\'' +
            ", defaultOctave=" + defaultOctave +
            ", defaultDuration=" + defaultDuration +
            ", defaultBeatsPerMinute=" + defaultBeatsPerMinute +
            ", toneList=" + toneList +
            '}';
    }
}
