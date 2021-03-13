
package com.tipplerow.jam.math;

import java.util.function.DoublePredicate;

/**
 * Distinguishes open and closed ranges.
 */
public enum RangeType {
    /**
     * Both left and right limits are open: {@code (a, b)}.
     */
    OPEN {
        @Override public DoublePredicate lowerPredicate(double lowerBound) {
            return ComparisonPredicate.GT(lowerBound);
        }

        @Override public DoublePredicate upperPredicate(double upperBound) {
            return ComparisonPredicate.LT(upperBound);
        }

        @Override public String format(double lowerBound, double upperBound) {
            return RangeFormatter.format(LEFT_OPEN_DELIM, lowerBound, upperBound, RIGHT_OPEN_DELIM);
        }
    },

    /**
     * Left limit is open, right is closed: {@code (a, b]}.
     */
    LEFT_OPEN {
        @Override public DoublePredicate lowerPredicate(double lowerBound) {
            return ComparisonPredicate.GE(lowerBound);
        }

        @Override public DoublePredicate upperPredicate(double upperBound) {
            return ComparisonPredicate.LE(upperBound);
        }

        @Override public String format(double lowerBound, double upperBound) {
            return RangeFormatter.format(LEFT_OPEN_DELIM, lowerBound, upperBound, RIGHT_CLOSED_DELIM);
        }
    },

    /**
     * Left limit is closed, right is open: {@code [a, b)}.
     */
    LEFT_CLOSED {
        @Override public DoublePredicate lowerPredicate(double lowerBound) {
            return ComparisonPredicate.GE(lowerBound);
        }

        @Override public DoublePredicate upperPredicate(double upperBound) {
            return ComparisonPredicate.LT(upperBound);
        }

        @Override public String format(double lowerBound, double upperBound) {
            return RangeFormatter.format(LEFT_CLOSED_DELIM, lowerBound, upperBound, RIGHT_OPEN_DELIM);
        }
    },

    /**
     * Both left and right limits are closed: {@code [a, b]}.
     */
    CLOSED {
        @Override public DoublePredicate lowerPredicate(double lowerBound) {
            return ComparisonPredicate.GE(lowerBound);
        }

        @Override public DoublePredicate upperPredicate(double upperBound) {
            return ComparisonPredicate.LE(upperBound);
        }

        @Override public String format(double lowerBound, double upperBound) {
            return RangeFormatter.format(LEFT_CLOSED_DELIM, lowerBound, upperBound, RIGHT_CLOSED_DELIM);
        }
    };

    /**
     * Delimiter for ranges open on the left boundary.
     */
    public static final char LEFT_OPEN_DELIM   = '(';

    /**
     * Delimiter for ranges closed on the left boundary.
     */
    public static final char LEFT_CLOSED_DELIM = '[';

    /**
     * Delimiter for ranges open on the right boundary.
     */
    public static final char RIGHT_OPEN_DELIM   = ')';

    /**
     * Delimiter for ranges closed on the right boundary.
     */
    public static final char RIGHT_CLOSED_DELIM = ']';

    /**
     * String that separates the lower and upper bound.
     */
    public static final String SEPARATOR = ", ";

    /**
     * Returns a predicate to determine whether a real value satisfies
     * the lower bound for a range of this type.
     *
     * @param lowerBound location of the lower (left) boundary.
     *
     * @return a predicate to determine whether a real value satisfies
     * the given lower bound.
     */
    public abstract DoublePredicate lowerPredicate(double lowerBound);

    /**
     * Returns a predicate to determine whether a real value satisfies
     * the upper bound for a range of this type.
     *
     * @param upperBound location of the upper (right) boundary.
     *
     * @return a predicate to determine whether a real value satisfies
     * the given upper bound.
     */
    public abstract DoublePredicate upperPredicate(double upperBound);

    /**
     * Formats boundary limits according to this range type.
     *
     * @param lowerBound location of the lower (left) boundary.
     *
     * @param upperBound location of the upper (right) boundary.
     *
     * @return the bounds formatted appropriately.
     */
    public abstract String format(double lowerBound, double upperBound);

    /**
     * Returns the range type for a string representation of a {@code
     * DoubleRange}.
     *
     * <p>The first character of the string must be either {@code [}
     * (closed) or {@code (} (open); the last character must be either
     * {@code ]} (closed) or {@code )} (open).
     *
     * @param s the string to parse.
     *
     * @return the range type for the given string representation.
     *
     * @throws IllegalArgumentException unless the input string
     * contains valid left and right range indicators.
     */
    public static RangeType parse(String s) {
        s = s.trim();

        char left  = s.charAt(0);
        char right = s.charAt(s.length() - 1);

        if (left == LEFT_OPEN_DELIM && right == RIGHT_OPEN_DELIM)
            return OPEN;

        if (left == LEFT_OPEN_DELIM && right == RIGHT_CLOSED_DELIM)
            return LEFT_OPEN;

        if (left == LEFT_CLOSED_DELIM && right == RIGHT_OPEN_DELIM)
            return LEFT_CLOSED;

        if (left == LEFT_CLOSED_DELIM && right == RIGHT_CLOSED_DELIM)
            return CLOSED;

        throw new IllegalArgumentException("Invalid double range specification.");
    }
}
