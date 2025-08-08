# Copyright (C) 2025 Free Software Foundation, Inc.
#
# Copying and distribution of this file, with or without modification,
# are permitted in any medium without royalty provided the copyright
# notice and this notice are preserved.

TORS=".tors :
  {
    ___ctors = . ;
    *(.ctors)
    ___ctors_end = . ;
    ___dtors = . ;
    *(.dtors)
    ___dtors_end = . ;
  } > ram"

cat <<EOF
/* Copyright (C) 2025 Free Software Foundation, Inc.

   Copying and distribution of this script, with or without modification,
   are permitted in any medium without royalty provided the copyright
   notice and this notice are preserved.  */

OUTPUT_FORMAT("${OUTPUT_FORMAT}")
OUTPUT_ARCH(${ARCH})

MEMORY
{
  /* rom (rx) : */
  /* ram (w!rx) : ORIGIN = 0x0000, LENGTH = 0x4000000 */
  ram (w!rx) : ORIGIN = 0x0000, LENGTH = 0x2000000
  far_ram_0 (w!rx) : ORIGIN = 0x1000000, LENGTH = 0x1000
  far_ram_1 (w!rx) : ORIGIN = 0x2000000, LENGTH = 0x1000
  farthest_ram (w!rx) : ORIGIN = 0xfe000004, LENGTH = 0x8000
}

SECTIONS
{
  .text :
  {
    ${RELOCATING+KEEP (*(SORT_NONE(.init)))
    *(.text)
    KEEP (*(SORT_NONE(.fini)))
    *(.strings)
    _etext = . ; }
  } ${RELOCATING+ > ram}
  ${CONSTRUCTING+${TORS}}
  .data :
  {
    ${RELOCATING+ ___data_start = . ; }
    ${RELOCATING+ ___data_source = . ; }
    *(.data)
    ${RELOCATING+ ___data_end = . ; }
    ${RELOCATING+ ___data_size = . - ___data_start ;  }
  } ${RELOCATING+ > ram}
  .bss :
  {
    ${RELOCATING+ ___bss_start = . ; }
    *(.bss)
    *(COMMON)
    ${RELOCATING+ ___bss_end = . ;  }
    ${RELOCATING+ ___bss_size = . - ___bss_start ;  }
  } ${RELOCATING+ > ram}
  /* ${RELOCATING+ PROVIDE (_stack = 0x03fffffc)} */
  .stack ${RELOCATING+ 0x30000 }  :
  {
    ${RELOCATING+ _stack = . ; }
    *(.stack)
  } ${RELOCATING+ > ram}
  .stab 0 ${RELOCATING+(NOLOAD)} :
  {
    *(.stab)
  }
  .stabstr 0 ${RELOCATING+(NOLOAD)} :
  {
    *(.stabstr)
  }
  .far_0 :
  {
    ${RELOCATING+ ___far_0_start = . ; }
    *(.far_0)
    ${RELOCATING+ ___far_0_end = . ;  }
    ${RELOCATING+ ___far_0_size = . - ___far_0_start ;  }
  } ${RELOCATING+ > far_ram_0}
  .far_1 :
  {
    ${RELOCATING+ ___far_1_start = . ; }
    *(.far_1)
    ${RELOCATING+ ___far_1_end = . ;  }
    ${RELOCATING+ ___far_1_size = . - ___far_1_start ;  }
  } ${RELOCATING+ > far_ram_1}
  .farthest :
  {
    ${RELOCATING+ ___farthest_start = . ; }
    *(.farthest)
    ${RELOCATING+ ___farthest_end = . ;  }
    ${RELOCATING+ ___farthest_size = . - ___farthest_start ;  }
  } ${RELOCATING+ > farthest_ram}
}
EOF
