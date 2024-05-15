This challenge is to build your own JSON parser.

Building a JSON parser is an easy way to learn about parsing techniques which are useful for everything from parsing simple data formats through to building a fully featured compiler for a programming language.

Parsing is often broken up into two stages: lexical analysis and syntactic analysis. Lexical analysis is the process of dividing a sequence of characters into meaningful chunks, called tokens. Syntactic analysis (which is also sometimes referred to as parsing) is the process of analysing the list of tokens to match it to a formal grammar.

You can read far more about building lexers, parses and compilers in what is regarded as the definitive book on compilers: Compilers: Principles, Techniques, and Tools - widely known as the “Dragon Book” (because there’s an illustration of a dragon on the cover).



{
"key1": true,
"key2": false,
"key3": null,
"key4": "value",
"key5": 101
}
this is a valid json file

{
"key": "value",
"key-n": 101,
"key-o": {
"inner key": "inner value"
},
"key-l": ['list value']
}
this is not a valid json file as String are quoted by "" not ''