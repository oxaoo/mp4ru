# com.github.oxaoo.mp4ru
MaltParser for Russian

## What is com.github.oxaoo.mp4ru?
The com.github.oxaoo.mp4ru is a parser for Russian language. 
The parser is based on [MaltParser](http://www.maltparser.org/).

## How it works?
At first it is necessary to build the cloned project with maven:
```code
mvn clean install
```
To run the program on the command line, execute the command:
```code
java -jar com.github.oxaoo.mp4ru-0.1.0.jar -cm res/russian-utf8.par -tt res/ -pc res/russian.mco -tf res/text.txt
```
Where *res* is a directory which contains resource files necessary for the parser.  
* The argument *-cm* points to the path of classifier model for morphological analysis. 
The russian-utf8.par a simple trained tagset which can be downloaded [here](http://corpus.leeds.ac.uk/mocky/russian.par.gz). 
And russian-big-utf8.par the expanded trained tagset available to this 
[address](http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/russian-par-linux-3.2-utf8.bin.gz).
The author of both tagsets is [Serge Sharoff](http://corpus.leeds.ac.uk/mocky/)  
* The argument *-tt* points to the directory of executable module TreeTagger which is performs the part-of-speech tagging of the text.
The TreeTagger can be downloaded to the following address: [TreeTagger v.3.2](http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/) 
* The argument *-pc* points to the home directory of parser configuration which can be download [here](http://corpus.leeds.ac.uk/tools/russian.mco)
* The argument *-tf* points to the text file path for parsing 
The result of parsing is presented in the same file with the extension **.parse**.  

For help use the following command:
```code
java -jar com.github.oxaoo.mp4ru-0.1.0.jar -h
```
For the correct program execution, the contents of the resource directory should look like this:
```code
\---res
    |   russian-utf8.par
    |   russian.mco
    |   text.parse
    |
    \---bin
            tree-tagger.exe
```