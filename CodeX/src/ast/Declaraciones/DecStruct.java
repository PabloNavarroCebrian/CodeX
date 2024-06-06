package ast.Declaraciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import ast.Programa;

public class DecStruct extends Declaracion {

    private ArrayList<DecVar> campos;

    public DecStruct(String nombre, ArrayList<DecVar> campos) {
        this.nombre = nombre;
        this.campos = campos;
        Collections.reverse(this.campos);
        for(DecVar dec : this.campos)
            dec.setIsGlobal(false);
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + this.nombre + ", " + this.campos.toString() + ")";
    }

    @Override
    public KindD kind() {
        return KindD.DECSTRUCT;
    }

    public ArrayList<DecVar> getCampos() {
        return this.campos;
    }

    @Override
    public void bind() {

        HashSet<String> ids = new HashSet<String>();
        for(DecVar dec : campos) {
            if(ids.contains(dec.getName()))
                throw new UnsupportedOperationException("ERROR: ¡Identificador repetido en la declaración del StructX!");

            dec.getType().bind();
            ids.add(dec.getName());
        }

        Programa.pila.insertaId(nombre, this);
    }

    @Override
    public void checkType() {
        for(DecVar dec : this.campos)
            dec.checkType();
    }

    @Override
    public int computeDelta(int totalDelta) {

        int structDelta = 0;
        
        for(DecVar campo : this.campos)
            structDelta = campo.computeDelta(structDelta);
            
        return totalDelta;
    }

    @Override
    public ArrayList<Integer> maxMemory(int maxMemory, int currentMemory) {

        ArrayList<Integer> nodeMem = new ArrayList<Integer>();
        nodeMem.add(new Integer(maxMemory));
        nodeMem.add(new Integer(currentMemory));

        return nodeMem;
    }

    @Override
    public String generateCode() {
        return "";
    }

    @Override
    public void updateType() {
        for(DecVar dec : this.campos)
            dec.updateType();
    }
}