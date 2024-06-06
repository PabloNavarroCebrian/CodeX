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
            echo -e "Usage: $0 [-a] [inputFile]\n"
            read -rsp $'Press enter to continue...\n'
            exit 1
            ;;
    esac
done

cd src

if [ "$opcion_a" = true ]; then
    for fichero in ../ficherosPrueba/*; do
        echo -e "\n---------------------- Fichero $(basename $fichero) ----------------------\n"
        java -cp ";../libs/cup.jar" Main  $fichero
    done
else
    if [ $# -eq 0 ]; then
        fichero="../ficherosPrueba/programa_prueba.txt"
    else
        fichero="../$1"
    fi

    if [ -f $fichero ]; then
        echo -e "\n---------------------- Fichero $(basename $fichero) ----------------------\n"
        java -cp ";../libs/cup.jar" Main  $fichero
    else
        echo -e "El fichero $(basename $fichero) no existe\n"
        read -rsp $'Press enter to continue...\n'
        exit 1
    fi
fi

cd ..

read -rsp $'Press enter to continue...\n'
exit 0
