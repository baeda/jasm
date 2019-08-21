/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright © 2018 Peter Skrypalle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dk.skrypalle;

import org.apache.commons.io.HexDump;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class Utils {

    public static String hexDump(byte[] data) {
        var out = new ByteArrayOutputStream();
        try {
            HexDump.dump(data, 0, out, 0);
        } catch (IOException e) { /* ignored - cannot happen in ByteArrayOutputStream */ }

        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }

}
