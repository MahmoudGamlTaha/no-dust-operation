/*******************************************************************************
Copyright (c) 2000 Oracle Corporation

File        : messages.js

Author      : Peter Ebell

Notes       :

Open Issues :

$revision_history$
26-jan-2007   Sandra Muller
  2.0         added Romanian (thanks to Serban Boca)
09-jun-2005   Sandra Muller
  1.9         added Greek (thanks to Nicolas Kyriazopoulos Panagiotopoulos)
06-apr-2005   Sandra Muller
  1.8         used unicode for faroese (thanks to Keita Watanabe)
18-mar-2005   Sandra Muller
  1.7         
18-mar-2005   Sandra Muller
  1.6         added Faroese (thanks to Samal of Sam~Teld)
11-feb-2005   Steven Davelaar
  1.5         Added message mustUseLovNewRow
10-jan-2005   Sandra Muller
  1.4         Added Korean translations by Dae-Kyun Lim
16-nov-2004   Sandra Muller
  1.3         Revisions in croatian (hr) and serbian (sr) messages, by Zrinka Frankovitch
11-nov-2004   Sandra Muller
  1.2         ordered language codes alphabetically
11-nov-2004   Sandra Muller
  1.1         Some changes in the French messages (by Laurent.Menez)
              + added Japanese messages (by Keita Watanabe)
05-mei-2004   Steven Davelaar
  1.0         ADF version
24-nov-2003   Sandra Muller
  1.18        Some changes in the French messages (by Jean-Rene.Rousseau)
              and reorganized messages by language
08-sep-2003   Sandra Muller
  1.15        Sandi Cemazar: Added slovenian, croatian (thanks Vjerocka Debeuc for help) 
              and serbian language (Serbia and Montenegro, former Yugoslavia,
              thanks Dusko Dimitrijevic for help)
21-aug-2003   Sandra Muller
  1.14        revised by the person responsible for products translation in Brazil.
15-aug-2003   Sandra Muller
  1.13        changed pt to pt-br and made sure that country is taken into consideration
14-aug-2003   Sandra Muller
  1.12        fixed the rendering of special characters by using unescape
13-aug-2003   Sandra Muller
  1.11        changed f"+uuml+"r into fuer because IE6 gives unterminated string constant error
13-aug-2003   Sandra Muller
  1.10        Added Spanish (es) by Mariano Duran and Hector Sola
13-aug-2003   Sandra Muller
  1.9         Added portuguese-Brazil (pt-BR) translations,
              by Leandro Batista and F"+aacute+"bio Nunes
12-aug-2003   Sandra Muller
  1.8         added German (by Juergen Menge) and Norwegian (by Sigrid Gylseth)
18-jul-2003   Sandra Muller
  1.7         second try
18-jul-2002   Sandra Muller
  1.5         small textual corrections in Dutch messages
03-oct-2002   Peter Ebell
  1.4         - Allow for easier registration of user messages.
              - Allow a substitution parameter in the messages
              - Added message to indicate that more query criteria are needed for the LOV
17-jul-2002   Peter Ebell
  1.3         - Changed mustUseLov message to depict that fields can now also be
                cleared using the LOV.
              - Added french translations (with thanks to Christophe Audran for translating them!).
09-jul-2002   Peter Ebell
  1.2         Javascript variable window.navigator.userLanguage does not exist
              in Netscape. Now using window.navigator.language when running on
              Netscape.
11-jun-2002   Peter Ebell
  1.1         Added message to indicate that a field can only be entered using
              the list of values.
31-may-2002   Peter Ebell
  1.0         Initial creation
******************************************************************************/
/*
$add_to_revision_history
$
*/

var allMessages            = new Array;

/*
 * This will be the locale used when the user has a language
 * that you did not include messages for.
 */
var defaultlocale="en";

