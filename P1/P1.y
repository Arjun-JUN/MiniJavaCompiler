%{
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

extern int yylex();
void yyerror(char* msg);

/*arglist*/
struct argList{
    char* name;
    struct argList* next;
};
int countArg(struct argList* arg){
    struct argList* ptr = arg;
    int count = 0;
    while(ptr!=NULL){
        count++;
        ptr = ptr->next;
    }
    return count;
}
/*arglist*/

/*paralist*/
struct paraList{
    char* val;
    struct paraList* next;
};
int countPara(struct paraList* para){
    struct paraList* ptr = para;
    int count = 0;
    while(ptr!=NULL){
        count++;
        ptr = ptr->next;
    }
    return count;
}
/*paralist*/



/*macro array*/
struct macro{
    char* id;
    struct argList* args;
    char* body;
};
struct macro*  macroStmts[1024];
int mslen = 0;
struct macro*   macroExp[1024];
int melen = 0;
/*macro array*/



/*Storing and getting macro*/
struct macro* findMacroStmt(char* id){
    int i = 0;
    while(i<mslen && i<1024){
        if (!strcmp(macroStmts[i]->id,id)){
            return macroStmts[i];
        }
        i++;
    }
    return NULL;
}
struct macro* findMacroExp(char* id){
    int i = 0;
    while(i<melen && i<1024){
        if (!strcmp(macroExp[i]->id,id)){
            return macroExp[i];
        }
        i++;
    }
    return NULL;
}
void SaveMacroStmt(char* id, struct argList* args, char* stmt){
    if(findMacroStmt(id)!=NULL || findMacroExp(id)!=NULL){
        //error
        yyerror("");
        exit(1);
    }
    macroStmts[mslen] = malloc(sizeof(struct macro));
    macroStmts[mslen]->id = strdup(id);
    macroStmts[mslen]->args = args;
    macroStmts[mslen]->body = strdup(stmt);
    
    mslen++;

}
void SaveMacroExp(char* id, struct argList* args, char* exp){
    if(findMacroStmt(id)!=NULL || findMacroExp(id)!=NULL){
        //error
        yyerror("");
        exit(1);
    }
    macroExp[melen] = malloc(sizeof(struct macro));
    macroExp[melen]->id = strdup(id);
    macroExp[melen]->args = args;
    macroExp[melen]->body = strdup(exp);
    
    melen++;
}
/*Storing and getting macro*/



/*Get body of macro*/
char* replaceStmt(char* body, char* arg, char* para){
    char* buffer = malloc(100000*sizeof(char));
    buffer[0] = '\0';
    int arglen = strlen(arg);
    int paralen = strlen(para);
    int bodylen = strlen(body);
    int i = 0;int j = 0;
    while(i<bodylen && body[i]!='\0'){
        if(strstr(&body[i], arg) == &body[i]){
            if ((body[i+arglen] <= 'Z' && body[i+arglen] >= 'A')||(body[i+arglen] <= 'z' && body[i+arglen] >= 'a')||(body[i+arglen] == '_')||(body[i+arglen] <='9' && body[i+arglen] >='0')){
                buffer[j] = body[i];
                j++;i++;buffer[j] = '\0';
            }
            else if ( i>0 && ((body[i-1] <= 'Z' && body[i-1] >= 'A')||(body[i-1] <= 'z' && body[i-1] >= 'a')||(body[i-1] == '_')||(body[i-1] <='9' && body[i-1] >='0'))){
                buffer[j] = body[i];
                j++;i++;buffer[j] = '\0';
            }
            else{
                strcat(buffer,para);
                i+=arglen;
                j+=paralen;
            }
        }
        else{
            buffer[j] = body[i];
            j++;i++;buffer[j] = '\0';
        }
    }
    return buffer;
}
char* getStmt(char* id, struct paraList* parameters){
    if(findMacroStmt(id) == NULL){/*error*/yyerror("");exit(1);}

    struct macro* mac = findMacroStmt(id);
    char* output = strdup(mac->body);
    

    struct argList*  argptr  = mac->args;
    struct paraList* paraptr = parameters;

    if(countArg(argptr) != countPara(paraptr)){/*error*/yyerror("");exit(1);}

    while(argptr != NULL){
        char* tmp = replaceStmt(output, argptr->name, paraptr->val);
        free(output);
        output = tmp;
        argptr  = argptr->next;
        paraptr = paraptr->next;
    }
    
    return output;
}
char* replaceExp(char* body, char* arg, char* para){
    char* buffer = malloc(100000*sizeof(char));
    buffer[0] = '\0';
    int arglen = strlen(arg);
    int paralen = strlen(para);
    int bodylen = strlen(body);
    int i = 0;int j = 0;
    while(i<bodylen && body[i]!='\0'){
        if(strstr(&body[i], arg) == &body[i]){
            if ((body[i+arglen] <= 'Z' && body[i+arglen] >= 'A')||(body[i+arglen] <= 'z' && body[i+arglen] >= 'a')||(body[i+arglen] == '_')||(body[i+arglen] <='9' && body[i+arglen] >='0')){
                buffer[j] = body[i];
                j++;i++;buffer[j] = '\0';
            }
            else if ( i>0 && ((body[i-1] <= 'Z' && body[i-1] >= 'A')||(body[i-1] <= 'z' && body[i-1] >= 'a')||(body[i-1] == '_')||(body[i-1] <='9' && body[i-1] >='0'))){
                buffer[j] = body[i];
                j++;i++;buffer[j] = '\0';
            }
            else{
                char* temp = malloc(1000000*sizeof(char));
                strcpy(temp,"(");
                strcat(temp,para);
                strcat(temp,")");
                strcat(buffer,temp);
                i+=arglen;
                j+=paralen+2;
                free(temp);
            }
        }
        else{
            buffer[j] = body[i];
            j++;i++;buffer[j] = '\0';
        }
    }
    return buffer;
}
char* getExp(char* id, struct paraList* parameters){
    if(findMacroExp(id) == NULL){/*error*/yyerror("");exit(1);}

    struct macro* mac = findMacroExp(id);
    char* output = strdup(mac->body);

    struct argList*  argptr  = mac->args;
    struct paraList* paraptr = parameters;

    if(countArg(argptr) != countPara(paraptr)){/*error*/yyerror("");exit(1);}

    while(argptr != NULL){
        char* tmp = replaceExp(output, argptr->name, paraptr->val);
        free(output);
        output = tmp;
        argptr  = argptr->next;
        paraptr = paraptr->next;
    }

    return output;
}
/*Get body of macro*/



/*concatenate to print*/
char* catenate(char* strings[],int num){
    char* result = malloc(1000000);
    strcpy(result,strings[0]);
    for(int i = 1 ; i < num ; i++){
        strcat(result,strings[i]);
    }
    return result;
}
/*concatenate to print*/

%}

