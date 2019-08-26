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
import dk.skrypalle.jasm.generated.JasmBaseVisitor;
import dk.skrypalle.jasm.generated.JasmParser.AaloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.AastoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.AnewarrayInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.AreturnInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.ArrayLengthInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.AstoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.AthrowInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.BaloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.BastoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.CaloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.CastoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.CheckcastInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.D2fInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.D2iInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.D2lInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DaddInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DaloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DastoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DcmpgInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DcmplInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DdivInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DmulInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DnegInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DremInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DreturnInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DstoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DsubInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.Dup2InstrContext;
import dk.skrypalle.jasm.generated.JasmParser.Dup2X1InstrContext;
import dk.skrypalle.jasm.generated.JasmParser.Dup2X2InstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DupX1InstrContext;
import dk.skrypalle.jasm.generated.JasmParser.DupX2InstrContext;
import dk.skrypalle.jasm.generated.JasmParser.F2dInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.F2iInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.F2lInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FaddInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FaloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FastoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FcmpgInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FcmplInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FdivInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FmulInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FnegInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FremInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FreturnInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FstoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.FsubInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.GetFieldInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.GotoInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.I2bInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.I2cInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.I2dInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.I2fInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.I2lInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.I2sInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IaloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IandInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IastoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IdivInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfAcmpeqInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfAcmpneInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfIcmpeqInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfIcmpgeInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfIcmpgtInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfIcmpleInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfIcmpltInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfIcmpneInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfeqInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfgeInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfgtInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfleInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfltInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfneInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfnonnullInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IfnullInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.ImulInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.InegInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.InstanceofInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.InvokeInterfaceInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IorInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IremInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IreturnInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IshlInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IshrInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IsubInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IushrInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.IxorInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.JsrInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.L2dInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.L2fInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.L2iInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LabelContext;
import dk.skrypalle.jasm.generated.JasmParser.LabelDefContext;
import dk.skrypalle.jasm.generated.JasmParser.LaddInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LaloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LandInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LastoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LcmpInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LdcDecInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LdcTypeInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LdivInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LmulInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LnegInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LorInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LremInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LreturnInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LshlInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LshrInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LstoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LsubInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LushrInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.LxorInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.MonitorenterInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.MonitorexitInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.NewarrayInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.NopInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.Pop2InstrContext;
import dk.skrypalle.jasm.generated.JasmParser.PutFieldInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.PutStaticInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.RetInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.SaloadInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.SastoreInstrContext;
import dk.skrypalle.jasm.generated.JasmParser.SwapInstrContext;
import org.antlr.v4.runtime.Token;
import org.apache.commons.text.StringEscapeUtils;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static dk.skrypalle.jasm.generated.JasmParser.AloadInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.DescriptorContext;
import static dk.skrypalle.jasm.generated.JasmParser.DupInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.FqcnContext;
import static dk.skrypalle.jasm.generated.JasmParser.GetStaticInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.IaddInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.IloadInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.InstructionListContext;
import static dk.skrypalle.jasm.generated.JasmParser.InvokeSpecialInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.InvokeStaticInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.InvokeVirtualInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.IstoreInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.LdcIntInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.LdcStringInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.NewInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.PopInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.ReturnInstrContext;
import static dk.skrypalle.jasm.generated.JasmParser.StringContext;

class InstructionVisitor extends JasmBaseVisitor<Object> {

    private final ErrorListener errorListener;
    private final List<Consumer<MethodVisitor>> deferredActions;
    private final LabelTracker labelTracker;

    InstructionVisitor(ErrorListener errorListener) {
        this.errorListener = errorListener;
        deferredActions = new ArrayList<>();
        labelTracker = new LabelTracker();
    }

    @Override
    public Void visitInstructionList(InstructionListContext ctx) {
        ctx.children.forEach(this::visit);
        return null;
    }

    @Override
    public Void visitLdcIntInstr(LdcIntInstrContext ctx) {
        var text = ctx.val.getText();
        if (text.endsWith("l")) {
            ldcLong(text);
        } else {
            ldcInt(text);
        }
        return null;
    }

