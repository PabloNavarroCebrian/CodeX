package ast.Declaraciones;

import ast.ASTNode;
import ast.NodeKind;
import ast.Tipos.Type;

public abstract class Declaracion implements ASTNode {

    protected String nombre;
    protected Type type;

    public abstract KindD kind();

    @Override
    public NodeKind nodeKind() {
        return NodeKind.DECLARACION;
    }

    public String getName() {
        return this.nombre;
    }

    @Override
    public Type getType() { return this.type; }

    @Override
    public int getDelta() { return 0; }
}