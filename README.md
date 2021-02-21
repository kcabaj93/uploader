# uploader

## Wymagania:
- Java 11
- połączenie do bazy danych (dane w pliku config.propertis)

### Tabele w bazie danych
- customers
    - id int(11)
    - name VARCHAR(50)
    - surname VARCHAR(50)
    - age int(11)
- contacts
    - id int(11)
    - id_customer(11)
    - type int(11)
    - contact text