package ast;

import java.util.ArrayList;

import ast.Tipos.Type;

public interface ASTNode {
    
    public void checkType();
    public void bind();
    public void updateType();
    public String generateCode();
    public NodeKind nodeKind();
    public String toString();
    public Type getType();
    public int computeDelta(int totalDelta); //Devuelve el delta actualizado
    public int getDelta();
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory); //Devuelve [maxMemory, currentMemory] actualizado
}