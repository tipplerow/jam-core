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
package com.tipplerow.jam.io;

import com.tipplerow.jam.junit.JamTestBase;

import java.io.File;

public abstract class IOTestBase extends JamTestBase {
    public final File lines123 = resolveFile("io/lines123.txt");
    public final File comments123 = resolveFile("io/comments123.txt");
    public final File continuation = resolveFile("io/continuation.txt");
}