    private void ldcLong(String text) {
        var value = Long.decode(text.substring(0, text.length() - 1));
        if (value == 0L) {
            defer(m -> m.visitInsn(Opcodes.LCONST_0));
        } else if (value == 1L) {
            defer(m -> m.visitInsn(Opcodes.LCONST_1));
        } else {
            defer(m -> m.visitLdcInsn(value));
        }
    }

    private void ldcInt(String text) {
        var value = Integer.decode(text);
        if (value >= 0 && value <= 5) {
            defer(m -> m.visitInsn(Opcodes.ICONST_0 + value));
        } else if (value == -1) {
            defer(m -> m.visitInsn(Opcodes.ICONST_M1));
        } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            defer(m -> m.visitIntInsn(Opcodes.BIPUSH, value));
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            defer(m -> m.visitIntInsn(Opcodes.SIPUSH, value));
        } else {
            defer(m -> m.visitLdcInsn(value));
        }
    }

    @Override
    public Object visitLdcDecInstr(LdcDecInstrContext ctx) {
        var text = ctx.val.getText();
        if (text.endsWith("f")) {
            ldcFloat(text);
        } else {
            ldcDouble(text);
        }
        return null;
    }

    private void ldcFloat(String text) {
        var value = Float.valueOf(text);
        if (Objects.equals(value, 0.0f)) {
            defer(m -> m.visitInsn(Opcodes.FCONST_0));
        } else if (Objects.equals(value, 1.0f)) {
            defer(m -> m.visitInsn(Opcodes.FCONST_1));
        } else if (Objects.equals(value, 2.0f)) {
            defer(m -> m.visitInsn(Opcodes.FCONST_2));
        } else {
            defer(m -> m.visitLdcInsn(value));
        }
    }

    private void ldcDouble(String text) {
        var value = Double.valueOf(text);
        if (Objects.equals(value, 0.0)) {
            defer(m -> m.visitInsn(Opcodes.DCONST_0));
        } else if (Objects.equals(value, 1.0)) {
            defer(m -> m.visitInsn(Opcodes.DCONST_1));
        } else {
            defer(m -> m.visitLdcInsn(value));
        }
    }

    @Override
    public Void visitLdcStringInstr(LdcStringInstrContext ctx) {
        var value = visitString(ctx.val);
        defer(m -> m.visitLdcInsn(value));
        return null;
    }

    @Override
    public Object visitLdcTypeInstr(LdcTypeInstrContext ctx) {
        var value = (String) new TypeVisitor(errorListener).visit(ctx.val);
        var type = Type.getType(value);
        defer(m -> m.visitLdcInsn(type));
        return null;
    }

    //region int instructions

    @Override
    public Object visitNewarrayInstr(NewarrayInstrContext ctx) {
        int type;
        switch (ctx.typ.getText()) {
            case "B":
                type = Opcodes.T_BYTE;
                break;
            case "S":
                type = Opcodes.T_SHORT;
                break;
            case "I":
                type = Opcodes.T_INT;
                break;
            case "L":
                type = Opcodes.T_LONG;
                break;
            case "F":
                type = Opcodes.T_FLOAT;
                break;
            case "D":
                type = Opcodes.T_DOUBLE;
                break;
            case "Z":
                type = Opcodes.T_BOOLEAN;
                break;
            case "C":
                type = Opcodes.T_CHAR;
                break;
            default:
                throw new IllegalStateException();
        }

        defer(m -> m.visitIntInsn(Opcodes.NEWARRAY, type));
        return null;
    }

    //endregion int instructions

    //region var instructions

    @Override
    public Void visitIloadInstr(IloadInstrContext ctx) {
        varInstr(Opcodes.ILOAD, ctx.val);
        return null;
    }

    @Override
    public Object visitLloadInstr(LloadInstrContext ctx) {
        varInstr(Opcodes.LLOAD, ctx.val);
        return null;
    }

    @Override
    public Object visitFloadInstr(FloadInstrContext ctx) {
        varInstr(Opcodes.FLOAD, ctx.val);
        return null;
    }

    @Override
    public Object visitDloadInstr(DloadInstrContext ctx) {
        varInstr(Opcodes.DLOAD, ctx.val);
        return null;
    }

    @Override
    public Void visitAloadInstr(AloadInstrContext ctx) {
        varInstr(Opcodes.ALOAD, ctx.val);
        return null;
    }

    @Override
    public Void visitIstoreInstr(IstoreInstrContext ctx) {
        varInstr(Opcodes.ISTORE, ctx.val);
        return null;
    }

    @Override
    public Object visitLstoreInstr(LstoreInstrContext ctx) {
        varInstr(Opcodes.LSTORE, ctx.val);
        return null;
    }

    @Override
    public Object visitFstoreInstr(FstoreInstrContext ctx) {
        varInstr(Opcodes.FSTORE, ctx.val);
        return null;
    }

    @Override
    public Object visitDstoreInstr(DstoreInstrContext ctx) {
        varInstr(Opcodes.DSTORE, ctx.val);
        return null;
    }

    @Override
    public Object visitAstoreInstr(AstoreInstrContext ctx) {
        varInstr(Opcodes.ASTORE, ctx.val);
        return null;
    }

    @Override
    public Object visitRetInstr(RetInstrContext ctx) {
        varInstr(Opcodes.RET, ctx.val);
        return null;
    }

    private void varInstr(int opcode, Token token) {
        var value = Integer.decode(token.getText());
        defer(m -> m.visitVarInsn(opcode, value));
    }

    //endregion var instructions

    //region type instructions

    @Override
    public Void visitNewInstr(NewInstrContext ctx) {
        var type = visitFqcn(ctx.typ);
        defer(m -> m.visitTypeInsn(Opcodes.NEW, type));
        return null;
    }

    @Override
    public Object visitAnewarrayInstr(AnewarrayInstrContext ctx) {
        var type = visitFqcn(ctx.typ);
        defer(m -> m.visitTypeInsn(Opcodes.ANEWARRAY, type));
        return null;
    }

    @Override
    public Object visitCheckcastInstr(CheckcastInstrContext ctx) {
        var type = visitFqcn(ctx.typ);
        defer(m -> m.visitTypeInsn(Opcodes.CHECKCAST, type));
        return null;
    }

    @Override
    public Object visitInstanceofInstr(InstanceofInstrContext ctx) {
        var type = visitFqcn(ctx.typ);
        defer(m -> m.visitTypeInsn(Opcodes.INSTANCEOF, type));
        return null;
    }

    //endregion type instructions

    //region no-arg instructions

    @Override
    public Object visitNopInstr(NopInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.NOP));
        return null;
    }

    @Override
    public Object visitIaloadInstr(IaloadInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IALOAD));
        return null;
    }

    @Override
    public Object visitLaloadInstr(LaloadInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LALOAD));
        return null;
    }

    @Override
    public Object visitFaloadInstr(FaloadInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FALOAD));
        return null;
    }

    @Override
    public Object visitDaloadInstr(DaloadInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DALOAD));
        return null;
    }

    @Override
    public Object visitAaloadInstr(AaloadInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.AALOAD));
        return null;
    }

    @Override
    public Object visitBaloadInstr(BaloadInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.BALOAD));
        return null;
    }

    @Override
    public Object visitCaloadInstr(CaloadInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.CALOAD));
        return null;
    }

    @Override
    public Object visitSaloadInstr(SaloadInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.SALOAD));
        return null;
    }

    @Override
    public Object visitIastoreInstr(IastoreInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IASTORE));
        return null;
    }

    @Override
    public Object visitLastoreInstr(LastoreInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LASTORE));
        return null;
    }

    @Override
    public Object visitFastoreInstr(FastoreInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FASTORE));
        return null;
    }

    @Override
    public Object visitDastoreInstr(DastoreInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DASTORE));
        return null;
    }

    @Override
    public Object visitAastoreInstr(AastoreInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.AASTORE));
        return null;
    }

    @Override
    public Object visitBastoreInstr(BastoreInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.BASTORE));
        return null;
    }

    @Override
    public Object visitCastoreInstr(CastoreInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.CASTORE));
        return null;
    }

    @Override
    public Object visitSastoreInstr(SastoreInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.SASTORE));
        return null;
    }

    @Override
    public Void visitPopInstr(PopInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.POP));
        return null;
    }

    @Override
    public Object visitPop2Instr(Pop2InstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.POP2));
        return null;
    }

    @Override
    public Void visitDupInstr(DupInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DUP));
        return null;
    }

    @Override
    public Object visitDupX1Instr(DupX1InstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DUP_X1));
        return null;
    }

    @Override
    public Object visitDupX2Instr(DupX2InstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DUP_X2));
        return null;
    }

    @Override
    public Object visitDup2Instr(Dup2InstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DUP2));
        return null;
    }

    @Override
    public Object visitDup2X1Instr(Dup2X1InstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DUP2_X1));
        return null;
    }

    @Override
    public Object visitDup2X2Instr(Dup2X2InstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DUP2_X2));
        return null;
    }

    @Override
    public Object visitSwapInstr(SwapInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.SWAP));
        return null;
    }

    @Override
    public Void visitIaddInstr(IaddInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IADD));
        return null;
    }

    @Override
    public Object visitLaddInstr(LaddInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LADD));
        return null;
    }

    @Override
    public Object visitFaddInstr(FaddInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FADD));
        return null;
    }

    @Override
    public Object visitDaddInstr(DaddInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DADD));
        return null;
    }

    @Override
    public Object visitIsubInstr(IsubInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.ISUB));
        return null;
    }

    @Override
    public Object visitLsubInstr(LsubInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LSUB));
        return null;
    }

    @Override
    public Object visitFsubInstr(FsubInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FSUB));
        return null;
    }

    @Override
    public Object visitDsubInstr(DsubInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DSUB));
        return null;
    }

    @Override
    public Object visitImulInstr(ImulInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IMUL));
        return null;
    }

    @Override
    public Object visitLmulInstr(LmulInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LMUL));
        return null;
    }

    @Override
    public Object visitFmulInstr(FmulInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FMUL));
        return null;
    }

    @Override
    public Object visitDmulInstr(DmulInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DMUL));
        return null;
    }

    @Override
    public Object visitIdivInstr(IdivInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IDIV));
        return null;
    }

    @Override
    public Object visitLdivInstr(LdivInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LDIV));
        return null;
    }

    @Override
    public Object visitFdivInstr(FdivInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FDIV));
        return null;
    }

    @Override
    public Object visitDdivInstr(DdivInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DDIV));
        return null;
    }

    @Override
    public Object visitIremInstr(IremInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IREM));
        return null;
    }

    @Override
    public Object visitLremInstr(LremInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LREM));
        return null;
    }

    @Override
    public Object visitFremInstr(FremInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FREM));
        return null;
    }

    @Override
    public Object visitDremInstr(DremInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DREM));
        return null;
    }

    @Override
    public Object visitInegInstr(InegInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.INEG));
        return null;
    }

    @Override
    public Object visitLnegInstr(LnegInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LNEG));
        return null;
    }

    @Override
    public Object visitFnegInstr(FnegInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FNEG));
        return null;
    }

    @Override
    public Object visitDnegInstr(DnegInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DNEG));
        return null;
    }

    @Override
    public Object visitIshlInstr(IshlInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.ISHL));
        return null;
    }

    @Override
    public Object visitLshlInstr(LshlInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LSHL));
        return null;
    }

    @Override
    public Object visitIshrInstr(IshrInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.ISHR));
        return null;
    }

    @Override
    public Object visitLshrInstr(LshrInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LSHR));
        return null;
    }

    @Override
    public Object visitIushrInstr(IushrInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IUSHR));
        return null;
    }

    @Override
    public Object visitLushrInstr(LushrInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LUSHR));
        return null;
    }

    @Override
    public Object visitIandInstr(IandInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IAND));
        return null;
    }

    @Override
    public Object visitLandInstr(LandInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LAND));
        return null;
    }

    @Override
    public Object visitIorInstr(IorInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IOR));
        return null;
    }

    @Override
    public Object visitLorInstr(LorInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LOR));
        return null;
    }

    @Override
    public Object visitIxorInstr(IxorInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IXOR));
        return null;
    }

    @Override
    public Object visitLxorInstr(LxorInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LXOR));
        return null;
    }

    @Override
    public Object visitI2lInstr(I2lInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.I2L));
        return null;
    }

    @Override
    public Object visitI2fInstr(I2fInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.I2F));
        return null;
    }

    @Override
    public Object visitI2dInstr(I2dInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.I2D));
        return null;
    }

    @Override
    public Object visitL2iInstr(L2iInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.L2I));
        return null;
    }

    @Override
    public Object visitL2fInstr(L2fInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.L2F));
        return null;
    }

    @Override
    public Object visitL2dInstr(L2dInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.L2D));
        return null;
    }

    @Override
    public Object visitF2iInstr(F2iInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.F2I));
        return null;
    }

    @Override
    public Object visitF2lInstr(F2lInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.F2L));
        return null;
    }

    @Override
    public Object visitF2dInstr(F2dInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.F2D));
        return null;
    }

    @Override
    public Object visitD2iInstr(D2iInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.D2I));
        return null;
    }

    @Override
    public Object visitD2lInstr(D2lInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.D2L));
        return null;
    }

    @Override
    public Object visitD2fInstr(D2fInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.D2F));
        return null;
    }

    @Override
    public Object visitI2bInstr(I2bInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.I2B));
        return null;
    }

    @Override
    public Object visitI2cInstr(I2cInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.I2C));
        return null;
    }

    @Override
    public Object visitI2sInstr(I2sInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.I2S));
        return null;
    }

    @Override
    public Object visitLcmpInstr(LcmpInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LCMP));
        return null;
    }

    @Override
    public Object visitFcmplInstr(FcmplInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FCMPL));
        return null;
    }

    @Override
    public Object visitFcmpgInstr(FcmpgInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FCMPG));
        return null;
    }

    @Override
    public Object visitDcmplInstr(DcmplInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DCMPL));
        return null;
    }

    @Override
    public Object visitDcmpgInstr(DcmpgInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DCMPG));
        return null;
    }

    @Override
    public Void visitIreturnInstr(IreturnInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.IRETURN));
        return null;
    }

    @Override
    public Void visitLreturnInstr(LreturnInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.LRETURN));
        return null;
    }

    @Override
    public Void visitFreturnInstr(FreturnInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.FRETURN));
        return null;
    }

    @Override
    public Void visitDreturnInstr(DreturnInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.DRETURN));
        return null;
    }

    @Override
    public Void visitAreturnInstr(AreturnInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.ARETURN));
        return null;
    }

    @Override
    public Void visitReturnInstr(ReturnInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.RETURN));
        return null;
    }

    @Override
    public Void visitArrayLengthInstr(ArrayLengthInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.ARRAYLENGTH));
        return null;
    }

    @Override
    public Object visitAthrowInstr(AthrowInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.ATHROW));
        return null;
    }

    @Override
    public Object visitMonitorenterInstr(MonitorenterInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.MONITORENTER));
        return null;
    }

    @Override
    public Object visitMonitorexitInstr(MonitorexitInstrContext ctx) {
        defer(m -> m.visitInsn(Opcodes.MONITOREXIT));
        return null;
    }

    //endregion no-arg instructions

    //region field instructions

    @Override
    public Void visitGetStaticInstr(GetStaticInstrContext ctx) {
        var owner = ctx.owner.getText();
        var name = ctx.name.getText();
        var descriptor = visitDescriptor(ctx.desc);
        defer(m -> m.visitFieldInsn(
                Opcodes.GETSTATIC,
                owner,
                name,
                descriptor
        ));
        return null;
    }

    @Override
    public Void visitPutStaticInstr(PutStaticInstrContext ctx) {
        var owner = ctx.owner.getText();
        var name = ctx.name.getText();
        var descriptor = visitDescriptor(ctx.desc);
        defer(m -> m.visitFieldInsn(
                Opcodes.PUTSTATIC,
                owner,
                name,
                descriptor
        ));
        return null;
    }

    @Override
    public Void visitGetFieldInstr(GetFieldInstrContext ctx) {
        var owner = ctx.owner.getText();
        var name = ctx.name.getText();
        var descriptor = visitDescriptor(ctx.desc);
        defer(m -> m.visitFieldInsn(
                Opcodes.GETFIELD,
                owner,
                name,
                descriptor
        ));
        return null;
    }

    @Override
    public Void visitPutFieldInstr(PutFieldInstrContext ctx) {
        var owner = ctx.owner.getText();
        var name = ctx.name.getText();
        var descriptor = visitDescriptor(ctx.desc);
        defer(m -> m.visitFieldInsn(
                Opcodes.PUTFIELD,
                owner,
                name,
                descriptor
        ));
        return null;
    }

    //endregion field instructions

    //region method instructions

    @Override
    public Void visitInvokeVirtualInstr(InvokeVirtualInstrContext ctx) {
        var owner = ctx.owner.getText();
        var name = ctx.name.getText();
        var descriptor = visitDescriptor(ctx.desc);
        defer(m -> m.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                owner,
                name,
                descriptor,
                false
        ));
        return null;
    }

    @Override
    public Void visitInvokeSpecialInstr(InvokeSpecialInstrContext ctx) {
        var owner = ctx.owner.getText();
        var name = ctx.name.getText();
        var descriptor = visitDescriptor(ctx.desc);
        defer(m -> m.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                owner,
                name,
                descriptor,
                false
        ));
        return null;
    }

    @Override
    public Void visitInvokeStaticInstr(InvokeStaticInstrContext ctx) {
        var owner = ctx.owner.getText();
        var name = ctx.name.getText();
        var descriptor = visitDescriptor(ctx.desc);
        defer(m -> m.visitMethodInsn(
                Opcodes.INVOKESTATIC,
                owner,
                name,
                descriptor,
                false
        ));
        return null;
    }

    @Override
    public Object visitInvokeInterfaceInstr(InvokeInterfaceInstrContext ctx) {
        var owner = ctx.owner.getText();
        var name = ctx.name.getText();
        var descriptor = visitDescriptor(ctx.desc);
        defer(m -> m.visitMethodInsn(
                Opcodes.INVOKEINTERFACE,
                owner,
                name,
                descriptor,
                true
        ));
        return null;
    }

    //endregion method instructions

    //region jump instructions

    @Override
    public Object visitIfeqInstr(IfeqInstrContext ctx) {
        resolveJump(Opcodes.IFEQ, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfneInstr(IfneInstrContext ctx) {
        resolveJump(Opcodes.IFNE, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfltInstr(IfltInstrContext ctx) {
        resolveJump(Opcodes.IFLT, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfgeInstr(IfgeInstrContext ctx) {
        resolveJump(Opcodes.IFGE, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfgtInstr(IfgtInstrContext ctx) {
        resolveJump(Opcodes.IFGT, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfleInstr(IfleInstrContext ctx) {
        resolveJump(Opcodes.IFLE, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfIcmpeqInstr(IfIcmpeqInstrContext ctx) {
        resolveJump(Opcodes.IF_ICMPEQ, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfIcmpneInstr(IfIcmpneInstrContext ctx) {
        resolveJump(Opcodes.IF_ICMPNE, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfIcmpltInstr(IfIcmpltInstrContext ctx) {
        resolveJump(Opcodes.IF_ICMPLT, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfIcmpgeInstr(IfIcmpgeInstrContext ctx) {
        resolveJump(Opcodes.IF_ICMPGE, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfIcmpgtInstr(IfIcmpgtInstrContext ctx) {
        resolveJump(Opcodes.IF_ICMPGT, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfIcmpleInstr(IfIcmpleInstrContext ctx) {
        resolveJump(Opcodes.IF_ICMPLE, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfAcmpeqInstr(IfAcmpeqInstrContext ctx) {
        resolveJump(Opcodes.IF_ACMPEQ, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfAcmpneInstr(IfAcmpneInstrContext ctx) {
        resolveJump(Opcodes.IF_ACMPNE, ctx.dst);
        return null;
    }

    @Override
    public Object visitGotoInstr(GotoInstrContext ctx) {
        resolveJump(Opcodes.GOTO, ctx.dst);
        return null;
    }

    @Override
    public Object visitJsrInstr(JsrInstrContext ctx) {
        resolveJump(Opcodes.JSR, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfnullInstr(IfnullInstrContext ctx) {
        resolveJump(Opcodes.IFNULL, ctx.dst);
        return null;
    }

    @Override
    public Object visitIfnonnullInstr(IfnonnullInstrContext ctx) {
        resolveJump(Opcodes.IFNONNULL, ctx.dst);
        return null;
    }

    private void resolveJump(int opcode, LabelContext labelCtx) {
        var labelName = visitLabel(labelCtx);
        var promise = labelTracker.getLabel(labelName);
        defer(m -> m.visitJumpInsn(opcode, promise.resolve()));
    }

    //endregion jump instructions

    @Override
    public String visitDescriptor(DescriptorContext ctx) {
        return new TypeVisitor(errorListener).visitDescriptor(ctx);
    }

    @Override
    public String visitFqcn(FqcnContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitString(StringContext ctx) {
        var raw = ctx.getText();
        var reduced = raw.substring(1, raw.length() - 1);
        return StringEscapeUtils.unescapeJava(reduced);
    }

    @Override
    public String visitLabel(LabelContext ctx) {
        return ctx.name.getText();
    }

    @Override
    public Object visitLabelDef(LabelDefContext ctx) {
        var name = visitLabel(ctx.label());
        var promise = labelTracker.getLabel(name);
        defer(m -> m.visitLabel(promise.resolve()));
        return null;
    }

    private void defer(Consumer<MethodVisitor> action) {
        deferredActions.add(action);
    }

    void consumeActions(MethodVisitor method) {
        for (Consumer<MethodVisitor> action : deferredActions) {
            action.accept(method);
        }
    }

}