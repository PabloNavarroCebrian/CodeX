#!/bin/bash

if [ $# -gt 1 ]; then
    echo -e "Usage: $0 [-a] | [inputFile]\n"
    read -rsp $'Press enter to continue...\n'
    exit 1
fi

while getopts "a" opt; do
    case $opt in
        a)
            opcion_a=true
            ;;            
        ?)
            echo -e "Usage: $0 [-a] [inputFile] [outputFileName]\n"
            read -rsp $'Press enter to continue...\n'
            exit 1
            ;;
    esac
done

cd ficherosWasm

if [ "$opcion_a" = true ]; then
    for fichero in ../ficherosWat/*; do
        echo -e "\n---------------------- Fichero $(basename $fichero) ----------------------\n"
        ../wat/wat2wasm.exe $fichero
    done
else
    if [ $# -eq 0 ]; then
        fichero="../ficherosWat/programa_prueba.wat"
    else
        fichero="../$1"
    fi

    if [ -f $fichero ]; then
        echo -e "\n---------------------- Fichero $(basename $fichero) ----------------------\n"
        ../wat/wat2wasm.exe $fichero
    else
        echo -e "El fichero $(basename $fichero) no existe\n"
        read -rsp $'Press enter to continue...\n'
        exit 1
    fi
fi

cd ..

read -rsp $'Press enter to continue...\n'
exit 0
