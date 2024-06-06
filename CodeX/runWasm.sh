#!/bin/bash

if [ $# -gt 1 ]; then
    echo -e "Usage: $0 inputFile\n"
    read -rsp $'Press enter to continue...\n'
    exit 1
fi

if [ $# -eq 0 ]; then
    fichero="ficherosWasm/programa_prueba.wasm"
else
    fichero=$1
fi

if [ -f $fichero ]; then
    echo -e "\n---------------------- Fichero $(basename $fichero) ----------------------\n"
    node ./wat/main.js $1
else
    echo -e "El fichero $(basename $fichero) no existe\n"
    read -rsp $'Press enter to continue...\n'
    exit 1
fi

read -rsp $'Press enter to continue...\n'