/*
 * The following variables can be used to show special characters in the messages.
 * The variable names are similar to the HTML escape codes for these characters.
 * Unfortunately, the characters themselves cannot be included in the comment,
 * it causes errors in IE6.
 */
        var Agrave = unescape('%C0');
        var Aacute = unescape('%C1');
        var Acirc  = unescape('%C2');
        var Atilde = unescape('%C3');
        var Auml   = unescape('%C4');
        var Aring  = unescape('%C5');
        var AElig  = unescape('%C6');
        var Ccedil = unescape('%C7');
        var Egrave = unescape('%C8');
        var Eacute = unescape('%C9');
        var Ecirc  = unescape('%CA');
        var Euml   = unescape('%CB');
        var Igrave = unescape('%CC');
        var Iacute = unescape('%CD');
        var Icirc  = unescape('%CE');
        var Iuml   = unescape('%CF');
        var ETH    = unescape('%D0');
        var Ntilde = unescape('%D1');
        var Ograve = unescape('%D2');
        var Oacute = unescape('%D3');
        var Ocirc  = unescape('%D4');
        var Otilde = unescape('%D5');
        var Ouml   = unescape('%D6');
        var Oslash = unescape('%D8');
        var Ugrave = unescape('%D9');
        var Uacute = unescape('%DA');
        var Ucirc  = unescape('%DB');
        var Uuml   = unescape('%DC');
        var Yacute = unescape('%DD');
        var THORN  = unescape('%DE');
        var szlig  = unescape('%DF');
        var agrave = unescape('%E0');
        var aacute = unescape('%E1');
        var acirc  = unescape('%E2');
        var atilde = unescape('%E3');
        var auml   = unescape('%E4');
        var aring  = unescape('%E5');
        var aelig  = unescape('%E6');
        var ccedil = unescape('%E7');
        var egrave = unescape('%E8');
        var eacute = unescape('%E9');
        var ecirc  = unescape('%EA');
        var euml   = unescape('%EB');
        var igrave = unescape('%EC');
        var iacute = unescape('%ED');
        var icirc  = unescape('%EE');
        var iuml   = unescape('%EF');
        var eth    = unescape('%F0');
        var ntilde = unescape('%F1');
        var ograve = unescape('%F2');
        var oacute = unescape('%F3');
        var ocirc  = unescape('%F4');
        var otilde = unescape('%F5');
        var ouml   = unescape('%F6');
        var oslash = unescape('%F8');
        var ugrave = unescape('%F9');
        var uacute = unescape('%FA');
        var ucirc  = unescape('%FB');
        var uuml   = unescape('%FC');
        var yacute = unescape('%FD');

        var Ysccu  = unescape('%u010C');
        var Ysssu  = unescape('%u0160');
        var Yszzu  = unescape('%u017D');
        var Yscmu  = unescape('%u0106');
        var Ysddu  = unescape('%u0110');
        var Ysccl  = unescape('%u010D');
        var Ysssl  = unescape('%u0161');
        var Yszzl  = unescape('%u017E');
        var Yscml  = unescape('%u0107');
        var Ysddl  = unescape('%u0111');

        var Ahorns  = unescape('%u0102');
        var ahorns  = unescape('%u0103');
        var Scedil  = unescape('%u015E');
        var scedil  = unescape('%u015F');
        var Tcedil  = unescape('%u0162');
        var tcedil  = unescape('%u0163');


        // The next two cause uncaught exceptions in Netscape
        // so they are commented out for the time being
        // var thorn  = unescape('%FE');
        // var yuml   = unescape('%FF');
/*
 * In this next section, you can add your own messages.
 * Make sure that the index you use is equal to the first two
 * characters of the javascript system variable: 
 *     window.navigator.userLanguage (on InternetExplorer)
 * or: window.navigator.language (on Netscape).
 * If the language specified in the browser is not listed here, english (en) is used
 *
 * You can use substitution parameters using the following notation: {x} where x is
 * the number of the parameter. See function getMessage(code,params) for information
 * on how to specify a substitution parameter list.
 */

addMessage("hasChanged","en", "The page you are about to leave has uncommitted changes, that will be lost if you proceed.\nAre you sure you want to continue?");
addMessage("noRowsSelected","en", "Please select a row first!");
addMessage("newRowSelected","en", "The row you selected has not been saved yet. Please save first!");
addMessage("requiredFields","en", "Please enter a value for ");
addMessage("mustUseLov","en", "Use the list of values to fill in or clear this field.");
addMessage("mustUseLovNewRow","en", "Use the list of values to fill in this field in a new row.");
addMessage("searchCriterium","en", "Please specify a search criterium of at least {0} characters.");

