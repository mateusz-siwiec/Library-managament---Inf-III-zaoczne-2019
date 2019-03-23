# Library-managament---Inf-III-zaoczne-2019
Programowanie zespołowe

System obslugi biblioteki

##Opis projektu  

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