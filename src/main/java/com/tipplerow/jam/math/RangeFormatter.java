/*
 * Copyright (C) 2021 Scott Shaffer - All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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