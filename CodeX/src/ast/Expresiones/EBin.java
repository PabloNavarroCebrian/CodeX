package ast.Expresiones;

import ast.Tipos.BoolXType;
import ast.Tipos.IntXType;
import ast.Tipos.Type;

public class EBin extends E {

    private E opnd1;
    private E opnd2;
    private KindE typeExp;

    public EBin(E opnd1, KindE typeExp, E opnd2) {
        this.opnd1 = opnd1;
        this.opnd2 = opnd2;
        this.typeExp = typeExp;
    }

    @Override
    public E opnd1() {
        return opnd1;
    }

    @Override
    public E opnd2() {
        return opnd2;
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + opnd1.toString() + ", " + opnd2.toString() + ")";
    }

    @Override
    public KindE kind() {
        return this.typeExp;
    }

    @Override
    public void bind() {
        this.opnd1.bind();
        this.opnd2.bind();
    }

    @Override
    public void checkType() {
        
        this.opnd1.checkType();
        this.opnd2.checkType();
        Type opnd1Type = this.opnd1.getType();
        Type opnd2Type = this.opnd2.getType();

        switch(this.typeExp) {
            case OR:
            case AND:
                if(!opnd1Type.equals(new BoolXType()) || !opnd2Type.equals(new BoolXType()))
                    throw new UnsupportedOperationException("ERROR: ¡No se puede hacer la operación " + this.typeExp.toString() + " con operandos que no son tipo BoolX!");
                this.type = new BoolXType();
                break;

            case EQ:
            case NOTEQ:
                if((!opnd1Type.equals(new BoolXType()) || !opnd2Type.equals(new BoolXType())) && (!opnd1Type.equals(new IntXType()) || !opnd2Type.equals(new IntXType())))
                    throw new UnsupportedOperationException("ERROR: ¡No se puede hacer la operación " + this.typeExp.toString() + " con operandos que no son los dos tipo BoolX o los dos tipo IntX!");
                this.type = new BoolXType();
                break;

            case MAYOR:
            case MENOR:
            case MAYEQ:
            case MENEQ:
                if(!opnd1Type.equals(new IntXType()) || !opnd2Type.equals(new IntXType()))
                    throw new UnsupportedOperationException("ERROR: ¡No se puede hacer la operación " + this.typeExp.toString() + " con operandos que no son tipo IntX!");
                this.type = new BoolXType();
                break;

            case SUMA:
            case RESTA:
            case MUL:
            case DIV:
            case MOD:
                if(!opnd1Type.equals(new IntXType()) || !opnd2Type.equals(new IntXType()))
                    throw new UnsupportedOperationException("ERROR: ¡No se puede hacer la operación " + this.typeExp.toString() + " con operandos que no son tipo IntX!");
                this.type = new IntXType();
                break;
                
            default:
                break;
        }
    }

    @Override
    public String generateCode() {
        
        StringBuilder codigo = new StringBuilder();
        codigo.append(this.opnd1.generateCode());
        codigo.append(this.opnd2.generateCode());
        codigo.append(this.getOpCode());

        return codigo.toString();
    }

    private String getOpCode() {

        String opCode = "i32.";

        switch (this.typeExp) {

            case OR:
                opCode += "or";
                break;
            case AND:
                opCode += "and";
                break;
            case EQ:
                opCode += "eq";
                break;
            case NOTEQ:
                opCode += "ne";
                break;
            case MAYOR:
                opCode += "gt_s";
                break;
            case MENOR:
                opCode += "lt_s";
                break;
            case MAYEQ:
                opCode += "ge_s";
                break;
            case MENEQ:
                opCode += "le_s";
                break;
            case SUMA:
                opCode += "add";
                break;
            case RESTA:
                opCode += "sub";
                break;
            case MUL:
                opCode += "mul";
                break;
            case DIV:
                opCode += "div_s";
                break;
            case MOD:
                opCode += "rem_s";
                break;
            default:
                break;
        }

        opCode += "\n";

        return opCode;
    }

    @Override
    public void updateType() {
        this.opnd1.updateType();
        this.opnd2.updateType();
    }
}