%union{
    char* strval ;
    struct argList* arg;
    struct paraList* para;
}

%token DEFINE
%token PRINT

%token MAIN
%token RET
%token CLASS

%token THIS
%token NEW

%token PUBLIC
%token STATIC

%token IF
%token ELSE
%token DO
%token WHILE

%token INT
%token BOOLEAN
%token STRING
%token VOID

%token EXTENDS
%token LENGTH


%token LB1
%token RB1
%token LB2
%token RB2
%token LB3
%token RB3

%token SEMICOLON
%token COMMA

%token ANDD
%token ORR

%token NEQ
%token LEQ
%token EQ

%token PLUS
%token MINUS
%token MULT
%token DIV

%token DOT

%token NOT



%token <strval> INTVAL
%token <strval> BOOLVAL

%token <strval> ID


%type <strval> MainClass 
%type <strval> TypeDecStar 
%type <strval> TypeDeclaration
%type <strval> MethodDecStar
%type <strval> MethodDeclaration
%type <strval> Args 
%type <strval> Args2 
%type <strval> TypeStar
%type <strval> Type	
%type <strval> StatementStar
%type <strval> Statement
%type <strval> ifElseStmts

%type <para> Parameters
%type <para> Parameters2

%type <strval> Expression

%type <strval> Parara
%type <strval> Parara2

