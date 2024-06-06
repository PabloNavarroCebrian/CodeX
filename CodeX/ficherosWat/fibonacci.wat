(module
(type $_sig_i32i32i32 (func (param i32 i32 i32) ))
(type $_sig_i32ri32 (func (param i32) (result i32)))
(type $_sig_i32 (func (param i32)))
(type $_sig_ri32 (func (result i32)))
(type $_sig_void (func ))
(export "init" (func $premain))
(export "memory" (memory 0))
(import "runtime" "exceptionHandler" (func $exception (type $_sig_i32)))
(import "runtime" "print" (func $print (type $_sig_i32)))
(import "runtime" "read" (func $read (type $_sig_ri32)))
(memory 2000)
(global $SP (mut i32) (i32.const 0)) ;; start of stack
(global $MP (mut i32) (i32.const 0)) ;; mark pointer
(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4
(start $premain)

(func $reserveStack (param $size i32)
   (result i32)
   global.get $MP
   global.get $SP
   global.set $MP
   global.get $SP
   local.get $size
   i32.add
   global.set $SP
   global.get $SP
   global.get $NP
   i32.gt_u
   if
   i32.const 3
   call $exception
   end
)

(func $freeStack (type $_sig_void)
   global.get $MP
   global.set $SP
   global.get $MP
   i32.load
   global.set $MP   
)

(func $reserveHeap (type $_sig_i32)
   (param $size i32)
   global.get $NP
   local.get $size
   i32.sub
   global.set $NP
   global.get $SP
   global.get $NP
   i32.gt_u
   if
   i32.const 3
   call $exception
   end
)

(func $copyn (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest
   (param $src i32)
   (param $dest i32)
   (param $n i32)
   block
     loop
       local.get $n
       i32.eqz
       br_if 1
       local.get $n
       i32.const 1
       i32.sub
       local.set $n
       local.get $dest
       local.get $src
       i32.load
       i32.store
       local.get $dest
       i32.const 4
       i32.add
       local.set $dest
       local.get $src
       i32.const 4
       i32.add
       local.set $src
       br 0
     end
   end
)
(func $premain
(local $localStart i32)
(local $temp i32)
(local $tempLF i32)
(local $tempI i32)
i32.const 0
call $reserveStack
global.get $MP
local.set $localStart
call $main
return
)
(func $fibonacciRec
(local $localStart i32)
(local $temp i32)
(local $tempLF i32)
(local $tempI i32)
i32.const 20
call $reserveStack
local.set $temp
global.get $MP
local.get $temp
i32.store
global.get $MP
i32.const 4
i32.add
local.set $localStart
i32.const 0
local.get $localStart
i32.add
i32.load
i32.const 0
i32.gt_s
if
i32.const 12
local.get $localStart
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 4
local.get $localStart
i32.add
i32.load
i32.const 8
local.get $localStart
i32.add
i32.load
i32.add
i32.store
i32.const 12
local.get $localStart
i32.add
i32.load
call $print
global.get $SP
i32.const 4
i32.add
local.set $tempLF
local.get $tempLF
i32.const 0
i32.add
i32.const 0
local.get $localStart
i32.add
i32.load
i32.const 1
i32.sub
i32.store
i32.const 8
local.get $localStart
i32.add
local.get $tempLF
i32.const 4
i32.add
i32.const 1
call $copyn
i32.const 12
local.get $localStart
i32.add
local.get $tempLF
i32.const 8
i32.add
i32.const 1
call $copyn
call $fibonacciRec
end
call $freeStack
return
)
(func $fibonacci
(local $localStart i32)
(local $temp i32)
(local $tempLF i32)
(local $tempI i32)
i32.const 8
call $reserveStack
local.set $temp
global.get $MP
local.get $temp
i32.store
global.get $MP
i32.const 4
i32.add
local.set $localStart
i32.const 0
call $print
i32.const 1
call $print
global.get $SP
i32.const 4
i32.add
local.set $tempLF
local.get $tempLF
i32.const 0
i32.add
i32.const 0
local.get $localStart
i32.add
i32.load
i32.const 2
i32.sub
i32.store
local.get $tempLF
i32.const 4
i32.add
i32.const 0
i32.store
local.get $tempLF
i32.const 8
i32.add
i32.const 1
i32.store
call $fibonacciRec
call $freeStack
return
)
(func $main
(result i32)
(local $localStart i32)
(local $temp i32)
(local $tempLF i32)
(local $tempI i32)
i32.const 8
call $reserveStack
local.set $temp
global.get $MP
local.get $temp
i32.store
global.get $MP
i32.const 4
i32.add
local.set $localStart
i32.const 0
local.get $localStart
i32.add
call $read
i32.store
global.get $SP
i32.const 4
i32.add
local.set $tempLF
i32.const 0
local.get $localStart
i32.add
local.get $tempLF
i32.const 0
i32.add
i32.const 1
call $copyn
call $fibonacci
i32.const 0
call $freeStack
return
)
)