addMessage("hasChanged","de", "Wenn Sie diese Seite verlassen, werden Ihre "+Auml+"nderungen nicht gespeichert. \nWollen Sie trotzdem fortsetzen?");
addMessage("noRowsSelected","de", "Bitte w"+auml+"hlen Sie zuerst eine Zeile aus!");
addMessage("newRowSelected","de", "Die ausgew"+auml+"hlte Zeile wurde noch nicht gespeichert. Bitte speichern Sie sie zuerst!");
addMessage("requiredFields","de", "Bitte tragen Sie einen Wert ein f"+uuml+"r ");
addMessage("mustUseLov","de", "Sie m"+uuml+"ssen die Werteliste benutzen, um das Feld mit einen Wert zu f"+uuml+"llen oder es leeren.");
addMessage("mustUseLovNewRow","de", "Sie m"+uuml+"ssen die Werteliste benutzen, um das Feld mit einen Wert zu f"+uuml+"llen oder es leeren.");
addMessage("searchCriterium","de", "Bitte geben Sie ein Suchkriterium an, das aus mindestens {0} Zeichen besteht!");

addMessage("hasChanged","el","\u0397 \u03c3\u03b5\u03bb\u03af\u03b4\u03b1 \u03b1\u03c0\u03cc \u03c4\u03b7\u03bd \u03bf\u03c0\u03bf\u03af\u03b1 \u03b5\u03af\u03c3\u03c4\u03b5 \u03ad\u03c4\u03bf\u03b9\u03bc\u03bf\u03b9 \u03bd\u03b1 \u03c6\u03cd\u03b3\u03b5\u03c4\u03b5 \u03ad\u03c7\u03b5\u03b9 \u03b1\u03bb\u03bb\u03b1\u03b3\u03ad\u03c2 \u03c0\u03bf\u03c5 \u03b4\u03b5\u03bd \u03b5\u03af\u03bd\u03b1\u03b9 \u03b1\u03c0\u03bf\u03b8\u03b7\u03ba\u03b5\u03c5\u03bc\u03ad\u03bd\u03b5\u03c2 \u03ba\u03b1\u03b9 \u03b8\u03b1 \u03c7\u03b1\u03b8\u03bf\u03cd\u03bd \u03b1\u03bd \u03c0\u03c1\u03bf\u03c7\u03c9\u03c1\u03ae\u03c3\u03b5\u03c4\u03b5.\n\u0395\u03af\u03c3\u03c4\u03b5 \u03c3\u03af\u03b3\u03bf\u03c5\u03c1\u03bf\u03c2/-\u03b7 \u03cc\u03c4\u03b9 \u03b8\u03ad\u03bb\u03b5\u03c4\u03b5 \u03bd\u03b1 \u03c0\u03c1\u03bf\u03c7\u03c9\u03c1\u03ae\u03c3\u03b5\u03c4\u03b5;");
addMessage("noRowsSelected","el", "\u03a7\u03c1\u03b5\u03b9\u03ac\u03b6\u03b5\u03c4\u03b1\u03b9 \u03bd\u03b1 \u03b5\u03c0\u03b9\u03bb\u03ad\u03be\u03b5\u03c4\u03b5 \u03c0\u03c1\u03ce\u03c4\u03b1 \u03bc\u03b9\u03b1 \u03b3\u03c1\u03b1\u03bc\u03bc\u03ae!");
addMessage("newRowSelected","el", "\u0397 \u03b3\u03c1\u03b1\u03bc\u03bc\u03ae \u03c0\u03bf\u03c5 \u03b5\u03c0\u03b9\u03bb\u03ad\u03be\u03b1\u03c4\u03b5 \u03b4\u03b5\u03bd \u03ad\u03c7\u03b5\u03b9 \u03b1\u03ba\u03cc\u03bc\u03b1 \u03b1\u03c0\u03bf\u03b8\u03b7\u03ba\u03b5\u03c5\u03c4\u03b5\u03af. \u03a0\u03b1\u03c1\u03b1\u03ba\u03b1\u03bb\u03bf\u03cd\u03bc\u03b5, \u03b1\u03c0\u03bf\u03b8\u03b7\u03ba\u03ad\u03c8\u03c4\u03b5 \u03c4\u03b7 \u03c0\u03c1\u03ce\u03c4\u03b1!");
addMessage("requiredFields","el", "\u0395\u03b9\u03c3\u03ac\u03b3\u03b5\u03c4\u03b5 \u03bc\u03b9\u03b1 \u03c4\u03b9\u03bc\u03ae \u03b3\u03b9\u03b1 ");
addMessage("mustUseLov","el", "\u03a7\u03c1\u03b7\u03c3\u03b9\u03bc\u03bf\u03c0\u03bf\u03b9\u03b5\u03af\u03c3\u03c4\u03b5 \u03c4\u03b7 \u03bb\u03af\u03c3\u03c4\u03b1 \u03c4\u03b9\u03bc\u03ce\u03bd \u03b3\u03b9\u03b1 \u03bd\u03b1 \u03b5\u03b9\u03c3\u03ac\u03b3\u03b5\u03c4\u03b5 \u03c4\u03b9\u03bc\u03ad\u03c2 \u03b7 \u03bd\u03b1 \u03ba\u03b1\u03b8\u03b1\u03c1\u03af\u03c3\u03b5\u03c4\u03b5 \u03b1\u03c5\u03c4\u03cc \u03c4\u03bf \u03c0\u03b5\u03b4\u03af\u03bf.");
addMessage("mustUseLovNewRow","el", "\u03a0\u03c1\u03ad\u03c0\u03b5\u03b9 \u03bd\u03b1 \u03c7\u03c1\u03b7\u03c3\u03b9\u03bc\u03bf\u03c0\u03bf\u03b9\u03b5\u03af\u03c3\u03c4\u03b5 \u03c4\u03b7 \u03bb\u03b9\u03c3\u03c4\u03b1 \u03c4\u03b9\u03bc\u03ce\u03bd \u03b1\u03bd \u03b8\u03ad\u03bb\u03b5\u03c4\u03b5 \u03bd\u03b1 \u03b5\u03b9\u03c3\u03ac\u03b3\u03b5\u03c4\u03b5 \u03c4\u03b9\u03bc\u03ad\u03c2 \u03c3\u03b5 \u03b1\u03c5\u03c4\u03cc \u03c4\u03bf \u03c0\u03b5\u03b4\u03af\u03bf \u03c3\u03b5 \u03bd\u03ad\u03b1 \u03b3\u03c1\u03b1\u03bc\u03bc\u03ae.");
addMessage("searchCriterium","el", "\u0395\u03c0\u03b9\u03bb\u03ad\u03be\u03c4\u03b5 \u03ad\u03bd\u03b1 \u03ba\u03c1\u03b9\u03c4\u03ae\u03c1\u03b9\u03bf \u03b1\u03bd\u03b1\u03b6\u03b7\u03c4\u03b7\u03c3\u03b7\u03c2 \u03bc\u03b5 \u03c4\u03bf\u03c5\u03bb\u03ac\u03c7\u03b9\u03c3\u03c4\u03bf\u03bd {0} \u03c7\u03b1\u03c1\u03b1\u03ba\u03c4\u03ae\u03c1\u03b5\u03c2.");