%type <strval> PrimaryExpression
%type <strval> Term

%type <arg> ArgForDef
%type <arg> ArgForDef2


%%
Goal	:   
    MacroDefStar MainClass TypeDecStar { printf("%s\n%s",$2,$3);}



MainClass	:   
    CLASS ID LB1 PUBLIC STATIC VOID MAIN LB2 STRING LB3 RB3 ID RB2 LB1 PRINT LB2 Expression RB2 SEMICOLON RB1 RB1 {char* strings[] = {"class ",$2,"\n{\npublic static void main ( String[] ",$12,")\n{\nSystem.out.println (",$17,") ;\n}\n\n}\n"}; char* code = catenate(strings,7);free($2);free($12);free($17); $$ = code ;}



TypeDecStar :
    TypeDeclaration TypeDecStar     {char* strings[] = {$1,"\n",$2};char* code = catenate(strings,3);free($1);free($2);$$ = code ;}
|   /*epsilon*/                     {char* code = malloc(1);strcpy(code,"");$$ = code ;}//epsilon

TypeDeclaration	:	
    CLASS ID LB1 TypeStar MethodDecStar RB1                 {char* strings[] = {"class ",$2,"\n{\n",$4,"\n",$5,"\n}\n"};char* code = catenate(strings,7);free($2);free($4);free($5);$$ = code ;}
|	CLASS ID EXTENDS ID LB1 TypeStar MethodDecStar RB1      {char* strings[] = {"class ",$2," extends ",$4,"\n{\n",$6,"\n",$7,"\n}\n"};char* code = catenate(strings,9);free($2);free($4);free($6);free($7);$$ = code ;}



MethodDecStar   :
    MethodDeclaration MethodDecStar     {char* strings[] = {$1,"\n",$2};char* code = catenate(strings,3);free($1);free($2);$$ = code ;}
|   /*epsilon*/                         {char* code = malloc(1);strcpy(code,"");$$ = code ;}//epsilon

MethodDeclaration	:	
    PUBLIC Type ID LB2 Args RB2 LB1 TypeStar StatementStar RET Expression SEMICOLON RB1       {char* strings[] = {"public ",$2," ",$3,"(",$5,")","\n{\n",$8,"\n",$9,"\n","return"," ",$11," ;","\n}\n"};char* code = catenate(strings,17);free($2);free($3);free($5);free($8);free($9);free($11);$$ = code ;}

Args    :
    Type ID Args2           {char* strings[] = {$1," ",$2,$3};char* code = catenate(strings,4);free($1);free($2);free($3);$$ = code ;}
|   /*epsilon*/             {char* code = malloc(1);strcpy(code,"");$$ = code ;}//epsilon

Args2   :
    COMMA Type ID Args2         {char* strings[] = {", ",$2," ",$3,$4};char* code = catenate(strings,5);free($2);free($3);free($4);$$ = code ;}
|   /*epsilon*/                 {char* code = malloc(1);strcpy(code,"");$$ = code ;}//epsilon



TypeStar :
    TypeStar Type ID SEMICOLON      {char* strings[] = {$1,$2," ",$3," ;\n"};char* code = catenate(strings,5);free($1);free($2);free($3);$$ = code ;}
|   /*epsilon*/                     {char* code = malloc(1);strcpy(code,"");$$ = code ;}//epsilon

Type	:	
    INT LB3 RB3         {char* strings[] = {"int","[","]"};char* code = catenate(strings,3);$$ = code ;}
|	BOOLEAN             {char* strings[] = {"boolean"};char* code = catenate(strings,1);$$ = code ;}
|   INT                 {char* strings[] = {"int"};char* code = catenate(strings,1);$$ = code ;}
|	ID                  {char* strings[] = {$1};char* code = catenate(strings,1);free($1);$$ = code ;}



