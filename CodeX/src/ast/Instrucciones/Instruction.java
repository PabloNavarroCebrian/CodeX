package ast.Instrucciones;

import java.util.ArrayList;

import ast.ASTNode;
import ast.NodeKind;
import ast.Tipos.Type;

public abstract class Instruction implements ASTNode {

    protected Type type;
    
    @Override
    public NodeKind nodeKind() { return NodeKind.INSTRUCCION; }
    
    public abstract KindI kind();

    @Override
    public Type getType() {
        return this.type;
    }

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
    public void updateType() {}
}