addMessage("hasChanged","es", "La p"+aacute+"gina de la cual intenta salir tiene cambios sin grabar que se perderan si procede.\nEsta seguro que desea continuar?");
addMessage("noRowsSelected","es", "Por favor primero seleccione un registro!");
addMessage("newRowSelected","es", "El registro seleccionado todav"+iacute+"a no ha sido grabado. Por favor primero grabelo!");
addMessage("requiredFields","es", "Por favor ingrese un valor para ");
addMessage("mustUseLov","es", "Debe usar la lista de valores para completar o limpiar el campo.");
addMessage("mustUseLovNewRow","es", "Debe usar la lista de valores para completar o limpiar el campo.");
addMessage("searchCriterium","es", "Por favor especifique un criterio de b"+uacute+"squeda de al menos {0} caracteres.");

addMessage("hasChanged","fo", "S\u00ED\u00F0an, sum t\u00FA fert fr\u00E1 n\u00FA, inniheldur broytingar, sum enn ikki eru goymdar. Hesar ver\u00F0a mistar, heldur t\u00FA fram.\nVilt t\u00FA halda fram h\u00F3ast ta\u00F0?");
addMessage("noRowsSelected","fo", "Vinarliga vel eina rekkju fyrst!");
addMessage("newRowSelected","fo", "Rekkjan, t\u00FA hevur valt, er ikki goymd enn. Vinarliga goym fyrst!");
addMessage("requiredFields","fo", "Vinarliga innset vir\u00F0i fyri ");
addMessage("mustUseLov","fo", "T\u00FA m\u00E1st br\u00FAka listan vi\u00F0 vir\u00F0um til at fylla teigin \u00FAt vi\u00F0, annars strika teigin.");
addMessage("mustUseLovNewRow","fo", "Br\u00FAka listan vi\u00F0 vir\u00F0um at fylla teigin \u00FAt vi\u00F0 \u00ED eini n\u00FDggjari rekkju.");
addMessage("searchCriterium","fo", "Vinarliga innset leititreytir upp\u00E1 minst {0} tekn.");

