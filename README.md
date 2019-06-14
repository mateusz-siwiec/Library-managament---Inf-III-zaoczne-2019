# System obslugi biblioteki
**Programowanie zespołowe**


## Opis projektu  

Nasz system bedzie sluzyl do obslugi biblioteki. 
Na poczatek nowy uzytkownik nie posiadający konta bedzie mogl sie zarejestrowac w naszej aplikacji. Jesli poprawnie przejdzie przez proces rejestracji, będzie mógł się zalogować. Po zalogowaniu jako uzytkownik sytem bedzie udostepnial opcje takie jak:
- wypozyczanie ksiazek
- podglad wszystkich ksiazek dostepnych w bibliotece
- sprawdzanie statystyk dla swojego konta ( ilosc swoich wypozyczen, kary, oraz szczeg?y dotyczace kazdego wypozyczenia)

Jesli zalogujemy sie do aplikacji jako bibliotekarz to bedziemy mieli opcje takie jak: 
- Operacje CRUD'owe na czytelnikach
- Operacje CRUD'owe na ksiazkach
- Nakladanie kary na czytelnika
- wszystkie, które posiada uzytkownik.

Po zalogowaniu jako administrator:
- dostęp do wszystkich opcji programu, wraz z opcjami z poziomu użytkownika i bibliotekarza.
- Operacje CRUD'owe na bibliotekarzach

## Rodzaje użytkowników w aplikacji
* Administrator 
* Bibliotekarz
* Czytelnik

## Baza danych - dane
* dane dot. książek - autor, tytuł, data wydania
* dane dot. wypożyczeń - data od, data do, jaka książka
* dane dot. użytkowników, bibliotekarza/y oraz użytkowników - imię, nazwisko, nr. tel, pesel

## Wykorzystane technologie
* JavaFX
* Spring Data
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

## Default users

W programie występują 3 rodzaje użytkowników,każdy posiada inne uprawnienia, stąd w ramach testów zostały utworzone trzy konta:
* administrator - login:"siwy", hasło:"start123", 
* bibliotekarz - login:"janek", hasło:"start123", 
* użytkownik - login:"michu", hasło:"start123", 


## Omówienie programu

Tworząc program postawiliśmy na funkcjonalność, stąd oprawa graficzna jest prosta ażeby nie rozpraszała osoby użytkującej program. Wszystkie opisy są w języku angielskim.

Początkowym oknem jest ekran logowania. Tutaj wpisujemy swój ek

