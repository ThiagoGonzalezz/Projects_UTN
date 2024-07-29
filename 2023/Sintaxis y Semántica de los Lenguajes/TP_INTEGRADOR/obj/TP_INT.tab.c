
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton implementation for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "2.4.1"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1

/* Using locations.  */
#define YYLSP_NEEDED 1



/* Copy the first part of user declarations.  */

/* Line 189 of yacc.c  */
#line 1 "../src/TP_INT.y"

#include <math.h>
#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include "funcionesListados.c"

//#include "funcionesFlex.c"
 //#include "funcionesFlex.h"



extern FILE *yyin; //para evitar el error que teniamos en Windows con el archivo de entrada
extern struct NodoCaractNoReconocidos* listaErroresLexicos; //para que flex guarde los errores lexicos en esta lista
extern int linActual;
extern int colActual;
extern void imprimirListaCaracNoReco();
extern void lineaSeparadora1(int sep1);
extern void lineaPrincipal1(int sep1);
extern void lineaFinal1(int sep1);

int yylex();
int yyerror (const char *s);
int yywrap(){ return(1); }
char* id;
int errorSintactico;

char* tipo = NULL;
char* identificador;
char* auxIdInvocacion;
int tipoSent;
int cantPunteros = 0;
int flagArray;

NodoSentencias* listaSentencias = NULL;

NodoAuxiliarF auxiliarFun;

NodoFunciones* listaFunDecla = NULL;

NodoParametros* listaAuxParametrosDecla = NULL;

NodoParametrosInv* listaAuxParametrosInv = NULL;

NodoErrorSintatico* listaErrorSintactico = NULL;

NodoErroresSemanticos* listaDeErroresSemanticos = NULL;

NodoVarible* listaDeVars=NULL;
NodoVarible* variablesAdeclarar = NULL;



/* Line 189 of yacc.c  */
#line 128 "TP_INT.tab.c"

/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* Enabling the token table.  */
#ifndef YYTOKEN_TABLE
# define YYTOKEN_TABLE 0
#endif


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     NUM = 258,
     FLOAT = 259,
     NOMBRE_TIPO = 260,
     IDENTIFICADOR = 261,
     LITERAL_CADENA = 262,
     OR = 263,
     AND = 264,
     OP_ADD = 265,
     OP_SUB = 266,
     OP_MAS_IG = 267,
     OP_COMP = 268,
     SIMB_DIFF = 269,
     RETURN = 270,
     DO = 271,
     WHILE = 272,
     IF = 273,
     ELSE = 274,
     FOR = 275,
     SWITCH = 276,
     CONSTANTE_CARACTER = 277
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
{

/* Line 214 of yacc.c  */
#line 55 "../src/TP_INT.y"

    char op;
    int dval;
    char* str;



/* Line 214 of yacc.c  */
#line 194 "TP_INT.tab.c"
} YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

#if ! defined YYLTYPE && ! defined YYLTYPE_IS_DECLARED
typedef struct YYLTYPE
{
  int first_line;
  int first_column;
  int last_line;
  int last_column;
} YYLTYPE;
# define yyltype YYLTYPE /* obsolescent; will be withdrawn */
# define YYLTYPE_IS_DECLARED 1
# define YYLTYPE_IS_TRIVIAL 1
#endif


/* Copy the second part of user declarations.  */


/* Line 264 of yacc.c  */
#line 219 "TP_INT.tab.c"

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#elif (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
typedef signed char yytype_int8;
#else
typedef short int yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(msgid) dgettext ("bison-runtime", msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(msgid) msgid
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(e) ((void) (e))
#else
# define YYUSE(e) /* empty */
#endif

/* Identity function, used to suppress warnings about constant conditions.  */
#ifndef lint
# define YYID(n) (n)
#else
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static int
YYID (int yyi)
#else
static int
YYID (yyi)
    int yyi;
#endif
{
  return yyi;
}
#endif

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#     ifndef _STDLIB_H
#      define _STDLIB_H 1
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's `empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (YYID (0))
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined _STDLIB_H \
       && ! ((defined YYMALLOC || defined malloc) \
	     && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef _STDLIB_H
#    define _STDLIB_H 1
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
	 || (defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL \
	     && defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
  YYLTYPE yyls_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE) + sizeof (YYLTYPE)) \
      + 2 * YYSTACK_GAP_MAXIMUM)

/* Copy COUNT objects from FROM to TO.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(To, From, Count) \
      __builtin_memcpy (To, From, (Count) * sizeof (*(From)))
#  else
#   define YYCOPY(To, From, Count)		\
      do					\
	{					\
	  YYSIZE_T yyi;				\
	  for (yyi = 0; yyi < (Count); yyi++)	\
	    (To)[yyi] = (From)[yyi];		\
	}					\
      while (YYID (0))
#  endif
# endif

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)				\
    do									\
      {									\
	YYSIZE_T yynewbytes;						\
	YYCOPY (&yyptr->Stack_alloc, Stack, yysize);			\
	Stack = &yyptr->Stack_alloc;					\
	yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
	yyptr += yynewbytes / sizeof (*yyptr);				\
      }									\
    while (YYID (0))

#endif

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  2
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   302

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  42
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  70
/* YYNRULES -- Number of rules.  */
#define YYNRULES  129
/* YYNRULES -- Number of states.  */
#define YYNSTATES  217

/* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   277

#define YYTRANSLATE(YYX)						\
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    37,     2,     2,     2,     2,    36,     2,
      38,    39,    24,    33,    27,    34,     2,    35,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,    30,    23,
      32,    28,    31,    29,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    25,     2,    26,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    40,     2,    41,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const yytype_uint16 yyprhs[] =
{
       0,     0,     3,     4,     7,     9,    11,    14,    15,    16,
      24,    25,    28,    29,    34,    36,    38,    39,    44,    45,
      46,    51,    52,    58,    59,    62,    64,    66,    70,    72,
      74,    76,    82,    85,    86,    90,    93,    94,    98,   101,
     104,   105,   108,   111,   114,   115,   118,   122,   125,   129,
     132,   135,   136,   140,   142,   144,   147,   150,   151,   154,
     157,   160,   163,   166,   169,   171,   173,   175,   177,   180,
     181,   183,   185,   188,   190,   192,   194,   196,   198,   202,
     203,   206,   210,   214,   215,   217,   220,   221,   225,   227,
     229,   231,   233,   235,   236,   242,   244,   246,   247,   249,
     252,   253,   257,   258,   264,   265,   268,   271,   274,   276,
     278,   280,   282,   284,   288,   291,   295,   296,   298,   299,
     306,   307,   316,   317,   328,   329,   337,   338,   345,   346
};

/* YYRHS -- A `-1'-separated list of the rules' RHS.  */
static const yytype_int8 yyrhs[] =
{
      43,     0,    -1,    -1,    43,    44,    -1,    45,    -1,    99,
      -1,     1,    23,    -1,    -1,    -1,     5,    46,    48,     6,
      49,    47,    50,    -1,    -1,    24,    48,    -1,    -1,    25,
      58,    26,    49,    -1,    90,    -1,    51,    -1,    -1,    57,
      52,    53,    23,    -1,    -1,    -1,    27,    55,    54,    53,
      -1,    -1,    48,     6,    49,    56,    57,    -1,    -1,    28,
      58,    -1,    59,    -1,    61,    -1,    78,    60,    59,    -1,
      28,    -1,    12,    -1,    62,    -1,    62,    29,    58,    30,
      61,    -1,    64,    63,    -1,    -1,     8,    64,    63,    -1,
      66,    65,    -1,    -1,     9,    66,    65,    -1,    69,    67,
      -1,    68,    67,    -1,    -1,    13,    69,    -1,    14,    69,
      -1,    72,    70,    -1,    -1,    71,    70,    -1,    31,    28,
      72,    -1,    31,    72,    -1,    32,    28,    72,    -1,    32,
      72,    -1,    75,    73,    -1,    -1,    74,    75,    73,    -1,
      33,    -1,    34,    -1,    78,    76,    -1,    77,    76,    -1,
      -1,    24,    78,    -1,    35,    78,    -1,    82,    80,    -1,
      10,    78,    -1,    11,    78,    -1,    79,    78,    -1,    24,
      -1,    36,    -1,    34,    -1,    37,    -1,    81,    80,    -1,
      -1,    10,    -1,    11,    -1,    83,    84,    -1,     6,    -1,
       3,    -1,     4,    -1,     7,    -1,    22,    -1,    38,    58,
      39,    -1,    -1,    85,    84,    -1,    25,    58,    26,    -1,
      38,    86,    39,    -1,    -1,    87,    -1,    89,    88,    -1,
      -1,    27,    89,    88,    -1,     6,    -1,     3,    -1,     4,
      -1,     7,    -1,    22,    -1,    -1,    38,    91,    93,    39,
      92,    -1,    23,    -1,   100,    -1,    -1,    94,    -1,    96,
      95,    -1,    -1,    27,    96,    95,    -1,    -1,    97,     5,
      48,     6,    49,    -1,    -1,    99,    98,    -1,    45,    98,
      -1,     1,    23,    -1,   100,    -1,   101,    -1,   102,    -1,
     104,    -1,   108,    -1,    40,    98,    41,    -1,   103,    23,
      -1,    15,   103,    23,    -1,    -1,    58,    -1,    -1,    17,
     105,    38,    58,    39,    99,    -1,    -1,    16,   106,    99,
      17,    38,    58,    39,    23,    -1,    -1,    20,   107,    38,
     103,    23,   103,    23,   103,    39,    99,    -1,    -1,    18,
     109,    38,    58,    39,    99,   111,    -1,    -1,    21,   110,
      38,    58,    39,    99,    -1,    -1,    19,    99,    -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const yytype_uint16 yyrline[] =
{
       0,   115,   115,   116,   119,   120,   121,   125,   125,   125,
     128,   129,   132,   133,   135,   136,   139,   139,   142,   143,
     143,   147,   147,   150,   151,   156,   158,   159,   162,   162,
     164,   165,   168,   171,   172,   175,   178,   179,   181,   183,
     184,   188,   189,   192,   195,   196,   198,   199,   200,   201,
     204,   207,   208,   210,   210,   213,   216,   217,   221,   222,
     225,   226,   227,   228,   231,   231,   231,   231,   234,   235,
     239,   240,   246,   250,   251,   252,   253,   254,   255,   259,
     260,   263,   264,   267,   268,   270,   273,   274,   277,   278,
     279,   280,   281,   286,   286,   288,   289,   291,   292,   294,
     296,   297,   299,   299,   303,   304,   305,   306,   309,   310,
     311,   312,   313,   316,   319,   322,   330,   331,   333,   333,
     334,   334,   335,   335,   338,   338,   339,   339,   342,   342
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || YYTOKEN_TABLE
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "NUM", "FLOAT", "NOMBRE_TIPO",
  "IDENTIFICADOR", "LITERAL_CADENA", "OR", "AND", "OP_ADD", "OP_SUB",
  "OP_MAS_IG", "OP_COMP", "SIMB_DIFF", "RETURN", "DO", "WHILE", "IF",
  "ELSE", "FOR", "SWITCH", "CONSTANTE_CARACTER", "';'", "'*'", "'['",
  "']'", "','", "'='", "'?'", "':'", "'>'", "'<'", "'+'", "'-'", "'/'",
  "'&'", "'!'", "'('", "')'", "'{'", "'}'", "$accept", "input", "line",
  "declaracion", "$@1", "$@2", "punteroOpc", "arrayOpc",
  "bifurcacionDeclaraciones", "declaracionVariable", "$@3",
  "op_RecursionListaDeDeclaradores", "$@4", "declarador", "$@5",
  "inicializacionOpcional", "exp", "expAsignacion", "operAsignacion",
  "expCondicional", "expOr", "opRecursionOr", "expAnd", "opRecursionAnd",
  "expIgualdad", "noTerminal3", "noTerminal2", "expRelacional",
  "opRecursionExpRelacional", "desigualdad", "expAditiva",
  "opRecursionExpAditiva", "op_Suma_Resta", "expMultiplicativa",
  "primerOperando", "segundoOperando", "expUnaria", "operUnario",
  "eliminaRecursividad", "parteOpcional", "expPostfijo", "expPrimaria",
  "opRecursionExpPostfijo", "op_Llave_Parentesis", "op_listaArgumentos",
  "listaArgumentos", "opRecursionListaArgum", "paramInv",
  "declaracionFuncion", "$@6", "division_decla_y_def",
  "op_listaParametros", "listaParametros", "op_masParametros", "parametro",
  "$@7", "sentencias", "sentencia", "sentenciaCompuesta",
  "sentenciaExpresion", "sentenciaSalto", "opc", "sentenciaIteracion",
  "$@8", "$@9", "$@10", "sentenciaSeleccion", "$@11", "$@12", "opcionElse", 0
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[YYLEX-NUM] -- Internal token number corresponding to
   token YYLEX-NUM.  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,    59,    42,    91,    93,    44,    61,    63,
      58,    62,    60,    43,    45,    47,    38,    33,    40,    41,
     123,   125
};
# endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    42,    43,    43,    44,    44,    44,    46,    47,    45,
      48,    48,    49,    49,    50,    50,    52,    51,    53,    54,
      53,    56,    55,    57,    57,    58,    59,    59,    60,    60,
      61,    61,    62,    63,    63,    64,    65,    65,    66,    67,
      67,    68,    68,    69,    70,    70,    71,    71,    71,    71,
      72,    73,    73,    74,    74,    75,    76,    76,    77,    77,
      78,    78,    78,    78,    79,    79,    79,    79,    80,    80,
      81,    81,    82,    83,    83,    83,    83,    83,    83,    84,
      84,    85,    85,    86,    86,    87,    88,    88,    89,    89,
      89,    89,    89,    91,    90,    92,    92,    93,    93,    94,
      95,    95,    97,    96,    98,    98,    98,    98,    99,    99,
      99,    99,    99,   100,   101,   102,   103,   103,   105,   104,
     106,   104,   107,   104,   109,   108,   110,   108,   111,   111
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     0,     2,     1,     1,     2,     0,     0,     7,
       0,     2,     0,     4,     1,     1,     0,     4,     0,     0,
       4,     0,     5,     0,     2,     1,     1,     3,     1,     1,
       1,     5,     2,     0,     3,     2,     0,     3,     2,     2,
       0,     2,     2,     2,     0,     2,     3,     2,     3,     2,
       2,     0,     3,     1,     1,     2,     2,     0,     2,     2,
       2,     2,     2,     2,     1,     1,     1,     1,     2,     0,
       1,     1,     2,     1,     1,     1,     1,     1,     3,     0,
       2,     3,     3,     0,     1,     2,     0,     3,     1,     1,
       1,     1,     1,     0,     5,     1,     1,     0,     1,     2,
       0,     3,     0,     5,     0,     2,     2,     2,     1,     1,
       1,     1,     1,     3,     2,     3,     0,     1,     0,     6,
       0,     8,     0,    10,     0,     7,     0,     6,     0,     2
};

