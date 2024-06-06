package ast.Instrucciones;

import ast.Expresiones.E;
import ast.Expresiones.Accesos.Acceso;
import ast.Expresiones.Accesos.KindA;
import ast.Tipos.KindT;

public class AsigInst extends Instruction {

    private Acceso ac;
    private E exp;

    public AsigInst(Acceso ac, E exp) {
        this.ac = ac;
        this.exp = exp;
    }

    @Override
    public KindI kind() {
        return KindI.ASIGNACION;
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + this.ac.toString() + ", " + this.exp.toString() + ")";
    }

    @Override
    public void bind() {
        this.ac.bind();
        this.exp.bind();
    }

    @Override
    public void checkType() {
        
        this.ac.checkType();
        this.exp.checkType();

        if(this.ac.kindA() == KindA.ACCESODIR) {
            throw new UnsupportedOperationException("ERROR: ¡El último operador en un acceso no puede ser '&'!");
        }

        if(!this.ac.getType().equals(this.exp.getType())) {
            throw new UnsupportedOperationException("ERROR: ¡Los tipos en una asignación no coinciden: " + this.ac.getType().kind().toString() + " y " + this.exp.getType().kind().toString() + "!");
        }

        if(this.ac.getType().kind() != KindT.INTX && this.ac.getType().kind() != KindT.BOOLX && this.ac.getType().kind() != KindT.PUNTERO) {
            throw new UnsupportedOperationException("ERROR: ¡El tipo en una asignación debe ser IntX, BoolX o Puntero y se intenta asignar a un tipo " + this.ac.getType().kind() + "!");
        }
    }

    @Override
    public String generateCode() {

        StringBuilder codigo = new StringBuilder();
        codigo.append(this.ac.generateDirCode());
        codigo.append(this.exp.generateCode());
        codigo.append("i32.store\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        this.ac.updateType();
        this.exp.updateType();
    }
}
