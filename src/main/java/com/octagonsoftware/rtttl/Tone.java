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
 * Immutable, single tone, containing a note and duration.
 */
public class Tone {
    /** The note in this tone */
    public final Note note;

    /** The duration of this tone, in seconds */
    public final float durationInSeconds;

    /**
     * Creates a new tone with the given note and duration in seconds
     *
     * @param note The note in this tone, or null if this is a rest.
     * @param durationInSeconds The duration of this tone, in seconds.
     * @throws IllegalArgumentException If the note is null.
     */
    public Tone(Note note, float durationInSeconds) {
        this.note = note;
        this.durationInSeconds = durationInSeconds;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tone tone = (Tone) o;

        if (Float.compare(tone.durationInSeconds, durationInSeconds) != 0) {
            return false;
        }
        return note == tone.note;
    }

    @Override public int hashCode() {
        int result = note != null ? note.hashCode() : 0;
        result = 31 * result + (durationInSeconds != +0.0f ? Float.floatToIntBits(durationInSeconds) : 0);
        return result;
    }

    @Override public String toString() {
        return "Tone{" +
            "note=" + note +
            ", durationInSeconds=" + durationInSeconds +
            '}';
    }
}
