%{
#include <stdio.h>
#include <string.h>
#include "P1.tab.h"
extern void yyerror(char* msg);
%}

%option noyywrap

%%
"#define"               {return DEFINE;}
"System.out.println"    {return PRINT;}

"main"      {return MAIN;}
"return"    {return RET;}
"class"     {return CLASS;}

"this"      {return THIS;}
"new"       {return NEW;}

"public"    {return PUBLIC;}
"static"    {return STATIC;}

"if"        {return IF;}
"else"      {return ELSE;}
"do"        {return DO;}
"while"     {return WHILE;}

"int"       {return INT;}
"boolean"   {return BOOLEAN;}
"String"    {return STRING;}
"void"      {return VOID;}

"extends"   {return EXTENDS;}
"length"    {return LENGTH;}


"{"         {return LB1;}
"}"         {return RB1;}
"("         {return LB2;}
")"         {return RB2;}
"["         {return LB3;}
"]"         {return RB3;}

";"         {return SEMICOLON;}
","         {return COMMA;}

"&&"        {return ANDD;}
"||"        {return ORR;}

"!="        {return NEQ;}
"<="        {return LEQ;}
"="         {return EQ;}

"+"         {return PLUS;}
"-"         {return MINUS;}
"*"         {return MULT;}
"/"         {return DIV;}

"."         {return DOT;}

"!"         {return NOT;}


(0|([1-9][0-9]*))       { yylval.strval = malloc(sizeof(char)*(strlen(yytext)+1)); strcpy(yylval.strval,yytext); return INTVAL; }
("false"|"true")        { yylval.strval = malloc(sizeof(char)*(strlen(yytext)+1)); strcpy(yylval.strval,yytext); return BOOLVAL; }

[a-zA-Z_][a-zA-Z0-9_]*  { yylval.strval = malloc(sizeof(char)*(strlen(yytext)+1)); strcpy(yylval.strval,yytext); return ID; }


[ \n\t]             ;//whitespace
(\/\/.*\n)          ;//single line comment
(\/\*(.|\n)*\*\/)   ;//multi line comment

[\r\f]              ;//ignore
.                   {yyerror("");exit(1);} 
%%