addMessage("hasChanged","fr", "La page que vous vous appr"+ecirc+"tez "+agrave+" quitter contient des changements non sauvegard"+eacute+"s. \n Voulez-vous continuer?");
addMessage("noRowsSelected","fr", "Veuillez tout d'abord s"+eacute+"lectionner un enregistrement.");
addMessage("newRowSelected","fr", "L'enregistrement que vous avez s"+eacute+"lectionn"+eacute+" n'a pas "+eacute+"t"+eacute+" sauvegard"+eacute+". Veuillez le sauvegarder");
addMessage("requiredFields","fr", "Veuillez entrer une valeur pour ");
addMessage("mustUseLov","fr", "Vous devez utiliser la liste de valeurs pour remplir ou effacer ce champ.");
addMessage("mustUseLovNewRow","fr", "Vous devez utiliser la liste de valeurs pour remplir ou effacer ce champ.");
addMessage("searchCriterium","fr", "Veuillez sp"+eacute+"cifier un crit"+egrave+"re de recherche de plus de {0} caract"+egrave+"res.");

addMessage("hasChanged","hr",  "Na stranici su promjene koje nisu potvr"+Ysddl+"ene, i bit "+Yscml+"e izgubljene ako nastavite.\nDa li ste sigurni da "+Yszzl+"elite nastaviti?");
addMessage("noRowsSelected","hr",  "Molimo prvo odaberite redak!");
addMessage("newRowSelected","hr", "Odabrani redak jo? nije sa"+Ysccl+"uvan. Molim, najprije sa"+Ysccl+"uvajte!");
addMessage("requiredFields","hr", "Unesite vrijednost polja ");
addMessage("mustUseLov","hr", "Za unos koristite listu vrijednosti, molim.");
addMessage("mustUseLovNewRow","hr", "Za unos koristite listu vrijednosti, molim.");
addMessage("searchCriterium","hr", "Molim specificirajte kriterij pretra"+Yszzl+"ivanja s najmanje {0} znakova.");

addMessage("hasChanged","ja", "\u73fe\u5728\u306e\u30da\u30fc\u30b8\u306f\u5909\u66f4\u304c\u78ba\u5b9a\u3055\u308c\u3066\u3044\u307e\u305b\u3093\u3002\u9077\u79fb\u3059\u308b\u3068\u5909\u66f4\u306f\u7834\u68c4\u3055\u308c\u307e\u3059\u3002\n\u7d99\u7d9a\u3057\u307e\u3059\u304b\uff1f");
addMessage("noRowsSelected","ja", "\u884c\u3092\u9078\u629e\u3057\u3066\u304f\u3060\u3055\u3044\u3002");
addMessage("newRowSelected","ja", "\u9078\u629e\u3055\u308c\u305f\u884c\u306f\u672a\u4fdd\u5b58\u3067\u3059\u3002 \u4fdd\u5b58\u3057\u3066\u304f\u3060\u3055\u3044\u3002");
addMessage("requiredFields","ja", "\u5de6\u8a18\u306e\u9805\u76ee\u306b\u5024\u3092\u5165\u529b\u3057\u3066\u304f\u3060\u3055\u3044\uff1a ");
addMessage("mustUseLov","ja", "\u3053\u306e\u9805\u76ee\u3092\u5165\u529b\u307e\u305f\u306f\u30af\u30ea\u30a2\u3059\u308b\u5834\u5408\u306f\u3001\u5024\u30ea\u30b9\u30c8\u3092\u5229\u7528\u3057\u3066\u304f\u3060\u3055\u3044\u3002");
addMessage("mustUseLovNewRow","ja", "\u3053\u306e\u9805\u76ee\u3092\u5165\u529b\u307e\u305f\u306f\u30af\u30ea\u30a2\u3059\u308b\u5834\u5408\u306f\u3001\u5024\u30ea\u30b9\u30c8\u3092\u5229\u7528\u3057\u3066\u304f\u3060\u3055\u3044\u3002");
addMessage("searchCriterium","ja", "{0} \u6587\u5b57\u4ee5\u4e0a\u306e\u691c\u7d22\u6761\u4ef6\u3092\u5165\u529b\u3057\u3066\u304f\u3060\u3055\u3044\u3002");

