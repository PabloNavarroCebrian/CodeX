package ast.Expresiones.Accesos;

import ast.Tipos.PointerType;
import ast.Tipos.Type;

public class AccesoDir extends Acceso {

    private Acceso ac;

    public AccesoDir(Acceso ac) {
        this.ac = ac;
    }

    @Override
    public String toString() {
        return this.kindA().toString() + "(" + ac.toString() + ")";
    }

    @Override
    public KindA kindA() {
        return KindA.ACCESODIR;
    }

    @Override
    public void bind() {
        this.ac.bind();
    }

    @Override
    public void checkType() {
        
        this.ac.checkType();

        Type acType = this.ac.getType();

        this.type = new PointerType(acType);
    }

    @Override
    public String generateDirCode() {
        return this.ac.generateDirCode();
    }

    @Override
    public String generateCode() {
        return this.ac.generateDirCode();
    }

    @Override
    public void updateType() {
        this.ac.updateType();
    }
}
