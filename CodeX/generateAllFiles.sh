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

./compile.sh

if [ "$opcion_a" = true ]; then
    ./generateWat.sh -a
    ./generateWasm.sh -a
else
    if [ $# -eq 0 ]; then
        fichero="ficherosPrueba/programa_prueba.txt"
    else
        fichero=$1
    fi

    if [ -f $fichero ]; then
        ./generateWat.sh $fichero
        nombre_sin_extension=$(basename "$fichero" | sed 's/\.[^.]*$//')
        fichero2="ficherosWat/$nombre_sin_extension.wat"
        ./generateWasm.sh $fichero2
    else
        echo -e "El fichero $(basename $fichero) no existe\n"
        read -rsp $'Press enter to continue...\n'
        exit 1
    fi
fi

./removeArchivosClass.sh

exit 0