addMessage("hasChanged","kr", "\ufffd\ufffd\ufffd\ufffd \ufffd\ufffd \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd \ufffd\ufffd: \ufffd\ufffd\ufffd\ufffd\ufffd\u0370\ufffd \ufffd\u05bd4\u03f4\ufffd. \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd \ufffd\ufffd\ufffd \ufffd\ufffd\ufffd\ufffd\ufffd \ufffd\ufffd\ufffd\ufffd\ufffd\u0374\ufffd \ufffd\u077f\ufffd\ufffd\ufffd \ufffd\ufffd\ufffd\ufffd \ufffd\u02bd4\u03f4\ufffd.\n\ufffd\ufffd\ufffd \ufffd\ufffd\ufffd\ufffd\ufffd\u03fd\u00f0\u06bd4\u03f1\ufffd?");
addMessage("noRowsSelected","kr", "\ufffd\u05f8\ufffd; \ufffd\ufffd\ufffd\ufffd \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd \ufffd\u05bd\u02bd\u00ff\ufffd.");
addMessage("newRowSelected","kr", "\ufffd\ufffd\ufffd\u00f5\ufffd \ufffd\u05f8\ufffd\ufffd\ufffd \ufffd\ufffd\ufffd\ufffd \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd \ufffd\u02be\u04bd4\u03f4\ufffd. \ufffd\ufffd\ufffd\ufffd\ufffd\u03f1\ufffd\ufffd\ufffd \ufffd\ufffd\ufffd\ufffd\ufffd\ufffd \ufffd\u05bd\u02bd\u00ff\ufffd");
addMessage("requiredFields","kr", "\ufffd\ufffd= \ufffd\u05f8\ufffd \ufffd\ufffd; \ufffd\u0537\ufffd\ufffd\ufffd \ufffd\u05bd\u02bd\u00ff\ufffd : ");
addMessage("mustUseLov","kr", "\ufffd\ufffd\ufffd\ufffd\u01ae\ufffd\ufffd \ufffd\u033f\ufffd\ufffd\ufffd \ufffd\u05bd\u02bd\u00ff\ufffd.");
addMessage("mustUseLovNewRow","kr", "\ufffd\ufffd\ufffd\ufffd\u01ae\ufffd\ufffd \ufffd\u033f\ufffd\ufffd\ufffd \ufffd\u05bd\u02bd\u00ff\ufffd.");
addMessage("searchCriterium","kr", "\ufffd\ufffd\ued75 {0}\ufffd\ufffd \ufffd\u033b\ufffd\ufffd\ufffd \ufffd\ufffd\ufffd\u06b8\ufffd \ufffd\u02fb\ufffd\ufffd\ufffd \ufffd\ufffdd\ufffd\ufffd \ufffd\u05bd\u02bd\u00ff\ufffd.");

