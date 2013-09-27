/* Generated By:JavaCC: Do not edit this line. SACParserCSS2Constants.java */
package com.steadystate.css.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface SACParserCSS2Constants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int S = 1;
  /** RegularExpression Id. */
  int W = 2;
  /** RegularExpression Id. */
  int LBRACE = 6;
  /** RegularExpression Id. */
  int RBRACE = 7;
  /** RegularExpression Id. */
  int COMMA = 8;
  /** RegularExpression Id. */
  int DOT = 9;
  /** RegularExpression Id. */
  int SEMICOLON = 10;
  /** RegularExpression Id. */
  int COLON = 11;
  /** RegularExpression Id. */
  int ASTERISK = 12;
  /** RegularExpression Id. */
  int SLASH = 13;
  /** RegularExpression Id. */
  int PLUS = 14;
  /** RegularExpression Id. */
  int MINUS = 15;
  /** RegularExpression Id. */
  int EQUALS = 16;
  /** RegularExpression Id. */
  int GT = 17;
  /** RegularExpression Id. */
  int LSQUARE = 18;
  /** RegularExpression Id. */
  int RSQUARE = 19;
  /** RegularExpression Id. */
  int HASH = 20;
  /** RegularExpression Id. */
  int STRING = 21;
  /** RegularExpression Id. */
  int RROUND = 22;
  /** RegularExpression Id. */
  int URL = 23;
  /** RegularExpression Id. */
  int URI = 24;
  /** RegularExpression Id. */
  int CDO = 25;
  /** RegularExpression Id. */
  int CDC = 26;
  /** RegularExpression Id. */
  int INCLUDES = 27;
  /** RegularExpression Id. */
  int DASHMATCH = 28;
  /** RegularExpression Id. */
  int IMPORT_SYM = 29;
  /** RegularExpression Id. */
  int PAGE_SYM = 30;
  /** RegularExpression Id. */
  int MEDIA_SYM = 31;
  /** RegularExpression Id. */
  int FONT_FACE_SYM = 32;
  /** RegularExpression Id. */
  int CHARSET_SYM = 33;
  /** RegularExpression Id. */
  int ATKEYWORD = 34;
  /** RegularExpression Id. */
  int IMPORTANT_SYM = 35;
  /** RegularExpression Id. */
  int INHERIT = 36;
  /** RegularExpression Id. */
  int EMS = 37;
  /** RegularExpression Id. */
  int EXS = 38;
  /** RegularExpression Id. */
  int LENGTH_PX = 39;
  /** RegularExpression Id. */
  int LENGTH_CM = 40;
  /** RegularExpression Id. */
  int LENGTH_MM = 41;
  /** RegularExpression Id. */
  int LENGTH_IN = 42;
  /** RegularExpression Id. */
  int LENGTH_PT = 43;
  /** RegularExpression Id. */
  int LENGTH_PC = 44;
  /** RegularExpression Id. */
  int ANGLE_DEG = 45;
  /** RegularExpression Id. */
  int ANGLE_RAD = 46;
  /** RegularExpression Id. */
  int ANGLE_GRAD = 47;
  /** RegularExpression Id. */
  int TIME_MS = 48;
  /** RegularExpression Id. */
  int TIME_S = 49;
  /** RegularExpression Id. */
  int FREQ_HZ = 50;
  /** RegularExpression Id. */
  int FREQ_KHZ = 51;
  /** RegularExpression Id. */
  int DIMEN = 52;
  /** RegularExpression Id. */
  int PERCENTAGE = 53;
  /** RegularExpression Id. */
  int NUMBER = 54;
  /** RegularExpression Id. */
  int RGB = 55;
  /** RegularExpression Id. */
  int FUNCTION = 56;
  /** RegularExpression Id. */
  int IDENT = 57;
  /** RegularExpression Id. */
  int NAME = 58;
  /** RegularExpression Id. */
  int NUM = 59;
  /** RegularExpression Id. */
  int UNICODERANGE = 60;
  /** RegularExpression Id. */
  int RANGE = 61;
  /** RegularExpression Id. */
  int Q16 = 62;
  /** RegularExpression Id. */
  int Q15 = 63;
  /** RegularExpression Id. */
  int Q14 = 64;
  /** RegularExpression Id. */
  int Q13 = 65;
  /** RegularExpression Id. */
  int Q12 = 66;
  /** RegularExpression Id. */
  int Q11 = 67;
  /** RegularExpression Id. */
  int NMSTART = 68;
  /** RegularExpression Id. */
  int NMCHAR = 69;
  /** RegularExpression Id. */
  int STRING1 = 70;
  /** RegularExpression Id. */
  int STRING2 = 71;
  /** RegularExpression Id. */
  int NONASCII = 72;
  /** RegularExpression Id. */
  int ESCAPE = 73;
  /** RegularExpression Id. */
  int NL = 74;
  /** RegularExpression Id. */
  int UNICODE = 75;
  /** RegularExpression Id. */
  int HNUM = 76;
  /** RegularExpression Id. */
  int H = 77;
  /** RegularExpression Id. */
  int UNKNOWN = 78;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int COMMENT = 1;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "<S>",
    "<W>",
    "\"/*\"",
    "\"*/\"",
    "<token of kind 5>",
    "\"{\"",
    "\"}\"",
    "\",\"",
    "\".\"",
    "\";\"",
    "\":\"",
    "\"*\"",
    "\"/\"",
    "\"+\"",
    "\"-\"",
    "\"=\"",
    "\">\"",
    "\"[\"",
    "\"]\"",
    "<HASH>",
    "<STRING>",
    "\")\"",
    "<URL>",
    "<URI>",
    "\"<!--\"",
    "\"-->\"",
    "\"~=\"",
    "\"|=\"",
    "\"@import\"",
    "\"@page\"",
    "\"@media\"",
    "\"@font-face\"",
    "\"@charset\"",
    "<ATKEYWORD>",
    "<IMPORTANT_SYM>",
    "\"inherit\"",
    "<EMS>",
    "<EXS>",
    "<LENGTH_PX>",
    "<LENGTH_CM>",
    "<LENGTH_MM>",
    "<LENGTH_IN>",
    "<LENGTH_PT>",
    "<LENGTH_PC>",
    "<ANGLE_DEG>",
    "<ANGLE_RAD>",
    "<ANGLE_GRAD>",
    "<TIME_MS>",
    "<TIME_S>",
    "<FREQ_HZ>",
    "<FREQ_KHZ>",
    "<DIMEN>",
    "<PERCENTAGE>",
    "<NUMBER>",
    "\"rgb(\"",
    "<FUNCTION>",
    "<IDENT>",
    "<NAME>",
    "<NUM>",
    "<UNICODERANGE>",
    "<RANGE>",
    "<Q16>",
    "<Q15>",
    "<Q14>",
    "<Q13>",
    "<Q12>",
    "\"?\"",
    "<NMSTART>",
    "<NMCHAR>",
    "<STRING1>",
    "<STRING2>",
    "<NONASCII>",
    "<ESCAPE>",
    "<NL>",
    "<UNICODE>",
    "<HNUM>",
    "<H>",
    "<UNKNOWN>",
  };

}
