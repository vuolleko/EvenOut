# Aihemäärittely

### Aihe: kustannustentasausjärjestelmä

Toteutetaan järjestelmä, jonka avulla ryhmä ihmisiä pystyy helposti jakamaan esim. yhteisen lomamatkan tai ravintolaillan kustannukset keskenään tasan. Järjestelmään pystyy lisäämään kustannuskokonaisuuksia (ryhmiä), käyttäjiä ja maksutapahtumia. Käyttäjät kuuluvat ryhmään/ryhmiin. Maksutapahtumalle määritellään maksaja/maksajat ja rahamäärä (ja valuutta). Yksittäinen käyttäjä voidaan myös merkitä maksutapahtumasta poissaolevaksi (esim. kalliin viinin väliin jättänyt ravintolaseurueen jäsen). Kun kustannuskokonaisuus merkitään päättyneeksi, järjestelmä ilmoittaa kuinka paljon kukin käyttäjä on toisilleen velkaa.

### Käyttäjät: kaikki, varsinainen käyttö vaatii rekisteröitymisen 

### Kaikkien käyttäjien toiminnot
* käyttäjän kirjautuminen
** onnistuu jos tunnus olemassa ja salasana oikein
* käyttäjän rekisteröinti

### Rekisteröityneiden käyttäjien toiminnot
* kustannuskokonaisuuden luominen
* käyttäjän lisääminen kustannuskokonaisuuteen
** onnistuu jos käyttäjä olemassa
* maksutapahtuman lisääminen
** vaaditut tiedot: maksaneet käyttäjät, rahamäärä (valuutta), poissaolevat käyttäjät
* kustannuskokonaisuuden päättäminen
** luo velkaraportti
