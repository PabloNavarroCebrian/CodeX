package ast.Instrucciones;

import ast.Expresiones.Accesos.Acceso;
import ast.Tipos.IntXType;
import ast.Tipos.Type;

public class ReadInst extends Instruction {

    private Acceso ac;

    public ReadInst(Acceso ac) {
        this.ac = ac;
    }

    @Override
    public KindI kind() {
        return KindI.READ;
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + ac.toString() + ")";
    }

    @Override
    public void bind() {
        this.ac.bind();
    }

    @Override
    public void checkType() {
        this.ac.checkType();
        Type acType = this.ac.getType();

        if(!acType.equals(new IntXType())) {
            throw new UnsupportedOperationException("ERROR: El tipo del argumento de una instrucción readIntX no es un tipo IntX!");
        }
    }

    @Override
    public String generateCode() {
        
        StringBuilder codigo = new StringBuilder();
        codigo.append(this.ac.generateDirCode());
        codigo.append("call $read\n");
        codigo.append("i32.store\n");

        return codigo.toString();
    }

        @Override
    public void updateType() {
        this.ac.updateType();
    }
}