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

import dk.skrypalle.jasm.assembler.err.ErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.objectweb.asm.ClassWriter;

import java.util.Objects;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;

abstract class BaseAssembler implements Assembler {

    protected final ErrorListener errorListener;
    protected final boolean verbose;

    BaseAssembler(ErrorListener errorListener, boolean verbose) {
        this.errorListener = Objects.requireNonNull(errorListener, "errorListener");
        this.verbose = verbose;
    }

    @Override
    public final Assembly assemble() {
        var input = getInput();
        if (input == null) {
            return null;
        }

        try {
            var lexer = new AssemblerLexer(input, errorListener);
            var parser = new AssemblerParser(lexer, errorListener);

            var root = parser.jasmFile();
            if (parser.getNumberOfSyntaxErrors() > 0) {
                return null;
            }

            // pass 1: semantic analysis
            new AssemblerVisitor(errorListener, NoOpClassVisitor.INSTANCE)
                    .visit(root);

            if (errorListener.getNumberOfErrors() > 0) {
                return null;
            }

            // pass 2: code generation analysis
            var classWriter = new ClassWriter(COMPUTE_MAXS | COMPUTE_FRAMES);
            var visitor = new AssemblerVisitor(errorListener, classWriter);
            visitor.visit(root);

            return new Assembly(visitor.getClassName(), classWriter.toByteArray());
        } catch (Throwable t) {
            var sourceName = input.getSourceName();
            if (verbose) {
                errorListener.emitUnexpectedErrorWhileAssembling(sourceName, t);
            } else {
                errorListener.emitUnexpectedErrorWhileAssembling(sourceName);
            }
            return null;
        }
    }

    protected abstract CharStream getInput();

}
