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
i32.const 76
call $reserveStack
global.get $MP
local.set $localStart
i32.const 0
i32.const 0
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 3
i32.store
i32.const 8
i32.const 0
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 1
i32.store
i32.const 56
i32.const 0
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 0
i32.store
local.get $tempI
i32.const 4
i32.add
i32.const 0
i32.store
i32.const 64
i32.const 0
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 0
i32.const 0
i32.add
i32.store
i32.const 68
i32.const 0
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 4
call $reserveHeap
global.get $NP
i32.store
i32.const 72
i32.const 0
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 8
call $reserveHeap
global.get $NP
i32.store
call $main
return
)
(func $main
(result i32)
(local $localStart i32)
(local $temp i32)
(local $tempLF i32)
(local $tempI i32)
i32.const 92
call $reserveStack
local.set $temp
global.get $MP
local.get $temp
i32.store
global.get $MP
i32.const 4
i32.add
local.set $localStart
i32.const 4
i32.const 0
i32.add
i32.const 40
i32.store
i32.const 0
i32.const 0
i32.add
i32.const 0
i32.const 0
i32.add
i32.load
i32.const 4
i32.const 0
i32.add
i32.load
i32.add
i32.store
i32.const 12
i32.const 0
i32.add
i32.const 8
i32.const 0
i32.add
i32.load
i32.eqz
i32.store
i32.const 0
local.get $localStart
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 0
i32.store
block
loop
i32.const 0
local.get $localStart
i32.add
i32.load
i32.const 10
i32.lt_s
i32.eqz
br_if 1
i32.const 16
i32.const 0
i32.add
i32.const 4
i32.const 0
local.get $localStart
i32.add
i32.load
local.tee $temp
local.get $temp
i32.const 10
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.const 0
local.get $localStart
i32.add
i32.load
i32.const 1
i32.add
i32.store
i32.const 0
local.get $localStart
i32.add
i32.const 0
local.get $localStart
i32.add
i32.load
i32.const 1
i32.add
i32.store
br 0
end
end
i32.const 56
i32.const 0
i32.add
i32.const 0
i32.add
i32.const 2
i32.store
i32.const 64
i32.const 0
i32.add
i32.load
i32.const 4
i32.store
i32.const 68
i32.const 0
i32.add
i32.load
i32.const 3
i32.store
i32.const 72
i32.const 0
i32.add
i32.load
i32.const 0
i32.add
i32.const 2
i32.store
i32.const 0
local.get $localStart
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 4
call $reserveHeap
global.get $NP
i32.store
local.get $tempI
i32.const 4
i32.add
i32.const 4
call $reserveHeap
global.get $NP
i32.store
i32.const 0
local.get $localStart
i32.add
i32.const 0
i32.add
i32.load
i32.const 3
i32.store
i32.const 0
local.get $localStart
i32.add
i32.const 4
i32.add
i32.load
i32.const 5
i32.store
i32.const 8
local.get $localStart
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 4
call $reserveHeap
global.get $NP
i32.store
local.get $tempI
i32.const 4
i32.add
i32.const 4
call $reserveHeap
global.get $NP
i32.store
local.get $tempI
i32.const 8
i32.add
i32.const 4
call $reserveHeap
global.get $NP
i32.store
local.get $tempI
i32.const 12
i32.add
i32.const 4
call $reserveHeap
global.get $NP
i32.store
i32.const 8
local.get $localStart
i32.add
i32.const 8
i32.const 0
i32.const 1
i32.add
local.tee $temp
local.get $temp
i32.const 2
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.const 0
i32.add
i32.load
i32.const 3
i32.store
i32.const 24
local.get $localStart
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 20
i32.store
local.get $tempI
i32.const 4
i32.add
i32.const 1
i32.store
local.get $tempI
i32.const 8
i32.add
i32.const 120
i32.store
local.get $tempI
i32.const 12
i32.add
i32.const 0
i32.store
local.get $tempI
i32.const 16
i32.add
i32.const 45
i32.store
local.get $tempI
i32.const 20
i32.add
i32.const 1
i32.store
i32.const 24
local.get $localStart
i32.add
i32.const 8
i32.const 2
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.const 0
i32.add
i32.const 24
local.get $localStart
i32.add
i32.const 8
i32.const 0
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.const 0
i32.add
i32.load
i32.store
i32.const 24
local.get $localStart
i32.add
i32.const 8
i32.const 0
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.const 4
i32.add
i32.const 0
i32.store
i32.const 48
local.get $localStart
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 1
i32.store
local.get $tempI
i32.const 4
i32.add
i32.const 0
i32.store
local.get $tempI
i32.const 8
i32.add
i32.const 1
i32.store
local.get $tempI
i32.const 12
i32.add
i32.const 1
i32.store
local.get $tempI
i32.const 16
i32.add
i32.const 2
i32.store
local.get $tempI
i32.const 20
i32.add
i32.const 3
i32.store
i32.const 48
local.get $localStart
i32.add
i32.const 0
i32.add
i32.const 4
i32.const 2
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.const 48
local.get $localStart
i32.add
i32.const 0
i32.add
i32.const 4
i32.const 1
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.load
i32.store
i32.const 72
local.get $localStart
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 4
call $reserveHeap
global.get $NP
i32.store
local.get $tempI
i32.const 4
i32.add
i32.const 4
call $reserveHeap
global.get $NP
i32.store
local.get $tempI
i32.const 8
i32.add
i32.const 4
call $reserveHeap
global.get $NP
i32.store
i32.const 72
local.get $localStart
i32.add
i32.const 4
i32.const 0
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.load
i32.const 0
i32.store
i32.const 0
i32.const 0
i32.add
i32.load
i32.const 4
i32.eq
call $print
i32.const 4
i32.const 0
i32.add
i32.load
i32.const 40
i32.eq
call $print
i32.const 8
i32.const 0
i32.add
i32.load
i32.const 1
i32.eq
call $print
i32.const 12
i32.const 0
i32.add
i32.load
i32.const 0
i32.eq
call $print
i32.const 84
local.get $localStart
i32.add
local.set $tempI
local.get $tempI
i32.const 0
i32.add
i32.const 0
i32.store
block
loop
i32.const 84
local.get $localStart
i32.add
i32.load
i32.const 10
i32.lt_s
i32.eqz
br_if 1
i32.const 16
i32.const 0
i32.add
i32.const 4
i32.const 84
local.get $localStart
i32.add
i32.load
local.tee $temp
local.get $temp
i32.const 10
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.load
i32.const 84
local.get $localStart
i32.add
i32.load
i32.const 1
i32.add
i32.eq
call $print
i32.const 84
local.get $localStart
i32.add
i32.const 84
local.get $localStart
i32.add
i32.load
i32.const 1
i32.add
i32.store
br 0
end
end
i32.const 56
i32.const 0
i32.add
i32.const 0
i32.add
i32.load
i32.const 2
i32.eq
call $print
i32.const 64
i32.const 0
i32.add
i32.load
i32.load
i32.const 4
i32.eq
call $print
i32.const 68
i32.const 0
i32.add
i32.load
i32.load
i32.const 3
i32.eq
call $print
i32.const 72
i32.const 0
i32.add
i32.load
i32.const 0
i32.add
i32.load
i32.const 2
i32.eq
call $print
i32.const 0
local.get $localStart
i32.add
i32.const 0
i32.add
i32.load
i32.load
i32.const 3
i32.eq
call $print
i32.const 0
local.get $localStart
i32.add
i32.const 4
i32.add
i32.load
i32.load
i32.const 5
i32.eq
call $print
i32.const 8
local.get $localStart
i32.add
i32.const 8
i32.const 1
local.tee $temp
local.get $temp
i32.const 2
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.const 0
i32.add
i32.load
i32.load
i32.const 3
i32.eq
call $print
i32.const 24
local.get $localStart
i32.add
i32.const 8
i32.const 2
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.const 0
i32.add
i32.load
i32.const 20
i32.eq
call $print
i32.const 24
local.get $localStart
i32.add
i32.const 8
i32.const 0
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.const 4
i32.add
i32.load
i32.const 0
i32.eq
call $print
i32.const 48
local.get $localStart
i32.add
i32.const 0
i32.add
i32.const 4
i32.const 0
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.load
i32.const 1
i32.eq
call $print
i32.const 48
local.get $localStart
i32.add
i32.const 0
i32.add
i32.const 4
i32.const 1
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.load
i32.const 0
i32.eq
call $print
i32.const 48
local.get $localStart
i32.add
i32.const 0
i32.add
i32.const 4
i32.const 2
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.load
i32.const 0
i32.eq
call $print
i32.const 72
local.get $localStart
i32.add
i32.const 4
i32.const 0
local.tee $temp
local.get $temp
i32.const 3
i32.ge_s
local.get $temp
i32.const 0
i32.lt_s
i32.or
if
i32.const 1
call $exception
end
i32.mul
i32.add
i32.load
i32.load
i32.const 0
i32.eq
call $print
i32.const 0
call $freeStack
return
)
)
