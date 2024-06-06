package alex;

import errors.GestionErrores;

%%
%cup
%line
%column
%class AnalizadorLexico
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErrores errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErrores errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = ({digitoPositivo}{digito}*|0)

true = True
false = False

numeroEntero = {parteEntera}

igual = \=
coma  = ,
puntoYComa = \;
flecha = \-\>
ampersand = \&
asterisco = \*

operadorSuma = \+
operadorResta = \-
operadorDivision = \/
operadorModulo = \%
operadorOr = or
operadorAnd = and
operadorIgual = equals
operadorDesigual = not_equals
operadorMayor = \>
operadorMenor = \<
operadorMenorOIgual = \<\=
operadorMayorOIgual = \>\=
operadorNegacion = not

corcheteApertura = \[
corcheteCierre = \]
parentesisApertura = \(
parentesisCierre = \)
llaveApertura = \{
llaveCierre = \}

separador = [ \t\r\b\n]
comentario = \/\/[^\n]*
comentarioLargo = \/\*[^]\*\/

listX = listX
structX = structX

intX = intX
boolX = boolX
voidX = voidX

new = new

return = return
if = if
else = otherwise
while = while
for = for

main = main
read = readIntX
write = writeX

alias = aliasX

identificador = {letra}({letra}|{digito}|_)*

%%
{separador}               {}
{comentario}              {}
{comentarioLargo}         {}

{true}                    {return ops.unidadTrue();}
{false}                   {return ops.unidadFalse();}

{numeroEntero}            {return ops.unidadEnt();}

{igual}                   {return ops.unidadIgual();}
{coma}                    {return ops.unidadComa();}
{puntoYComa}              {return ops.unidadPuntoYComa();}
{flecha}                  {return ops.unidadFlecha();}
{ampersand}               {return ops.unidadAmpersand();}
{asterisco}               {return ops.unidadAst();}

{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorDivision}        {return ops.unidadDiv();}
{operadorModulo}          {return ops.unidadModulo();}
{operadorOr}              {return ops.unidadOr();}
{operadorAnd}             {return ops.unidadAnd();}
{operadorIgual}           {return ops.unidadEquals();}
{operadorDesigual}        {return ops.unidadNotEquals();}
{operadorMayor}           {return ops.unidadMayor();}
{operadorMenor}           {return ops.unidadMenor();}
{operadorMayorOIgual}     {return ops.unidadMayorOIgual();}
{operadorMenorOIgual}     {return ops.unidadMenorOIgual();}
{operadorNegacion}        {return ops.unidadNegacion();}

{parentesisApertura}      {return ops.unidadParentesisAp();}
{parentesisCierre}        {return ops.unidadParentesisC();}
{corcheteApertura}        {return ops.unidadCorcheteAp();}
{corcheteCierre}          {return ops.unidadCorcheteC();}
{llaveApertura}           {return ops.unidadLlaveAp();}
{llaveCierre}             {return ops.unidadLlaveC();}

{listX}                   {return ops.unidadListX();}
{structX}                 {return ops.unidadStructX();}

{intX}                    {return ops.unidadIntX();}
{boolX}                   {return ops.unidadBoolX();}
{voidX}                   {return ops.unidadVoidX();}

{new}                     {return ops.unidadNew();}

{return}                  {return ops.unidadReturn();}
{if}                      {return ops.unidadIf();}
{else}                    {return ops.unidadElse();}
{while}                   {return ops.unidadWhile();}
{for}                     {return ops.unidadFor();}

{main}                    {return ops.unidadMain();}
{read}                    {return ops.unidadRead();}
{write}                   {return ops.unidadWrite();}

{alias}                   {return ops.unidadAlias();}

{identificador}           {return ops.unidadId();}

[^]                       {errores.errorLexico(fila(),columna(),lexema());}  
