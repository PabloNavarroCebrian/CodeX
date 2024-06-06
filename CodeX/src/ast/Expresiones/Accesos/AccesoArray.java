package ast.Expresiones.Accesos;

import ast.Expresiones.E;
import ast.Tipos.KindT;
import ast.Tipos.ListXType;
import ast.Tipos.Type;

public class AccesoArray extends Acceso {

    private Acceso ac;
    private E index;

    public AccesoArray(Acceso ac, E index) {
        this.ac = ac;
        this.index = index;
    }

    @Override
    public String toString() {
        return this.kindA().toString() + "(" + ac.toString() + ", " + index.toString() + ")";
    }

    @Override
    public KindA kindA() {
        return KindA.ACCESOLISTX;
    }

    @Override
    public void bind() {
        this.ac.bind();
        this.index.bind();
    }

    @Override
    public void checkType() {

        this.ac.checkType();
        this.index.checkType();

        Type acType = this.ac.getType();

        if(acType.kind() != KindT.LISTX)
            throw new UnsupportedOperationException("ERROR: ¡Se está intentando acceder a un elemento de una variable que no es una ListX!");

        this.type = ((ListXType) acType).elemsListXType();
    }

    @Override
    public String generateDirCode() {
        StringBuilder codigo = new StringBuilder();
        codigo.append(this.ac.generateDirCode());
        codigo.append("i32.const " + this.type.getSize() * 4 + "\n");
        codigo.append(this.index.generateCode());
        codigo.append("local.tee $temp\n");
        codigo.append("local.get $temp\n");
        codigo.append(((ListXType) this.ac.getType()).lengthListX().generateCode());
        codigo.append("i32.ge_s\n");
        codigo.append("local.get $temp\n");
        codigo.append("i32.const 0\n");
        codigo.append("i32.lt_s\n");
        codigo.append("i32.or\n");
        codigo.append("if\n");
        codigo.append("i32.const 1\n");
        codigo.append("call $exception\n");
        codigo.append("end\n");
        codigo.append("i32.mul\n");
        codigo.append("i32.add\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        this.ac.updateType();
        this.index.updateType();
    }
}
