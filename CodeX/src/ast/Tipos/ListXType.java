package ast.Tipos;

import java.util.ArrayList;

import ast.Expresiones.E;
import ast.Expresiones.Ent;
import ast.Expresiones.ExpArray;
import ast.Expresiones.KindE;

public class ListXType extends Type {

    private Ent length;
    private Type elemsType;

    public ListXType(Type elemsType, Ent size) {
        this.elemsType = elemsType;
        this.length = size;
    }

    public ListXType(Type elemsType, int size) {
        this.elemsType = elemsType;
        this.length = new Ent(Integer.toString(size));
    }

    public Type elemsListXType() {
        return this.elemsType;
    }

    public Ent lengthListX() {
        return this.length;
    }

    @Override
    public KindT kind() {
        return KindT.LISTX;
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + this.elemsType.toString() + ", " + this.length.toString() + ")";
    }

    @Override
    public void bind() {
        this.length.bind();
        this.elemsType.bind();
    }

    @Override
    public void checkType() {
        this.length.checkType();
        this.elemsType.checkType();
    }

    @Override
    public boolean equals(Type type) {
        return type.kind() == KindT.LISTX && ((ListXType) type).lengthListX().getEnt() == this.length.getEnt() 
                && ((ListXType) type).elemsListXType().equals(this.elemsType);
    }

    @Override
    public void checkInitialization(E exp) {
        
        if(exp.kind() != KindE.ARRAYEXP)
            throw new UnsupportedOperationException("ERROR: ¡No se puede inicializar una ListX con una expresión que no es la de inicialización de ListX! ");
    
    
        ArrayList<E> listExp = ((ExpArray) exp).getListExp();

        if(this.length.getEnt() != listExp.size())
            throw new UnsupportedOperationException("ERROR: ¡Intentando inicializar una ListX con una cantidad de argumentos que no es la correcta!");

        for(E initializationExp : listExp) {
            try {
                elemsType.checkInitialization(initializationExp);
            }
            catch (Exception e) {
                throw new UnsupportedOperationException("ERROR: ¡Error en los tipos de los argumentos de inicialización de una ListX!\n" + e.getMessage());
            }
        }
    }

    @Override
    public int getSize() {
        return this.length.getEnt() * this.elemsType.getSize();
    }

    @Override
    protected String generateInitializationCode(E exp, int delta) {

        StringBuilder codigo = new StringBuilder();

        ArrayList<E> listExp = ((ExpArray) exp).getListExp();
        int elemsSize = this.elemsType.getSize() * 4;

        for(E initilizationExp : listExp) {
            codigo.append(elemsType.generateInitializationCode(initilizationExp, delta));
            delta += elemsSize;
        }

        return codigo.toString();
    }

    @Override
    public void updateType() {
        this.elemsType.updateType();
        this.elemsType = this.elemsType.getType();
    }
}