addMessage("hasChanged","nl", "De huidige pagina heeft openstaande wijzigingen, die verloren zullen raken als u doorgaat.\nWeet u zeker dat u wilt doorgaan?");
addMessage("noRowsSelected","nl", "Selecteer eerst een rij!");
addMessage("newRowSelected","nl", "De geselecteerde rij is nog niet opgeslagen. Sla de wijzigigen eerst op!");
addMessage("requiredFields","nl", "Voer een waarde in voor ");
addMessage("mustUseLov","nl", "Gebruik de lijst met toegestane waarden om dit veld in te vullen of leeg te maken.");
addMessage("mustUseLovNewRow","nl", "Gebruik de lijst met toegestane waarden om dit veld in te vullen in een nieuwe rij.");
addMessage("searchCriterium","nl", "Geef minimaal {0} tekens op als zoekcriterium.");

addMessage("hasChanged","no", "Siden du "+oslash+"nsker "+aring+" forlate inneholder endringer som du vil miste dersom du forlater den.\n"+Oslash+"nsker du "+aring+" fortsette?");
addMessage("noRowsSelected","no", "Vennligst velg en rad f"+oslash+"rst!");
addMessage("newRowSelected","no", "Raden du har valgt er ikke lagret. Vennligst lagre f"+oslash+"rst!");
addMessage("requiredFields","no", "Vennligst legg inn en verdi for ");
addMessage("mustUseLov","no", "Du m"+aring+" bruke listen for "+aring+" legge inn eller fjerne innholdet i dette feltet.");
addMessage("mustUseLovNewRow","no", "Du m"+aring+" bruke listen for "+aring+" legge inn eller fjerne innholdet i dette feltet.");
addMessage("searchCriterium","no", "Vennligst lag et s"+oslash+"k med minst {0} tegn.");

addMessage("hasChanged","pt-br", "A p"+aacute+"gina que voc"+ecirc+" est"+aacute+" prestes a deixar possui altera"+ccedil+otilde+"es que n"+atilde+"o foram submetidas a commit, que ser"+atilde+"o perdidas se voc"+ecirc+" prosseguir.\nTem certeza de que deseja continuar?");
addMessage("noRowsSelected","pt-br", "Selecione uma linha primeiro!");
addMessage("newRowSelected","pt-br", "A linha selecionada ainda n"+atilde+"o foi salva. Salve-a primeiro!");
addMessage("requiredFields","pt-br", "Informe um valor para ");
addMessage("mustUseLov","pt-br", "Voc"+ecirc+" deve utilizar a lista de valores para preencher ou limpar esse campo.");
addMessage("mustUseLovNewRow","pt-br", "Voc"+ecirc+" deve utilizar a lista de valores para preencher ou limpar esse campo.");
addMessage("searchCriterium","pt-br", "Especifique um crit"+eacute+"rio de pesquisa com pelo menos {0} caracteres.");

addMessage("hasChanged","ro", "Pagina pe care o p"+ahorns+"r"+ahorns+"si"+tcedil+"i con"+tcedil+"ine modific"+ahorns+"ri nesalvate, care se vor pierde dac"+ahorns+" continua"+tcedil+"i.\nDori"+tcedil+"i s"+ahorns+" p"+ahorns+"r"+ahorns+"si"+tcedil+"i pagina?");
addMessage("noRowsSelected","ro", "V"+ahorns+" rug"+ahorns+"m selecta"+tcedil+"i mai "+icirc+"nt"+acirc+"i o "+icirc+"nregistrare!");
addMessage("newRowSelected","ro", Icirc+"nregistrarea selectat"+ahorns+" nu a fost "+icirc+"nc"+ahorns+" salvat"+ahorns+". V"+ahorns+" rug"+ahorns+"m salva"+tcedil+"i mai "+icirc+"nt"+acirc+"i!");
addMessage("requiredFields","ro", "V"+ahorns+" rug"+ahorns+"m introduce"+tcedil+"i o valoare pentru ");
addMessage("mustUseLov","ro", "Folosi"+tcedil+"i lista de valori pentru a completa sau "+scedil+"terge acest c"+acirc+"mp.");
addMessage("mustUseLovNewRow","ro", "Folosi"+tcedil+"i lista de valori pentru a completa acest c"+acirc+"mp "+icirc+"ntr-o nou"+ahorns+" "+icirc+"nregistrare.");
addMessage("searchCriterium","ro", "V"+ahorns+" rug"+ahorns+"m specifica"+tcedil+"i un criteriu de c"+ahorns+"utare de cel pu"+tcedil+"in {0} caractere.");

