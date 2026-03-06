# This shell script emits a C file. -*- C -*-
#   Copyright (C) 2025 Free Software Foundation, Inc.
#
# This file is part of the GNU Binutils.
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street - Fifth Floor, Boston,
# MA 02110-1301, USA.
#

# This file is sourced from elf.em, and defines snowhousecpu-elf specific
# routines
fragment <<EOF

#include "../bfd/elf-bfd.h"
#include "elf/snowhousecpu.h"
#include "bfd.h"

static void
elf_snowhousecpu_before_parse (void)
{
  /* First call the ELF version.  */
  gld${EMULATION_NAME}_before_parse ();
}

static void
elf_snowhousecpu_after_open (void)
{
  /* First call the ELF version.  */
  gld${EMULATION_NAME}_after_open ();
}

/* This is called after the sections have been attached to output
   sections, but before any sizes or addresses have been set.  */
static void
elf_snowhousecpu_before_allocation (void)
{
  gld${EMULATION_NAME}_before_allocation ();
  if (RELAXATION_DISABLED_BY_DEFAULT)
  {
    ENABLE_RELAXATION;
  }
}
EOF

LDEMUL_BEFORE_PARSE=elf_snowhousecpu_before_parse
LDEMUL_AFTER_OPEN=elf_snowhousecpu_after_open
#LDEMUL_CHOOSE_TARGET=elf_snowhousecpu_choose_target
LDEMUL_BEFORE_ALLOCATION=elf_snowhousecpu_before_allocation
