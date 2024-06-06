package ast.Tipos;

import java.util.ArrayList;

import ast.ASTNode;
import ast.NodeKind;
import ast.Declaraciones.DecVar;
import ast.Expresiones.E;

public abstract class Type implements ASTNode {

    public abstract KindT kind();

    @Override
    public NodeKind nodeKind() {
        return NodeKind.TYPE;
    }

    @Override
    public void bind() {}

    public abstract boolean equals(Type type);

    @Override
    public void checkType() {}

    @Override
    public Type getType() { return this; }

    public abstract void checkInitialization(E exp);

    public abstract int getSize();

    @Override
    public int computeDelta(int totalDelta) { return totalDelta; }

    @Override
    public int getDelta() { return 0; }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        ArrayList<Integer> nodeMem = new ArrayList<Integer>();
        nodeMem.add(new Integer(maxMemory));
        nodeMem.add(new Integer(currentMemory));

        return nodeMem;
    }

    @Override
    public String generateCode() { return ""; }

    protected abstract String generateInitializationCode(E exp, int delta);

    public String generateInitializationCode(E exp, DecVar dec) {

        StringBuilder codigo = new StringBuilder();
        codigo.append(dec.generateDirCode());
        codigo.append("local.set $tempI\n");
        codigo.append(this.generateInitializationCode(exp, 0));

        return codigo.toString();
    }

    @Override
    public void updateType() {}
}