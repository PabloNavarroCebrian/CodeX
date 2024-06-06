package ast.Expresiones;

import ast.Tipos.BoolXType;
import ast.Tipos.IntXType;
import ast.Tipos.Type;

public class EUnaria extends E {

    private E opnd1;
    private KindE typeExp;

    public EUnaria(KindE typeExp, E opnd1) {
        this.opnd1 = opnd1;
        this.typeExp = typeExp;
    }

    @Override
    public E opnd1() {
        return opnd1;
    }

    @Override
    public String toString() {
        return typeExp.toString() + "(" + opnd1.toString() + ")";
    }

    @Override
    public KindE kind() {
        return this.typeExp;
    }

    @Override
    public void bind() {
        this.opnd1.bind();
    }

    @Override
    public void checkType() {

        this.opnd1.checkType();
        Type opnd1Type = this.opnd1.getType();

        switch(this.typeExp) {
            case NOT:
                if(!opnd1Type.equals(new BoolXType()))
                    throw new UnsupportedOperationException("ERROR: ¡No se puede negar algo que no es tipo BoolX!");
                this.type = new BoolXType();
                break;
            case MENOS:
                if(!opnd1Type.equals(new IntXType()))
                    throw new UnsupportedOperationException("ERROR: ¡No se puede cambiar de signo algo que no es tipo IntX!");
                this.type = new IntXType();
                break;
            default:
                break;
        }
    }

    @Override
    public String generateCode() {
        StringBuilder codigo = new StringBuilder();

        switch (this.typeExp) {
            case NOT:
                codigo.append(this.opnd1.generateCode());
                codigo.append("i32.eqz\n");                
                break;
            case MENOS:
                codigo.append("i32.const 0\n");
                codigo.append(this.opnd1.generateCode());
                codigo.append("i32.sub\n");
            default:
                break;
        }

        return codigo.toString();
    }

    @Override
    public void updateType() {
        this.opnd1.updateType();
    }
}