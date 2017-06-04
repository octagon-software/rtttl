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

/**
 * Duration of a note (e.g. quarter note, whole note)
 */
public enum Duration
{
    DOTTED_WHOLE(6.0f, null, 1),
    WHOLE(4.0f, DOTTED_WHOLE, 1),
    DOTTED_HALF(3.0f, null, 2),
    HALF(2.0f, DOTTED_HALF, 2),
    DOTTED_QUARTER(1.5f, null, 4),
    QUARTER(1.0f, DOTTED_QUARTER, 4),
    DOTTED_EIGHTH(0.75f, null, 8),
    EIGHTH(0.5f, DOTTED_EIGHTH, 8),
    DOTTED_SIXTEENTH(0.375f, null, 16),
    SIXTEENTH(0.25f, DOTTED_SIXTEENTH, 16),
    DOTTED_THIRTY_SECOND(0.1875f, null, 32),
    THIRTY_SECOND(0.125f, DOTTED_THIRTY_SECOND, 32);

    /**
     * Number of quarterNoteBeats (quarter note) for which the rest or note is sustained
     */
    public final float quarterNoteBeats;

    /**
     * If null, this note is dotted (i.e. it has 50% more duration), else the duration that makes it dotted
     */
    public final Duration dotted;

    /**
     * The denominator of the beat (1 for whole, 2 for half, 4 for quarter, etc.) excluding the dotted.
     */
    public final int beatDenominator;

    Duration(float quarterNoteBeats, Duration dotted, int beatDenominator) {
        this.quarterNoteBeats = quarterNoteBeats;
        this.dotted = dotted;
        this.beatDenominator = beatDenominator;
    }

    /**
     * Returns true if this note is dotted, false if not.
     */
    public boolean isDotted() {
        return this.dotted == null;
    }

    /**
     * Return this duration as a dotted duration (50% more duration)
     *
     * @throws IllegalStateException If this note is already dotted
     */
    public Duration asDotted() {
        if (this.dotted == null) {
            throw new IllegalStateException("Already dotted");
        }
        return this.dotted;
    }

    /**
     * Returns the number of seconds this duration represents given beats per minute
     */
    public float secondsAtBeatsPerMinute(float beatsPerMinute) {
        float secondsPerBeat = 60.0f / beatsPerMinute;
        return this.quarterNoteBeats * secondsPerBeat;
    }
}
