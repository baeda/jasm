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

import dk.skrypalle.jasm.generated.JasmBaseVisitor;
import dk.skrypalle.jasm.generated.JasmParser.LabelContext;
import dk.skrypalle.jasm.generated.JasmParser.LocalVarSpecContext;
import org.objectweb.asm.MethodVisitor;

class LocalVarSpecVisitor extends JasmBaseVisitor<Object> {

    private final MethodVisitor methodVisitor;
    private final LabelTracker labelTracker;
    private final TypeTokenMap typeTokenMap;
    private final IdentifierVisitor identifierVisitor;
    private final TypeVisitor typeVisitor;

    LocalVarSpecVisitor(
            MethodVisitor methodVisitor,
            LabelTracker labelTracker,
            TypeTokenMap typeTokenMap,
            IdentifierVisitor identifierVisitor,
            TypeVisitor typeVisitor) {
        this.methodVisitor = methodVisitor;
        this.labelTracker = labelTracker;
        this.typeTokenMap = typeTokenMap;
        this.identifierVisitor = identifierVisitor;
        this.typeVisitor = typeVisitor;
    }

    @Override
    public Object visitLocalVarSpec(LocalVarSpecContext ctx) {
        var index = Integer.decode(ctx.index.getText());
        var name = identifierVisitor.visitIdentifier(ctx.name);
        var descriptor = typeVisitor.visitTypeDescriptor(ctx.typ);
        String signature = null;
        if (AssemblerUtils.isGenericDescriptor(descriptor)) {
            signature = descriptor;
            descriptor = AssemblerUtils.toRawVarDescriptor(descriptor, typeTokenMap);
        }

        var start = labelTracker.getLabel(visitLabel(ctx.start));
        var end = labelTracker.getLabel(visitLabel(ctx.end));

        methodVisitor.visitLocalVariable(
                name,
                descriptor,
                signature,
                start,
                end,
                index
        );

        return null;
    }

    @Override
    public String visitLabel(LabelContext ctx) {
        return identifierVisitor.visitIdentifier(ctx.name);
    }

}
