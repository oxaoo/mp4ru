# mp4ru
MaltParser для русского языка

## mp4ru, что это такое?
mp4ru это парсер для русского языка. 
Реализация парсера основана на [MaltParser](http://www.maltparser.org/).

## Как это работает?
Имеется четырне варианта использования mp4ru:
* mp4ru как [docker](https://docs.docker.com/install/) контейнер
* mp4ru как сервис
* mp4ru как приложение
* mp4ru как библиотека

### mp4ru как docker контрейнер
Для загрузки последней версии docker образа из Docker Hub необходимо выполнить следующую команду:
```
docker pull oxaoo/mp4ru-service
```
И запустить загруженный mp4ru-service:
```
docker run -d --name mp4ru-service -p 2000:2000 oxaoo/mp4ru-service
```
Docker контейнер будет доступен для запросов по адресу `http://localhost:2000`.  
После чего можно выполнять парсинг текста. mp4ru-service поддерживает следующие HTTP методы:
```
GET http://localhost:2000/parse HTTP/1.1
POST http://localhost:2000/parse HTTP/1.1
```
Примеры запросов с помощью curl:
```
curl -X GET 'http://localhost:2000/parse?text=%D0%9F%D1%80%D0%B8%D0%B2%D0%B5%D1%82%2C%20%D0%BA%D0%B0%D0%BA%20%D0%B4%D0%B5%D0%BB%D0%B0%3F'

curl -X POST http://localhost:2000/parse -d 'Привет, как дела?'
```
Также в проекте имеется [postman](https://www.getpostman.com/) коллекция, содержащая примеры запросов. Коллекция расположена в папке `postman-collection/`.

### mp4ru как сервис
Если на локальной машине не установлен docker и нет возможности это сделать, то можно использовать mp4ru как сервис.  
Для этого необходимо иметь как минимум [JRE](http://www.oracle.com/technetwork/java/javase/downloads/index.html), 
[maven](https://maven.apache.org/download.cgi) 
и дополнительные файлы ресурсов используемые для парсера, которые доступны для загрузки по [этому адресу](https://goo.gl/ThapRm).  
Сначала необходимо собрать склонированный проект с помощью maven.
Выберите корневую директорию `mp4ru/` и выполните команду `mvn clean install`.  
Затем, по следующему пути `mp4ru/mp4ru-service/target/` будет доступен **jar** файл mp4ru-service.  
Загруженные файлы ресурсов необходимо разархивировать и разместить рядом с jar файлом.  
Содержимое директории должно выглядить, примерно, следующим образом: 
```
mp4ru\mp4ru-service\target
│   mp4ru-service-1.0.0.jar
│                   
└───res
    │   russian-utf8.par
    │   russian.mco
    │   
    └───bin
            tree-tagger     <-- файл необходимый для выполнения на linux
            tree-tagger.exe <-- файл необходимый для выполнения на windows
```

Для того чтобы запустить сервис, необходимо выполнить следующую инструкции в командной строке:
```
java -jar mp4ru-service-1.0.0.jar
```
После этого можно выполнять разбор текста, используя HTTP методы, которые были описаны в предыдущем разделе.

### mp4ru как приложение
Данный способ актуален для одноразового парсинга. Для этого необходимо иметь (как и в предыдущем разделе) JRE, maven и файлы ресурсов.   
Необходимо выбрать поддиректорию `mp4ru/mp4ru/` и собрать проект с помощью команды `mvn clean install`.  
После чего mp4ru **jar** файл будет доступен по следующему пути: `mp4ru/mp4ru/target/`.  
Загруженные файлы ресурсов необходимо разархивировать и разместить рядом с jar файлом.  
Содержимое директории должно выглядить, примерно, следующим образом: 
```
mp4ru\mp4ru\target
│   mp4ru-1.0.0.jar
│                   
├───res
    │   russian-utf8.par
    │   russian.mco
    │   
    └───bin
            tree-tagger     <-- файл необходимый для выполнения на linux
            tree-tagger.exe <-- файл необходимый для выполнения на windows
```
Теперь, для запуска приложения и парсинга текста необходимо выполнить следующую инструкцию в командной строке:
```
java -jar mp4ru-1.0.0.jar -cm res/russian-utf8.par -tt res/ -pc res/russian.mco -tf res/text.txt
```
Результат парсинга будет доступен в файле `res/text.txt`.  
Для вызова справки, используйте следующую команду:
```
java -jar mp4ru-1.0.0.jar -h
```


### mp4ru как библиотека
mp4ru может также использоваться как библиотека. Для этого необходимо выбрать поддиректорию `mp4ru/mp4ru/` и собрать проект `mvn clean install`.  
После этого mp4ru **jar** будет доступен по следующему адресу: `mp4ru/mp4ru/target/`.  
Далее необходимо добавить артефакт в локальный репозиторий maven (необходимо скорректировать команду в зависимости от версии):
```
mvn install:install-file -DgeneratePom=true -Dpackaging=jar -DgroupId=com.github.oxaoo -DartifactId=mp4ru -Dversion=1.0.0 -Dfile=mp4ru-1.0.0.jar
```
Теперь, артефакт может быть использоваться в проекте, для этого достаточно добавить зависимость на библиотеку в pom:
```xml
<dependency>
    <groupId>com.github.oxaoo</groupId>
    <artifactId>mp4ru</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Файлы ресурсов
Файлы ресурсов могут быть загружены одним архивом по [этой ссылке](https://goo.gl/ThapRm).
Этот архив содержит необходимые файлы для парсинга: 
* Модель классификатора для морфологического анализа.
Файл russian-utf8.par содержит обучаемый набор тегов, который можно скачать отдельно по [следующему адресу](http://corpus.leeds.ac.uk/mocky/russian.par.gz). 
Файл russian-big-utf8.par содержит расширенный обучаемый набор тегов, доступный по 
[адресу](http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/data/russian-par-linux-3.2-utf8.bin.gz).
Автор обоих наборов тегов [Сергей Шаров](http://corpus.leeds.ac.uk/mocky/)  
* Исполняемый файл TreeTagger, выполняющий частеречную разметку текста.
TreeTagger может быть загружен по следующему адресу: [TreeTagger v.3.2](http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/) 
* Файл конфигурации парсера russian.mco, доступный для загрузки по [адресу](http://corpus.leeds.ac.uk/tools/russian.mco), 
автором данной работы является [Марилена Ди Бари](http://corpus.leeds.ac.uk/marilena/).
