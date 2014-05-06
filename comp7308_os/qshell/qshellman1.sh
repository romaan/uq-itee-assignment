#!/bin/bash
cwd=`pwd`
export MANPATH="$cwd/man:$(manpath)"
man 1 qshell
