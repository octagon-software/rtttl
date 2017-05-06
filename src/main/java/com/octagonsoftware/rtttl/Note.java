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

import java.util.HashMap;
import java.util.Map;

/**
 * Notes on a musical scale, along with MIDI semitones and note frequencies.
 * <p>
 * This enum contains notes from octave 0 through 8, inclusive.
 * <p>
 * There is also a utility method to the closest note given hz.
 */
public enum Note {
    C0  ("C",   0, 12),
    CS0 ("C#",  0, 13),
    D0  ("D",   0, 14),
    DS0 ("D#",  0, 15),
    E0  ("E",   0, 16),
    F0  ("F",   0, 17),
    FS0 ("F#",  0, 18),
    G0  ("G",   0, 19),
    GS0 ("G#",  0, 20),
    A0  ("A",   0, 21),
    AS0 ("A#",  0, 22),
    B0  ("B",   0, 23),
    C1  ("C",   1, 24),
    CS1 ("C#",  1, 25),
    D1  ("D",   1, 26),
    DS1 ("D#",  1, 27),
    E1  ("E",   1, 28),
    F1  ("F",   1, 29),
    FS1 ("F#",  1, 30),
    G1  ("G",   1, 31),
    GS1 ("G#",  1, 32),
    A1  ("A",   1, 33),
    AS1 ("A#",  1, 34),
    B1  ("B",   1, 35),
    C2  ("C",   2, 36),
    CS2 ("C#",  2, 37),
    D2  ("D",   2, 38),
    DS2 ("D#",  2, 39),
    E2  ("E",   2, 40),
    F2  ("F",   2, 41),
    FS2 ("F#",  2, 42),
    G2  ("G",   2, 43),
    GS2 ("G#",  2, 44),
    A2  ("A",   2, 45),
    AS2 ("A#",  2, 46),
    B2  ("B",   2, 47),
    C3  ("C",   3, 48),
    CS3 ("C#",  3, 49),
    D3  ("D",   3, 50),
    DS3 ("D#",  3, 51),
    E3  ("E",   3, 52),
    F3  ("F",   3, 53),
    FS3 ("F#",  3, 54),
    G3  ("G",   3, 55),
    GS3 ("G#",  3, 56),
    A3  ("A",   3, 57),
    AS3 ("A#",  3, 58),
    B3  ("B",   3, 59),
    C4  ("C",   4, 60),
    CS4 ("C#",  4, 61),
    D4  ("D",   4, 62),
    DS4 ("D#",  4, 63),
    E4  ("E",   4, 64),
    F4  ("F",   4, 65),
    FS4 ("F#",  4, 66),
    G4  ("G",   4, 67),
    GS4 ("G#",  4, 68),
    A4  ("A",   4, 69),
    AS4 ("A#",  4, 70),
    B4  ("B",   4, 71),
    C5  ("C",   5, 72),
    CS5 ("C#",  5, 73),
    D5  ("D",   5, 74),
    DS5 ("D#",  5, 75),
    E5  ("E",   5, 76),
    F5  ("F",   5, 77),
    FS5 ("F#",  5, 78),
    G5  ("G",   5, 79),
    GS5 ("G#",  5, 80),
    A5  ("A",   5, 81),
    AS5 ("A#",  5, 82),
    B5  ("B",   5, 83),
    C6  ("C",   6, 84),
    CS6 ("C#",  6, 85),
    D6  ("D",   6, 86),
    DS6 ("D#",  6, 87),
    E6  ("E",   6, 88),
    F6  ("F",   6, 89),
    FS6 ("F#",  6, 90),
    G6  ("G",   6, 91),
    GS6 ("G#",  6, 92),
    A6  ("A",   6, 93),
    AS6 ("A#",  6, 94),
    B6  ("B",   6, 95),
    C7  ("C",   7, 96),
    CS7 ("C#",  7, 97),
    D7  ("D",   7, 98),
    DS7 ("D#",  7, 99),
    E7  ("E",   7, 100),
    F7  ("F",   7, 101),
    FS7 ("F#",  7, 102),
    G7  ("G",   7, 103),
    GS7 ("G#",  7, 104),
    A7  ("A",   7, 105),
    AS7 ("A#",  7, 106),
    B7  ("B",   7, 107),
    C8  ("C",   8, 108),
    CS8 ("C#",  8, 109),
    D8  ("D",   8, 110),
    DS8 ("D#",  8, 111),
    E8  ("E",   8, 112),
    F8  ("F",   8, 113),
    FS8 ("F#",  8, 114),
    G8  ("G",   8, 115),
    GS8 ("G#",  8, 116),
    A8  ("A",   8, 117),
    AS8 ("A#",  8, 118),
    B8  ("B",   8, 119)
    ;

