package ast.Expresiones.Accesos;

import ast.Programa;
import ast.Declaraciones.DecVar;
import ast.Declaraciones.Declaracion;
import ast.Declaraciones.KindD;

public class AccesoVar extends Acceso {

    private String id;

    public AccesoVar(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.kindA().toString() + "(" + this.id + ")";
    }

    @Override
    public KindA kindA() {
        return KindA.ACCESOVAR;
    }

    @Override
    public void bind() {
        this.bindingNode = Programa.pila.buscaId(this.id);
    }

    @Override
    public void checkType() {
        if(((Declaracion) this.bindingNode).kind() != KindD.DECVAR && ((Declaracion) this.bindingNode).kind() != KindD.DECPARAMETRO)
            throw new UnsupportedOperationException("ERROR: ¡Se está intentando acceder al identificador " + this.id +" que no es una variable!");

        this.type = ((DecVar) this.bindingNode).getType();
    }

    @Override
    public String generateDirCode() {
        return ((DecVar) this.bindingNode).generateDirCode();
    }

    @Override
    public void updateType() {}
}