/* YYDEFACT[STATE-NAME] -- Default rule to reduce with in state
   STATE-NUM when YYTABLE doesn't specify something else to do.  Zero
   means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       2,     0,     1,     0,    74,    75,     7,    73,    76,     0,
       0,   116,   120,   118,   124,   122,   126,    77,    64,    66,
      65,    67,     0,     0,     3,     4,   117,    25,    26,    30,
      33,    36,    40,    44,    51,    57,     0,    69,    79,     5,
     108,   109,   110,     0,   111,   112,     6,    10,    61,    62,
       0,   116,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,    32,     0,    35,     0,     0,    38,    40,
       0,     0,    43,    44,    53,    54,    50,     0,    29,     0,
      28,     0,     0,    55,    57,    63,    70,    71,    60,    69,
       0,    83,    72,    79,   114,    10,     0,   115,     0,     0,
       0,   116,     0,    78,   107,   106,   113,   105,     0,    33,
      57,    36,    41,    42,    39,     0,    47,     0,    49,    45,
      51,    58,    59,    27,    56,    68,     0,    89,    90,    88,
      91,    92,     0,    84,    86,    80,    11,    12,     0,     0,
       0,     0,     0,     0,    34,    37,    46,    48,    52,    81,
      82,     0,    85,     0,     8,     0,   116,   116,   116,   116,
      31,    86,     0,    23,     0,   119,   128,     0,   127,    87,
      12,     0,    93,     9,    15,    16,    14,     0,   116,   125,
     116,    13,    24,    97,    18,   121,   129,     0,     0,    98,
     100,     0,    10,     0,   116,     0,   102,    99,    10,     0,
      19,    17,   123,    95,    94,    96,   100,     0,    12,    18,
     101,    12,    21,    20,   103,    23,    22
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     1,    24,    58,    47,   163,    96,   154,   173,   174,
     184,   193,   209,   200,   215,   175,    26,    27,    82,    28,
      29,    63,    30,    65,    31,    68,    69,    32,    72,    73,
      33,    76,    77,    34,    83,    84,    35,    36,    88,    89,
      37,    38,    92,    93,   132,   133,   152,   134,   176,   183,
     204,   188,   189,   197,   190,   191,    59,    60,    40,    41,
      42,    43,    44,    52,    51,    54,    45,    53,    55,   179
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -163
static const yytype_int16 yypact[] =
{
    -163,    83,  -163,    -5,  -163,  -163,  -163,  -163,  -163,   264,
     264,   264,  -163,  -163,  -163,  -163,  -163,  -163,  -163,  -163,
    -163,  -163,   264,   158,  -163,  -163,  -163,  -163,  -163,     2,
      12,    15,    14,     3,     4,    -3,   264,    29,    -2,  -163,
    -163,  -163,  -163,    21,  -163,  -163,  -163,    23,  -163,  -163,
      22,   197,    10,    16,    17,    19,    13,    36,   158,    25,
     158,   264,   264,  -163,   264,  -163,   264,   264,  -163,    14,
     219,   255,  -163,     3,  -163,  -163,  -163,   264,  -163,   264,
    -163,   264,   264,  -163,    -9,  -163,  -163,  -163,  -163,    29,
     264,     7,  -163,    -2,  -163,    23,    56,  -163,    46,   264,
     264,   264,   264,  -163,  -163,  -163,  -163,  -163,    37,    12,
      -9,    15,  -163,  -163,  -163,   264,  -163,   264,  -163,  -163,
       4,  -163,  -163,  -163,  -163,  -163,    42,  -163,  -163,  -163,
    -163,  -163,    30,  -163,    45,  -163,  -163,    49,    38,    39,
      53,    54,    57,   264,  -163,  -163,  -163,  -163,  -163,  -163,
    -163,     7,  -163,   264,  -163,   264,   197,   197,   264,   197,
    -163,    45,    55,   -16,    58,  -163,    66,    72,  -163,  -163,
      49,   264,  -163,  -163,  -163,  -163,  -163,    87,   197,  -163,
     264,  -163,  -163,   107,    86,  -163,  -163,    75,    76,  -163,
      89,   113,    23,    99,   197,    -7,  -163,  -163,    23,   118,
    -163,  -163,  -163,  -163,  -163,  -163,    89,   119,    49,    86,
    -163,    49,  -163,  -163,  -163,    98,  -163
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
    -163,  -163,  -163,   126,  -163,  -163,   -90,  -162,  -163,  -163,
    -163,   -81,  -163,  -163,  -163,   -86,   -20,    48,  -163,   -12,
    -163,    27,    70,    28,    74,    65,  -163,   -24,    67,  -163,
     -64,    24,  -163,    64,    59,  -163,    -6,  -163,    60,  -163,
    -163,  -163,    52,  -163,  -163,  -163,   -19,    -4,  -163,  -163,
    -163,  -163,  -163,   -60,   -46,  -163,   -41,    -1,   -43,  -163,
    -163,   -10,  -163,  -163,  -163,  -163,  -163,  -163,  -163,  -163
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If zero, do what YYDEFACT says.
   If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -117
static const yytype_int16 yytable[] =
{
      39,    50,    56,    48,    49,   136,   116,   118,   181,    78,
     127,   128,   171,   129,   130,    79,   203,   105,    46,   107,
      62,    79,   172,    90,    64,    80,    81,    66,    67,   131,
      85,    61,    81,    23,    70,    71,    91,    74,    75,    86,
      87,   108,   112,   113,    94,    97,   212,    95,    99,   214,
      98,   146,   103,   147,   100,   101,   110,   102,   110,   104,
     110,   110,   137,   138,   110,   110,   106,   143,   149,   150,
     126,   110,   151,   121,   153,   122,   155,   158,   156,   139,
     140,   170,   142,     2,     3,   178,     4,     5,     6,     7,
       8,   141,   157,     9,    10,   180,   159,   177,    11,    12,
      13,    14,   199,    15,    16,    17,  -116,    18,   207,   110,
     185,   110,  -102,   192,   194,   195,   196,    19,   198,    20,
      21,    22,   201,    23,   208,   211,   171,    25,   213,   216,
     123,   160,   109,   162,   114,   164,   144,   110,   111,   145,
     119,   120,   169,   124,   148,   135,   210,   161,   167,   125,
     206,   182,   205,     0,     0,   165,   166,     0,   168,    57,
       0,     4,     5,     6,     7,     8,     0,     0,     9,    10,
     187,     0,     0,    11,    12,    13,    14,   186,    15,    16,
      17,  -116,    18,     0,     0,     0,     0,     0,     0,     0,
       0,     0,    19,   202,    20,    21,    22,     0,    23,  -104,
       4,     5,     0,     7,     8,     0,     0,     9,    10,     0,
       0,     0,    11,    12,    13,    14,     0,    15,    16,    17,
       0,    18,     4,     5,     0,     7,     8,     0,     0,     9,
      10,    19,     0,    20,    21,    22,     0,    23,     0,     0,
       0,    17,     0,    18,     0,     0,     0,   115,     0,     0,
       0,     0,     0,    19,     0,    20,    21,    22,     4,     5,
       0,     7,     8,     0,     0,     9,    10,     4,     5,     0,
       7,     8,     0,     0,     9,    10,     0,    17,     0,    18,
       0,     0,     0,   117,     0,     0,    17,     0,    18,    19,
       0,    20,    21,    22,     0,     0,     0,     0,    19,     0,
      20,    21,    22
};

static const yytype_int16 yycheck[] =
{
       1,    11,    22,     9,    10,    95,    70,    71,   170,    12,
       3,     4,    28,     6,     7,    24,    23,    58,    23,    60,
       8,    24,    38,    25,     9,    28,    35,    13,    14,    22,
      36,    29,    35,    40,    31,    32,    38,    33,    34,    10,
      11,    61,    66,    67,    23,    23,   208,    24,    38,   211,
      51,   115,    39,   117,    38,    38,    62,    38,    64,    23,
      66,    67,     6,    17,    70,    71,    41,    30,    26,    39,
      90,    77,    27,    79,    25,    81,    38,    23,    39,    99,
     100,    26,   102,     0,     1,    19,     3,     4,     5,     6,
       7,   101,    39,    10,    11,    23,    39,    39,    15,    16,
      17,    18,   192,    20,    21,    22,    23,    24,   198,   115,
      23,   117,     5,    27,    39,    39,    27,    34,     5,    36,
      37,    38,    23,    40,     6,     6,    28,     1,   209,   215,
      82,   143,    62,   153,    69,   155,   109,   143,    64,   111,
      73,    77,   161,    84,   120,    93,   206,   151,   158,    89,
     196,   171,   195,    -1,    -1,   156,   157,    -1,   159,     1,
      -1,     3,     4,     5,     6,     7,    -1,    -1,    10,    11,
     180,    -1,    -1,    15,    16,    17,    18,   178,    20,    21,
      22,    23,    24,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    34,   194,    36,    37,    38,    -1,    40,    41,
       3,     4,    -1,     6,     7,    -1,    -1,    10,    11,    -1,
      -1,    -1,    15,    16,    17,    18,    -1,    20,    21,    22,
      -1,    24,     3,     4,    -1,     6,     7,    -1,    -1,    10,
      11,    34,    -1,    36,    37,    38,    -1,    40,    -1,    -1,
      -1,    22,    -1,    24,    -1,    -1,    -1,    28,    -1,    -1,
      -1,    -1,    -1,    34,    -1,    36,    37,    38,     3,     4,
      -1,     6,     7,    -1,    -1,    10,    11,     3,     4,    -1,
       6,     7,    -1,    -1,    10,    11,    -1,    22,    -1,    24,
      -1,    -1,    -1,    28,    -1,    -1,    22,    -1,    24,    34,
      -1,    36,    37,    38,    -1,    -1,    -1,    -1,    34,    -1,
      36,    37,    38
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,    43,     0,     1,     3,     4,     5,     6,     7,    10,
      11,    15,    16,    17,    18,    20,    21,    22,    24,    34,
      36,    37,    38,    40,    44,    45,    58,    59,    61,    62,
      64,    66,    69,    72,    75,    78,    79,    82,    83,    99,
     100,   101,   102,   103,   104,   108,    23,    46,    78,    78,
     103,   106,   105,   109,   107,   110,    58,     1,    45,    98,
      99,    29,     8,    63,     9,    65,    13,    14,    67,    68,
      31,    32,    70,    71,    33,    34,    73,    74,    12,    24,
      28,    35,    60,    76,    77,    78,    10,    11,    80,    81,
      25,    38,    84,    85,    23,    24,    48,    23,    99,    38,
      38,    38,    38,    39,    23,    98,    41,    98,    58,    64,
      78,    66,    69,    69,    67,    28,    72,    28,    72,    70,
      75,    78,    78,    59,    76,    80,    58,     3,     4,     6,
       7,    22,    86,    87,    89,    84,    48,     6,    17,    58,
      58,   103,    58,    30,    63,    65,    72,    72,    73,    26,
      39,    27,    88,    25,    49,    38,    39,    39,    23,    39,
      61,    89,    58,    47,    58,    99,    99,   103,    99,    88,
      26,    28,    38,    50,    51,    57,    90,    39,    19,   111,
      23,    49,    58,    91,    52,    23,    99,   103,    93,    94,
      96,    97,    27,    53,    39,    39,    27,    95,     5,    48,
      55,    23,    99,    23,    92,   100,    96,    48,     6,    54,
      95,     6,    49,    53,    49,    56,    57
};

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		(-2)
#define YYEOF		0

#define YYACCEPT	goto yyacceptlab
#define YYABORT		goto yyabortlab
#define YYERROR		goto yyerrorlab


/* Like YYERROR except do call yyerror.  This remains here temporarily
   to ease the transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  */

