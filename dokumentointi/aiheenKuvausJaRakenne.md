# Aihemäärittely

### Aihe: kustannustentasausjärjestelmä

Toteutetaan järjestelmä, jonka avulla ryhmä ihmisiä pystyy helposti jakamaan esim. yhteisen lomamatkan tai ravintolaillan kustannukset keskenään tasan. Järjestelmään pystyy lisäämään kustannuskokonaisuuksia (maksuryhmiä), käyttäjiä ja maksutapahtumia. Käyttäjät kuuluvat maksuryhmään/-ryhmiin. Maksutapahtumalle määritellään maksaja/maksajat ja rahamäärä. Yksittäinen käyttäjä voidaan myös merkitä maksutapahtumasta poissaolevaksi (esim. kalliin viinin väliin jättänyt ravintolaseurueen jäsen). Kun kustannuskokonaisuus merkitään päättyneeksi, järjestelmä ilmoittaa kuinka paljon kukin käyttäjä on toisilleen velkaa.

### Käyttäjät: kaikki, varsinainen käyttö vaatii rekisteröitymisen 

### Kaikkien käyttäjien toiminnot
* käyttäjän kirjautuminen
** onnistuu jos tunnus olemassa ja salasana oikein
* käyttäjän rekisteröinti

### Rekisteröityneiden käyttäjien toiminnot
* kustannuskokonaisuuden luominen
* käyttäjän lisääminen kustannuskokonaisuuteen
* maksutapahtuman lisääminen
** vaaditut tiedot: maksaneet käyttäjät, rahamäärä, poissaolevat käyttäjät
* kustannuskokonaisuuden päättäminen
** luo velkaraportti

### Rakenne
* kirjautumisikkuna (LoginGUI) tarkistaa käyttäjätiedot käyttäjätiedostoa lukevalta luokalta (UserListFile)
* onnistunut kirjautuminen avaa maksuryhmänhallintaliittymän (PaymentGUI)
* maksuryhmänhallintaliittymä käsittelee maksuryhmää (CostGroup) ja listaa henkilöitä (Person)
* maksuryhmä koostuu henkilöistä (Person) ja maksutapahtumista (Payment)
* maksutapahtumaan liittyy maksaja (Person) sekä lista maksusta hyötyneistä (Person)
* henkilöt voivat olla toisilleen (Person) velkaa tai heillä on saatavia
* maksuryhmässä tapahtuneet muutokset tallentuvat tilannetietoihin (StatusFile), jotka ladataan mahdollisen uudelleenkäynnistyksen yhteydessä