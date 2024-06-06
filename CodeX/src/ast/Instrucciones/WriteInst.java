package ast.Instrucciones;

import ast.Expresiones.E;
import ast.Tipos.BoolXType;
import ast.Tipos.IntXType;
import ast.Tipos.Type;

public class WriteInst extends Instruction {

    private E exp;

    public WriteInst(E exp) {
        this.exp = exp;
    }

    @Override
    public KindI kind() {
        return KindI.WRITE;
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + this.exp.toString() + ")";
    }

    @Override
    public void bind() {
        this.exp.bind();
    }

    @Override
    public void checkType() {
        this.exp.checkType();
        Type typeExp = exp.getType();

        if(!typeExp.equals(new IntXType()) && !typeExp.equals(new BoolXType())) {
            throw new UnsupportedOperationException("ERROR: ¡El tipo del argumento de una instrucción writeX no es un tipo IntX ni un tipo BoolX!");
        }
    }

    @Override
    public String generateCode() {
        
        StringBuilder codigo = new StringBuilder();
        codigo.append(this.exp.generateCode());
        codigo.append("call $print\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        this.exp.updateType();
    }
}
