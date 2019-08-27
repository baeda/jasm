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
package dk.skrypalle.jasm.assembler;

import dk.skrypalle.jasm.Promise;
import org.objectweb.asm.Label;

class LookupSwitchTarget {

    private final int key;
    private final Promise<Label> labelPromise;

    LookupSwitchTarget(int key, Promise<Label> labelPromise) {
        this.key = key;
        this.labelPromise = labelPromise;
    }

    int getKey() {
        return key;
    }

    Promise<Label> getLabelPromise() {
        return labelPromise;
    }

}
