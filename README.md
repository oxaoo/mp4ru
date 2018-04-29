# mp4ru
MaltParser for Russian  
_Описание проекта [на русском](README_ru.md)_

## What is mp4ru?
The mp4ru is a parser for Russian language. 
The parser is based on the [MaltParser](http://www.maltparser.org/).

## How it works?
There are four options for using the mp4ru:
* mp4ru as [docker](https://docs.docker.com/install/) container
* mp4ru as service
* mp4ru as application
* mp4ru as library

### mp4ru as docker container
For download the latest docker image from Docker Hub need to execute following command:
```
docker pull oxaoo/mp4ru-service
```
And run the pulled mp4ru-service:
```
docker run -d --name mp4ru-service -p 2000:2000 oxaoo/mp4ru-service
```
The docker container will be available on the `http://localhost:2000`.  
After that it is possible to parse text. The mp4ru-service is support next HTTP methods:
```
GET http://localhost:2000/parse HTTP/1.1
POST http://localhost:2000/parse HTTP/1.1
```

Request examples using curl:
```
curl -X GET 'http://localhost:2000/parse?text=%D0%9F%D1%80%D0%B8%D0%B2%D0%B5%D1%82%2C%20%D0%BA%D0%B0%D0%BA%20%D0%B4%D0%B5%D0%BB%D0%B0%3F'

curl -X POST http://localhost:2000/parse -d 'Привет, как дела?'
```
Also there is a [postman](https://www.getpostman.com/) collection which is presented in the folder `postman-collection/` it contains request examples.

### mp4ru as service
If the docker isn't installed or there is no way to do it, then it is possible to use mp4ru as service.  
For this purpose it is necessary to have at least [JRE](http://www.oracle.com/technetwork/java/javase/downloads/index.html), 
[maven](https://maven.apache.org/download.cgi) 
and have additional resources that are available [here](https://goo.gl/ThapRm).  
At first it is necessary to build the cloned project with maven. 
Select the root directory `mp4ru/` and execute command `mvn clean install`.  
Then the mp4ru-service **jar** will appear on the following path: `mp4ru/mp4ru-service/target/`.  
The downloaded resources need to unzip and put near the jar.  
The contents of the directory should look something like this:
```
mp4ru\mp4ru-service\target
│   mp4ru-service-1.0.0.jar
│                   
└───res
    │   russian-utf8.par
    │   russian.mco
    │   
    └───bin
            tree-tagger     <-- this is necessary for linux
            tree-tagger.exe <-- this is necessary for windows
```

Now to run the service need to execute the following instruction on the command line:
```
java -jar mp4ru-service-1.0.0.jar
```
After that it is possible to parse text using HTTP methods how it described in previous section.

### mp4ru as application
This way is suitable for one-time parsing. For it also need to have (as in the previous section) the JRE, maven and resources.  
Select the subdirectory `mp4ru/mp4ru/` and build `mvn clean install`.  
After that the mp4ru **jar** will appear on the following path: `mp4ru/mp4ru/target/`.  
The downloaded resources need to unzip and put near the jar.  
The contents of the directory should look something like this:
```
mp4ru\mp4ru\target
│   mp4ru-1.0.0.jar
│                   
├───res
    │   russian-utf8.par
    │   russian.mco
    │   
    └───bin
            tree-tagger     <-- this is necessary for linux
            tree-tagger.exe <-- this is necessary for windows
```
Now to run the application and parse text need to execute the following instruction on the command line:
```
java -jar mp4ru-1.0.0.jar -cm res/russian-utf8.par -tt res/ -pc res/russian.mco -tf res/text.txt
```
The result of parsing will be in the `res/text.txt`.  
For help use the following command:
```
java -jar mp4ru-1.0.0.jar -h
```


### mp4ru as library
The mp4ru can also be used as a library. For it need to select the subdirectory `mp4ru/mp4ru/` and build `mvn clean install`.  
After that the mp4ru **jar** will appear on the following path: `mp4ru/mp4ru/target/`.  
Now need to add the artifact to the local maven repository (instruction depends on version):
```
mvn install:install-file -DgeneratePom=true -Dpackaging=jar -DgroupId=com.github.oxaoo -DartifactId=mp4ru -Dversion=1.0.0 -Dfile=mp4ru-1.0.0.jar
```
After that, the artifact can be used in the project, adding the following dependencies to the pom:
```xml
<dependency>
    <groupId>com.github.oxaoo</groupId>
    <artifactId>mp4ru</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Resources
Resources can be downloaded in one file from [here](https://goo.gl/ThapRm).
This archive contains resource files necessary for the parser.  
* The classifier model for morphological analysis. 
The russian-utf8.par a simple trained tagset which can be download [here](http://corpus.leeds.ac.uk/mocky/russian.par.gz). 
And russian-big-utf8.par the expanded trained tagset available to this 
[address](http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/russian-par-linux-3.2-utf8.bin.gz).
The author of both tagsets is [Serge Sharoff](http://corpus.leeds.ac.uk/mocky/)  
* The executable module TreeTagger which is performs the part-of-speech tagging of the text.
The TreeTagger can be downloaded from the following address: [TreeTagger v.3.2](http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/) 
* The file russian.mco is the parser configuration that can be download [here](http://corpus.leeds.ac.uk/tools/russian.mco), 
which is the authorship of [Marilena Di Bari](http://corpus.leeds.ac.uk/marilena/).