#define YYFAIL		goto yyerrlab

#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)					\
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    {								\
      yychar = (Token);						\
      yylval = (Value);						\
      yytoken = YYTRANSLATE (yychar);				\
      YYPOPSTACK (1);						\
      goto yybackup;						\
    }								\
  else								\
    {								\
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;							\
    }								\
while (YYID (0))


#define YYTERROR	1
#define YYERRCODE	256


/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

#define YYRHSLOC(Rhs, K) ((Rhs)[K])
#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)				\
    do									\
      if (YYID (N))                                                    \
	{								\
	  (Current).first_line   = YYRHSLOC (Rhs, 1).first_line;	\
	  (Current).first_column = YYRHSLOC (Rhs, 1).first_column;	\
	  (Current).last_line    = YYRHSLOC (Rhs, N).last_line;		\
	  (Current).last_column  = YYRHSLOC (Rhs, N).last_column;	\
	}								\
      else								\
	{								\
	  (Current).first_line   = (Current).last_line   =		\
	    YYRHSLOC (Rhs, 0).last_line;				\
	  (Current).first_column = (Current).last_column =		\
	    YYRHSLOC (Rhs, 0).last_column;				\
	}								\
    while (YYID (0))
#endif


/* YY_LOCATION_PRINT -- Print the location on the stream.
   This macro was not mandated originally: define only if we know
   we won't break user code: when these are the locations we know.  */

