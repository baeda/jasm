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
package dk.skrypalle.jasm.it.util;

import dk.skrypalle.jasm.assembler.Assembly;

class DynamicClassLoader extends ClassLoader {

    Class<?> defineClass(Assembly assembly) {
        var binaryData = assembly.getBinaryData();
        var className = assembly.getJvmClassName().replace("/", ".");
        return defineClass(className, binaryData, 0, binaryData.length);
    }

}
