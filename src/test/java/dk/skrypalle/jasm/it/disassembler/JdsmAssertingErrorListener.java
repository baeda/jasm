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
package dk.skrypalle.jasm.it.disassembler;

import dk.skrypalle.jasm.disassembler.err.ErrorListener;
import org.assertj.core.api.Assertions;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class JdsmAssertingErrorListener extends ErrorListener {

    @Override
    protected void emitGeneralError(String message) {
        Assertions.fail(message);
    }

    @Override
    protected void emitGeneralError(String message, Throwable error) {
        Assertions.fail(message, error);
    }

}
