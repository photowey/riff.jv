# `Authentication`

## 1.`Dependency`

```http
https://central.sonatype.com/artifact/io.jsonwebtoken/jjwt
```

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.12.6</version>
</dependency>
```

## `2.Authentication`

### 2.1.`Client`

#### 2.1.1.`Web`

- `Scope`
  - `web`

#### `2.1.2.Schedule app`

- `Scope`
  - `oauth`
    - `schedule app`

### 2.2.`Type`

#### 2.2.1.`Bearer`

- `Username`
- `Password`



#### 2.2.2.`OAuth`

- `AccessKey`
- `AccessSecret`



### 2.2.`Model`

#### 2.2.1.`Request`

##### 2.2.1.1.`web`

> `Options`
>
> - `captchaId`
> - `captcha`

```json
{
  "username":"admin",
  "password": "itsasecret",
  "rememberMe": true,
  "captchaId":"63222855815719188245190111831234", 
  "captcha" :"9527" 
}
```


- `2fa`
  - `enabled`

```json
{
  "twofaSecret": "996000"
}
```

```shell
-H "Authorization:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"
```



##### 2.2.1.2.`OAuth`

```json
{
  "accessKey":"70202507155719188245190111831234",
  "accessSecret": "71202507155719188245190187654321"
}
```



#### 2.2.2.`Response`

```http
https://jwt.io
```

##### 2.2.2.1.`Bearer`

- `Token`
  - `7 days`
- `RefreshToken`
  - `30 days`

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30",
  "type": "Bearer",
  "issuedAt": "1752563363000",
  "expiresIn ": "604800",
  "refreshToken": {
    "enabled": 1,
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30",
    "type": "Bearer",
    "issuedAt": "1752563363000",
    "expiresIn ": "2592000"
  }
}
```

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30",
  "type": "Bearer",
  "issuedAt": "1752563363000",
  "expiresIn ": "604800",
  "refreshToken": {
    "enabled": 0,
    "token": "*",
    "type": "Bearer",
    "issuedAt": "1752563363000",
    "expiresIn ": "-1"
  }
}
```

##### 2.2.2.2.`OAuth`

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30",
  "type": "OAuth",
  "issuedAt": "1752563363000",
  "expiresIn ": "604800",
  "refreshToken": {
    "enabled": 1,
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30",
    "type": "OAuth",
    "issuedAt": "1752563363000",
    "expiresIn ": "2592000"
  }
}
```

