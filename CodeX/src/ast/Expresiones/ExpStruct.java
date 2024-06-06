package ast.Expresiones;

import java.util.ArrayList;
import java.util.Collections;

public class ExpStruct extends E {

    private ArrayList<E> listaExp;

    public ExpStruct(ArrayList<E> lista) {
        this.listaExp = lista;
        Collections.reverse(this.listaExp);
    }

    public ArrayList<E> expStruct() {
        return listaExp;
    }

    @Override
    public KindE kind() {
        return KindE.STRUCTEXP;
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + listaExp.toString() + ")";
    }

    @Override
    public void bind() {
        for(E exp : this.listaExp)
            exp.bind();
    }

    @Override
    public void checkType() {
        for(E exp : this.listaExp)
            exp.checkType();
    }

    public ArrayList<E> getListExp() {
        return this.listaExp;
    }

    @Override
    public String generateCode() {
        return "";
    }

    @Override
    public void updateType() {
        for(E exp : this.listaExp)
            exp.updateType();
    }
}
