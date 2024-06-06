package ast.Instrucciones;

import ast.ASTNode;
import ast.Declaraciones.DecFuncion;
import ast.Expresiones.E;
import ast.Tipos.VoidXType;

public class ReturnInst extends Instruction {

    private E exp = null;
    private ASTNode bindingNode;

    public ReturnInst() {}

    public ReturnInst(E exp) {
        this.exp = exp;
    }

    @Override
    public KindI kind() {
        return KindI.RETURN;
    }

    @Override
    public String toString() {
        if(exp == null)
            return this.kind().toString() + "()";
        else
            return this.kind().toString() + "(" + this.exp.toString() + ")";
    }

    @Override
    public void bind() {
        if(this.exp != null)
            this.exp.bind();
    }

    public void setFunction(ASTNode node) {
        this.bindingNode = node;
    }

    @Override
    public void checkType() {
        
        if(exp != null) {
            this.exp.checkType();
            this.type = this.exp.getType();
        }
        else {
            this.type = new VoidXType();
        }

        if(this.bindingNode == null)
            throw new UnsupportedOperationException("ERROR: ¡Hay una función Return que no está al final de una función!");

        if(!((DecFuncion) this.bindingNode).getType().equals(this.type))
            throw new UnsupportedOperationException("ERROR: ¡El tipo del Return de la función " + ((DecFuncion) this.bindingNode).getName() + " no es el correcto!");
    }

    @Override
    public String generateCode() {
        
        StringBuilder codigo = new StringBuilder();
        if(this.exp != null)
            codigo.append(this.exp.generateCode());

        codigo.append("call $freeStack\n");
        codigo.append("return\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        if(this.exp != null)
            exp.updateType();
    }
}