StatementStar :
    Statement StatementStar     {char* strings[] = {$1,$2};char* code = catenate(strings,2);free($1);free($2);$$ = code ;}
|   /*epsilon*/                 {char* code = malloc(1);strcpy(code,"");$$ = code ;}
Statement	:
    LB1 StatementStar RB1                               {char* strings[] = {"\n{\n",$2,"}\n"};    char* code = catenate(strings,3);free($2);    $$ = code ;}
|	PRINT LB2 Expression RB2 SEMICOLON                  {char* strings[] = {"System.out.println","(",$3,")"," ;\n"};    char* code = catenate(strings,5);free($3);     $$ = code ;}
|	ID EQ Expression SEMICOLON                          {char* strings[] = {$1," = ",$3," ;\n"};    char* code = catenate(strings,4);free($1); free($3);     $$ = code ;}
|	ID LB3 Expression RB3 EQ Expression SEMICOLON       {char* strings[] = {$1,"[",$3,"]"," = ",$6," ;"};    char* code = catenate(strings,7);free($1); free($3); free($6);     $$ = code ;}
|   ifElseStmts                                         {$$ = $1;}
|	DO Statement WHILE LB2 Expression RB2 SEMICOLON     {char* strings[] = {"do ",$2,"while","(",$5,")"," ;\n"};    char* code = catenate(strings,7);free($2); free($5);     $$ = code ;}
|	WHILE LB2 Expression RB2 Statement                  {char* strings[] = {"while","(",$3,")",$5};     char* code = catenate(strings,5);free($3); free($5);     $$ = code ;}
|	ID LB2 Parameters RB2 SEMICOLON                     /* Macro stmt call */{$$ = getStmt($1,$3);}
    
ifElseStmts:
	IF LB2 Expression RB2 Statement ELSE Statement      {char* strings[] = {"if ","(",$3,")",$5,"else ",$7};    char* code = catenate(strings,7);free($3); free($5);free($7);    $$ = code ;}
|	IF LB2 Expression RB2 Statement                     {char* strings[] = {"if ","(",$3,")",$5};    char* code = catenate(strings,5); free($3); free($5);   $$ = code ;}



Parameters :
    Expression Parameters2              {struct paraList* para = malloc(sizeof(struct paraList)); para->val = $1; para->next = $2; $$ = para;}
|   /*epsilon*/                         {$$ = NULL;}
Parameters2 :
    COMMA Expression Parameters2        {struct paraList* para = malloc(sizeof(struct paraList)); para->val = $2; para->next = $3; $$ = para;}
|   /*epsilon*/                         {$$ = NULL;}



Expression	:	
    PrimaryExpression ANDD PrimaryExpression        {char* strings[] = {$1," && ",$3};char* code = catenate(strings,3);free($1);free($3);$$ = code ;}
|	PrimaryExpression ORR PrimaryExpression         {char* strings[] = {$1," || ",$3};char* code = catenate(strings,3);free($1);free($3);$$ = code ;}
|	PrimaryExpression NEQ PrimaryExpression         {char* strings[] = {$1," != ",$3};char* code = catenate(strings,3);free($1);free($3);$$ = code ;}
|	PrimaryExpression LEQ PrimaryExpression         {char* strings[] = {$1," <= ",$3};char* code = catenate(strings,3);free($1);free($3);$$ = code ;}
|	PrimaryExpression PLUS PrimaryExpression        {char* strings[] = {$1," + ",$3};char* code = catenate(strings,3);free($1);free($3);$$ = code ;}
|	PrimaryExpression MINUS PrimaryExpression       {char* strings[] = {$1," - ",$3};char* code = catenate(strings,3);free($1);free($3);$$ = code;}
|	PrimaryExpression MULT PrimaryExpression        {char* strings[] = {$1," * ",$3};char* code = catenate(strings,3);free($1);free($3);$$ = code ;}
|	PrimaryExpression DIV PrimaryExpression         {char* strings[] = {$1," / ",$3};char* code = catenate(strings,3);free($1);free($3);$$ = code ;}
|	PrimaryExpression LB3 PrimaryExpression RB3     {char* strings[] = {$1,"[",$3,"]"};char* code = catenate(strings,4);free($1);free($3);$$ = code ;}
|	PrimaryExpression DOT LENGTH                    {char* strings[] = {$1,".","length"};char* code = catenate(strings,3);free($1);$$ = code ;}
|	PrimaryExpression                               {char* strings[] = {$1};char* code = catenate(strings,1);$$ = code ;}
|	PrimaryExpression DOT ID LB2 Parara RB2         {char* strings[] = {$1,".",$3,"(",$5,")"};char* code = catenate(strings,6);free($1);free($3);free($5);$$ = code ;}
|	ID LB2 Parameters RB2                           /* Macro expr call */{if(strcmp($1,"ZERO"));$$ = getExp($1,$3);}  