addMessage("hasChanged","sl",  "Na strani so nepotrjene spremembe, ki bodo ob nadaljevanju izgubljene.\nAli nadaljujem s prikazom nove strani?");
addMessage("noRowsSelected","sl",  "Najprej ozna"+Ysccl+"ite vrstico, prosim!");
addMessage("newRowSelected","sl", "Izbran zapis ni shranjen. Prosim, najprej shranite spremembe!");
addMessage("requiredFields","sl", "Vnesite vrednost polja ");
addMessage("mustUseLov","sl", "Za spremembo podatka uporabite listo vrednosti, prosim.");
addMessage("mustUseLovNewRow","sl", "Za spremembo podatka uporabite listo vrednosti, prosim.");
addMessage("searchCriterium","sl", "Za kriterij poizvedbe navedite najmanj {0} znakov.");

addMessage("hasChanged","sr",  "Na strani postoje izmene koje nisu sa"+Ysccl+"uvane, i biti "+Yscml+"e izgubljene ako nastavite.\nDa li ste sigurni da ho"+Yscml+"ete da nastavite?");
addMessage("noRowsSelected","sr",  "Molim vas da prvo ozna"+Ysccl+"ite zapis!");
addMessage("newRowSelected","sr", "Izabrani zapis nije sa"+Ysccl+"uvan. Prvo ga sa"+Ysccl+"uvajte!");
addMessage("requiredFields","sr", "Unesite vrednost polja ");
addMessage("mustUseLov","sr", "Za izmenu vrednosti u ovom polju, upotrebite listu vrednosti.");
addMessage("mustUseLovNewRow","sr", "Za izmenu vrednosti u ovom polju, upotrebite listu vrednosti.");
addMessage("searchCriterium","sr", "Molim specificirajte kriterij pretra"+Yszzl+"ivanja s najmanje {0} znakova.");

/*
 * The following private functions are used to put messages in a message stack and retrieve the
 * messages from that stack. They should not be touched
 */

function addMessage(code,locale,text)
{
  allMessages[code+"#"+locale] = text;
}

function retrieveMessage(code,locale)
{
  return allMessages[code+"#"+locale];
}



/*
 * Function getLocaleMessage is a "private" function, called from
 * "public" function getMessage
 */

function getLocaleMessage(code,params)
{

  /* S. Davelaar 04-03-2004: we now set the browserLocale variable in
      the page, so it is the same locale as at the server side (the true
      browser locale instead of the windows OS locale!). 
      The code below gets the locale of windows machine */

  /* determine the locale if var not set in page */
  var locale = browserLocale;

  if (locale==null) locale=window.navigator.userLanguage;
  if (locale==null) locale=window.navigator.language;
  locale=locale.toLowerCase();
  //alert ("locale="+locale);
  /* retrieve the message in the user's locale, using language AND country.
     If the message exists, but not in the user's language and country,
     try with just the language
  */
  var countryMsg = retrieveMessage(code,locale);
  //alert ("countryMsg="+countryMsg);
  var languageMsg = (countryMsg != null)?countryMsg:retrieveMessage(code,locale.substring(0,2));
  //alert("languageMsg="+languageMsg);
  /* If the message exists, but not in the user's language, use the default locale
     specified above
  */
  var resultmsg = (languageMsg != null)?languageMsg:retrieveMessage(code,defaultlocale);
  //alert("resultmsg="+resultmsg);
  /* if the message does not exist at all, return an error message */
  if (resultmsg == null) return "Unknown message code: '"+code+"'";

  /* if substitution parameters were supplied, replace the corresponding
     parameters in the message.
  */
  if (params)
  {
    for (i=0;i<params.length;i++)
    {
      resultmsg = resultmsg.replace("{"+i+"}",params[i]);
    }
  }
  return resultmsg;
}



/*
 * The following public function is used to retrieve a message text,
 * based on the message code and (optionally) a list of substitution
 * values.
 *
 * Example usages:
 *
 *   alert(getMessage("mustUseLov"));
 *
 *   alert(getMessage("searchCriterium",["4"]));
 *
 *   alert(getMessage("roleAlreadyExists",["Consulting","Manager"]));
 */

function getMessage(code,params){

  return getLocaleMessage(code,params);

}
