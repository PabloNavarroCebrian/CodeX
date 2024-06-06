package ast.Expresiones.Accesos;

import ast.Tipos.KindT;
import ast.Tipos.PointerType;
import ast.Tipos.Type;

public class AccesoPointer extends Acceso {

    private Acceso ac;

    public AccesoPointer(Acceso ac) {
        this.ac = ac;
    }

    @Override
    public String toString() {
        return this.kindA().toString() + "(" + ac.toString() + ")";
    }

    @Override
    public KindA kindA() {
        return KindA.ACCESOPUNTERO;
    }

    @Override
    public void bind() {
        this.ac.bind();
    }

    @Override
    public void checkType() {
        
        this.ac.checkType();

        Type acType = this.ac.getType();

        if(acType.kind() != KindT.PUNTERO)
            throw new UnsupportedOperationException("ERROR: ¡Se está intentando acceder al contenido apuntado por un puntero de una varibale que no es un puntero!");

        this.type = ((PointerType) acType).pointedType();
    }

    @Override
    public String generateDirCode() {
        
        StringBuilder codigo = new StringBuilder();
        codigo.append(this.ac.generateDirCode());
        codigo.append("i32.load\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        this.ac.updateType();
    }
}
