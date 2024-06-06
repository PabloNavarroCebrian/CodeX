package ast.Expresiones.Accesos;

import ast.Tipos.KindT;
import ast.Tipos.StructXType;
import ast.Tipos.Type;

public class AccesoStruct extends Acceso {

    private Acceso ac;
    private String campo;

    public AccesoStruct(Acceso ac, String campo) {
        this.ac = ac;
        this.campo = campo;
    }

    @Override
    public String toString() {
        return this.kindA().toString() + "(" + ac.toString() + ", " + campo + ")";
    }

    @Override
    public KindA kindA() {
        return KindA.ACCESOSTRUCTX;
    }

    @Override
    public void bind() {
        this.ac.bind();    }

    @Override
    public void checkType() {

        this.ac.checkType();

        Type acType = this.ac.getType();

        if(acType.kind() != KindT.STRUCTX)
            throw new UnsupportedOperationException("ERROR: ¡Se está intentando acceder a un campo de una variable que no es un StructX!");
        
        
        if(!((StructXType) acType).checkCampo(this.campo))
            throw new UnsupportedOperationException("ERROR: ¡Se está intentando acceder al campo " + this.campo +  " del StructX " + ((StructXType) acType).getNombreStruct() + " el cual no contiene el campo!");

        this.type = ((StructXType) acType).getTypeCampo(this.campo);
    }

    @Override
    public String generateDirCode() {
        StringBuilder codigo = new StringBuilder();
        codigo.append(this.ac.generateDirCode());
        codigo.append("i32.const " + ((StructXType) this.ac.getType()).getDeltaCampo(this.campo) * 4 + "\n");
        codigo.append("i32.add\n");

        return codigo.toString();
    }

    @Override
    public void updateType() {
        this.ac.updateType();
    }
}