# System obslugi biblioteki
**Programowanie zespołowe**


## Opis projektu  

Nasz system bedzie sluzyl do obslugi biblioteki. 
Na poczatek nowy uzytkownik nie posiadający konta bedzie mogl sie zarejestrowac w naszej aplikacji. Jesli poprawnie przejdzie przez proces rejestracji, będzie mógł się zalogować. Po zalogowaniu jako uzytkownik sytem bedzie udostepnial opcje takie jak:
- podgląd własnych wypożyczeń,
- generowanie pdf z wypożyczeniami,
- zapytanie o dostępność danej książki,
- aktualizacja danych własnego profilu.


Jesli zalogujemy sie do aplikacji jako bibliotekarz to bedziemy mieli opcje takie jak: 
- dodawanie, edytowanie oraz usuwanie książek w zbiorach,
- dodawanie oraz usuwanie wypożyczeń,
-edycja profilu.

Po zalogowaniu jako administrator:
- dodawanie użytkowników, blibliotekarzy, administraotrów,
- Edycja profili użytkowników, blibliotekarzy, administraotrów,
- edycja własnego profilu.

## Rodzaje użytkowników w aplikacji
W programie występują 3 rodzaje użytkowników,każdy posiada inne uprawnienia, stąd w ramach testów zostały utworzone trzy konta:
* administrator - login:"siwy", hasło:"start123", 
* bibliotekarz - login:"janek", hasło:"start123", 
* użytkownik - login:"michu", hasło:"start123", 

## Baza danych - dane
* tabela book - autor, tytuł, data wydania
* tabela orders - data od, data do, id książki, id czytelnika
* tabela user - imię, nazwisko, nr. tel, pesel, wiek, rola, login, hasło

## Wykorzystane technologie
* Java 8
* JavaFX
* Hibernate
* Maven

## Autorzy
* Paweł Dybowski
* Mateusz Siwiec
* Adrian Toczek
* Kamil Krzywonos
* Łukasz Ruszała

## Diagramy UML
* Diagram przypadków użycia

![Diagram przypadków użycia](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/UML/diagram%20przypadk%C3%B3w.jpg)

* Diagram klas

![Diagram klas](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/UML/diagramklasv2.JPG)

* Diagram sekwencji

![Diagram klas](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/UML/sekwencji.JPG)


## Omówienie programu

Tworząc program postawiliśmy na funkcjonalność, stąd oprawa graficzna jest prosta ażeby nie rozpraszała osoby użytkującej program. Wszystkie opisy są w języku angielskim.

Początkowym oknem jest ekran logowania. Tutaj wpisujemy swoje dane logowania.

![Logowanie](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/logowanie.PNG)

Gdy nie posiadamy konta w programie i chcielibyśmy zostać nowym użytkownikiem, mamy możliwość to zrobić za pomocą formularza Register.

![Rejestracja](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/rejestracja.PNG)

Po popranym zalogowaniu (ewentualnej rejestracji i zalogowaniu) pojawia na się panel główny. Dla każdej grupy jest on inny i posiada inne opcje.

* Administrator

![Admin1](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/admin1.PNG)
![Admin2](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/admin2.PNG)
![Admin3](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/admin3.PNG)
![Admin4](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/admin4.PNG)

* Bibliotekarz

![Blibliotekarz1](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/bibliotekarz1.PNG)
![Blibliotekarz2](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/bibliotekarz2.PNG)
![Blibliotekarz3](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/bibliotekarz3.PNG)
![Blibliotekarz4](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/bibliotekarz4.PNG)

* Użytkownik

![Użytkownik1](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/user1.PNG)
![Użytkownik2](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/user2.PNG)
![Użytkownik3](https://github.com/mateusz-siwiec/Library-managament---Inf-III-zaoczne-2019/blob/master/images/user3.PNG)







