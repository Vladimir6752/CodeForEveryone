# CodeForEveryone
Pet project of the site's backend for writing and executing code directly in the browser in its own programming language.
<p>The code interpretation module is covered by more than 90% unit tests
The code is located on the server in the form of projects that are convenient to use from the browser. 
The code can also be written in different languages. The programming language itself is intended for the initial study of basic concepts. 
The project has a microservice architecture and will be deployed in docker containers on a remote server in the future.
At the moment, the project is under active development. Below are already implemented and tested sample lines of code:</p>

# Simple check 2 arrays on equals
<p>Provided that the length of the arrays is less than 100, if the arrays are the same, true is output, otherwise false</p>

```
Число [] первыйМассив ;
первыйМассив::добавитьЭл(3) ;
первыйМассив::добавитьЭл(4) ;
первыйМассив::добавитьЭл(5) ;

Число [] второйМассив ;

второйМассив::добавитьЭл(3) ;
второйМассив::добавитьЭл(4) ;
второйМассив::добавитьЭл(5) ;

Число счетчик ;

Если первыйМассив::длина != второйМассив::длина {
    счетчик = 100 ;
    Консоль ложь ;
}

Пока счетчик < первыйМассив::длина {
    Число элементПервогоМассива = первыйМассив::получитьЭл(счетчик) ;
    Число элементВторогоМассива = второйМассив::получитьЭл(счетчик) ;

    счетчик = счетчик + 1 ;

    Если элементПервогоМассива != элементВторогоМассива {
        счетчик = 100 ;
        Консоль ложь ;
    }
}

Если счетчик != 100 {
    Консоль истина ;
}
```
