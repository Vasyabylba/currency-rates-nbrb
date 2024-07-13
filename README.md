<h1 align="center">CURRENCY RATES</h1>

## Description

Service for obtaining and displaying currency exchange rates from the NB RB website
(API of the national bank: https://www.nbrb.by/apihelp/exrates).

## Technical stack

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Web MVC
* Spring Cloud OpenFeign
* Spring Boot Test
* H2 Database
* JUnit 5
* Swagger (OpenAPI 3.0)

## Install

<ul>
<li><b>Clone the repository:</b></li>

```bash
git clone https://github.com/vasyabylba/currency-rates-nbrb.git
cd currency-rates-nbrb
```
<li><b>Build and run the project:</b></li>
<ul>
  <li>Windows</li>

```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```
  <li>Unix system</li>

```bash
./mvnw clean install
./mvnw spring-boot:run
```
</ul>
</ul>


## Usage

### API

API specification (Swagger) is available at:  http://localhost:8080/swagger-ui/index.html

| Method | URI                     | Description                     |
|--------|-------------------------|---------------------------------|
| `POST` | `/api/v1/rates`         | Save currency rates information |
| `GET`  | `/api/v1/rates/{curId}` | Get currency rate by code       |

#### `POST` /api/v1/rates

**Parameters:**

* `date` - The date on which the rates will be saved to the database. 
If not specified, the rates for today will be saved.

**Example API Requests:**

```sh
curl -X 'POST' \
  'http://localhost:8080/api/v1/rates' \
  -H 'accept: application/json' \
  -d ''
```

```sh
curl -X 'POST' \
  'http://localhost:8080/api/v1/rates?date=2024-01-01' \
  -H 'accept: application/json' \
  -d ''
```

#### `GET` /api/v1/rates/{curId}

**Parameters:**

* `curId` - Currency code
* `date` - The date for which the rate is requested. If not specified, the rate for today is returned
* `curmode` - Currency code format : 0 - internal currency code, 
1 - three-digit numeric currency code according to ISO 4217 standard, 
2 - three-digit alphabetic currency code (ISO 4217). Default value : 0

When using an alphabetic or numeric currency code (ISO 4217), consider its value on the date requested.

**Example API Requests:**

```bash
curl -X 'GET' \
  'http://localhost:8080/api/v1/rates/431?date=2024-01-01' \
  -H 'accept: application/json'
```

```bash
curl -X 'GET' \
  'http://localhost:8080/api/v1/rates/840?date=2024-01-01&curmode=1' \
  -H 'accept: application/json'
```

```bash
curl -X 'GET' \
  'http://localhost:8080/api/v1/rates/USD?date=2024-01-01&curmode=2' \
  -H 'accept: application/json' 
```

### H2 Console

H2 Console is available at: http://localhost:8080/h2-console

The connection parameters to the H2 database are already configured in the file: `src/main/resources/application.yml`

Use the following parameters to connect:

```
JDBC URL: jdbc:h2:mem:currency_rates
User Name: root
Password:
```