Parara :
    Expression Parara2                  {char* strings[] = {$1,$2};char* code = catenate(strings,2);free($1); free($2);$$ = code ;}
|   /*epsilon*/                         {char* code = malloc(1);strcpy(code,"");$$ = code ;}//epsilon
Parara2 :
    COMMA Expression Parara2            {char* strings[] = {", ",$2,$3};    char* code = catenate(strings,3);free($3); free($2);    $$ = code ;}
|   /*epsilon*/                         {char* code = malloc(1);strcpy(code,"");$$ = code ;}//epsilon


PrimaryExpression	:
    Term                            {$$ = $1;}
|	NEW INT LB3 Expression RB3      {char* strings[] = {"new ","int","[",$4,"]"};char* code = catenate(strings,5);free($4);$$ = code ;} 
|	NEW ID LB2 RB2                  {char* strings[] = {"new ",$2,"(",")"};char* code = catenate(strings,4);free($2);$$ = code ;} 
|	NOT Expression                  {char* strings[] = {"!",$2};char* code = catenate(strings,2);free($2);$$ = code ;} 
|	LB2 Expression RB2              {char* strings[] = {"(",$2,")"};char* code = catenate(strings,3);free($2);$$ = code ;} 
Term    :
    MINUS INTVAL                    {char* code = malloc(1000000);strcpy(code,"-");strcat(code,$2);free($2);$$ = code;}
|   PLUS INTVAL                     {char* code = malloc(1000000);strcpy(code,"+");strcat(code,$2);free($2);$$ = code;}
|   INTVAL                          {$$ = $1;} 
|   BOOLVAL                         {$$ = $1;} 
|	ID                              {$$ = $1;} 
|	THIS                            {char* strings[] = {"this"};char* code = catenate(strings,1);$$ = code ;} 



MacroDefStar :
    MacroDefStar MacroDefinition        {} 
|   /*epsilon*/                         {}
MacroDefinition	:	
    MacroDefExpression          {} 
|	MacroDefStatement           {} 

ArgForDef2:
    COMMA ID ArgForDef2         {struct argList* arg = malloc(sizeof(struct argList)); arg->name = strdup($2); free($2); arg->next = $3; $$ = arg;}
|   /*epsilon*/                 {$$ = NULL;}//epsilon
ArgForDef   :
    ID ArgForDef2           {struct argList* arg = malloc(sizeof(struct argList)); arg->name = strdup($1); free($1); arg->next = $2; $$ = arg;}
|   /*epsilon*/             {$$ = NULL;}
MacroDefStatement	:
    DEFINE ID LB2 ArgForDef RB2 LB1 StatementStar RB1       {SaveMacroStmt($2,$4,$7);free($2);free($7);} 
MacroDefExpression	:
    DEFINE ID LB2 ArgForDef RB2 LB2 Expression RB2          {char* strings[] = {"(",$7,")"};char* code = catenate(strings,3);free($7);SaveMacroExp($2,$4,code);free($2);free(code);}

%%

int main(int argc, char* argv[]) {
    yyparse();
}

void yyerror(char* msg) {
    printf("// Failed to parse macrojava code.");
    exit(1);
}

