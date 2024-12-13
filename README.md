# Browse Our Parents API

## Descrizione

L'API "Browse Our Parents" è un servizio RESTful progettato per gestire informazioni su specie, generi, famiglie, ordini, classi e regni nel contesto della biodiversità. L'API consente agli utenti di eseguire operazioni CRUD (Create, Read, Update, Delete) su queste entità e di gestire gli utenti e le loro preferenze.

## Tecnologie Utilizzate

- **Java**: Linguaggio di programmazione principale.
- **Spring Boot**: Framework per la creazione di applicazioni Java.
- **PostgreSQL**: Database relazionale per la memorizzazione dei dati.
- **JWT (JSON Web Token)**: Per l'autenticazione e l'autorizzazione degli utenti.
- **Cloudinary**: Per la gestione delle immagini.

## Endpoint Principali

### Specie

- `GET /specie`: Recupera tutte le specie.
- `GET /specie/{specieId}`: Recupera una specie specifica per ID.
- `POST /specie`: Crea una nuova specie (solo per ADMIN).
- `PUT /specie/{specieId}`: Aggiorna una specie esistente (solo per ADMIN).
- `DELETE /specie/{specieId}`: Elimina una specie (solo per ADMIN).
- `GET /specie/nomeQuery`: Trova specie per nome.
- `GET /specie/descQuery`: Trova specie per descrizione.
- `GET /specie/storiaQuery`: Trova specie per storia.
- `GET /specie/fenotipoQuery`: Trova specie per fenotipo.

### Utenti

- `GET /user`: Recupera tutti gli utenti.
- `GET /user/{userId}`: Recupera un utente specifico per ID.
- `POST /auth/register`: Registra un nuovo utente.
- `POST /auth/login`: Effettua il login e restituisce un token JWT.
- `PATCH /user/me/avatar`: Carica un avatar per l'utente autenticato.
- `DELETE /user/me`: Elimina l'utente autenticato.

## Autenticazione

L'API utilizza **JWT** per l'autenticazione. Gli utenti devono fornire un token valido nell'intestazione **Authorization** per accedere a endpoint protetti.

## Esempi di Richiesta

### Creare una Nuova Specie

POST /specie
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "Nome Comune",
  "nome_comune": "Nome Comune",
  "nome_scientifico": "Nome Scientifico",
  "img": "http://example.com/image.jpg",
  "descrizione": "Descrizione della specie",
  "storia": "Storia della specie",
  "anno_di_classificazione": 2023,
  "fenotipo_id": "fenotipoId",
  "esemplari_rimasti": 100,
  "genere_id": "genereId"
}

### Effettuare il Login

POST /auth/login
Content-Type: application/json

{
  "username": "tuo_username",
  "password": "tua_password"
}

## Configurazione

1. Clona il repository:

   git clone https://github.com/bip.vi/BrowseOurParents-BE.git

2. Configura il file `application.properties` con le credenziali del database e le impostazioni di Cloudinary.

3. Esegui l'applicazione con Maven:

   ./mvnw spring-boot:run

4. L'applicazione sarà disponibile su `http://localhost:3001`.

## Contributi

Se desideri contribuire a questo progetto, sentiti libero di aprire un problema o inviare una richiesta di pull.

## Licenza

Questo progetto è concesso in licenza sotto la Licenza **Apache 2.0**. Vedi il file [LICENSE](LICENSE) per ulteriori dettagli.

## Contatti

Per domande o suggerimenti, puoi contattare [costantini062@gmail.com].
