package ast.Tipos;

import java.util.ArrayList;

import ast.Declaraciones.DecVar;
import ast.Expresiones.E;
import ast.Expresiones.KindE;
import ast.Expresiones.ExpStruct;

public class StructXType extends Type {

    private ArrayList<DecVar> campos;
    private String nombreStruct;

    public StructXType(String nombreStruct, ArrayList<DecVar> campos) {
        this.nombreStruct = nombreStruct;
        this.campos = campos;
    }

    @Override
    public KindT kind() {
        return KindT.STRUCTX;
    }

    @Override
    public String toString() {
        return this.kind().toString() + "(" + this.nombreStruct + ")";
    }

    public String getNombreStruct() {
        return this.nombreStruct;
    }

    @Override
    public void bind() {}

    @Override
    public void checkType() {}

    @Override
    public boolean equals(Type type) {
        return type.kind() == KindT.STRUCTX && ((StructXType) type).getNombreStruct().equals(this.nombreStruct);
    }

    public boolean checkCampo(String campo) {

        for(DecVar dec : this.campos)
            if(dec.getName().equals(campo))
                return true;
        
        return false;
    }

    public Type getTypeCampo(String campo) {
        for(DecVar dec : this.campos)
            if(dec.getName().equals(campo))
                return dec.getType();
        
        return null;
    }

    public void checkInitialization(E exp) {

        if(exp.kind() != KindE.STRUCTEXP)
            throw new UnsupportedOperationException("ERROR: ¡No se puede inicializar un StructX con una expresión que no es la de inicialización de StructX! ");
        
        
        ArrayList<E> listExp = ((ExpStruct) exp).getListExp();

        if(this.campos.size() != listExp.size())
            throw new UnsupportedOperationException("ERROR: ¡Intentando inicializar un StructX con una cantidad de argumentos que no es la correcta!");

        for(int i = 0; i < this.campos.size(); ++i) {
            Type campoType = this.campos.get(i).getType();
            E initializationExp = listExp.get(i);
            try {
                campoType.checkInitialization(initializationExp);
            }
            catch (Exception e) {
                throw new UnsupportedOperationException("ERROR: ¡Error en los argumentos de inicialización de un StructX!\n" + e.getMessage());
            }
        }
    }

    @Override
    public int getSize() {
        
        int size = 0;
        for(DecVar dec : this.campos)
            size += dec.getType().getSize();

        return size;
    }

    public int getDeltaCampo(String campo) {
        for(DecVar dec : this.campos)
            if(dec.getName().equals(campo))
                return dec.getDelta();
        
        return 0;
    }

    @Override
    protected String generateInitializationCode(E exp, int delta) {
        
        StringBuilder codigo = new StringBuilder();

        ArrayList<E> listExp = ((ExpStruct) exp).getListExp();

        for(int i = 0; i < listExp.size(); ++i) {
            Type campoType = this.campos.get(i).getType();
            codigo.append(campoType.generateInitializationCode(listExp.get(i), delta));
            delta += campoType.getSize() * 4;
        }

        return codigo.toString();
    }
}