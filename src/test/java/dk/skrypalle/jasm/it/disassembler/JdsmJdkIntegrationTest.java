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

import dk.skrypalle.jasm.disassembler.Disassembler;
import dk.skrypalle.jasm.disassembler.Disassemblers;
import dk.skrypalle.jasm.it.util.TestDataProvider;
import dk.skrypalle.jasm.it.util.TestUtil;
import org.testng.annotations.Test;

import static dk.skrypalle.jasm.it.util.TestAssertions.assertThat;

public class JdsmJdkIntegrationTest {

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "provideJdkClassNames")
    public void noSevereImplementationErrors(String className) {
        // arrange
        var dsm = getDisassembler(className);

        // act
        var disassembly = dsm.disassemble();

        // assert
        var jvmClassName = TestUtil.toJvmClassName(className);
        var regexSafeJvmClassName = TestUtil.escapeJvmClassNameForRegex(jvmClassName);
        assertThat(disassembly)
                .isNotNull()
                .hasJvmClassName(jvmClassName)
                .containsSourcePattern("\\.class.*" + regexSafeJvmClassName + "\\n");
    }

    private Disassembler getDisassembler(String className) {
        return Disassemblers.fromClassName(
                className,
                new JdsmAssertingErrorListener(),
                true
        );
    }

}
