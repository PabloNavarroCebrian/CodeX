package alex;

import ast.ClaseLexica;

public class ALexOperations {
  private AnalizadorLexico alex;
  public ALexOperations(AnalizadorLexico alex) {
   this.alex = alex;   
  }

  public UnidadLexica unidadTrue() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TRUE); 
  }
  public UnidadLexica unidadFalse() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FALSE); 
  }

  public UnidadLexica unidadSuma() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.SUMA); 
  }
  public UnidadLexica unidadResta() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RESTA); 
  }
  public UnidadLexica unidadAst() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AST); 
  }
  public UnidadLexica unidadDiv() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIV); 
  }
  public UnidadLexica unidadModulo() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MOD); 
  }
  public UnidadLexica unidadOr() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OR); 
  }
  public UnidadLexica unidadAnd() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AND); 
  }
  public UnidadLexica unidadEquals() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EQ); 
  }
  public UnidadLexica unidadNotEquals() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NOTEQ); 
  }
  public UnidadLexica unidadMayor() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYOR); 
  }
  public UnidadLexica unidadMenor() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOR); 
  }
  public UnidadLexica unidadMayorOIgual() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYEQ); 
  }
  public UnidadLexica unidadMenorOIgual() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENEQ); 
  }
  public UnidadLexica unidadNegacion() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NOT); 
  }

  public UnidadLexica unidadParentesisAp() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PAP); 
  }
  public UnidadLexica unidadParentesisC() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PCIERRE); 
  }
  public UnidadLexica unidadCorcheteAp() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CAP); 
  }
  public UnidadLexica unidadCorcheteC() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CCIERRE); 
  }
  public UnidadLexica unidadLlaveAp() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LAP); 
  }
  public UnidadLexica unidadLlaveC() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LCIERRE); 
  }

  public UnidadLexica unidadIgual() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IGUAL); 
  }
  public UnidadLexica unidadComa() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMA); 
  }
  public UnidadLexica unidadPuntoYComa() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTOCOMA); 
  }
  public UnidadLexica unidadFlecha() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FLECHA); 
  }
  public UnidadLexica unidadAmpersand() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AMPERSAND); 
  }

  public UnidadLexica unidadListX() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LISTX); 
  }
  public UnidadLexica unidadStructX() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.STRUCTX); 
  }

  public UnidadLexica unidadIntX() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.INTX); 
  }
  public UnidadLexica unidadBoolX() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BOOLX); 
  }
  public UnidadLexica unidadVoidX() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.VOIDX); 
  }

  public UnidadLexica unidadNew() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NEW); 
  }

  public UnidadLexica unidadReturn() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RETURN); 
  }
  public UnidadLexica unidadIf() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IF); 
  }
  public UnidadLexica unidadElse() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ELSE); 
  }
  public UnidadLexica unidadWhile() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WHILE); 
  }
  public UnidadLexica unidadFor() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FOR); 
  }

  public UnidadLexica unidadMain() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAIN); 
  }
  public UnidadLexica unidadRead() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.READ); 
  }
  public UnidadLexica unidadWrite() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WRITE); 
  }

  public UnidadLexica unidadAlias() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ALIAS);
  }
  
  public UnidadLexica unidadEnt() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ENT, alex.lexema()); 
  }
  public UnidadLexica unidadId() {
     return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ID, alex.lexema()); 
  }

  public UnidadLexica unidadEof() {
   return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EOF); 
  }

}
