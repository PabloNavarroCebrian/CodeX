package ast.Expresiones;

import java.util.ArrayList;

import ast.ASTNode;
import ast.NodeKind;
import ast.Tipos.Type;

public abstract class E implements ASTNode {

    protected Type type;

    public abstract KindE kind();
    
    public E opnd1() {throw new UnsupportedOperationException("opnd1");} 
    public E opnd2() {throw new UnsupportedOperationException("opnd2");} 
    
    @Override
    public NodeKind nodeKind() {return NodeKind.EXPRESION;}
    
    @Override
    public String toString() {return "";}

    @Override
    public Type getType() { return this.type; }

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