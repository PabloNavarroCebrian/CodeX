package ast.Expresiones.Accesos;

import ast.ASTNode;
import ast.Expresiones.E;
import ast.Expresiones.KindE;

public abstract class Acceso extends E {

    protected ASTNode bindingNode;

    @Override
    public KindE kind() {
        return KindE.ACCESO;
    }

    public abstract KindA kindA();

    public abstract String generateDirCode();

    @Override
    public String generateCode() {
        
        StringBuilder codigo = new StringBuilder();
        codigo.append(this.generateDirCode());
        codigo.append("i32.load\n");

        return codigo.toString();
    }
}
