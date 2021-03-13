package com.tipplerow.jam.math;

final class RangeFormatter {
    //
    // The static "format" method cannot be defined within the RangeType
    // class and then called from the instance format() methods, so this
    // helper class is a work-around...
    //
    static String format(char lowerDelim, double lowerBound, double upperBound, char upperDelim) {
        StringBuilder builder = new StringBuilder();

        builder.append(lowerDelim);
        builder.append(lowerBound);
        builder.append(RangeType.SEPARATOR);
        builder.append(upperBound);
        builder.append(upperDelim);

        return builder.toString();
    }
}