#ifndef YY_LOCATION_PRINT
# if YYLTYPE_IS_TRIVIAL
#  define YY_LOCATION_PRINT(File, Loc)			\
     fprintf (File, "%d.%d-%d.%d",			\
	      (Loc).first_line, (Loc).first_column,	\
	      (Loc).last_line,  (Loc).last_column)
# else
#  define YY_LOCATION_PRINT(File, Loc) ((void) 0)
# endif
#endif


/* YYLEX -- calling `yylex' with the right arguments.  */

#ifdef YYLEX_PARAM
# define YYLEX yylex (YYLEX_PARAM)
#else
# define YYLEX yylex ()
#endif

/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)			\
do {						\
  if (yydebug)					\
    YYFPRINTF Args;				\
} while (YYID (0))

# define YY_SYMBOL_PRINT(Title, Type, Value, Location)			  \
do {									  \
  if (yydebug)								  \
    {									  \
      YYFPRINTF (stderr, "%s ", Title);					  \
      yy_symbol_print (stderr,						  \
		  Type, Value, Location); \
      YYFPRINTF (stderr, "\n");						  \
    }									  \
} while (YYID (0))


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep, YYLTYPE const * const yylocationp)
#else
static void
yy_symbol_value_print (yyoutput, yytype, yyvaluep, yylocationp)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
    YYLTYPE const * const yylocationp;
#endif
{
  if (!yyvaluep)
    return;
  YYUSE (yylocationp);
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# else
  YYUSE (yyoutput);
# endif
  switch (yytype)
    {
      default:
	break;
    }
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep, YYLTYPE const * const yylocationp)
#else
static void
yy_symbol_print (yyoutput, yytype, yyvaluep, yylocationp)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
    YYLTYPE const * const yylocationp;
#endif
{
  if (yytype < YYNTOKENS)
    YYFPRINTF (yyoutput, "token %s (", yytname[yytype]);
  else
    YYFPRINTF (yyoutput, "nterm %s (", yytname[yytype]);

  YY_LOCATION_PRINT (yyoutput, *yylocationp);
  YYFPRINTF (yyoutput, ": ");
  yy_symbol_value_print (yyoutput, yytype, yyvaluep, yylocationp);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
#else
static void
yy_stack_print (yybottom, yytop)
    yytype_int16 *yybottom;
    yytype_int16 *yytop;
#endif
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)				\
do {								\
  if (yydebug)							\
    yy_stack_print ((Bottom), (Top));				\
} while (YYID (0))


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_reduce_print (YYSTYPE *yyvsp, YYLTYPE *yylsp, int yyrule)
#else
static void
yy_reduce_print (yyvsp, yylsp, yyrule)
    YYSTYPE *yyvsp;
    YYLTYPE *yylsp;
    int yyrule;
#endif
{
  int yynrhs = yyr2[yyrule];
  int yyi;
  unsigned long int yylno = yyrline[yyrule];
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
	     yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr, yyrhs[yyprhs[yyrule] + yyi],
		       &(yyvsp[(yyi + 1) - (yynrhs)])
		       , &(yylsp[(yyi + 1) - (yynrhs)])		       );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)		\
do {					\
  if (yydebug)				\
    yy_reduce_print (yyvsp, yylsp, Rule); \
} while (YYID (0))

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef	YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif



#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static YYSIZE_T
yystrlen (const char *yystr)
#else
static YYSIZE_T
yystrlen (yystr)
    const char *yystr;
#endif
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static char *
yystpcpy (char *yydest, const char *yysrc)
#else
static char *
yystpcpy (yydest, yysrc)
    char *yydest;
    const char *yysrc;
#endif
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
	switch (*++yyp)
	  {
	  case '\'':
	  case ',':
	    goto do_not_strip_quotes;

	  case '\\':
	    if (*++yyp != '\\')
	      goto do_not_strip_quotes;
	    /* Fall through.  */
	  default:
	    if (yyres)
	      yyres[yyn] = *yyp;
	    yyn++;
	    break;

	  case '"':
	    if (yyres)
	      yyres[yyn] = '\0';
	    return yyn;
	  }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into YYRESULT an error message about the unexpected token
   YYCHAR while in state YYSTATE.  Return the number of bytes copied,
   including the terminating null byte.  If YYRESULT is null, do not
   copy anything; just return the number of bytes that would be
   copied.  As a special case, return 0 if an ordinary "syntax error"
   message will do.  Return YYSIZE_MAXIMUM if overflow occurs during
   size calculation.  */
static YYSIZE_T
yysyntax_error (char *yyresult, int yystate, int yychar)
{
  int yyn = yypact[yystate];

  if (! (YYPACT_NINF < yyn && yyn <= YYLAST))
    return 0;
  else
    {
      int yytype = YYTRANSLATE (yychar);
      YYSIZE_T yysize0 = yytnamerr (0, yytname[yytype]);
      YYSIZE_T yysize = yysize0;
      YYSIZE_T yysize1;
      int yysize_overflow = 0;
      enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
      char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
      int yyx;

# if 0
      /* This is so xgettext sees the translatable formats that are
	 constructed on the fly.  */
      YY_("syntax error, unexpected %s");
      YY_("syntax error, unexpected %s, expecting %s");
      YY_("syntax error, unexpected %s, expecting %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s");
# endif
      char *yyfmt;
      char const *yyf;
      static char const yyunexpected[] = "syntax error, unexpected %s";
      static char const yyexpecting[] = ", expecting %s";
      static char const yyor[] = " or %s";
      char yyformat[sizeof yyunexpected
		    + sizeof yyexpecting - 1
		    + ((YYERROR_VERBOSE_ARGS_MAXIMUM - 2)
		       * (sizeof yyor - 1))];
      char const *yyprefix = yyexpecting;

      /* Start YYX at -YYN if negative to avoid negative indexes in
	 YYCHECK.  */
      int yyxbegin = yyn < 0 ? -yyn : 0;

      /* Stay within bounds of both yycheck and yytname.  */
      int yychecklim = YYLAST - yyn + 1;
      int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
      int yycount = 1;

      yyarg[0] = yytname[yytype];
      yyfmt = yystpcpy (yyformat, yyunexpected);

      for (yyx = yyxbegin; yyx < yyxend; ++yyx)
	if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR)
	  {
	    if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
	      {
		yycount = 1;
		yysize = yysize0;
		yyformat[sizeof yyunexpected - 1] = '\0';
		break;
	      }
	    yyarg[yycount++] = yytname[yyx];
	    yysize1 = yysize + yytnamerr (0, yytname[yyx]);
	    yysize_overflow |= (yysize1 < yysize);
	    yysize = yysize1;
	    yyfmt = yystpcpy (yyfmt, yyprefix);
	    yyprefix = yyor;
	  }

      yyf = YY_(yyformat);
      yysize1 = yysize + yystrlen (yyf);
      yysize_overflow |= (yysize1 < yysize);
      yysize = yysize1;

      if (yysize_overflow)
	return YYSIZE_MAXIMUM;

      if (yyresult)
	{
	  /* Avoid sprintf, as that infringes on the user's name space.
	     Don't have undefined behavior even if the translation
	     produced a string with the wrong number of "%s"s.  */
	  char *yyp = yyresult;
	  int yyi = 0;
	  while ((*yyp = *yyf) != '\0')
	    {
	      if (*yyp == '%' && yyf[1] == 's' && yyi < yycount)
		{
		  yyp += yytnamerr (yyp, yyarg[yyi++]);
		  yyf += 2;
		}
	      else
		{
		  yyp++;
		  yyf++;
		}
	    }
	}
      return yysize;
    }
}
#endif /* YYERROR_VERBOSE */