    /** Minimum semitone, for C0 note */
    public static int MIN_SEMITONE = Note.C0.semitone;

    /** Maximum semitone, for B8 note */
    public static int MAX_SEMITONE = Note.B8.semitone;

    /** Map from semitone to note */
    private static final Note[] semitoneToNote = new Note[MAX_SEMITONE + 1];
    static {
        for (Note note : Note.values()) {
            semitoneToNote[note.semitone] = note;
        }
    }

    /** Map from name to note */
    private static final Map<String, Note> nameToNote = new HashMap<String, Note>();
    static {
        for (Note note : Note.values()) {
            nameToNote.put(note.note + note.octave, note);
        }
    }

    /** Name of this note, such as "C" or "C#". Only sharps are named, not flats. */
    public final String note;

    /** The octave of this note, where in octave 4, A = 440 Hz */
    public final int octave;

    /** The MIDI semitone of this note, where semitone = 69 + 12 * log2(freq / 440) */
    public final int semitone;

    /** The frequency of this note, in hz */
    public final float hz;

    /** True if this note is sharp, else false */
    public final boolean sharp;

    /** Initializes this enum instance */
    Note(String note, int octave, int semitone) {
        this.note = note;
        this.octave = octave;
        this.semitone = semitone;
        this.hz = hzFromSemitone(semitone);
        this.sharp = note.length() > 1;
    }

    /**
     * Finds the note with the given name (e.g. "C#4").
     *
     * @param name The name of the note. Must be uppercase and include the octave.
     * @return The note, or null if the note with the given name is not found.
     */
    public static Note findNoteByName(String name) {
        return nameToNote.get(name);
    }

    /**
     * Returns the note for the given MIDI semitone.
     *
     * @throws IndexOutOfBoundsException if the semitone is below MIN_SEMITONE or above MAX_SEMITONE.
     */
    public static Note findNoteFromSemitone(int semitone)
        throws IndexOutOfBoundsException
    {
        if ((semitone < MIN_SEMITONE) || (semitone > MAX_SEMITONE)) {
            throw new IndexOutOfBoundsException("Semitone " + semitone + " is out of bounds.");
        }
        return semitoneToNote[semitone];
    }

    /** Returns the closest note for the given frequency */
    public static Note findClosestNote(float hz) {
        int semitone = Math.round((float) (69 + 12 * (Math.log(hz / 440.0) / Math.log(2))));
        if (semitone < MIN_SEMITONE) {
            semitone = MIN_SEMITONE;
        }
        if (semitone > MAX_SEMITONE) {
            semitone = MAX_SEMITONE;
        }
        return semitoneToNote[semitone];
    }

    /** Returns the MIDI semitone, given a frequency */
    public static float semitoneFromHz(float hz) {
        return (float) (69 + 12 * (Math.log(hz / 440) / Math.log(2)));
    }

    /** Returns the frequency, given a MIDI semitone */
    public static float hzFromSemitone(float semitone) {
        return (float) (Math.pow(2, (semitone - 69) / 12) * 440);
    }
}
