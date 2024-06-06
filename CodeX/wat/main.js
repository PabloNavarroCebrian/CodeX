const { readFileSync } = require("fs");
const readline = require('readline');

const fs = require('fs');
const filePath = 'wat/entrada.txt';

const insrc = readline.createInterface({
  input: fs.createReadStream(filePath),
  output: process.stdout
});

entrada = [];
i = 0; 

async function readInput(n){
    var line;
//    console.log(line);
    for await (line of insrc) {
//	console.log(line);
	entrada.push(parseInt(line));
	n--;
	if (n==0) return;
    }
    insrc.close();
}

var importObjects = {
    runtime: {
        exceptionHandler : function(code) {
            let errStr;
            if (code == 1) {
                errStr= "Index out of bounds. ";
            } else if (code == 2) {
                errStr= "Error 2. ";
	    } else if (code == 3) {
                errStr= "Not enough memory. ";
	    } else {
		errStr= "Unknown error\n";
            }
            throw new Error(errStr);
        },
	print: function(n) {
	    console.log(n);
        },
	read: function() {
	    let val = entrada[i];
	    i += 1;
	    return val;
        }
    }};

async function start() {
    var file = "ficherosWasm/programa_prueba.wasm";
    if (process.argv.length > 2) {
        file = process.argv[2];
    }
    const code = readFileSync(file);
    wasmModule = await WebAssembly.compile(code);
    instance = await WebAssembly.instantiate(wasmModule, importObjects);
//    await instance.exports.init();
    process.exit(0);
}

async function run() {
    await readInput(17);
    console.clear();
    start();
}

run();