/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep, YYLTYPE *yylocationp)
#else
static void
yydestruct (yymsg, yytype, yyvaluep, yylocationp)
    const char *yymsg;
    int yytype;
    YYSTYPE *yyvaluep;
    YYLTYPE *yylocationp;
#endif
{
  YYUSE (yyvaluep);
  YYUSE (yylocationp);

  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  switch (yytype)
    {

      default:
	break;
    }
}

/* Prevent warnings from -Wmissing-prototypes.  */
#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */


/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;

/* Location data for the lookahead symbol.  */
YYLTYPE yylloc;

/* Number of syntax errors so far.  */
int yynerrs;



/*-------------------------.
| yyparse or yypush_parse.  |
`-------------------------*/

#ifdef YYPARSE_PARAM
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void *YYPARSE_PARAM)
#else
int
yyparse (YYPARSE_PARAM)
    void *YYPARSE_PARAM;
#endif
#else /* ! YYPARSE_PARAM */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void)
#else
int
yyparse ()

#endif
#endif
{


    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       `yyss': related to states.
       `yyvs': related to semantic values.
       `yyls': related to locations.

       Refer to the stacks thru separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    /* The location stack.  */
    YYLTYPE yylsa[YYINITDEPTH];
    YYLTYPE *yyls;
    YYLTYPE *yylsp;

    /* The locations where the error started and ended.  */
    YYLTYPE yyerror_range[2];

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;
  YYLTYPE yyloc;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N), yylsp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yytoken = 0;
  yyss = yyssa;
  yyvs = yyvsa;
  yyls = yylsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */
  yyssp = yyss;
  yyvsp = yyvs;
  yylsp = yyls;

#if YYLTYPE_IS_TRIVIAL
  /* Initialize the default location before parsing starts.  */
  yylloc.first_line   = yylloc.last_line   = 1;
  yylloc.first_column = yylloc.last_column = 1;
#endif

  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
	/* Give user a chance to reallocate the stack.  Use copies of
	   these so that the &'s don't force the real ones into
	   memory.  */
	YYSTYPE *yyvs1 = yyvs;
	yytype_int16 *yyss1 = yyss;
	YYLTYPE *yyls1 = yyls;

	/* Each stack pointer address is followed by the size of the
	   data in use in that stack, in bytes.  This used to be a
	   conditional around just the two extra args, but that might
	   be undefined if yyoverflow is a macro.  */
	yyoverflow (YY_("memory exhausted"),
		    &yyss1, yysize * sizeof (*yyssp),
		    &yyvs1, yysize * sizeof (*yyvsp),
		    &yyls1, yysize * sizeof (*yylsp),
		    &yystacksize);

	yyls = yyls1;
	yyss = yyss1;
	yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
	goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
	yystacksize = YYMAXDEPTH;

      {
	yytype_int16 *yyss1 = yyss;
	union yyalloc *yyptr =
	  (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
	if (! yyptr)
	  goto yyexhaustedlab;
	YYSTACK_RELOCATE (yyss_alloc, yyss);
	YYSTACK_RELOCATE (yyvs_alloc, yyvs);
	YYSTACK_RELOCATE (yyls_alloc, yyls);
#  undef YYSTACK_RELOCATE
	if (yyss1 != yyssa)
	  YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;
      yylsp = yyls + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
		  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
	YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yyn == YYPACT_NINF)
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = YYLEX;
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yyn == 0 || yyn == YYTABLE_NINF)
	goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  *++yyvsp = yylval;
  *++yylsp = yylloc;
  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     `$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];

  /* Default location.  */
  YYLLOC_DEFAULT (yyloc, (yylsp - yylen), yylen);
  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 7:

/* Line 1455 of yacc.c  */
#line 125 "../src/TP_INT.y"
    {eliminarNodosVariablesAdeclarar(&variablesAdeclarar);tipo=(yyvsp[(1) - (1)].str);;}
    break;

  case 8:

/* Line 1455 of yacc.c  */
#line 125 "../src/TP_INT.y"
    {id = (yyvsp[(4) - (5)].str);;}
    break;

  case 11:

/* Line 1455 of yacc.c  */
#line 129 "../src/TP_INT.y"
    {cantPunteros++;;}
    break;

  case 13:

/* Line 1455 of yacc.c  */
#line 133 "../src/TP_INT.y"
    {flagArray=1;;}
    break;

  case 16:

/* Line 1455 of yacc.c  */
#line 139 "../src/TP_INT.y"
    {insertarVariables(&variablesAdeclarar,tipo, id, linActual,cantPunteros,flagArray, (yyvsp[(1) - (1)].str)); cantPunteros = 0;flagArray=0;;}
    break;

  case 17:

/* Line 1455 of yacc.c  */
#line 139 "../src/TP_INT.y"
    {declararVariablesProvisorias(variablesAdeclarar,&listaDeVars);;}
    break;

  case 19:

/* Line 1455 of yacc.c  */
#line 143 "../src/TP_INT.y"
    {insertarVariables(&variablesAdeclarar,tipo, id, linActual,cantPunteros,flagArray,(yyvsp[(2) - (2)].str));cantPunteros = 0;flagArray=0;;}
    break;

  case 21:

/* Line 1455 of yacc.c  */
#line 147 "../src/TP_INT.y"
    {id = (yyvsp[(2) - (3)].str);;}
    break;

  case 22:

/* Line 1455 of yacc.c  */
#line 147 "../src/TP_INT.y"
    {(yyval.str)=(yyvsp[(5) - (5)].str);;}
    break;

  case 23:

/* Line 1455 of yacc.c  */
#line 150 "../src/TP_INT.y"
    { (yyval.str) = "op";;}
    break;

  case 24:

/* Line 1455 of yacc.c  */
#line 151 "../src/TP_INT.y"
    {(yyval.str) = (yyvsp[(2) - (2)].str); ;}
    break;

  case 25:

/* Line 1455 of yacc.c  */
#line 156 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (1)].str); ;}
    break;

  case 26:

/* Line 1455 of yacc.c  */
#line 158 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (1)].str); ;}
    break;

  case 27:

/* Line 1455 of yacc.c  */
#line 159 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(3) - (3)].str); ;}
    break;

  case 30:

/* Line 1455 of yacc.c  */
#line 164 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (1)].str); ;}
    break;

  case 31:

/* Line 1455 of yacc.c  */
#line 165 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(2) - (5)].str); ;}
    break;

  case 32:

/* Line 1455 of yacc.c  */
#line 168 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (2)].str); ;}
    break;

  case 33:

/* Line 1455 of yacc.c  */
#line 171 "../src/TP_INT.y"
    { (yyval.str) = ""; ;}
    break;

  case 34:

/* Line 1455 of yacc.c  */
#line 172 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(2) - (3)].str); ;}
    break;

  case 35:

/* Line 1455 of yacc.c  */
#line 175 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (2)].str); ;}
    break;

  case 36:

/* Line 1455 of yacc.c  */
#line 178 "../src/TP_INT.y"
    { (yyval.str) = ""; ;}
    break;

  case 37:

/* Line 1455 of yacc.c  */
#line 179 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(2) - (3)].str); ;}
    break;

  case 38:

/* Line 1455 of yacc.c  */
#line 181 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (2)].str); ;}
    break;

  case 39:

/* Line 1455 of yacc.c  */
#line 183 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (2)].str); ;}
    break;

  case 40:

/* Line 1455 of yacc.c  */
#line 184 "../src/TP_INT.y"
    { (yyval.str) = ""; ;}
    break;

  case 46:

/* Line 1455 of yacc.c  */
#line 198 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (3)].str); ;}
    break;

  case 47:

/* Line 1455 of yacc.c  */
#line 199 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (2)].str); ;}
    break;

  case 48:

/* Line 1455 of yacc.c  */
#line 200 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (3)].str); ;}
    break;

  case 49:

/* Line 1455 of yacc.c  */
#line 201 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (2)].str); ;}
    break;

  case 50:

/* Line 1455 of yacc.c  */
#line 204 "../src/TP_INT.y"
    { char* res = compararTipos((yyvsp[(1) - (2)].str), (yyvsp[(2) - (2)].str), linActual); ;}
    break;

  case 51:

/* Line 1455 of yacc.c  */
#line 207 "../src/TP_INT.y"
    { (yyval.str) = ""; ;}
    break;

  case 52:

/* Line 1455 of yacc.c  */
#line 208 "../src/TP_INT.y"
    { (yyval.str) = compararTipos((yyvsp[(2) - (3)].str), (yyvsp[(3) - (3)].str), linActual); ;}
    break;

  case 55:

/* Line 1455 of yacc.c  */
#line 213 "../src/TP_INT.y"
    {(yyval.str) = (yyvsp[(1) - (2)].str); ;}
    break;

  case 60:

/* Line 1455 of yacc.c  */
#line 225 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(1) - (2)].str) ; ;}
    break;

  case 61:

/* Line 1455 of yacc.c  */
#line 226 "../src/TP_INT.y"
    {;}
    break;

  case 62:

/* Line 1455 of yacc.c  */
#line 227 "../src/TP_INT.y"
    {;}
    break;

  case 63:

/* Line 1455 of yacc.c  */
#line 228 "../src/TP_INT.y"
    { (yyval.str) = (yyvsp[(2) - (2)].str) ; ;}
    break;

  case 73:

/* Line 1455 of yacc.c  */
#line 250 "../src/TP_INT.y"
    {auxIdInvocacion = (yyvsp[(1) - (1)].str); char* tipo = buscarEnTabla(listaDeVars,(yyvsp[(1) - (1)].str),linActual) ;(yyval.str) = tipo; ;}
    break;

  case 74:

/* Line 1455 of yacc.c  */
#line 251 "../src/TP_INT.y"
    { (yyval.str) = "int" ;;}
    break;

  case 75:

/* Line 1455 of yacc.c  */
#line 252 "../src/TP_INT.y"
    {(yyval.str) = "float";;}
    break;

  case 76:

/* Line 1455 of yacc.c  */
#line 253 "../src/TP_INT.y"
    { (yyval.str) = "string"; ;}
    break;

  case 77:

/* Line 1455 of yacc.c  */
#line 254 "../src/TP_INT.y"
    { (yyval.str) = "char"; ;}
    break;

  case 78:

/* Line 1455 of yacc.c  */
#line 255 "../src/TP_INT.y"
    { (yyval.str) = ""; ;}
    break;

  case 82:

/* Line 1455 of yacc.c  */
#line 264 "../src/TP_INT.y"
    {validarInvocacion(linActual, auxIdInvocacion, &listaAuxParametrosInv); listaAuxParametrosInv = NULL; ;}
    break;

  case 88:

/* Line 1455 of yacc.c  */
#line 277 "../src/TP_INT.y"
    {insertarParamInv(&listaAuxParametrosInv, obtenerTipo(listaDeVars,(yyvsp[(1) - (1)].str)));}
    break;

  case 89:

/* Line 1455 of yacc.c  */
#line 278 "../src/TP_INT.y"
    {insertarParamInv(&listaAuxParametrosInv, "int");}
    break;

  case 90:

/* Line 1455 of yacc.c  */
#line 279 "../src/TP_INT.y"
    {insertarParamInv(&listaAuxParametrosInv, "float");}
    break;

  case 91:

/* Line 1455 of yacc.c  */
#line 280 "../src/TP_INT.y"
    {insertarParamInv(&listaAuxParametrosInv, "char*");}
    break;

  case 92:

/* Line 1455 of yacc.c  */
#line 281 "../src/TP_INT.y"
    {insertarParamInv(&listaAuxParametrosInv, "char");}
    break;

  case 93:

/* Line 1455 of yacc.c  */
#line 286 "../src/TP_INT.y"
    {auxiliarFun.identificador = id; auxiliarFun.numLinea = linActual; auxiliarFun.tipo = tipo; auxiliarFun.parametros = NULL;;}
    break;

  case 95:

/* Line 1455 of yacc.c  */
#line 288 "../src/TP_INT.y"
    {insertarFuncionesDeclaradas(&listaFunDecla, auxiliarFun.identificador, auxiliarFun.numLinea, auxiliarFun.tipo, listaAuxParametrosDecla); listaAuxParametrosDecla = NULL; ;}
    break;

  case 96:

/* Line 1455 of yacc.c  */
#line 289 "../src/TP_INT.y"
    {insertarFuncionesDefinidas(&listaFunDecla, auxiliarFun.identificador, auxiliarFun.numLinea, auxiliarFun.tipo, listaAuxParametrosDecla); listaAuxParametrosDecla = NULL; ;}
    break;

  case 102:

/* Line 1455 of yacc.c  */
#line 299 "../src/TP_INT.y"
    {cantPunteros=0;flagArray=0;;}
    break;

  case 103:

/* Line 1455 of yacc.c  */
#line 299 "../src/TP_INT.y"
    {insertarUltimoParametro(&listaAuxParametrosDecla, (yyvsp[(2) - (5)].str), (yyvsp[(4) - (5)].str));insertarVariables(&listaDeVars,(yyvsp[(2) - (5)].str),(yyvsp[(4) - (5)].str),linActual,cantPunteros,flagArray,"op");cantPunteros = 0;flagArray=0;;}
    break;

  case 113:

/* Line 1455 of yacc.c  */
#line 316 "../src/TP_INT.y"
    {tipoSent=1; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);;}
    break;

  case 114:

/* Line 1455 of yacc.c  */
#line 319 "../src/TP_INT.y"
    {tipoSent=2; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);;}
    break;

  case 115:

/* Line 1455 of yacc.c  */
#line 322 "../src/TP_INT.y"
    {tipoSent=5; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);;}
    break;

  case 118:

/* Line 1455 of yacc.c  */
#line 333 "../src/TP_INT.y"
    {tipoSent=4; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);;}
    break;

  case 120:

/* Line 1455 of yacc.c  */
#line 334 "../src/TP_INT.y"
    {tipoSent=4; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);;}
    break;

  case 122:

/* Line 1455 of yacc.c  */
#line 335 "../src/TP_INT.y"
    {tipoSent=4; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);;}
    break;

  case 124:

/* Line 1455 of yacc.c  */
#line 338 "../src/TP_INT.y"
    {tipoSent=3; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);;}
    break;

  case 126:

/* Line 1455 of yacc.c  */
#line 339 "../src/TP_INT.y"
    {tipoSent=3; agregarNodoSentencia(&listaSentencias,tipoSent,linActual);;}
    break;



/* Line 1455 of yacc.c  */
#line 2111 "TP_INT.tab.c"
      default: break;
    }
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;
  *++yylsp = yyloc;

  /* Now `shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*------------------------------------.
| yyerrlab -- here on detecting error |
`------------------------------------*/
yyerrlab:
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
      {
	YYSIZE_T yysize = yysyntax_error (0, yystate, yychar);
	if (yymsg_alloc < yysize && yymsg_alloc < YYSTACK_ALLOC_MAXIMUM)
	  {
	    YYSIZE_T yyalloc = 2 * yysize;
	    if (! (yysize <= yyalloc && yyalloc <= YYSTACK_ALLOC_MAXIMUM))
	      yyalloc = YYSTACK_ALLOC_MAXIMUM;
	    if (yymsg != yymsgbuf)
	      YYSTACK_FREE (yymsg);
	    yymsg = (char *) YYSTACK_ALLOC (yyalloc);
	    if (yymsg)
	      yymsg_alloc = yyalloc;
	    else
	      {
		yymsg = yymsgbuf;
		yymsg_alloc = sizeof yymsgbuf;
	      }
	  }

	if (0 < yysize && yysize <= yymsg_alloc)
	  {
	    (void) yysyntax_error (yymsg, yystate, yychar);
	    yyerror (yymsg);
	  }
	else
	  {
	    yyerror (YY_("syntax error"));
	    if (yysize != 0)
	      goto yyexhaustedlab;
	  }
      }
#endif
    }

  yyerror_range[0] = yylloc;

  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
	 error, discard it.  */

      if (yychar <= YYEOF)
	{
	  /* Return failure if at end of input.  */
	  if (yychar == YYEOF)
	    YYABORT;
	}
      else
	{
	  yydestruct ("Error: discarding",
		      yytoken, &yylval, &yylloc);
	  yychar = YYEMPTY;
	}
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  yyerror_range[0] = yylsp[1-yylen];
  /* Do not reclaim the symbols of the rule which action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;	/* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (yyn != YYPACT_NINF)
	{
	  yyn += YYTERROR;
	  if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
	    {
	      yyn = yytable[yyn];
	      if (0 < yyn)
		break;
	    }
	}

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
	YYABORT;

      yyerror_range[0] = *yylsp;
      yydestruct ("Error: popping",
		  yystos[yystate], yyvsp, yylsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  *++yyvsp = yylval;

  yyerror_range[1] = yylloc;
  /* Using YYLLOC is tempting, but would change the location of
     the lookahead.  YYLOC is available though.  */
  YYLLOC_DEFAULT (yyloc, (yyerror_range - 1), 2);
  *++yylsp = yyloc;

  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined(yyoverflow) || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
     yydestruct ("Cleanup: discarding lookahead",
		 yytoken, &yylval, &yylloc);
  /* Do not reclaim the symbols of the rule which action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
		  yystos[*yyssp], yyvsp, yylsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  /* Make sure YYID is used.  */
  return YYID (yyresult);
}



/* Line 1675 of yacc.c  */
#line 344 "../src/TP_INT.y"


/* Llamada por yyparse ante un error */
int yyerror (const char *s) { agregarNodoErrorSintactico (&listaErrorSintactico, linActual); ;return 0; } 

int main() {
    
    //Definiendo el archivo a analizar
    const char *nombre_archivo = "testPresentacion.c";

    //Asignación del archivo de entrada
    yyin = fopen(nombre_archivo, "r");
    if (yyin == NULL) {
        perror("Error al abrir el archivo");
        return 1;
    }

    yyparse(); // Llamar a la función generada por Bison para analizar la entrada
   
    fclose(yyin);

    char opcion;
    do {
        lineaPrincipal1(38);
        printf("%c      Menu: Presione una tecla        %c\n",186,186);
        lineaSeparadora1(38);
        printf("%c1. Imprimir declaraciones de funciones%c\n",186,186);
        printf("%c2. Imprimir lista de variables        %c\n",186,186);
        printf("%c3. Imprimir errores lexicos           %c\n",186,186);
        printf("%c4. Imprimir errores sintacticos       %c\n",186,186);
        printf("%c5. Imprimir errores semanticos        %c\n",186,186);
        printf("%c0. Cerrar Consola                     %c\n",186,186);
        lineaFinal1(38);
        printf("Opcion: ");
        
        opcion = getchar();  
        
        switch (opcion) {
            case '1':
                mostrarListaFuncionesDeclaradas(listaFunDecla);
                break;
            case '2':
                imprimirListaDeVars(listaDeVars);
                break;
            case '3':
                imprimirListaCaracNoReco(listaErroresLexicos);
            break;
            case '4':
                imprimirListaDeErrorSintactico(listaErrorSintactico);
            break;
            case '5':
                imprimirListaDeErroresSemanticos(listaDeErroresSemanticos);
            break;
            case '0':  
                printf("Saliendo...\n");
            break;
            default:
                printf("Opcion no valida. Por favor, elige una opcion valida.\n");
        }
        getchar();
    } while(opcion != '0');

   // getchar();

    return 0;
}
