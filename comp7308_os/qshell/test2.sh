#!/bin/sh

#The following is a here document
#It takes everything between the __EOF__ as input to qshell.
#This should run just as if you typed each command in at the prompt.
echo No Argument Command Test
echo ps
ps
echo Command with Argument Test
echo ls -l
ls -l
echo Output Redirection Test
echo date > myFile
date > myFile
echo Input Redirection Test
echo wc < myFile
echo Comment Test
#Does myShell treat this as a comment?
echo Blank Line Test


#here document done
