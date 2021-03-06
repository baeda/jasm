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
package dk.skrypalle.jasm.disassembler;

import dk.skrypalle.jasm.Utils;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class DisassemblerClassVisitor extends ClassVisitor {

    private final LabelTrackerMap labelTrackerMap;
    private final ClassFile classFile;

    DisassemblerClassVisitor(LabelTrackerMap labelTrackerMap) {
        super(Utils.ASM_VERSION);

        this.labelTrackerMap = labelTrackerMap;
        classFile = new ClassFile();
    }

    String dumpJasmSourceCode() {
        return classFile.toString();
    }

    @Override
    public void visit(
            int version,
            int access,
            String name,
            String signature,
            String superName,
            String[] interfaces) {
        classFile.setVersion(version);
        var classSpec = classFile.getClassSpec();
        classSpec.setAccess(access);
        classSpec.setName(name);
        classSpec.setSignature(signature);
        classSpec.setSuperName(superName);
        classSpec.addInterfaces(interfaces);
    }

    @Override
    public void visitSource(String source, String debug) {
        classFile.setSource(source);
    }

    @Override
    public MethodVisitor visitMethod(
            int access,
            String name,
            String descriptor,
            String signature,
            String[] exceptions) {
        var labelTracker = labelTrackerMap.getLabelTrackerForMethod(
                access,
                name,
                descriptor,
                signature,
                exceptions);

        var methodVisitor = new DisassemblerMethodVisitor(labelTracker);
        var methodSpec = methodVisitor.getMethodSpec();
        methodSpec.setAccess(access);
        methodSpec.setName(name);
        methodSpec.setDescriptor(descriptor);
        methodSpec.setSignature(signature);
        classFile.addMethodVisitor(methodVisitor);
        return methodVisitor;
    }

    @Override
    public FieldVisitor visitField(
            int access,
            String name,
            String descriptor,
            String signature,
            Object value) {
        var fieldVisitor = new DisassemblerFieldVisitor();
        var fieldSpec = fieldVisitor.getFieldSpec();
        fieldSpec.setAccess(access);
        fieldSpec.setName(name);
        fieldSpec.setDescriptor(descriptor);
        classFile.addFieldVisitor(fieldVisitor);
        return fieldVisitor;
    }

}
