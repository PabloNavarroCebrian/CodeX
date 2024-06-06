package ast.Tipos;

import ast.ASTNode;
import ast.Programa;
import ast.Declaraciones.DecAlias;
import ast.Declaraciones.DecStruct;
import ast.Declaraciones.Declaracion;
import ast.Declaraciones.KindD;
import ast.Expresiones.E;

public class AliasXType extends Type {

    String nombre;
    ASTNode bindingNode;
    Type typeWithoutAlias;
    
    public AliasXType(String nombre){
        this.nombre = nombre;
    }

    @Override
    public KindT kind() {
        return KindT.ALIASX;
    }

    @Override
    public String toString() {
        return this.kind().toString();
    }

    @Override
    public void bind() {
        this.bindingNode = Programa.pila.buscaId(this.nombre);
    }

    @Override
    public boolean equals(Type type) {
        throw new UnsupportedOperationException("ERROR: ¡Alguna variable sigue teniendo asignado el tipo AliasX!");
    }

    @Override
    public void checkInitialization(E exp) {
        throw new UnsupportedOperationException("ERROR: ¡Alguna variable sigue teniendo asignado el tipo AliasX!");
    }

    @Override
    public int getSize() {
        throw new UnsupportedOperationException("ERROR: ¡Alguna variable sigue teniendo asignado el tipo AliasX!");
    }

    @Override
    protected String generateInitializationCode(E exp, int delta) {
        throw new UnsupportedOperationException("ERROR: ¡Alguna variable sigue teniendo asignado el tipo AliasX!");
    }

    @Override
    public void updateType() {

        KindD decType = ((Declaracion) this.bindingNode).kind();

        if(decType != KindD.DECALIAS && decType != KindD.DECSTRUCT)
            throw new UnsupportedOperationException("ERROR: ¡El tipo declarado no es ni tipo AliasX ni tipo StructX!");

        if(decType == KindD.DECALIAS) {
            this.typeWithoutAlias = ((DecAlias) this.bindingNode).getType();
        }
        else {
            DecStruct decStruct = (DecStruct) this.bindingNode;

            this.typeWithoutAlias = new StructXType(decStruct.getName(), decStruct.getCampos());
        }
    }

    @Override
    public Type getType() {
        return this.typeWithoutAlias;
    }
}
